package io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl;

import io.github.seikodictionaryenginev2.platform_onebot.event.basic.BasicEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/26 下午10:04
 */
//消息、通知、请求三大类事件是与聊天软件直接相关的、机器人真实接收到的事件，除了这些，OneBot 自己还会产生一类事件，这里称之为「元事件」，例如生命周期事件、心跳事件等，这类事件与 OneBot 本身的运行状态有关，而与聊天软件无关。元事件的上报方式和普通事件完全一样。
//
//生命周期#
//字段名	数据类型	可能的值	说明
//time	number (int64)	-	事件发生的时间戳
//self_id	number (int64)	-	收到事件的机器人 QQ 号
//post_type	string	meta_event	上报类型
//meta_event_type	string	lifecycle	元事件类型
//sub_type	string	enable、disable、connect	事件子类型，分别表示 OneBot 启用、停用、WebSocket 连接成功
//WARNING
//
//目前生命周期元事件中，只有 HTTP POST 的情况下可以收到 enable 和 disable，只有正向 WebSocket 和反向 WebSocket 可以收到 connect。
//
//心跳#
//字段名	数据类型	可能的值	说明
//time	number (int64)	-	事件发生的时间戳
//self_id	number (int64)	-	收到事件的机器人 QQ 号
//post_type	string	meta_event	上报类型
//meta_event_type	string	heartbeat	元事件类型
//status	object	-	状态信息
//interval	number (int64)	-	到下次心跳的间隔，单位毫秒
@EqualsAndHashCode(callSuper = true)
@Data
public class MetaEvent extends BasicEvent {
    private String meta_event_type;
    private String sub_type;

    @Override
    public void setPost_type(String post_type) {
        super.setPost_type(post_type);
        assertRightPostType("meta_event");
    }
}
