package io.kimmking.rpcfx.demo.consumer;

import io.kimmking.rpcfx.io.client.AbsClient;
import io.kimmking.rpcfx.io.client.NettyClient;
import io.kimmking.rpcfx.io.server.NettyServerClient;
import io.kimmking.rpcfx.proxy.ByteBuddyRpcfx;
import io.kimmking.rpcfx.proxy.CglibRpcfx;
import io.kimmking.rpcfx.proxy.Rpcfx;
import io.kimmking.rpcfx.demo.api.Order;
import io.kimmking.rpcfx.demo.api.OrderService;
import io.kimmking.rpcfx.demo.api.User;
import io.kimmking.rpcfx.demo.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@SpringBootApplication
public class RpcfxClientApplication {

    // 二方库
    // 三方库 lib
    // nexus, userserivce -> userdao -> user
    //
    @Autowired
    private AbsClient client;
    @Autowired
    private ByteBuddyRpcfx byteBuddyRpcfx;

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        SpringApplication.run(RpcfxClientApplication.class, args);
    }

    @GetMapping("/findId")
    public User findId() throws InstantiationException, IllegalAccessException {
        log.info("findId");
        UserService userService = byteBuddyRpcfx.create(UserService.class, "http://localhost:8081/");
        return userService.findById(1);
    }

    @Bean
    public AbsClient creditClient() {
        // return new HttpClient();
        return new NettyClient();
    }

    @Bean
    public ByteBuddyRpcfx byteBuddyRpcfx() {
        return new ByteBuddyRpcfx(client);
    }
}
