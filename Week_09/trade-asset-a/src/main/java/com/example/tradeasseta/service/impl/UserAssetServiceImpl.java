package com.example.tradeasseta.service.impl;

import com.example.tradeasseta.dao.UserAssetMapper;
import com.example.tradeasseta.entity.UserAsset;
import com.example.tradeasseta.service.UserAssetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/21 下午10:57
 */
//@Service(timeout = 5000, version = "1.0", group = "dubbo")
//@Service("userAssetService")
public class UserAssetServiceImpl implements UserAssetService {
    @Resource
    private UserAssetMapper userAssetMapper;

    @Override
    public UserAsset findByName(String name) {
        return userAssetMapper.findByName(name);
    }
}
