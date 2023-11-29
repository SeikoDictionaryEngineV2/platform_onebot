package io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl;

import com.alibaba.fastjson2.annotation.JSONField;
import io.github.seikodictionaryenginev2.platform_onebot.event.basic.BasicEvent;
import io.github.seikodictionaryenginev2.platform_onebot.bean.RemoteFile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/26 下午10:03
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class NoticeEvent extends BasicEvent {

    private String notice_type;

    @EqualsAndHashCode(callSuper = true)
    @Getter
@Setter
    public static class PrivateNoticeEvent extends NoticeEvent {
        private long user_id;

        //好友添加事件
        @EqualsAndHashCode(callSuper = true)
        @Getter
@Setter
        public static class FriendAddEvent extends PrivateNoticeEvent {
            @Override
            public void setNotice_type(String notice_type) {
                super.setNotice_type(notice_type);
                assertRightNoticeType("friend_add");
            }
        }

        //好友消息撤回事件
        @EqualsAndHashCode(callSuper = true)
        @Getter
@Setter
        public static class FriendMessageRecallEvent extends NoticeEvent {
            private long operator_id;
            private long message_id;
            @Override
            public void setNotice_type(String notice_type) {
                super.setNotice_type(notice_type);
                assertRightNoticeType("friend_recall");
            }
        }

        //私聊文件上传事件
        @EqualsAndHashCode(callSuper = true)
        @Getter
@Setter
        public static class PrivateFileUploadEvent extends PrivateNoticeEvent {
            private RemoteFile private_file;

            @Override
            public void setNotice_type(String notice_type) {
                super.setNotice_type(notice_type);
                assertRightNoticeType("private_upload");
            }
        }
    }

    //群消息提示事件
    @EqualsAndHashCode(callSuper = true)
    @Getter
@Setter
    public static class GroupNoticeEvent extends NoticeEvent {
        private long group_id;
        private long user_id;

        //群消息设精事件
        @Getter
@Setter
        @EqualsAndHashCode(callSuper = true)
        public static class GroupMessageEssenceEvent extends GroupNoticeEvent {
            @JSONField(name = "sender_id")
            private long user_id;
            private long operator_id;
            private long message_id;
            private String sub_type;

            @Override
            public void setNotice_type(String notice_type) {
                super.setNotice_type(notice_type);
                assertRightNoticeType("essence");
            }

            //sub_type	string	leave、kick、kick_me	事件子类型，分别表示主动退群、成员被踢、登录号被踢
        }

        //群成员名片被修改
        @Getter
@Setter
        @EqualsAndHashCode(callSuper = true)
        public static class GroupMemberNameCardModifiedEvent extends GroupNoticeEvent {
//            card_new	string	新名片
//            card_old	string	旧名片
            private String card_new;
            private String card_old;

            @Override
            public void setNotice_type(String notice_type) {
                super.setNotice_type(notice_type);
                assertRightNoticeType("group_card");
            }

            //sub_type	string	leave、kick、kick_me	事件子类型，分别表示主动退群、成员被踢、登录号被踢
        }

        //群消息撤回事件
        @Getter
@Setter
        @EqualsAndHashCode(callSuper = true)
        public static class GroupMessageRecallEvent extends GroupNoticeEvent {
            private long operator_id;
            private long message_id;

            @Override
            public void setNotice_type(String notice_type) {
                super.setNotice_type(notice_type);
                assertRightNoticeType("group_recall");
            }

            //sub_type	string	leave、kick、kick_me	事件子类型，分别表示主动退群、成员被踢、登录号被踢
        }

        //群禁言事件
        @Getter
@Setter
        @EqualsAndHashCode(callSuper = true)
        public static class GroupMemberBannedEvent extends GroupNoticeEvent {
            private SubType sub_type;
            private long operator_id;
            private long duration;

            public enum SubType {
                ban,lift_ban
            }

            @Override
            public void setNotice_type(String notice_type) {
                super.setNotice_type(notice_type);
                assertRightNoticeType("group_ban");
            }

            //sub_type	string	leave、kick、kick_me	事件子类型，分别表示主动退群、成员被踢、登录号被踢
        }

        //群成员入群事件
        @Getter
@Setter
        @EqualsAndHashCode(callSuper = true)
        public static class GroupMemberEnterEvent extends GroupNoticeEvent {
            private SubType sub_type;
            private long operator_id;

            public enum SubType {
                approve,invite
            }

            @Override
            public void setNotice_type(String notice_type) {
                super.setNotice_type(notice_type);
                assertRightNoticeType("group_increase");
            }

            //sub_type	string	leave、kick、kick_me	事件子类型，分别表示主动退群、成员被踢、登录号被踢
        }

        //群成员退群事件
        @Getter
@Setter
        @EqualsAndHashCode(callSuper = true)
        public static class GroupMemberLeaveEvent extends GroupNoticeEvent {
            private SubType sub_type;
            private long operator_id;

            public enum SubType {
                leave, kick, kick_me
            }

            @Override
            public void setNotice_type(String notice_type) {
                super.setNotice_type(notice_type);
                assertRightNoticeType("group_decrease");
            }
            //sub_type	string	leave、kick、kick_me	事件子类型，分别表示主动退群、成员被踢、登录号被踢
        }

        //群成员文件上传事件
        @EqualsAndHashCode(callSuper = true)
        @Getter
@Setter
        public static class GroupFileUploadEvent extends GroupNoticeEvent {
            private RemoteFile file;

            @Override
            public void setNotice_type(String notice_type) {
                super.setNotice_type(notice_type);
                assertRightNoticeType("group_upload");
            }
        }

        //管理员改动事件
        @EqualsAndHashCode(callSuper = true)
        @Getter
@Setter
        public static class GroupAdminModifiedEvent extends GroupNoticeEvent {
            private SetType type;

            @Override
            public void setNotice_type(String notice_type) {
                super.setNotice_type(notice_type);
                assertRightNoticeType("group_admin");
            }

            public enum SetType {
                set, unset
            }
        }
    }


    @Override
    public void setPost_type(String post_type) {
        super.setPost_type(post_type);
        assertRightPostType("notice");
    }

    protected void assertRightNoticeType(String expert) {
        assertFieldRightType("notice_type", expert);
    }
}
