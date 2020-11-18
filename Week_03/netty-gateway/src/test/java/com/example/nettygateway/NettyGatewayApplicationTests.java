package com.example.nettygateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class NettyGatewayApplicationTests {
    @Resource
    private NettyGatewayServer nettyGatewayServer;
    @Test
    void contextLoads() {
        nettyGatewayServer.run();
    }

}
