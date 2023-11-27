package io.github.seikodictionaryenginev2.platform_onebot;

import io.github.seikodictionaryenginev2.base.env.DICList;
import io.github.seikodictionaryenginev2.base.env.DictionaryEnvironment;
import io.github.seikodictionaryenginev2.platform_onebot.connect.BotConnection;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/27 下午6:20
 */
public class DictionaryTest {
    @Test
    public void test() throws InterruptedException {
        Path dicRoot = new File("").toPath().resolve("mock");
        System.out.println(dicRoot.toFile().getAbsolutePath());
        DictionaryEnvironment.getInstance().setDicData(dicRoot.resolve("dic-data"));
        DictionaryEnvironment.getInstance().setDicRoot(dicRoot.resolve("dic").toFile());
        DictionaryEnvironment.getInstance().setDicConfigPoint(dicRoot.resolve("dicConfig.json").toFile().getAbsolutePath());
        Registrator.inject();


        DICList.INSTANCE.refresh().forEach((v) -> {
            if (v.success) {
                System.out.println(v.dicName + " load successful");
            } else {
                v.err.printStackTrace();
            }
        });

        BotConnection connection = new BotConnection(URI.create("ws://192.168.1.19:5800/"));
        connection.connectBlocking();
        System.out.println("Connect Success!" + connection.isOpen());
        while (!connection.isFlushAndClose());
        System.out.println("Connect Closed");
    }
}
