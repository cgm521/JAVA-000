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
