package com.example.redisdemo.jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2021/1/4 下午11:38
 */
@Configuration
@EnableConfigurationProperties(JedisProperties.class)
public class JedisPoolUtils {
    @Autowired
    private JedisProperties prop;

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(prop.getMaxTotal());
        config.setMaxIdle(prop.getMaxIdle());
        config.setMaxWaitMillis(prop.getMaxWaitMillis());
        config.setTestOnBorrow(true);
        JedisPool jedisPool;
        if ("".equals(prop.getPassword())) {
            jedisPool = new JedisPool(config, prop.getHost(), prop.getPort(), prop.getTimeout());
        } else {
            jedisPool = new JedisPool(config, prop.getHost(), prop.getPort(), prop.getTimeout(), prop.getPassword());
        }
        // 此处为JedisUtils设置了jedisPool
        JedisUtils.setJedisPool(jedisPool);
        return jedisPool;

    }
}
