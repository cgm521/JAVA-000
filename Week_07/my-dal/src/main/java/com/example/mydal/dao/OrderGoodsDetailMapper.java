package com.example.mydal.dao;

import com.example.mydal.entity.OrderGoodsDetail;
import java.util.List;

public interface OrderGoodsDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderGoodsDetail record);

    OrderGoodsDetail selectByPrimaryKey(Long id);

    List<OrderGoodsDetail> selectAll();

    int updateByPrimaryKey(OrderGoodsDetail record);
}