package io.github.seikodictionaryenginev2.platform_onebot.message;

import java.util.ArrayList;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/26 下午10:45
 */
public class MessageList extends ArrayList<SingleMessage> {

    @Override
    public boolean add(SingleMessage origin) {
        SingleMessage transfer = switch (origin.getType()) {
            case "text" -> origin.to(SingleMessage.PlainText.class);
            case "face" -> origin.to(SingleMessage.Face.class);
            case "image" -> origin.to(SingleMessage.Image.class);
            case "record" -> origin.to(SingleMessage.Record.class);
            case "video" -> origin.to(SingleMessage.Video.class);
            case "at" -> origin.to(SingleMessage.At.class);
            default -> throw new IllegalStateException("Unexpected value: " + origin.getType());
        };


        return super.add(transfer);
    }
}
