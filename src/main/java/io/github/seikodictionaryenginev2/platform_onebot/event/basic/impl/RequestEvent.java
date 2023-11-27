package io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl;

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
public class RequestEvent extends BasicEvent {
    private RequestType request_type;

    private long user_id;
    private String comment;
    private String flag;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class FriendRequestEvent extends RequestEvent {

    }

    //group_id	number (int64)	-	群号
    //user_id	number (int64)	-	发送请求的 QQ 号
    //comment	string	-	验证信息
    //flag	string	-	请求 flag，在调用处理请求的 API 时需要传入
    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class GroupMemberRequestEvent extends RequestEvent {
        private JoinType sub_type;
        private long group_id;


        public  enum JoinType {
            add,invite
        }
    }


    @Override
    public void setPost_type(String post_type) {
        super.setPost_type(post_type);
        assertRightPostType("request");
    }

    public enum RequestType {
        group,friend
    }
}
