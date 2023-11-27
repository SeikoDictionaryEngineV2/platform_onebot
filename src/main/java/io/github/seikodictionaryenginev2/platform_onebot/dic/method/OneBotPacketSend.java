package io.github.seikodictionaryenginev2.platform_onebot.dic.method;

import com.alibaba.fastjson2.JSON;
import io.github.seikodictionaryenginev2.base.command.func.CallMethod;
import io.github.seikodictionaryenginev2.base.entity.code.func.Function;
import io.github.seikodictionaryenginev2.base.entity.code.func.type.SendMessageWhenPostExecute;
import io.github.seikodictionaryenginev2.base.session.BasicRuntime;
import io.github.seikodictionaryenginev2.platform_onebot.connect.APIRequest;
import io.github.seikodictionaryenginev2.platform_onebot.connect.APIResponse;
import io.github.seikodictionaryenginev2.platform_onebot.connect.BotConnection;
import io.github.seikodictionaryenginev2.platform_onebot.dic.runtime.OneBotRuntime;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 发包，格式为:$OneBot发包 api字段 传的值(可选)，返回特定的集合结构$
 *
 * @Description
 * @Author kagg886
 * @Date 2023/11/27 下午10:35
 */
public class OneBotPacketSend extends Function implements SendMessageWhenPostExecute {

    public OneBotPacketSend(int line, String code) {
        super(line, code);
    }

    @SneakyThrows
    @Override
    protected Object run(BasicRuntime<?, ?, ?> runtime, List<Object> args) {
        BotConnection conn;

        while (runtime instanceof CallMethod.FunctionRuntime<?, ?, ?> r) {
            Method m = r.getClass().getMethod("getOrigin");
            m.setAccessible(true);
            runtime = (BasicRuntime<?, ?, ?>) m.invoke(r);
        }

        conn = ((OneBotRuntime<?, ?>) runtime).getConn();

        APIResponse<Object> resp = conn.sendMessageBlocking(APIRequest.newRequest(args.get(0).toString(), args.get(1)), 10, TimeUnit.SECONDS);
        return JSON.parseObject(JSON.toJSONString(resp));
    }
}
