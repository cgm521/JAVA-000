package com.example.nettygateway;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class NettyGatewayApplication implements CommandLineRunner {

    @Resource
    private NettyGatewayServer nettyGatewayServer;
    public static void main(String[] args) {
        SpringApplication.run(NettyGatewayApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        nettyGatewayServer.run();
    }
}