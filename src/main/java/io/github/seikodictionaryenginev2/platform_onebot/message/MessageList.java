package io.github.seikodictionaryenginev2.platform_onebot.message;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/26 下午10:45
 */
public class MessageList extends ArrayList<SingleMessage> {

    @Override
    public boolean add(SingleMessage origin) {
        //这里的转码会破坏原有消息结构
        SingleMessage transfer = switch (origin.getType()) {
            case "text" -> origin.to(SingleMessage.PlainText.class);
            case "face", "mface" -> origin.to(SingleMessage.Face.class); //小emoji为数字，大emoji为md5
            case "image" -> origin.to(SingleMessage.Image.class);
            case "record" -> origin.to(SingleMessage.Record.class);
            case "video" -> origin.to(SingleMessage.Video.class);
            case "at" -> origin.to(SingleMessage.At.class);
            case "reply" -> origin.to(SingleMessage.QuoteReply.class);
            default -> throw new IllegalStateException("Unexpected value: " + origin.getType());
        };
        return super.add(transfer);
    }

    public String contentToString() {
        return stream().map(SingleMessage::contentToString).collect(Collectors.joining());
    }

    //上面的add方法会破坏原有消息结构，因此这里要重新转回去
    public MessageList transferToSend() {
        MessageList l = new MessageList();
        l.addAll(
                stream().map((v) -> {
                    SingleMessage m = new SingleMessage();
                    m.setType(v.getType());
                    m.setData(v.writeData());
                    return m;
                }).collect(Collectors.toList())
        );
        return l;
    }
}
