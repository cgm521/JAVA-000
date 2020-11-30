package com.example.mydal.support;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:wb-cgm503374
 * @Description 序列区间
 * @Date:Created in 2020/11/29 10:38 下午
 */

public class SequenceRange {
    /**
     * 最小值
     */
    private long min;

    /**
     * 最大值
     */
    private long max;

    /**
     * 当前值
     */
    private AtomicLong value;

    /**
     * 是否使用完
     */
    private volatile boolean over = false;


    private final Lock lock = new ReentrantLock();

    public SequenceRange(long min, long max) {
        this.min = min;
        this.max = max;
        this.value = new AtomicLong(min);
    }

    public void reset(long min, long max) {
        this.min = min;
        this.max = max;
        this.value = new AtomicLong(min);
        this.over = false;
    }

    /**
     * 获取当前值并自增一
     *
     * @return
     */
    public long getAndIncrement() {
        if (over) {
            return -1;
        }
        long currValue = value.getAndIncrement();
        if (currValue > max) {
            over = true;
            return -1;
        }
        return currValue;
    }

    public Lock getLock() {
        return lock;
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }

    public AtomicLong getValue() {
        return value;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }
}
