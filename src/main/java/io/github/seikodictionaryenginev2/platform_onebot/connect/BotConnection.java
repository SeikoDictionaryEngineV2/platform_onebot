package io.github.seikodictionaryenginev2.platform_onebot.connect;

import com.alibaba.fastjson2.JSON;
import io.github.seikodictionaryenginev2.base.env.DICList;
import io.github.seikodictionaryenginev2.platform_onebot.dic.runtime.impl.GroupMessageRuntime;
import io.github.seikodictionaryenginev2.platform_onebot.dic.runtime.impl.GroupNoticeRuntime;
import io.github.seikodictionaryenginev2.platform_onebot.dic.runtime.impl.PrivateMessageRuntime;
import io.github.seikodictionaryenginev2.platform_onebot.dic.runtime.impl.PrivateNoticeRuntime;
import io.github.seikodictionaryenginev2.platform_onebot.event.EventSource;
import io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl.MessageEvent;
import io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl.NoticeEvent;
import io.github.seikodictionaryenginev2.platform_onebot.utils.BlockingLock;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/27 下午1:58
 */
public class BotConnection extends WebSocketClient {
    private final Map<String, BlockingLock<APIResponse.UnknownTypeAPIResponse>> latchMap = new ConcurrentHashMap<>();

    public BotConnection(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshake) {

    }

    @Override
    public void onMessage(String message) {
        CompletableFuture.runAsync(() -> {
            APIResponse.UnknownTypeAPIResponse response = JSON.parseObject(message, APIResponse.UnknownTypeAPIResponse.class);
            if (response.getEcho() != null) {
                System.out.println("[Client]:recv->" + message);
                //响应Echo
                latchMap.get(response.getEcho()).release(response);
                return;
            }
            //响应event
            EventSource source = new EventSource(message);
            System.out.println("[Client]:recv->" + source);
            if (source.getEventType() == EventSource.Type.message) {

                switch (source.transfer(MessageEvent.class).getMessage_type()) {
                    case "group":
                        MessageEvent.GroupMessageEvent event = source.transfer(MessageEvent.GroupMessageEvent.class);
                        DICList.INSTANCE.subFiles().forEach((v) -> {
                            GroupMessageRuntime runtime = new GroupMessageRuntime(v, event, this);
                            runtime.invoke(event.getMessage().contentToString());
                        });
                        break;
                    case "private":
                        MessageEvent.PrivateMessageEvent event1 = source.transfer(MessageEvent.PrivateMessageEvent.class);
                        DICList.INSTANCE.subFiles().forEach((v) -> {
                            PrivateMessageRuntime runtime = new PrivateMessageRuntime(v, event1, this);
                            runtime.invoke(event1.getMessage().contentToString());
                        });
                        break;
                }
            }

            if (source.getEventType() == EventSource.Type.notice) {
                Object[] type = switch (source.transfer(NoticeEvent.class).getNotice_type()) {
                    case "group_recall" ->
                            new Object[]{"群撤回", true, NoticeEvent.GroupNoticeEvent.GroupMessageRecallEvent.class};
                    case "group_increase" ->
                            new Object[]{"群成员增加", true, NoticeEvent.GroupNoticeEvent.GroupMemberEnterEvent.class};
                    case "group_decrease" ->
                            new Object[]{"群成员减少", true, NoticeEvent.GroupNoticeEvent.GroupMemberLeaveEvent.class};
                    case "group_admin" ->
                            new Object[]{"群管理变动", true, NoticeEvent.GroupNoticeEvent.GroupAdminModifiedEvent.class};
                    case "group_upload" ->
                            new Object[]{"群文件上传", true, NoticeEvent.GroupNoticeEvent.GroupFileUploadEvent.class};
                    case "group_ban" ->
                            new Object[]{"群禁言", true, NoticeEvent.GroupNoticeEvent.GroupMemberBannedEvent.class};
                    case "group_card" ->
                            new Object[]{"群名片修改", true, NoticeEvent.GroupNoticeEvent.GroupMemberNameCardModifiedEvent.class};
                    case "essence" ->
                            new Object[]{"群精华", true, NoticeEvent.GroupNoticeEvent.GroupMessageEssenceEvent.class};
                    case "friend_recall" ->
                            new Object[]{"私聊撤回", false, NoticeEvent.PrivateNoticeEvent.FriendMessageRecallEvent.class};
                    case "private_upload" ->
                            new Object[]{"私聊文件上传", false, NoticeEvent.PrivateNoticeEvent.PrivateFileUploadEvent.class};
                    case "friend_add" ->
                            new Object[]{"好友添加", false, NoticeEvent.PrivateNoticeEvent.FriendAddEvent.class};
                    default ->
                            throw new IllegalStateException("Unexpected value: " + source.transfer(NoticeEvent.class).getNotice_type());
                };
                if (((boolean) type[1])) {
                    NoticeEvent.GroupNoticeEvent event1 = source.transfer(((Class<? extends NoticeEvent.GroupNoticeEvent>) type[2]));
                    DICList.INSTANCE.subFiles().forEach((v) -> {
                        GroupNoticeRuntime runtime = new GroupNoticeRuntime(v, event1, this);
                        runtime.invoke(type[0].toString());
                    });
                } else {
                    NoticeEvent.PrivateNoticeEvent event1 = source.transfer(NoticeEvent.PrivateNoticeEvent.class);
                    DICList.INSTANCE.subFiles().forEach((v) -> {
                        PrivateNoticeRuntime runtime = new PrivateNoticeRuntime(v, event1, this);
                        runtime.invoke(type[0].toString());
                    });
                }
            }
        }).exceptionally((t) -> {
            t.printStackTrace();
            return null;
        });
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.err.println("Close!" + code + ":" + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public void sendMessage(APIRequest<?> request) {
        sendMessageAsync(request);
    }

    public APIResponse.UnknownTypeAPIResponse sendMessageBlocking(APIRequest<?> request) throws InterruptedException {
        BlockingLock<APIResponse.UnknownTypeAPIResponse> lock = new BlockingLock<>();
        latchMap.put(request.getEcho(), lock);
        String packet = JSON.toJSONString(request);
        System.out.println("[Client]:send->" + packet);
        send(packet);
        lock.await();
        return latchMap.remove(request.getEcho()).getData();
    }

    public APIResponse.UnknownTypeAPIResponse sendMessageBlocking(APIRequest<?> request, int time, TimeUnit unit) throws InterruptedException, TimeoutException {
        BlockingLock<APIResponse.UnknownTypeAPIResponse> lock = new BlockingLock<>();
        latchMap.put(request.getEcho(), lock);
        String packet = JSON.toJSONString(request);
        System.out.println("[Client]:send->" + packet);
        send(packet);
        if (!lock.await(time, unit)) {
            throw new TimeoutException();
        }
        return latchMap.remove(request.getEcho()).getData();
    }

    public <T> CompletableFuture<APIResponse.UnknownTypeAPIResponse> sendMessageAsync(APIRequest<T> request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return sendMessageBlocking(request);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
