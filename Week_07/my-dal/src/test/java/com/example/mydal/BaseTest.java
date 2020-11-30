package com.example.mydal;

import com.example.mydal.support.SequenceSupport;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class BaseTest {
    @Resource
    public SequenceSupport sequenceSupport;
    @Test
    void contextLoads() {
    }

}
