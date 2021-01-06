package com.example.redisdemo;

import com.example.redisdemo.jedis.JedisUtils;
import com.example.redisdemo.redistemplate.RedisMessageListenerAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class RedisDemoApplicationTests {

    @Test
    void lock() {
        System.out.println(JedisUtils.tryLock("LOCK_1", "111", 60));
    }

    @Test
    void concurrentLock() throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(2);
        String lockKey = "LOCK_KEY";
        for (int i = 0; i < 2; i++) {
            executorService.execute(() -> {
                String requestId = String.valueOf(Thread.currentThread().getId());
                // 不加锁，打印的是乱序，加锁后是一个线程打印完，在打印下个线程的
                JedisUtils.lock(lockKey, requestId, 3);
                try {
                    for (int j = 0; j < 2; j++) {
                        System.out.println(Thread.currentThread().getName() + "_" + j);
                        TimeUnit.MILLISECONDS.sleep(10);
                    }
                    System.out.println(Thread.currentThread().getName() + "--------------------");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    JedisUtils.unLock(lockKey, requestId);
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
    }

    @Test
    void unLockError() {
        String lockKey = "LOCK_1";
        String requestId = UUID.randomUUID().toString();
        System.out.println(JedisUtils.tryLock(lockKey, requestId, 10));
        System.out.println(JedisUtils.unLock(lockKey,"123"));
    }

    @Resource
    private RedisMessageListenerAdapter redisMessageListenerAdapter;
    @Test
    public void subpub() {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(redisMessageListenerAdapter);
        messageListenerAdapter.afterPropertiesSet();
    }
}
