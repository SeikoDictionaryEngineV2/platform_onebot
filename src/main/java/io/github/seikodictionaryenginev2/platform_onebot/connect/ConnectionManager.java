package io.github.seikodictionaryenginev2.platform_onebot.connect;

import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/12/1 下午12:39
 */
public class ConnectionManager extends ConcurrentHashMap<String, BotConnection> {
    private static final ConnectionManager INSTANCE = new ConnectionManager();


    public static ConnectionManager getInstance() {
        return INSTANCE;
    }

    public BotConnection register(String uri) throws InterruptedException {
        BotConnection c = new BotConnection(URI.create(uri));
        c.connectBlocking();
        INSTANCE.put(c.getUuid(), c);
        return c;
    }

    protected BotConnection register(String uri,String uuid) throws InterruptedException {
        BotConnection c = new BotConnection(URI.create(uri),uuid);
        c.connectBlocking();
        INSTANCE.put(c.getUuid(), c);
        return c;
    }

    public void unregister(String uuid) {
        BotConnection c = INSTANCE.remove(uuid);
        if (!c.isClosed()) {
            c.close();
        }
    }
}
