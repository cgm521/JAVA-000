package com.example.tradeasseta;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@Slf4j
@SpringBootApplication
//@ImportResource({"classpath:spring-dubbo.xml"})
//@DubboComponentScan("com.example.tradeasseta.service.impl")
public class TradeAssetAApplication {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(TradeAssetAApplication.class, args);
        log.info("start end...");
    }

}
