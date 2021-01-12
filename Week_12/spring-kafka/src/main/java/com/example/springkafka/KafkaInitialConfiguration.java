package com.example.springkafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2021/1/13 上午12:27
 */
@Configurable
public class KafkaInitialConfiguration {
    // 创建一个名为testtopic的Topic并设置分区数为2，分区副本数为1
    @Bean
    public NewTopic initialTopic() {
        return new NewTopic("test-11",2, (short) 1 );
    }


}
