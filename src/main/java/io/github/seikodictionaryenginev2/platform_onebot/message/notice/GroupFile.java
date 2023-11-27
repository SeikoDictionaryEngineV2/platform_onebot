package io.github.seikodictionaryenginev2.platform_onebot.message.notice;

import lombok.Data;

/**
 * @Description
 * id    string	文件 ID
 * name	string	文件名
 * size	number (int64)	文件大小（字节数）
 * busid	number (int64)	busid（目前不清楚有什么作用）
 * @Author kagg886
 * @Date 2023/11/27 上午10:56
 */
@Data
public class GroupFile {
    private String id;
    private String name;
    private long number;
    private long busid;
}
