package com.example.mydal.dao;

import com.example.mydal.entity.Order;
import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    Order selectByPrimaryKey(Long id);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);
}