package io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl;

import io.github.seikodictionaryenginev2.platform_onebot.bean.GroupMember;
import io.github.seikodictionaryenginev2.platform_onebot.message.MessageList;
import io.github.seikodictionaryenginev2.platform_onebot.bean.Sender;
import io.github.seikodictionaryenginev2.platform_onebot.event.basic.BasicEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/26 下午10:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageEvent extends BasicEvent {
    private String message_type;
    private String sub_type;
    private long message_id;
    private long user_id;
    private MessageList message;
    private String raw_message;
    private int font;
    private Sender sender;

    @Override
    public void setPost_type(String post_type) {
        super.setPost_type(post_type);
        assertRightPostType("message");
    }

    protected void assertRightMessageType(String expert) {
        assertFieldRightType("message_type",expert);
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class GroupMessageEvent extends MessageEvent {
        private long group_id;
        private Object anonymous;
        private GroupMember sender;


        @Override
        public void setMessage_type(String message_type) {
            super.setMessage_type(message_type);
            assertFieldRightType("message_type","group");
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class PrivateMessageEvent extends MessageEvent {
        @Override
        public void setMessage_type(String message_type) {
            super.setMessage_type(message_type);
            assertFieldRightType("message_type","private");
        }
    }
}
