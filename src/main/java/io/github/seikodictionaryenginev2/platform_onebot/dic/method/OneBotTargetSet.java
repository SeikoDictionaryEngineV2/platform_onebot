package io.github.seikodictionaryenginev2.platform_onebot.dic.method;

import io.github.seikodictionaryenginev2.base.entity.code.func.Function;
import io.github.seikodictionaryenginev2.base.exception.DictionaryOnRunningException;
import io.github.seikodictionaryenginev2.base.session.BasicRuntime;
import io.github.seikodictionaryenginev2.platform_onebot.connect.BotConnection;
import io.github.seikodictionaryenginev2.platform_onebot.connect.ConnectionManager;
import io.github.seikodictionaryenginev2.platform_onebot.dic.runtime.OneBotRuntime;

import java.util.List;

/**
 * @Description
 * $端点切换 UUID$
 * 默认连接为词库被触发时的连接
 * 此指令可以修改之。
 * @Author kagg886
 * @Date 2023/12/5 下午9:44
 */
public class OneBotTargetSet extends Function {
    public OneBotTargetSet(int line, String code) {
        super(line, code);
    }

    @Override
    protected Object run(BasicRuntime<?, ?, ?> runtime, List<Object> args) {
        String uuid = args.get(0).toString();
        BotConnection c = ConnectionManager.getInstance().get(uuid);
        if (c == null) {
            throw new DictionaryOnRunningException("此链接不存在!");
        }
        ((OneBotRuntime<?,?>) runtime).setConn(c);
        return null;
    }
}
