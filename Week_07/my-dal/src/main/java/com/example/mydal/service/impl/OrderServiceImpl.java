package com.example.mydal.service.impl;

import com.example.mydal.dao.GoodsMapper;
import com.example.mydal.dao.OrderGoodsDetailMapper;
import com.example.mydal.dao.OrderMapper;
import com.example.mydal.entity.Goods;
import com.example.mydal.entity.Order;
import com.example.mydal.enums.SequenceEnum;
import com.example.mydal.service.OrderService;
import com.example.mydal.support.SequenceSupport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/30 12:40 上午
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderGoodsDetailMapper orderGoodsDetailMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private SequenceSupport sequenceSupport;
    @Override
    public Long create(Order order) {
        Long orderId = sequenceSupport.getSequence(SequenceEnum.ORDER);
        order.setId(orderId);
        orderMapper.insert(order);
        Goods goods = goodsMapper.selectByPrimaryKey(order.getGoodsId());

        return null;
    }
}
