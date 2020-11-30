package com.example.mydal.support;

import com.example.mydal.dao.SequenceMapper;
import com.example.mydal.entity.Sequence;
import com.example.mydal.enums.SequenceEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/29 10:22 下午
 */
@Service
public class SequenceSupport implements InitializingBean {
    private static final Long STEP_SIZE = 100L;
    private static Map<SequenceEnum, SequenceRange> map = new ConcurrentHashMap<>();
    @Resource
    private SequenceMapper sequenceMapper;

    public Long getSequence(SequenceEnum sequenceEnum) {
        SequenceRange sequenceRange = map.get(sequenceEnum);
        if (null == sequenceRange) {
            // 懒加载
            sequenceRange = initSequenceRange(sequenceEnum);
        }
        long value = sequenceRange.getAndIncrement();
        if (value == -1) {
            // 序列号使用完，重新获取
            sequenceRange.getLock().lock();
            try {
                for (; ; ) {
                    if (sequenceRange.isOver()) {
                        // 使用完，重新更新 获取下一段序列值
                        long newMin = nextSequence(sequenceEnum.name());
                        sequenceRange.reset(newMin + 1, newMin + STEP_SIZE);
                    }
                    value = sequenceRange.getAndIncrement();
                    if (value == -1) {
                        continue;
                    }
                    break;
                }
            } finally {
                sequenceRange.getLock().unlock();
            }
        }
        return value;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        for (SequenceEnum sequenceEnum : SequenceEnum.values()) {
//            initSequenceRange(sequenceEnum);
//        }
    }

    private SequenceRange initSequenceRange(SequenceEnum sequenceEnum) {
        long min = nextSequence(sequenceEnum.name());
        SequenceRange sequenceRange = new SequenceRange(min + 1, min + STEP_SIZE);
        map.put(sequenceEnum, sequenceRange);
        return sequenceRange;
    }
    /**
     * 获取下一段序列号的开始
     *
     * @param name
     * @return
     */
    private long nextSequence(String name) {
        Sequence sequence = sequenceMapper.selectByName(name);
        if (sequence == null) {
            // 设置初始序列值
            sequence = new Sequence();
            sequence.setName(name);
            sequence.setValue(STEP_SIZE);
            sequenceMapper.insert(sequence);
            return 0L;
        } else {
            long min = sequence.getValue();
            sequence.setValue(sequence.getValue() + STEP_SIZE);
            sequenceMapper.updateByPrimaryKey(sequence);
            return min;
        }
    }
}
