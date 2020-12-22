package com.example.tradeassetapi.service;


import com.example.tradeassetapi.entity.UserAssetVO;
import org.dromara.hmily.annotation.Hmily;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/21 下午10:56
 */

public interface UserAssetService {

    /**
     * 根据名称获取资产信息
     * @param name
     * @return
     */
    UserAssetVO findByName(String name);

    /**
     * 资产核减
     * @param userId
     * @param assetCny
     * @param assetUs
     */
    @Hmily
    void assetsSubtract(Long userId, Long assetCny, Long assetUs);
}
