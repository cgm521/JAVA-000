package com.example.mydal.service;

import com.example.mydal.entity.Goods;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/30 11:00 下午
 */

public interface GoodsService {
    Goods selectById(Long id);

    /**
     *
     * @param id
     * @param updateCount  要增加的数量 +|-
     * @return
     */
    Boolean updateCount(Long id, Long updateCount) throws Exception;
}
