package io.github.seikodictionaryenginev2.platform_onebot.dic.runtime;

import com.alibaba.fastjson2.JSONObject;
import io.github.seikodictionaryenginev2.base.entity.DictionaryFile;
import io.github.seikodictionaryenginev2.platform_onebot.bean.Sender;
import io.github.seikodictionaryenginev2.platform_onebot.connect.APIRequest;
import io.github.seikodictionaryenginev2.platform_onebot.connect.BotConnection;
import io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl.MessageEvent;
import io.github.seikodictionaryenginev2.platform_onebot.message.MessageList;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/27 下午10:28
 */
public class PrivateMessageRuntime extends OneBotRuntime<MessageEvent.PrivateMessageEvent, Sender> {
    public PrivateMessageRuntime(DictionaryFile file, MessageEvent.PrivateMessageEvent event, BotConnection conn) {
        super(file, event, conn);
    }

    @Override
    protected Sender initContact(MessageEvent.PrivateMessageEvent EVENT) {
        return EVENT.getSender();
    }

    @Override
    protected void clearMessage0(MessageList singleMessages) {
        getConn().sendMessage(APIRequest.newRequest("send_private_msg", new JSONObject() {{
            put("user_id", getEvent().getUser_id());
            put("message", singleMessages.transferToSend());
        }}));
    }
}
