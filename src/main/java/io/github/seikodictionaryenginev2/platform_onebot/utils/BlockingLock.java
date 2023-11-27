package io.github.seikodictionaryenginev2.platform_onebot.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author kagg886
 * @Date 2023/11/27 下午2:09
 */
@Data
public class BlockingLock<T> {
    private CountDownLatch latch;
    private T data;

    public BlockingLock() {
        this.latch = new CountDownLatch(1);
    }

    public void release(T data) {
        this.data = data;
        this.latch.countDown();
    }

    public void await() throws InterruptedException {
        latch.await();
    }

    public void await(int time, TimeUnit unit) throws InterruptedException {
        latch.await(time,unit);
    }

}
