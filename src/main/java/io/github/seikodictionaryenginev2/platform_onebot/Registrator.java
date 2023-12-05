package io.github.seikodictionaryenginev2.platform_onebot;

import io.github.seikodictionaryenginev2.base.entity.code.DictionaryCommandMatcher;
import io.github.seikodictionaryenginev2.base.entity.code.func.Function;
import io.github.seikodictionaryenginev2.platform_onebot.bean.Group;
import io.github.seikodictionaryenginev2.platform_onebot.dic.method.BotListGet;
import io.github.seikodictionaryenginev2.platform_onebot.dic.method.OneBotPacketSend;
import io.github.seikodictionaryenginev2.platform_onebot.dic.method.OneBotTargetSet;
import io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl.MessageEvent;
import io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl.NoticeEvent;

public class Registrator {
    //初始化Onebot部分
    public static void inject() {
        io.github.seikodictionaryenginev2.base.command.Registrator.inject();
        DictionaryCommandMatcher.domainQuoteNew.put("群", new Class[]{MessageEvent.GroupMessageEvent.class});
        DictionaryCommandMatcher.domainQuoteNew.put("私聊", new Class[]{MessageEvent.PrivateMessageEvent.class});

        DictionaryCommandMatcher.domainQuoteNew.put("群事件", new Class[]{NoticeEvent.GroupNoticeEvent.class});
        DictionaryCommandMatcher.domainQuoteNew.put("私聊事件", new Class[]{NoticeEvent.PrivateNoticeEvent.class});

        Function.globalManager.put("OneBot发包", OneBotPacketSend.class);
        Function.globalManager.put("获取BOT列表", BotListGet.class);
        Function.globalManager.put("端点切换", OneBotTargetSet.class);
    }
}