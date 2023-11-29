package io.github.seikodictionaryenginev2.platform_onebot.dic.runtime.impl;

import com.alibaba.fastjson2.JSONObject;
import io.github.seikodictionaryenginev2.base.entity.DictionaryFile;
import io.github.seikodictionaryenginev2.platform_onebot.bean.Group;
import io.github.seikodictionaryenginev2.platform_onebot.connect.APIRequest;
import io.github.seikodictionaryenginev2.platform_onebot.connect.BotConnection;
import io.github.seikodictionaryenginev2.platform_onebot.dic.runtime.OneBotNoticeRuntime;
import io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl.NoticeEvent;
import io.github.seikodictionaryenginev2.platform_onebot.message.MessageList;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/29 下午7:06
 */
public class GroupNoticeRuntime extends OneBotNoticeRuntime<NoticeEvent.GroupNoticeEvent,Group> {
    public GroupNoticeRuntime(DictionaryFile file, NoticeEvent.GroupNoticeEvent event, BotConnection conn) {
        super(file, event, conn);
    }

    @Override
    protected Group initContact(NoticeEvent.GroupNoticeEvent EVENT) {
        return new Group(EVENT.getGroup_id());
    }

    @Override
    protected void clearMessage0(MessageList singleMessages) {
        getConn().sendMessage(APIRequest.newRequest("send_group_msg", new JSONObject() {{
            put("group_id", getContact().getGroup_id());
            put("message", singleMessages.transferToSend());
        }}));
    }
}
