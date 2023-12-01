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

        System.out.println(ev.getMessage().contentToString());
    }

    @Test
    void testVideo() {
        EventSource s = new EventSource("{\"time\":1701064380,\"self_id\":485184047,\"post_type\":\"message\",\"message_type\":\"group\",\"sub_type\":\"normal\",\"message_id\":1031277134,\"group_id\":726144025,\"peer_id\":485184047,\"user_id\":2409808180,\"message\":[{\"data\":{\"file\":\"da2ee4dd7d194f7be1a5fd8d59451fcf.mp4\",\"url\":\"http://116.130.220.27:443/qqdownload?ver=537180568&rkey=3081fd0201010481f53081f202010102010002041ceb522f0481a633303531303230313030303433363330333430323031303030323034386661326331333430323033376131616664303230343239326135363331303230343635363432656263303431306461326565346464376431393466376265316135666438643539343531666366303230333761316462393032303130303034313430303030303030383636363936633635373437393730363530303030303030343331333033303333020465642ebd04350000000866696c657479706500000004313030330000000b646f776e656e63727970740000000130000000047175696300000001310400&filetype=1003&videotype=0&subvideotype=0&term=android&video_codec=0\"},\"type\":\"video\"}],\"raw_message\":\"[CQ:video,file=da2ee4dd7d194f7be1a5fd8d59451fcf.mp4,url=http://122.97.71.98:443/qqdownload?ver=537180568&amp;rkey=3081fd0201010481f53081f202010102010002041ceb522f0481a633303531303230313030303433363330333430323031303030323034386661326331333430323033376131616664303230343239326135363331303230343635363432656263303431306461326565346464376431393466376265316135666438643539343531666366303230333761316462393032303130303034313430303030303030383636363936633635373437393730363530303030303030343331333033303333020465642ebc04350000000866696c657479706500000004313030330000000b646f776e656e63727970740000000130000000047175696300000001310400&amp;filetype=1003&amp;videotype=0&amp;subvideotype=0&amp;term=android&amp;video_codec=0]\",\"font\":0,\"sender\":{\"user_id\":2409808180,\"nickname\":\"是憨憨の小师哟~（社畜版）\",\"card\":\"是憨憨の小师哟~（社畜版）\",\"role\":\"admin\",\"title\":\"\",\"level\":\"\"}}\n");

        MessageEvent.GroupMessageEvent ev = s.transfer(MessageEvent.GroupMessageEvent.class);

        System.out.println(ev.getMessage().contentToString());
    }

    @Test
    void testFileMessage() {
        EventSource s = new EventSource("{\"time\":1701406702,\"self_id\":485184047,\"post_type\":\"message\",\"message_type\":\"group\",\"sub_type\":\"normal\",\"message_id\":699349934,\"group_id\":914085636,\"peer_id\":485184047,\"user_id\":2207540782,\"message\":[{\"data\":{\"sub\":\"\",\"biz\":102,\"size\":6952,\"expire\":0,\"name\":\"SkipMap.kt\",\"id\":\"/12f02b10-ed2e-41c2-b9e2-3369a0ff0fdb\",\"url\":\"https://122.97.71.253/ftn_handler/a4c0672c97e10be12fc557e2b6c1cfacbc26ba5c7a8238fdcf1f1177603ba464d6b7a8dcccb11eb73a73016a33c072862b6180983638b7c238fca7e8ef643e69/?fname=/12f02b10-ed2e-41c2-b9e2-3369a0ff0fdb&client_proto=qq&client_appid=537180568&client_type=android&client_ver=8.9.85&client_down_type=auto&client_aio_type=unk\"},\"type\":\"file\"}],\"raw_message\":\"[CQ:file,sub=,biz=102,size=6952,expire=0,name=SkipMap.kt,id=/12f02b10-ed2e-41c2-b9e2-3369a0ff0fdb,url=https://122.97.71.251/ftn_handler/93c74b86e43c0d1c12fcd3864b27fe40f9f55060943e66d9d7af143e7d1dbe7e1a222a8b9433f93b67f4c89037ac9a0f3f465ebff835827bd34b1b11ebc06ebc/?fname=/12f02b10-ed2e-41c2-b9e2-3369a0ff0fdb&amp;client_proto=qq&amp;client_appid=537180568&amp;client_type=android&amp;client_ver=8.9.85&amp;client_down_type=auto&amp;client_aio_type=unk]\",\"font\":0,\"sender\":{\"user_id\":2207540782,\"nickname\":\"看到我叫我补化学基础，考太烂了\",\"card\":\"看到我叫我补化学基础，考太烂了\",\"role\":\"member\",\"title\":\"\",\"level\":\"\"}}\n");
        MessageEvent.GroupMessageEvent ev = s.transfer(MessageEvent.GroupMessageEvent.class);
        System.out.println(ev.getMessage());
        System.out.println(ev.getMessage().contentToString());
    }
}