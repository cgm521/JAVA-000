package com.demo.autowired;

import com.demo.service.AService;
import com.demo.service.BService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/18 10:18 下午
 */
@Component
public class AutowiredService {
    @Autowired
    private AService aService;

    @Resource
    private BService bService;

    public void sayHi() {
        System.out.println("I am AutowiredService sayHi start");
        aService.sayHi();
        bService.sayHi();
        System.out.println("AutowiredService end");
    }

    public AService getaService() {
        return aService;
    }

    public void setaService(AService aService) {
        this.aService = aService;
    }

    public BService getbService() {
        return bService;
    }

    public void setbService(BService bService) {
        this.bService = bService;
    }
}
