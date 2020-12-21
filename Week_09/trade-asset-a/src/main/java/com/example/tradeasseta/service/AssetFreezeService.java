package com.example.tradeasseta.service;

import com.example.tradeasseta.entity.AssetFreeze;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/21 下午11:10
 */

public interface AssetFreezeService {

    AssetFreeze findByUserId(Long userId);
}
