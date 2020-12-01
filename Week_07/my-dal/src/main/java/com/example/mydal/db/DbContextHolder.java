package com.example.mydal.db;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/1 9:46 下午
 */
@Slf4j
public class DbContextHolder {
    public static final String WRITE = "write";
    public static final String READ = "read";
    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDbType(String dbType) {
        if (dbType == null) {
            log.error("dbType为空");
            throw new NullPointerException();
        }
        log.info("设置dbType为：{}", dbType);
        contextHolder.set(dbType);
    }

    public static String getDbType() {
        return contextHolder.get() == null ? WRITE : contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
