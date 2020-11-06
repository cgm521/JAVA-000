package com.example.configserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.example.configserver.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:wb-cgm503374
 * @Description: 服务配置中心控制器
 * @Date:Created in 2020/11/1 22:28
 */
@Slf4j
@RestController
public class ServerConfigController {
    private final static String APP = "APP";
    private final static String REMOTE_ADDRESS = "ADDRESS";
    private final static String REMOTE_ADDRESS_STATUS_KEY = "REMOTE_ADDRESS_STATUS_";
    /**
     * 远程服务地址列表，
     */
    private final static ConcurrentHashMap<String, Set<String>> REMOTE_ADDRESS_MAP = new ConcurrentHashMap<>();

    @Resource
    private RedisUtil redisUtil;
    /**
     * 服务注册
     *
     * @param request
     */
    @GetMapping("/register")
    public void register(HttpServletRequest request) {
        String app = request.getParameter(APP);
        String remoteAddress = request.getParameter(REMOTE_ADDRESS);
        if (REMOTE_ADDRESS_MAP.containsKey(app)) {
            REMOTE_ADDRESS_MAP.get(app).add(remoteAddress);
        } else {
            synchronized (REMOTE_ADDRESS_MAP) {
                Set<String> remoteAddressList = Collections.synchronizedSet(new TreeSet<>());
                remoteAddressList.add(remoteAddress);
                REMOTE_ADDRESS_MAP.put(app, remoteAddressList);
                redisUtil.del(REMOTE_ADDRESS_STATUS_KEY + app);
            }
        }
        log.info("应用{}地址注册成功，address:{}", app, remoteAddress);
    }

    /**
     * 服务销毁通知
     *
     * @param request
     */
    @GetMapping("/destroy")
    public void destroy(HttpServletRequest request) {
        String app = request.getParameter(APP);
        String remoteAddress = request.getParameter(REMOTE_ADDRESS);
        if (!REMOTE_ADDRESS_MAP.containsKey(app)) {
            return;
        } else {
            synchronized (REMOTE_ADDRESS_MAP) {
                Set<String> set = REMOTE_ADDRESS_MAP.get(app);
                set.remove(remoteAddress);
                redisUtil.del(REMOTE_ADDRESS_STATUS_KEY + app);
            }
        }
        log.info("应用{}地址销毁成功，address:{}", app, remoteAddress);
    }

    /**
     * 服务地址拉取
     *
     * @param request
     * @return
     */
    @GetMapping("/pull")
    public Set<String> pull(HttpServletRequest request) {
        String app = request.getParameter(APP);
        return REMOTE_ADDRESS_MAP.get(app);
    }
}
