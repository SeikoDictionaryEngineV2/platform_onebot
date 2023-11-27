package io.github.seikodictionaryenginev2.platform_onebot.connect;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class BotClientTest {
    @Test
    void testConnect() throws InterruptedException {
        WebSocketClient client = new WebSocketClient(URI.create("ws://10.19.149.143:5800/")) {
            @Override
            public void onOpen(ServerHandshake handshake) {

            }

            @Override
            public void onMessage(String message) {
                System.out.println(message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.err.println("Close!" + code + ":" + getResourceDescriptor());
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        };

        client.connectBlocking();

        System.out.println("Connect Success!" + client.isOpen());
        while (!client.isFlushAndClose()) {
        }
        System.out.println("Connect Closed");
    }

}