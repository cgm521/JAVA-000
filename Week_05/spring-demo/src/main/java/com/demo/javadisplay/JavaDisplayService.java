package com.demo.javadisplay;

import com.demo.service.CService;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/18 10:27 下午
 */

public class JavaDisplayService {
    private CService cService;

    public void sayHi() {
        System.out.println("I am JavaDisplayService sayHi start");
        cService.sayHi();
        System.out.println("JavaDisplayService end");
    }

    public CService getcService() {
        return cService;
    }

    public void setcService(CService cService) {
        this.cService = cService;
    }
}
