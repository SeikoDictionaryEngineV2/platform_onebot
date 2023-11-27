package io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl;

import io.github.seikodictionaryenginev2.platform_onebot.bean.impl.GroupMember;
import io.github.seikodictionaryenginev2.platform_onebot.message.MessageList;
import io.github.seikodictionaryenginev2.platform_onebot.bean.Sender;
import io.github.seikodictionaryenginev2.platform_onebot.event.basic.BasicEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * 事件数据#
 * 字段名	数据类型	可能的值	说明
 * message_type	string	group	消息类型
 * 	string	normal、anonymous、notice	消息子类型，正常消息是 normal，匿名消息是 anonymous，系统提示（如「管理员已禁止群内匿名聊天」）是 notice
 * message_id	number (int32)	-	消息 ID
 * group_id	number (int64)	-	群号
 * user_id	number (int64)	-	发送者 QQ 号
 * anonymous	object	-	匿名信息，如果不是匿名消息则为 null
 * message	message	-	消息内容
 * raw_message	string	-	原始消息内容
 * font	number (int32)	-	字体
 * sender	object	-	发送人信息
 * 其中 anonymous 字段的内容如下：
 *
 * 字段名	数据类型	说明
 * id	number (int64)	匿名用户 ID
 * name	string	匿名用户名称
 * flag	string	匿名用户 flag，在调用禁言 API 时需要传入
 * sender 字段的内容如下：
 *
 * 字段名	数据类型	说明
 * user_id	number (int64)	发送者 QQ 号
 * nickname	string	昵称
 * card	string	群名片／备注
 * sex	string	性别，male 或 female 或 unknown
 * age	number (int32)	年龄
 * area	string	地区
 * level	string	成员等级
 * role	string	角色，owner 或 admin 或 member
 * title	string	专属头衔
 * 需要注意的是，sender 中的各字段是尽最大努力提供的，也就是说，不保证每个字段都一定存在，也不保证存在的字段都是完全正确的（缓存可能过期）。尤其对于匿名消息，此字段不具有参考价值。
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
