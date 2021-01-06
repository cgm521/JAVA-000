package com.example.redisdemo.controller;

import com.example.redisdemo.redisson.RedissonUtils;
import com.example.redisdemo.redistemplate.RedisTemplatUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2021/1/6 下午10:32
 */
@RestController
public class StockController {
    @Resource
    private RedisTemplatUtils redisTemplatUtils;
    @GetMapping("/init")
    public void initStock(@RequestParam("num") long num) {
        RedissonUtils.initStock("redisson_STOCK", num);
        redisTemplatUtils.initStock("redisTemplae_STOCK",num);
    }

    @GetMapping("/redissonBuy")
    public boolean redissonBuy() {
        return RedissonUtils.stockDecr("redisson_STOCK");
    }

    @GetMapping("/redisTemplaeBuy")
    public boolean redisTemplaeBuy() {
        return redisTemplatUtils.decrStock("redisTemplae_STOCK");
    }
}
