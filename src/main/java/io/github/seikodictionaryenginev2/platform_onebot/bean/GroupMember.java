package io.github.seikodictionaryenginev2.platform_onebot.bean;

import io.github.seikodictionaryenginev2.platform_onebot.bean.Sender;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/27 上午10:37
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class GroupMember extends Sender {
    private String card;
    private String area;
    private String level;
    private String role;
    private String title;
}
