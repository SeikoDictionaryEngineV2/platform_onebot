package io.github.seikodictionaryenginev2.platform_onebot.dic.runtime;

import com.alibaba.fastjson2.JSON;
import io.github.seikodictionaryenginev2.base.entity.DictionaryFile;
import io.github.seikodictionaryenginev2.base.session.BasicRuntime;
import io.github.seikodictionaryenginev2.platform_onebot.connect.BotConnection;
import io.github.seikodictionaryenginev2.platform_onebot.event.basic.BasicEvent;
import io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl.MessageEvent;
import io.github.seikodictionaryenginev2.platform_onebot.message.MessageList;
import io.github.seikodictionaryenginev2.platform_onebot.message.SingleMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/12/5 下午9:46
 */
@Getter
@Setter
public abstract class OneBotRuntime<Event extends BasicEvent, Contact> extends BasicRuntime<Event, Contact, MessageList> {
    private BotConnection conn;

    public OneBotRuntime(DictionaryFile file, Event event, BotConnection conn) {
        super(file, event);
        this.conn = conn;
    }

    @Override
    protected void initObject(String command, Event event) {
        super.initObject(command, event);
        getRuntimeObject().put("上下文", JSON.parseObject(event.toString()));
        getRuntimeObject().put("BOT",event.getSelf_id());
    }

    @Override
    protected MessageList initMessageCache() {
        return new MessageList();
    }

    @Override
    protected void appendMessage(String str) {
        getMessageCache().add(new SingleMessage.PlainText(str));
    }

    @Override
    protected abstract void clearMessage0(MessageList singleMessages);
}
