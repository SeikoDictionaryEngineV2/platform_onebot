package io.github.seikodictionaryenginev2.platform_onebot.event.basic;

import com.alibaba.fastjson2.annotation.JSONField;
import io.github.seikodictionaryenginev2.platform_onebot.utils.FieldUtil;
import lombok.Data;

import java.lang.reflect.Field;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/26 下午10:04
 */
@Data
public class BasicEvent {
    private long time;
    private long self_id;
    private String post_type;


    protected void assertRightPostType(String expert) {
        assertFieldRightType("post_type",expert);
//        if (!post_type.equals(expert)) {
//            throw new IllegalStateException(this.getClass().getName() + "must required post_type:" + expert + ", but this event is:" + post_type);
//        }
    }

    protected void assertFieldRightType(String name, String expert) {
        FieldUtil.assertFieldRightType(this,name,expert);
    }
}
