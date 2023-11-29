package io.github.seikodictionaryenginev2.platform_onebot.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/26 下午10:45
 */
@Getter
@Setter
public class Sender {
    private long user_id;
    private String nickname;

    private String sex;
    private int age;


}
