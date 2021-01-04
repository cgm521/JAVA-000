package com.example.redisdemo.jedis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2021/1/4 下午11:15
 */
@Data
@ConfigurationProperties(prefix = "java.jedis")
public class JedisProperties {
    private String host;

    private int port;

    private String password;

    private int timeout;

    private int maxTotal;

    private int maxIdle;

    private int maxWaitMillis;

}
