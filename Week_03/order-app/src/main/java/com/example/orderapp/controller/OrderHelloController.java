package com.example.orderapp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:wb-cgm503374
 * @Description:
 * @Date:Created in 2020/11/1 16:52
 */
@Slf4j
@RestController
public class OrderHelloController {
    @GetMapping("/api/hello")
    public String sayHello(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        log.info("sayHello request traceId:{},param:{}", request.getHeader("traceId"), JSONObject.toJSONString(parameterMap));
        return "order hello world";
    }

    @PostMapping("/api/query")
    public String query(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        log.info("query request traceId:{},param:{}", request.getHeader("traceId"), JSONObject.toJSONString(parameterMap));
        return "query order";
    }
}
