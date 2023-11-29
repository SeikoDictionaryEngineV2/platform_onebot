package io.github.seikodictionaryenginev2.platform_onebot.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * id    string	文件 ID
 * name	string	文件名
 * size	number (int64)	文件大小（字节数）
 * busid	number (int64)	busid（目前不清楚有什么作用）
 * @Author kagg886
 * @Date 2023/11/27 上午10:56
 */
@Getter
@Setter
public class RemoteFile {
    private String id;
    private String name;
    private long number;
    private long busid;
    private String url;
}
