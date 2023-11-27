package io.github.seikodictionaryenginev2.platform_onebot;

import io.github.seikodictionaryenginev2.base.entity.code.DictionaryCommandMatcher;
import io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl.MessageEvent;

public class Registrator {
    //初始化Onebot部分
    public static void inject() {
        io.github.seikodictionaryenginev2.base.command.Registrator.inject();
        DictionaryCommandMatcher.domainQuoteNew.put("群", new Class[]{MessageEvent.GroupMessageEvent.class});
    }
}