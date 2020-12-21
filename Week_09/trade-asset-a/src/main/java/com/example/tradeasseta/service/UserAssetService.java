package com.example.tradeasseta.service;

import com.example.tradeasseta.entity.UserAsset;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/21 下午10:56
 */

public interface UserAssetService {
    UserAsset findByName(String name);
}
