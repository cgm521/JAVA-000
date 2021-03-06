package io.kimmking.rpcfx.demo.provider;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.io.server.NettyServerClient;
import io.kimmking.rpcfx.demo.api.OrderService;
import io.kimmking.rpcfx.demo.api.UserService;
import io.kimmking.rpcfx.server.RpcfxInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RpcfxServerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RpcfxServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// 启动 netty 服务端
		new NettyServerClient().run(resolver);
	}

	@Autowired
	RpcfxInvoker invoker;
	@Autowired
	RpcfxResolver resolver;


	@PostMapping("/")
	public RpcfxResponse invoke(@RequestBody RpcfxRequest request) {
		System.out.println("invoke...");
		return invoker.invoke(request);
	}

	@PostMapping("/xml")
	public String invokeXml(@RequestBody String request) {
		System.out.println("invoke...");
		return invoker.invokeXml(request);
	}

	@Bean
	public RpcfxInvoker createInvoker(@Autowired RpcfxResolver resolver){
		return new RpcfxInvoker(resolver);
	}

	@Bean
	public RpcfxResolver createResolver(){
		return new DemoResolver();
	}

	// 能否去掉name
	//
	@Bean
	public UserService createUserService(){
		return new UserServiceImpl();
	}

	@Bean
	public OrderService createOrderService(){
		return new OrderServiceImpl();
	}

}
