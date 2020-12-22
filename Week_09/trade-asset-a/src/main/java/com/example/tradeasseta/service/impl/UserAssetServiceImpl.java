package com.example.tradeasseta.service.impl;

import com.example.tradeasseta.dao.AssetFreezeMapper;
import com.example.tradeasseta.dao.UserAssetMapper;
import com.example.tradeasseta.entity.AssetFreeze;
import com.example.tradeassetapi.entity.UserAssetVO;
import com.example.tradeassetapi.service.UserAssetService;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/21 下午10:57
 */
@DubboService(timeout = 5000, version = "1.0", group = "dubbo")
public class UserAssetServiceImpl implements UserAssetService {
    @Resource
    private UserAssetMapper userAssetMapper;
    @Resource
    private AssetFreezeMapper assetFreezeMapper;

    @Override
    public UserAssetVO findByName(String name) {
        UserAssetVO vo = new UserAssetVO();
        BeanUtils.copyProperties(userAssetMapper.findByName(name), vo);
        return vo;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmSubtract", cancelMethod = "cancelSubtract")
    public void assetsSubtract(Long userId, Long assetCny, Long assetUs) {
        // 资产减少
        userAssetMapper.assetsSubtract(userId, assetCny, assetUs);
        // 资产冻结
        AssetFreeze assetFreeze = new AssetFreeze();
        assetFreeze.setUserId(userId);
        assetFreeze.setFreezeAssetCny(assetCny);
        assetFreeze.setFreezeAssetUs(assetUs);
        assetFreeze.setStatus(0);
        assetFreezeMapper.insert(assetFreeze);
    }

    @Transactional(rollbackFor = Exception.class,timeout = 2000)
    public void confirmSubtract(Long userId, Long assetCny, Long assetUs) {
        AssetFreeze assetFreeze = assetFreezeMapper.findByUserId(userId);
        assetFreeze.setStatus(1);
        assetFreezeMapper.updateByPrimaryKey(assetFreeze);
    }

    @Transactional(rollbackFor = Exception.class,timeout = 2000)
    public void cancelSubtract(Long userId, Long assetCny, Long assetUs) {
        AssetFreeze assetFreeze = assetFreezeMapper.findByUserId(userId);
        assetFreeze.setStatus(9);
        assetFreezeMapper.updateByPrimaryKey(assetFreeze);
        userAssetMapper.cancelAssetsSubtract(userId, assetCny, assetUs);
    }
}
