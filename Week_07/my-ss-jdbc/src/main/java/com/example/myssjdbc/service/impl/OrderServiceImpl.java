package com.example.myssjdbc.service.impl;

import com.example.myssjdbc.dao.OrderMapper;
import com.example.myssjdbc.entity.Order;
import com.example.myssjdbc.enums.OrderStatusEnum;
import com.example.myssjdbc.enums.SequenceEnum;
import com.example.myssjdbc.service.OrderService;
import com.example.myssjdbc.support.SequenceSupport;
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
    private SequenceSupport sequenceSupport;

    @Override
    public Long initOrder(Order order) {
        Long orderId = sequenceSupport.getSequence(SequenceEnum.ORDER);
        order.setId(orderId);
        order.setStatus(OrderStatusEnum.INIT.name());
        orderMapper.insert(order);
        return orderId;
    }

    @Override
    public Boolean updateOrderStatus(Long id, OrderStatusEnum statusEnum) {
        Order order = orderMapper.selectByPrimaryKey(id);
        order.setStatus(statusEnum.name());
        return orderMapper.updateByPrimaryKey(order) > 0;
    }
}
