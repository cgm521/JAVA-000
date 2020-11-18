package com.demo.xmldisplay;

import com.demo.service.AService;
import com.demo.service.BService;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/18 10:03 下午
 */

public class XmlDisplayService {
    private AService aService;

    private BService bService;
    public void sayHi() {
        System.out.println("I am XmlDisplayService sayHi start");
        aService.sayHi();
        bService.sayHi();
        System.out.println("XmlDisplayService end");
    }

    public AService getaService() {
        return aService;
    }

    public void setaService(AService aService) {
        this.aService = aService;
    }

    public com.demo.service.BService getbService() {
        return bService;
    }

    public void setbService(com.demo.service.BService bService) {
        this.bService = bService;
    }
}
