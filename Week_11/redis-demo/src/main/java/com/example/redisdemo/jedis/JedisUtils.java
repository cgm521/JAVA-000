package com.example.redisdemo.jedis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2021/1/4 下午11:08
 */
@Slf4j
public class JedisUtils {
    private static JedisPool jedisPool;

    public static void setJedisPool(JedisPool jedisPool) {
        JedisUtils.jedisPool = jedisPool;
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

    public static String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.set(key, value);
        } catch (Exception e) {
            log.error("jedis get error =", e);
        } finally {
            close(jedis);
        }
        return null;
    }

    public static boolean lock(String key, String value, int expireTime) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value, "NX", "PX", expireTime);
            return true;
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
