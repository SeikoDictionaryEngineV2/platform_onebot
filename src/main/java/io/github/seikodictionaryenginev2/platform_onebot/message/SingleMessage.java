package io.github.seikodictionaryenginev2.platform_onebot.message;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.seikodictionaryenginev2.platform_onebot.utils.FieldUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/26 下午10:52
 */
@Data
public class SingleMessage {
    private String type;
    private JSONObject data;

    public String contentToString() {
        return "";
    }

    public JSONObject writeData() {
        return data;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = true)
    public static class RemoteFile extends SingleMessage {
        private String id;
        private String name;
        private String url;

        @Override
        public String contentToString() {
            return "[文件]:"+name;
        }
    }


    @EqualsAndHashCode(callSuper = true)
    @Getter
    @Setter
    public static class QuoteReply extends SingleMessage {
        private String id;

        @Override
        public JSONObject writeData() {
            return new JSONObject() {{
                put("id", id);
            }};
        }

        @Override
        public void setType(String type) {
            super.setType(type);
            assertTypeRight("reply");
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Getter
    @Setter
    public static class PlainText extends SingleMessage {
        private String text;

        public PlainText(String str) {
            setType("text");
            this.text = str;
        }

        @Override
        public JSONObject writeData() {
            return new JSONObject() {{
                put("text", text);
            }};
        }


        @Override
        public void setType(String type) {
            super.setType(type);
            assertTypeRight("text");
        }

        @Override
        public String contentToString() {
            return text;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Getter
    @Setter
    public static class Face extends SingleMessage {
        private String id;

        @Override
        public JSONObject writeData() {
            return new JSONObject() {{
                put("id", id);
            }};
        }


        @Override
        public void setType(String type) {
            super.setType(type);
            assertTypeRight("face", "mface");
        }

        @Override
        public String contentToString() {
            return String.format("[表情:%s]", id);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Getter
    @Setter
    public static class Image extends SingleMessage {
        private String file;
        private String url;

        @Override
        public JSONObject writeData() {
            return new JSONObject() {{
                put("file", file);
                put("url", url);
            }};
        }


        @Override
        public void setType(String type) {
            super.setType(type);
            assertTypeRight("image");
        }

        @Override
        public String contentToString() {
            return "[图片]";
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Getter
    @Setter
    public static class Record extends SingleMessage {
        private String file;

        @Override
        public JSONObject writeData() {
            return new JSONObject() {{
                put("file", file);
            }};
        }


        @Override
        public void setType(String type) {
            super.setType(type);
            assertTypeRight("record");
        }

        @Override
        public String contentToString() {
            return "[语音]";
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Getter
    @Setter
    public static class Video extends SingleMessage {
        private String file;

        @Override
        public JSONObject writeData() {
            return new JSONObject() {{
                put("file", file);
            }};
        }

        @Override
        public void setType(String type) {
            super.setType(type);
            assertTypeRight("video");
        }

        @Override
        public String contentToString() {
            return "[视频]";
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Getter
    @Setter
    public static class At extends SingleMessage {
        private String qq;

        @Override
        public JSONObject writeData() {
            return new JSONObject() {{
                put("qq", qq);
            }};
        }

        @Override
        public void setType(String type) {
            super.setType(type);
            assertTypeRight("at");
        }

        @Override
        public String contentToString() {
            return "@" + qq + " ";
        }
    }

    public <T extends SingleMessage> T to(Class<T> t) {
        if (this.getData() == null) {
            return (T) this;
        }
        T m = data.to(t);
        m.setType(getType());
        m.setData(getData());
        return m;
    }


    protected void assertTypeRight(String... expert) {
        FieldUtil.assertFieldRightType(this, "type", expert);
    }
}
