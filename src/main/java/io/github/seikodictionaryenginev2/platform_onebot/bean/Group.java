package io.github.seikodictionaryenginev2.platform_onebot.bean;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/29 上午11:18
 */
@Getter
@Setter
@NoArgsConstructor
public class Group {
    private long group_id;
    private String group_name;
    private String group_remark;
    private long group_uin;
    private List<Integer> admins;
    private String class_text;
    private boolean is_frozen;
    private int max_member;
    private int member_num;
    private int member_count;
    private int max_member_count;

    public Group(long id) {
        this.group_id = id;
    }
}
