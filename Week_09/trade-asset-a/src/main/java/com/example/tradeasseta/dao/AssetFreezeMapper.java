package com.example.tradeasseta.dao;

import com.example.tradeasseta.entity.AssetFreeze;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface AssetFreezeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AssetFreeze record);

    AssetFreeze selectByPrimaryKey(Long id);

    List<AssetFreeze> selectAll();

    int updateByPrimaryKey(AssetFreeze record);

    AssetFreeze findByUserId(Long userId);
}