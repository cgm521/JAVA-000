package com.example.nettygateway.utils;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

/**
 * @Author:wb-cgm503374
 * @Description:
 * @Date:Created in 2020/11/1 18:05
 */
public class YmlUtils {

    public static String getProperty(String key) {
        Yaml yaml = new Yaml();
        InputStream resourceAsStream = YmlUtils.class.getClassLoader().getResourceAsStream("application.yml");
        Object property = yaml.load(resourceAsStream);
        String[] split = key.split("\\.");
        for (String s : split) {
            property = ((Map)property).get(s);
        }
        return String.valueOf(property);

    }
}
