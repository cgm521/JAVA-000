package com.example.myssjdbc.service;

import com.example.myssjdbc.entity.Order;
import com.example.myssjdbc.enums.OrderStatusEnum;

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
