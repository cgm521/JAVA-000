package com.example.tradeasseta.service.impl;

import com.example.tradeasseta.dao.AssetFreezeMapper;
import com.example.tradeasseta.entity.AssetFreeze;
import com.example.tradeasseta.service.AssetFreezeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/21 下午11:11
 */
//@Service(timeout = 5000, version = "1.0", group = "dubbo")
//@Service("assetFreezeService")
public class AssetFreezeServiceImpl implements AssetFreezeService {
    @Resource
    private AssetFreezeMapper assetFreezeMapper;

    @Override
    public AssetFreeze findByUserId(Long userId) {
        return assetFreezeMapper.findByUserId(userId);
    }

}
