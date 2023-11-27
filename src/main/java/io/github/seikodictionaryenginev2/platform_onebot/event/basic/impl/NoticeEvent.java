package io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl;

import io.github.seikodictionaryenginev2.platform_onebot.event.basic.BasicEvent;
import io.github.seikodictionaryenginev2.platform_onebot.message.notice.GroupFile;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/26 下午10:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NoticeEvent extends BasicEvent {

    private String notice_type;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class FriendAddEvent extends NoticeEvent {
        private long user_id;
        @Override
        public void setNotice_type(String notice_type) {
            super.setNotice_type(notice_type);
            assertRightNoticeType("friend_add");
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class FriendMessageRecallEvent extends NoticeEvent {
        private long user_id;
        private long message_id;
        @Override
        public void setNotice_type(String notice_type) {
            super.setNotice_type(notice_type);
            assertRightNoticeType("friend_recall");
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class GroupNoticeEvent extends NoticeEvent {
        private long group_id;
        private long user_id;

        @Data
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

        @Data
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

        @Data
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

        @Data
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

        @EqualsAndHashCode(callSuper = true)
        @Data
        public static class GroupFileUploadEvent extends GroupNoticeEvent {
            private GroupFile file;

            @Override
            public void setNotice_type(String notice_type) {
                super.setNotice_type(notice_type);
                assertRightNoticeType("group_upload");
            }
        }

        @EqualsAndHashCode(callSuper = true)
        @Data
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
