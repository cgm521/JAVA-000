package com.example.nettygateway.router;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;

import com.example.nettygateway.redis.RedisUtil;
import com.google.common.collect.Lists;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @Author:wb-cgm503374
 * @Description: 地址注册类
 * @Date:Created in 2020/11/1 22:15
 */
@Slf4j
@Component
public class HttpEndpointRouter {
    private final static String CONFIG_URL = "http://localhost:8070/pull";
    private static List<String> APP_LIST = Lists.newArrayList("order");
    private final static String REMOTE_ADDRESS_STATUS_KEY = "REMOTE_ADDRESS_STATUS_";

    private final static ConcurrentHashMap<String, List<String>> REMOTE_ADDRESS_MAP = new ConcurrentHashMap<>();

    @Resource
    private RedisUtil redisUtil;
    /**
     * 请求路径
     *
     * @param fullRequest
     * @return 随机路由地址
     */
    public String route(FullHttpRequest fullRequest) {
        String uri = fullRequest.uri();
        String app = uri.split("/")[1];
        List<String> addressList = pull(app);
        if (!CollectionUtils.isEmpty(addressList)) {
            Random rand = new Random();
            int i = rand.nextInt(addressList.size());
            return addressList.get(i);
        }
        return null;
    }

    private List<String> pull(String app) {
        List<String> list = Lists.newArrayList();
        if (!APP_LIST.contains(app)) {
            return list;
        }
        try {
            list = REMOTE_ADDRESS_MAP.get(app);
            if (!CollectionUtils.isEmpty(list) && Boolean.TRUE.equals(redisUtil.get(REMOTE_ADDRESS_STATUS_KEY + app))) {
                return list;
            } else {
                HttpClient client = new HttpClient();
                client.getParams().setContentCharset("UTF-8");
                GetMethod getMethod = new GetMethod(CONFIG_URL + "?APP=" + app);
                client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
                int status = client.executeMethod(getMethod);
                if (status == HttpStatus.SC_OK) {
                    log.info("地址拉取成功");
                    String responseBodyAsString = getMethod.getResponseBodyAsString();
                    System.out.println(responseBodyAsString);
                    list = JSONObject.parseArray(responseBodyAsString, String.class);
                    REMOTE_ADDRESS_MAP.put(app, list);
                    // 远程地址有效时间为60s，或者配置中心把地址失效，重新拉取
                    redisUtil.set(REMOTE_ADDRESS_STATUS_KEY + app, true, 60);
                } else {
                    log.error("地址拉取失败 " + status);
                }
            }
        } catch (IOException e) {
            log.error("地址拉取失败", e);
        } return list;
    }

    private void cleanRoute(String app) {
        REMOTE_ADDRESS_MAP.remove(app);
    }
}
