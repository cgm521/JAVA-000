package io.kimmking.rpcfx.demo.consumer;

import io.kimmking.rpcfx.proxy.ByteBuddyRpcfx;
import io.kimmking.rpcfx.proxy.CglibRpcfx;
import io.kimmking.rpcfx.proxy.Rpcfx;
import io.kimmking.rpcfx.demo.api.Order;
import io.kimmking.rpcfx.demo.api.OrderService;
import io.kimmking.rpcfx.demo.api.User;
import io.kimmking.rpcfx.demo.api.UserService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpcfxClientApplication {

    // 二方库
    // 三方库 lib
    // nexus, userserivce -> userdao -> user
    //

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

        // UserService service = new xxx();
        // service.findById

        UserService userService = ByteBuddyRpcfx.create(UserService.class, "http://localhost:8080/");
        User user = userService.findById(1);
        System.out.println("find user id=1 from server: " + user.getName());
        System.out.println("111111>>>" + userService);


//        OrderService orderService = CglibRpcfx.create(OrderService.class, "http://localhost:8080/");
//        Order order = orderService.findOrderById(1992129);
//        System.out.println(String.format("find order name=%s, amount=%f", order.getName(), order.getAmount()));
//        System.out.println("111111>>>" + orderService);


    }

}
