package com.example.redisdemo.controller;

import com.example.redisdemo.jedis.JedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2021/1/5 下午10:03
 */
@Api(tags = "Jedis测试")
@RestController
public class JedisController {

    @ApiOperation("获取锁")
    @GetMapping("/lock")
    public Boolean lock() {
        return JedisUtils.tryLock("LOCK_1", "111", 6000);
    }
}
