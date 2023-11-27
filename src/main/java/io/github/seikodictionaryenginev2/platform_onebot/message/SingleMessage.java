package io.github.seikodictionaryenginev2.platform_onebot.message;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.seikodictionaryenginev2.platform_onebot.utils.FieldUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/26 下午10:52
 */
@Data
public class SingleMessage {
    private String type;
    private JSONObject data;

    public void setData(JSONObject data) {
        this.data = data;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class PlainText extends SingleMessage {
        private String text;

        @Override
        public void setType(String type) {
            super.setType(type);
            assertTypeRight("text");
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Face extends SingleMessage {
        private String id;

        @Override
        public void setType(String type) {
            super.setType(type);
            assertTypeRight("face");
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Image extends SingleMessage {
        private String file;
        private String url;

        @Override
        public void setType(String type) {
            super.setType(type);
            assertTypeRight("image");
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Record extends SingleMessage {
        private String file;

        @Override
        public void setType(String type) {
            super.setType(type);
            assertTypeRight("record");
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Video extends SingleMessage {
        private String file;

        @Override
        public void setType(String type) {
            super.setType(type);
            assertTypeRight("video");
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class At extends SingleMessage {
        private String qq;

        @Override
        public void setType(String type) {
            super.setType(type);
            assertTypeRight("at");
        }
    }

    public <T extends SingleMessage> T to(Class<T> t) {
        T m = data.to(t);
        m.setType(getType());
        m.setData(getData());
        return m;
    }

    protected void assertTypeRight(String expert) {
        FieldUtil.assertFieldRightType(this,"type",expert);
    }
}
