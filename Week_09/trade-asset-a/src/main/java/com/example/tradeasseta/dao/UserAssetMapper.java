package com.example.tradeasseta.dao;

import com.example.tradeasseta.entity.UserAsset;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserAssetMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserAsset record);

    UserAsset selectByPrimaryKey(Long id);

    List<UserAsset> selectAll();

    int updateByPrimaryKey(UserAsset record);

    UserAsset findByName(String name);
}