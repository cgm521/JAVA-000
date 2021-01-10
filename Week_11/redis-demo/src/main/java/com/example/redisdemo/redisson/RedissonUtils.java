package com.example.redisdemo.redisson;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2021/1/6 下午9:56
 */
@Configuration
public class RedissonUtils {
    private static RedissonClient redissonClient;
    @PostConstruct
    public void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:7001");
        redissonClient = Redisson.create(config);

    }

    /**
     * 库存核减
     * @param key
     * @return
     */
    public static boolean stockDecr(String key) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
        if (atomicLong.get()<=0) {
            return false;
        }
        redissonClient.getLock("").lock();
        return atomicLong.decrementAndGet()>=0;
    }

    /**
     * 初始化库存
     * @param key
     * @param num
     */
    public static void initStock(String key, long num) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
        atomicLong.set(num);
        atomicLong.expire(3, TimeUnit.MINUTES);
    }

    public static long getStock(String key) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
        return atomicLong.get();
    }
}
