package io.github.seikodictionaryenginev2.platform_onebot.event;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.seikodictionaryenginev2.platform_onebot.event.basic.BasicEvent;
import lombok.Data;
import lombok.ToString;

import java.util.Optional;

/**
 * @Description 事件
 * 事件是用户需要从 OneBot 被动接收的数据，有以下几个大类：
 * <p>
 * 消息事件，包括私聊消息、群消息等
 * 通知事件，包括群成员变动、好友变动等
 * 请求事件，包括加群请求、加好友请求等
 * 元事件，包括 OneBot 生命周期、心跳等
 * 在所有能够推送事件的通信方式中（HTTP POST、正向和反向 WebSocket），事件都以 JSON 格式表示。
 * @Author kagg886
 * @Date 2023/11/26 下午9:57
 */
@Data
public class EventSource {
    private JSONObject origin;

    @Override
    public String toString() {
        return origin.toString();
    }

    public EventSource(String source) {
        this.origin = JSON.parseObject(source);
        //每个事件都有 time、self_id 和 post_type 字段
        checkRequiredField("time");
        checkRequiredField("self_id");
        checkRequiredField("post_type");
//        其中 post_type 不同字段值表示的事件类型对应如下：
//
//        message：消息事件
//        notice：通知事件
//        request：请求事件
//        meta_event：元事件
    }

    public Type getEventType() {
        return Type.valueOf(origin.getString("post_type"));
    }

    private void checkRequiredField(String field) {
        if (Optional.ofNullable(origin.get(field)).isEmpty()) {
            throw new IllegalArgumentException(field + " must required");
        }
    }

    public <T extends BasicEvent> T transfer(Class<T> t) {
        return origin.to(t);
    }

    public enum Type {
        message,notice,request,meta_event
    }
}
