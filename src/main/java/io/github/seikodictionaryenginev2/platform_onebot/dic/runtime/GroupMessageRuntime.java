package io.github.seikodictionaryenginev2.platform_onebot.dic.runtime;

import com.alibaba.fastjson2.JSONObject;
import io.github.seikodictionaryenginev2.base.entity.DictionaryFile;
import io.github.seikodictionaryenginev2.platform_onebot.bean.GroupMember;
import io.github.seikodictionaryenginev2.platform_onebot.connect.APIRequest;
import io.github.seikodictionaryenginev2.platform_onebot.connect.BotConnection;
import io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl.MessageEvent;
import io.github.seikodictionaryenginev2.platform_onebot.message.MessageList;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/27 下午6:09
 */
public class GroupMessageRuntime extends OneBotRuntime<MessageEvent.GroupMessageEvent, GroupMember> {
    public GroupMessageRuntime(DictionaryFile file, MessageEvent.GroupMessageEvent event, BotConnection conn) {
        super(file, event, conn);
    }

    @Override
    protected GroupMember initContact(MessageEvent.GroupMessageEvent EVENT) {
        return EVENT.getSender();
    }

    @Override
    protected void clearMessage0(MessageList singleMessages) {
        getConn().sendMessage(APIRequest.newRequest("send_group_msg", new JSONObject() {{
            put("group_id", getEvent().getGroup_id());
            put("message", singleMessages.transferToSend());
        }}));
    }
}
