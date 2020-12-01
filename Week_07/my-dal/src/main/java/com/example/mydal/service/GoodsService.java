package com.example.mydal.service;

import com.example.mydal.annotation.ReadOnly;
import com.example.mydal.entity.Goods;

import java.util.List;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/30 11:00 下午
 */

public interface GoodsService {
    List<Goods> selectAll();

    Goods selectById(Long id);

    /**
     * @param id
     * @param updateCount 要增加的数量 +|-
     * @return
     */
    Boolean updateCount(Long id, Long updateCount) throws Exception;
}
