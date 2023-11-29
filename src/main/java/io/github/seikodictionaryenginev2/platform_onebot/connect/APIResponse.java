package io.github.seikodictionaryenginev2.platform_onebot.connect;

import com.alibaba.fastjson2.JSON;
import lombok.*;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/27 下午2:02
 */
//{
//    "status": "failed",
//    "retcode": 1404,
//    "data": null,
//    "echo": "123"
//}
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse<T> {
    private String status;
    private int retcode;
    private T data;
    private String echo;

    @EqualsAndHashCode(callSuper = true)
    @Getter
    @Setter
    @AllArgsConstructor
    public static class UnknownTypeAPIResponse extends APIResponse<Object> {
        public <R> APIResponse<R> to(Class<R> response) {
            return new APIResponse<>(getStatus(), getRetcode(), JSON.parseObject(getData().toString(), response), getEcho());
        }
    }
}
