package com.example.redisdemo;

import com.example.redisdemo.jedis.JedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisDemoApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(JedisUtils.lock("LOCK_1", "111", 60));
    }

}
