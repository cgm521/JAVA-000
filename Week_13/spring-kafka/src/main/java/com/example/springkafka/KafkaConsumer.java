package com.example.springkafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2021/1/12 下午10:42
 */
@Slf4j
@Component
public class KafkaConsumer {
    @Autowired
    ConsumerFactory consumerFactory;

    /**
     * 单个消费
     * spring.kafka.listener.type=SINGLE
     *
     * @param record
     */
    @KafkaListener(topics = {"test-11"}, errorHandler = "consumerAwareErrorHandler", containerFactory = "filterContainerFactory")
    public void onMessage(ConsumerRecord<?, ?> record) {
        System.out.println("简单消费：" + record.topic() + "-" + record.partition() + "-" + record.value());
    }

    /**
     * 消息转发
     *
     * @param record
     * @return
     */
    @KafkaListener(topics = {"test32"}, errorHandler = "consumerAwareErrorHandler", containerFactory = "filterContainerFactory")
    @SendTo("test-11")
    public String onMessage32(ConsumerRecord<?, ?> record) {
        return "forward message-" + record.topic() + ":" + record.value();
    }

    /**
     * 批量消费
     * spring.kafka.listener.type=BATCH
     *
     * @param records
     */
//    @KafkaListener(topics = {"test32"}, errorHandler = "consumerAwareErrorHandler", containerFactory = "filterContainerFactory")
//    public void batchCustomer(List<ConsumerRecord<?, ?>> records) {
//        log.info("批量消费size:{}", records.size());
//        records.forEach(record -> {
//            System.out.println("批量消费：" + record.topic() + "-" + record.partition() + "-" + record.value());
//        });
//    }


    /**
     * 新建一个异常处理器，用@Bean注入
     *
     * @return
     */
    @Bean
    public ConsumerAwareListenerErrorHandler consumerAwareErrorHandler() {
        return (message, exception, consumer) -> {
            log.error("消息：{},消费异常：{}", message.getPayload(), exception.getMessage());
            return null;
        };
    }

    /**
     * 消息过滤器
     * 返回true的时候消息将会被抛弃，返回false时，消息能正常抵达监听容器。
     *
     * @return
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory filterContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory);
        // 被过滤的消息将被丢弃
        factory.setAckDiscarded(true);
        // 消息过滤策略
        factory.setRecordFilterStrategy(consumerRecord -> {
            if (consumerRecord.value().toString().length() % 2 == 0) {
                return false;
            }
            //返回true消息则被过滤
            log.warn("消息被过滤：{}", consumerRecord);
            return true;
        });
        return factory;

    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> retryKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setRetryTemplate(new RetryTemplate());
        return factory;
    }
}
