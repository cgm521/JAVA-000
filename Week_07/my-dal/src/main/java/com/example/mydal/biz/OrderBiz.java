package com.example.mydal.biz;

/**
 * @Author:wb-cgm503374
 * @Description 订单业务接口
 * @Date:Created in 2020/11/30 10:51 下午
 */

public interface OrderBiz {

    /**
     * 下单
     * @param userId
     * @param goodsIds
     * @return
     */
    Long placeOrder(Long userId, Long... goodsIds) throws Exception;
}
