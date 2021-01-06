package com.example.redisdemo.redistemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2021/1/6 下午11:55
 */
@Component
@Slf4j
public class RedisMessageListenerAdapter implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("接收到Redis的消息:{}", new String(message.getBody()));
    }
}
