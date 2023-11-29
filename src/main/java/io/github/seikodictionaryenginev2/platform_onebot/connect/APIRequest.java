package io.github.seikodictionaryenginev2.platform_onebot.connect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/27 下午2:00
 */
//{
//    "action": "send_private_msg",
//    "params": {
//        "user_id": 10001000,
//        "message": "你好"
//    },
//    "echo": "123"
//}
@Getter
@Setter
@AllArgsConstructor
public class APIRequest<T> {
    private String action;
    private T params;
    private String echo;

    public static <T> APIRequest<T> newRequest(String action,T params) {
        return new APIRequest<>(action,params, UUID.randomUUID().toString());
    }
}
