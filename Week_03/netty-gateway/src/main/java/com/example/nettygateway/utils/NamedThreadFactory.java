package com.example.nettygateway.utils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:wb-cgm503374
 * @Description: 地址注册类
 * @Date:Created in 2020/11/1 23:15
 */
public class NamedThreadFactory implements ThreadFactory {
    
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    
    private final String namePrefix;
    private final boolean daemon;
    
    public NamedThreadFactory(String namePrefix, boolean daemon) {
        this.daemon = daemon;
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        this.namePrefix = namePrefix;
    }
    
    public NamedThreadFactory(String namePrefix) {
        this(namePrefix, false);
    }
    
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + "-thread-" + threadNumber.getAndIncrement(), 0);
        t.setDaemon(daemon);
        return t;
    }
}