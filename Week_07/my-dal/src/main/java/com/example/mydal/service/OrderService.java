package com.example.mydal.service;

import com.example.mydal.entity.Order;
import com.example.mydal.enums.OrderStatusEnum;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/30 12:38 上午
 */

public interface OrderService {
    /**
     * 创建初始化状态的订单
     * @param order
     * @return
     */
    Long initOrder(Order order);

    /**
     * 更新订单状态
     * @param id
     * @param statusEnum
     * @return
     */
    Boolean updateOrderStatus(Long id, OrderStatusEnum statusEnum);
}
