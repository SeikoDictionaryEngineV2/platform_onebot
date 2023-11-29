# Platform_OneBot

是SeikoDIC对OneBot11平台的一个简单适配，计划长期支持Shamrock。



## 1. 事件匹配器

1. 群消息

   使用`[群]`进行匹配，例如：

   ```text
   [群]111
   222
   ```

2. 私聊消息

   使用`[私聊]`进行匹配，例如：

   ```text
   [私聊]222
   333
   ```

## 2. 内置变量

收到[群|好友]消息事件一定会内置的变量如下：

| 变量名 | 解释                       | 类型     | 例子       |
| ------ | -------------------------- | -------- | ---------- |
| 上下文 | 词条被触发时程序收到的包体 | 集合对象 | 略         |
| BOT    | 触发词条的机器人qq号       | 数字     | 1693256674 |
| QQ     | 触发词条的人的qq号         | 数字     | 485184047  |
| 昵称   | 触发词条的人的用户名       | 字符串   | 绪山真寻   |

其中：

群消息会内置：

| 变量名 | 解释                               | 类型       | 例子               |
| ------ | ---------------------------------- | ---------- | ------------------ |
| 群号   | 词条被触发的群号                   | 数字       | 572306032          |
| 名片   | 词条被触发的群名片(若未设置则为空) | 字符串     | 绪山真寻           |
| 权限   | 词条被处罚的人的权限               | 特定字符串 | owner/admin/member |



## 3. 特定指令

### 3.1 OneBot发包

- **指令**：`$OneBot发包 包类型 包体$`

- **返回值：**集合对象，代表本次回调的结果，成功示例如下：

  ```json
  {
      "data": {
          "message_id": 1597100000,
          "time": 1701097157
      },
      "echo": "5ec64699-8e39-4259-9928-115c6f6a177c",
      "retcode": 0,
      "status": "ok"
  }
  
  ```

  > 完整的回调集合请参考：https://whitechi73.github.io/OpenShamrock/api/request-response.html#%E5%93%8D%E5%BA%94

- **描述：**向连接的OneBot客户端发送一个数据包。本指令为阻塞式方法，在发送前会清空缓冲区。

- **示例：**

  ```text
  [群]ABC
  DATA<-///
  {
      "group_id": 572360632,
      "message": "你好啊\n---来自SeikoDICV2的跨群发送",
  }
  ///
  K<-$OneBot发包 send_group_msg {DATA}$
  {K}
  ```
