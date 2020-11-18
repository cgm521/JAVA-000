package com.demo.javadisplay;

import com.demo.service.CService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/18 10:25 下午
 */
@Configuration
public class BeanConfig {

    @Bean
    public CService getCService() {
        return new CService();
    }

    @Bean
    public JavaDisplayService getJavaDisplayService() {
        JavaDisplayService javaDisplayService = new JavaDisplayService();
        javaDisplayService.setcService(getCService());
        return javaDisplayService;
    }
}
