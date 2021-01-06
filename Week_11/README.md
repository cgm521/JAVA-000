# 4、(必做)基于Redis封装分布式数据操作:
## 1)在Java中实现一个简单的分布式锁;
- 加锁 [JedisUtils#lock](redis-demo/src/main/java/com/example/redisdemo/jedis/JedisUtils.java#L32-L51)
```java
     /**
     * 首先，为了确保分布式锁可用，我们至少要确保锁的实现同时满足以下四个条件：
     *
     *  1、互斥性。在任意时刻，只有一个客户端能持有锁。
     *  2、不会发生死锁。即使有一个客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁。
     *  3、具有容错性。只要大部分的Redis节点正常运行，客户端就可以加锁和解锁。
     *  4、解铃还须系铃人。加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给解了。
     */
    /**
     * 加锁
     *
     * @param lockKey
     * @param requestId  请求唯一id，缓存value，加锁者持有，释放锁时验证使用
     * @param expireTime EX 秒 PX 毫秒
     * @return
     */
    public static boolean tryLock(String lockKey, String requestId, int expireTime) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return OK.equals(jedis.set(lockKey, requestId, "NX", "EX", expireTime));
        } catch (Exception e) {
            log.error("jedis lock error =", e);
        } finally {
            close(jedis);
        }
        return false;
    }
```
- 释放锁[JedisUtils#unLock](redis-demo/src/main/java/com/example/redisdemo/jedis/JedisUtils.java#L53-L74)
```java
   /**
     * 释放锁
     *
     * @param lockKey   锁key
     * @param requestId 请求唯一id，缓存value，加锁者持有，防止其他人误释放锁
     * @return
     */
    public static boolean unLock(String lockKey, String requestId) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
            return RELEASE_SUCCESS.equals(result);
        } catch (Exception e) {
            log.error("jedis get unLock =", e);
        } finally {
            close(jedis);
        }
        return false;
    }
```
- 测试类[RedisDemoApplicationTests#concurrentLock](redis-demo/src/test/java/com/example/redisdemo/RedisDemoApplicationTests.java#L20-L44)
```java
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
```
## 2)在Java中实现一个分布式计数器，模拟减库存。
- redisson实现 [RedissonUtils.java](redis-demo/src/main/java/com/example/redisdemo/redisson/RedissonUtils.java#L12-L56)
```java
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
```
- redisTemplat 配合lua脚本实现
  - [RedisTemplateConfig.java](redis-demo/src/main/java/com/example/redisdemo/redistemplate/RedisTemplateConfig.java)
  - [RedisTemplatUtils.java](redis-demo/src/main/java/com/example/redisdemo/redistemplate/RedisTemplatUtils.java)
```java
package com.example.redisdemo.redistemplate;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2021/1/6 下午10:41
 */
@Configuration
public class RedisTemplatUtils {
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 执行扣库存的脚本
     */
    private static final String STOCK_DECR_LUA = "" +
            "if (redis.call('exists', KEYS[1]) == 1) then" +
            "   local stock = tonumber(redis.call('get', KEYS[1]));" +
            "   if (stock == -1) then " +
            "       return false; " +
            "   end;" +
            "   if (stock > 0) then " +
            "       redis.call('incrby', KEYS[1], -1); " +
            "       return true; " +
            "   end;" +
            "   return false;" +
            "end;" +
            "return false;";
    public void initStock(String key, long num) {
        redisTemplate.opsForValue().set(key, num, 2, TimeUnit.MINUTES);
    }

    public Boolean decrStock(String key) {
        DefaultRedisScript<Boolean> script = new DefaultRedisScript<>(STOCK_DECR_LUA,Boolean.class);
        return (Boolean) redisTemplate.execute(script, Collections.singletonList(key));
    }
}

```
