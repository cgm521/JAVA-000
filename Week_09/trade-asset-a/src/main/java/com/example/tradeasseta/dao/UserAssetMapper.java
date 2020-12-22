package com.example.tradeasseta.dao;

import com.example.tradeasseta.entity.UserAsset;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;
@Mapper
public interface UserAssetMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserAsset record);

    UserAsset selectByPrimaryKey(Long id);

    List<UserAsset> selectAll();

    int updateByPrimaryKey(UserAsset record);

    UserAsset findByName(String name);

    @Update("update user_asset set asset_cny = asset_cny - #{assetCny} , asset_us = asset_us - #{assetUs} where id =#{userId}")
    int assetsSubtract(Long userId, Long assetCny, Long assetUs);

    @Update("update user_asset set asset_cny = asset_cny + #{assetCny} , asset_us = asset_us + #{assetUs} where id =#{userId}")
    int cancelAssetsSubtract(Long userId, Long assetCny, Long assetUs);
}