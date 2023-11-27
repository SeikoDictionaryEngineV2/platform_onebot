package io.github.seikodictionaryenginev2.platform_onebot.dic.runtime;

import com.alibaba.fastjson2.JSONObject;
import io.github.seikodictionaryenginev2.base.entity.DictionaryFile;
import io.github.seikodictionaryenginev2.base.session.BasicRuntime;
import io.github.seikodictionaryenginev2.platform_onebot.bean.GroupMember;
import io.github.seikodictionaryenginev2.platform_onebot.connect.APIRequest;
import io.github.seikodictionaryenginev2.platform_onebot.connect.BotConnection;
import io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl.MessageEvent;
import io.github.seikodictionaryenginev2.platform_onebot.message.MessageList;
import io.github.seikodictionaryenginev2.platform_onebot.message.SingleMessage;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/27 下午6:09
 */
public class GroupMessageRuntime extends BasicRuntime<MessageEvent.GroupMessageEvent, GroupMember, MessageList> {
    private final BotConnection connection;
    public GroupMessageRuntime(DictionaryFile file, MessageEvent.GroupMessageEvent groupMessageEvent,BotConnection connection) {
        super(file, groupMessageEvent);
        this.connection = connection;
    }

    @Override
    protected GroupMember initContact(MessageEvent.GroupMessageEvent EVENT) {
        return EVENT.getSender();
    }

    @Override
    protected MessageList initMessageCache() {
        return new MessageList();
    }

    @Override
    protected void clearMessage0(MessageList singleMessages) {
        connection.sendMessage(APIRequest.newRequest("send_group_msg",new JSONObject() {{
            put("group_id",getEvent().getGroup_id());
            put("message",singleMessages.transferToSend());
        }}));
    }

    @Override
    protected void appendMessage(String str) {
        getMessageCache().add(new SingleMessage.PlainText(str));
    }
}
