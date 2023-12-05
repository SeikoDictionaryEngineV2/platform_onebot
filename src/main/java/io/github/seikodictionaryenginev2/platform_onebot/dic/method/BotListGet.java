package io.github.seikodictionaryenginev2.platform_onebot.dic.method;

import com.alibaba.fastjson2.JSON;
import io.github.seikodictionaryenginev2.base.entity.code.func.Function;
import io.github.seikodictionaryenginev2.base.entity.code.func.type.SendMessageWhenPostExecute;
import io.github.seikodictionaryenginev2.base.session.BasicRuntime;
import io.github.seikodictionaryenginev2.platform_onebot.connect.APIRequest;
import io.github.seikodictionaryenginev2.platform_onebot.connect.ConnectionManager;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description $获取BOT列表$
 * @Author kagg886
 * @Date 2023/12/5 下午9:37
 */
public class BotListGet extends Function implements SendMessageWhenPostExecute {
    public BotListGet(int line, String code) {
        super(line, code);
    }

    @Override
    protected Object run(BasicRuntime<?, ?, ?> runtime, List<Object> args) {
        List<Profile> profiles = new ArrayList<>();
        ConnectionManager.getInstance().forEach((k,v) -> {
            try {
                Profile p = v.sendMessageBlocking(APIRequest.newRequest("get_login_info",null)).to(Profile.class).getData();
                p.uuid = k;
                profiles.add(p);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return JSON.toJSONString(profiles);
    }

    @Data
    private static class Profile {
        private String uuid;
        private long user_id;
        private String nickname;
    }
}
