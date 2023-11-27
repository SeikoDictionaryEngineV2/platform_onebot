package io.github.seikodictionaryenginev2.platform_onebot.event.basic.impl;

import io.github.seikodictionaryenginev2.platform_onebot.event.EventSource;
import org.junit.jupiter.api.Test;

class MessageEventTest {
    @Test
    void testGroupMessage() {
        EventSource e = new EventSource("{\"time\":1701010377,\"self_id\":1693256674,\"post_type\":\"message\",\"message_type\":\"group\",\"sub_type\":\"normal\",\"message_id\":711958395,\"group_id\":786442984,\"peer_id\":1693256674,\"user_id\":485184047,\"message\":[{\"data\":{\"text\":\"1\"},\"type\":\"text\"}],\"raw_message\":\"1\",\"font\":0,\"sender\":{\"user_id\":485184047,\"nickname\":\"可爱猫猫\",\"card\":\"可爱猫猫\",\"role\":\"admin\",\"title\":\"\",\"level\":\"\"}}");
        MessageEvent.GroupMessageEvent ev =  e.transfer(MessageEvent.GroupMessageEvent.class);
        System.out.println(ev);
    }
    @Test
    void testPrivateMessage() {
        EventSource e = new EventSource("{\"time\":1701052563,\"self_id\":1693256674,\"post_type\":\"message\",\"message_type\":\"private\",\"sub_type\":\"friend\",\"message_id\":1456418083,\"target_id\":485184047,\"peer_id\":1693256674,\"user_id\":485184047,\"message\":[{\"data\":{\"text\":\"2\"},\"type\":\"text\"}],\"raw_message\":\"2\",\"font\":0,\"sender\":{\"user_id\":485184047,\"nickname\":\"Oyama Hoshinoble!\",\"card\":\"\",\"role\":\"member\",\"title\":\"\",\"level\":\"\"}}");
        MessageEvent.PrivateMessageEvent ev =  e.transfer(MessageEvent.PrivateMessageEvent.class);
        System.out.println(ev);
    }

    @Test
    void testMessageChain() {
        EventSource s = new EventSource("{\"time\":1701052964,\"self_id\":1693256674,\"post_type\":\"message\",\"message_type\":\"private\",\"sub_type\":\"friend\",\"message_id\":1531879459,\"target_id\":485184047,\"peer_id\":1693256674,\"user_id\":485184047,\"message\":[{\"data\":{\"text\":\"qwq\"},\"type\":\"text\"},{\"data\":{\"id\":66},\"type\":\"face\"},{\"data\":{\"file\":\"4a2648a5a72b7236b6be675b94ce6bc7\",\"url\":\"https://c2cpicdw.qpic.cn/offpic_new/0/123-0-4A2648A5A72B7236B6BE675B94CE6BC7/0?term=2\"},\"type\":\"image\"}],\"raw_message\":\"qwq[CQ:face,id=66][CQ:image,file=4a2648a5a72b7236b6be675b94ce6bc7,url=https://c2cpicdw.qpic.cn/offpic_new/0/123-0-4A2648A5A72B7236B6BE675B94CE6BC7/0?term=2]\",\"font\":0,\"sender\":{\"user_id\":485184047,\"nickname\":\"Oyama Hoshinoble!\",\"card\":\"\",\"role\":\"member\",\"title\":\"\",\"level\":\"\"}}");

        MessageEvent.PrivateMessageEvent ev = s.transfer(MessageEvent.PrivateMessageEvent.class);

        System.out.println(ev.getMessage());
    }
}