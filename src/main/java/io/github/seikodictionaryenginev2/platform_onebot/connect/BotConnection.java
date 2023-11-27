package io.github.seikodictionaryenginev2.platform_onebot.connect;

import com.alibaba.fastjson2.JSON;
import io.github.seikodictionaryenginev2.base.entity.DictionaryProject;
import io.github.seikodictionaryenginev2.base.env.DICList;
import io.github.seikodictionaryenginev2.platform_onebot.bean.GroupMember;
import io.github.seikodictionaryenginev2.platform_onebot.dic.runtime.GroupMessageRuntime;
import io.github.seikodictionaryenginev2.platform_onebot.event.EventSource;
import io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl.MessageEvent;
import io.github.seikodictionaryenginev2.platform_onebot.utils.BlockingLock;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

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
        APIResponse.UnknownTypeAPIResponse response = JSON.parseObject(message, APIResponse.UnknownTypeAPIResponse.class);
        if (response.getEcho() != null) {
            //响应Echo
            latchMap.get(response.getEcho()).release(response);
            return;
        }
        //响应event
        EventSource source = new EventSource(message);
        if (source.getEventType() == EventSource.Type.message) {
            System.out.println("[Client]:recv->" + source);
            if (source.transfer(MessageEvent.class).getMessage_type().equals("group")) {
                MessageEvent.GroupMessageEvent event = source.transfer(MessageEvent.GroupMessageEvent.class);

                DICList.INSTANCE.subFiles().forEach((v) -> {
                    GroupMessageRuntime runtime = new GroupMessageRuntime(v, event, this);
                    runtime.invoke(event.getMessage().contentToString());
                });
            }
        }
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
        sendMessage(request);
        lock.await();
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
