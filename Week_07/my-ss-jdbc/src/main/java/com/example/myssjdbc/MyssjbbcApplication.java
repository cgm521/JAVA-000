package com.example.myssjdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class MyssjbbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyssjbbcApplication.class, args);
    }

}
