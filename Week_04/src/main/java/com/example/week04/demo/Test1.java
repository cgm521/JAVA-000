package com.example.week04.demo;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/7 5:57 下午
 */

public class Test1 {
    @Test
    public void boolTest() {
        AtomicB b = new AtomicB();
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        b.tran();
                    }
                }
            }).start();
        }
        System.out.println(b.b);
    }
    static class B{
        volatile boolean b = false;

        public void tran() {
            this.setB(!b);
        }

        public boolean isB() {
            return b;
        }

        public void setB(boolean b) {
            this.b = b;
        }
    }
    static class AtomicB {
        AtomicBoolean b = new AtomicBoolean(false);

        public void tran() {
            b.compareAndSet(isB(),!isB());
        }

        public boolean isB() {
            return b.get();
        }

        public void setB(AtomicBoolean b) {
            this.b = b;
        }
    }
}
