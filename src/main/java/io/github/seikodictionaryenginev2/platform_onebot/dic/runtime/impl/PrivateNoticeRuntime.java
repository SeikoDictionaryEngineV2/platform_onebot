package io.github.seikodictionaryenginev2.platform_onebot.dic.runtime.impl;

import com.alibaba.fastjson2.JSONObject;
import io.github.seikodictionaryenginev2.base.entity.DictionaryFile;
import io.github.seikodictionaryenginev2.platform_onebot.bean.Group;
import io.github.seikodictionaryenginev2.platform_onebot.connect.APIRequest;
import io.github.seikodictionaryenginev2.platform_onebot.connect.BotConnection;
import io.github.seikodictionaryenginev2.platform_onebot.dic.runtime.OneBotMessageRuntime;
import io.github.seikodictionaryenginev2.platform_onebot.dic.runtime.OneBotNoticeRuntime;
import io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl.MessageEvent;
import io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl.NoticeEvent;
import io.github.seikodictionaryenginev2.platform_onebot.message.MessageList;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/27 下午6:09
 */
public class PrivateNoticeRuntime extends OneBotNoticeRuntime<NoticeEvent.PrivateNoticeEvent, Long> {
    public PrivateNoticeRuntime(DictionaryFile file, NoticeEvent.PrivateNoticeEvent event, BotConnection conn) {
        super(file, event, conn);
    }

    @Override
    protected Long initContact(NoticeEvent.PrivateNoticeEvent EVENT) {
        return EVENT.getUser_id();
    }

    @Override
    protected void clearMessage0(MessageList singleMessages) {
        getConn().sendMessage(APIRequest.newRequest("send_private_msg", new JSONObject() {{
            put("user_id", getContact());
            put("message", singleMessages.transferToSend());
        }}));
    }
}
