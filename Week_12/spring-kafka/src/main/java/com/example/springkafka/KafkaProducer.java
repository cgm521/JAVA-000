package com.example.springkafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2021/1/12 下午10:40
 */
@Slf4j
@RestController
public class KafkaProducer {
    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("/kafka/normal/{message}")
    public void sendMessage1(@PathVariable("message") String normalMessage) throws ExecutionException, InterruptedException {
        ListenableFuture<SendResult<String, Object>> f = kafkaTemplate.send("test32", normalMessage);
        System.out.println(f);
        SendResult<String, Object> result = f.get();
        System.out.println(result);
    }

    @GetMapping("/kafka/callback/{topic}/{message}")
    public void send2(@PathVariable("topic") String topic, @PathVariable("message") String message) {
        kafkaTemplate.send(topic, message, message).addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("发送消息失败：" + throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                String topic = stringObjectSendResult.getRecordMetadata().topic();
                int partition = stringObjectSendResult.getRecordMetadata().partition();
                long offset = stringObjectSendResult.getRecordMetadata().offset();
                log.info("发送消息成功：topic:{},partition:{},offset:{}", topic, partition, offset);
            }
        });
    }

    @GetMapping("/kafka/transation/{message}")
    public void send3(@PathVariable("message") String message) {
        kafkaTemplate.executeInTransaction(operations -> {
            operations.send("test32", message);
            throw new RuntimeException("fail");
        });
    }

    @GetMapping("/kafka/notransation/{message}")
    public void send4(@PathVariable("message") String message) throws InterruptedException {
        kafkaTemplate.send("test32", message);
        TimeUnit.SECONDS.sleep(10);
        throw new RuntimeException("fail");
    }
}