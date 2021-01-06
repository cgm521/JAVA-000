package com.example.redisdemo.jedis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2021/1/4 下午11:08
 */
@Slf4j
public class JedisUtils {
    private static JedisPool jedisPool;
    private final static String OK = "OK";
    private static final Long RELEASE_SUCCESS = 1L;


    public static void setJedisPool(JedisPool jedisPool) {
        JedisUtils.jedisPool = jedisPool;
    }


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

    public static boolean lock(String lockKey, String requestId, int expireTime) {
        while (true) {
            if (tryLock(lockKey, requestId, expireTime)) {
                return true;
            }
        }
    }

    public static String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            log.error("jedis get error =", e);
        } finally {
            close(jedis);
        }
        return null;
    }

    public static boolean set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return OK.equals(jedis.set(key, value));
        } catch (Exception e) {
            log.error("jedis get error =", e);
        } finally {
            close(jedis);
        }
        return false;
    }

    private static void close(Jedis jedis) {
        if (null != jedis) {
            jedis.close();
        }
    }
}
