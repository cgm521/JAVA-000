package com.example.myssjdbc.dao;

import com.example.myssjdbc.entity.OrderGoodsDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface OrderGoodsDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderGoodsDetail record);

    OrderGoodsDetail selectByPrimaryKey(Long id);

    List<OrderGoodsDetail> selectAll();

    int updateByPrimaryKey(OrderGoodsDetail record);
}