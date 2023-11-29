package io.github.seikodictionaryenginev2.platform_onebot.dic.runtime;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.seikodictionaryenginev2.base.entity.DictionaryFile;
import io.github.seikodictionaryenginev2.platform_onebot.bean.Group;
import io.github.seikodictionaryenginev2.platform_onebot.connect.APIRequest;
import io.github.seikodictionaryenginev2.platform_onebot.connect.BotConnection;
import io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl.MessageEvent;
import io.github.seikodictionaryenginev2.platform_onebot.message.MessageList;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/27 下午6:09
 */
public class GroupMessageRuntime extends OneBotRuntime<MessageEvent.GroupMessageEvent, Group> {
    public GroupMessageRuntime(DictionaryFile file, MessageEvent.GroupMessageEvent event, BotConnection conn) {
        super(file, event, conn);
    }

    @Override
    protected Group initContact(MessageEvent.GroupMessageEvent EVENT) {
        return new Group(EVENT.getGroup_id());
    }

    @Override
    protected void initObject(String command, MessageEvent.GroupMessageEvent event) {
        super.initObject(command, event);
        
        getRuntimeObject().put("群号",event.getGroup_id());
        getRuntimeObject().put("名片", event.getSender().getCard());
        getRuntimeObject().put("权限",event.getSender().getRole());
    }

    @Override
    protected void clearMessage0(MessageList singleMessages) {
        getConn().sendMessage(APIRequest.newRequest("send_group_msg", new JSONObject() {{
            put("group_id", getContact().getGroup_id());
            put("message", singleMessages.transferToSend());
        }}));
    }
}
