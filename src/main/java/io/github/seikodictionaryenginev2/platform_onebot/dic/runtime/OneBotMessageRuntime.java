package io.github.seikodictionaryenginev2.platform_onebot.dic.runtime;

import com.alibaba.fastjson2.JSON;
import io.github.seikodictionaryenginev2.base.entity.DictionaryFile;
import io.github.seikodictionaryenginev2.base.session.BasicRuntime;
import io.github.seikodictionaryenginev2.platform_onebot.connect.BotConnection;
import io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl.MessageEvent;
import io.github.seikodictionaryenginev2.platform_onebot.message.MessageList;
import io.github.seikodictionaryenginev2.platform_onebot.message.SingleMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/27 下午10:17
 */
@Getter
@Setter
public abstract class OneBotMessageRuntime<Event extends MessageEvent, Contact> extends OneBotRuntime<Event, Contact> {

    public OneBotMessageRuntime(DictionaryFile file, Event event, BotConnection conn) {
        super(file, event,conn);
    }

    @Override
    protected void initObject(String command, Event event) {
        super.initObject(command, event);
        getRuntimeObject().put("QQ", event.getSender().getUser_id());
        getRuntimeObject().put("昵称", event.getSender().getNickname());
    }
}
