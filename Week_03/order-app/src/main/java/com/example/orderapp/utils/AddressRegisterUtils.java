package com.example.orderapp.utils;

import java.io.IOException;

import javax.annotation.PreDestroy;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.stereotype.Component;

/**
 * @Author:wb-cgm503374
 * @Description: 地址注册类
 * @Date:Created in 2020/11/1 22:15
 */
@Slf4j
@Component
public class AddressRegisterUtils {
    private final static String CONFIG_REGISTER_URL = "http://localhost:8070/register";
    private final static String CONFIG_DESTROY_URL = "http://localhost:8070/destroy";

    public static void register() {
        try {
            HttpClient client = new HttpClient();
            client.getParams().setContentCharset("UTF-8");
            GetMethod getMethod = new GetMethod(CONFIG_REGISTER_URL + "?APP=order&ADDRESS=http://localhost:8091");
            client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            int status = client.executeMethod(getMethod);
            if (status == HttpStatus.SC_OK) {
                log.info("地址上报成功");
            } else {
                log.info("地址上报失败 " + status);
            }

        } catch (IOException e) {
            log.error("地址上报失败", e);
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            HttpClient client = new HttpClient();
            client.getParams().setContentCharset("UTF-8");
            GetMethod getMethod = new GetMethod(CONFIG_DESTROY_URL + "?APP=order&ADDRESS=http://localhost:8091");
            client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            int status = client.executeMethod(getMethod);
            if (status == HttpStatus.SC_OK) {
                log.info("服务销毁上报成功");
            } else {
                log.info("服务销毁上报失败 " + status);
            }

        } catch (IOException e) {
            log.error("服务销毁上报失败", e);
        }
    }

}
