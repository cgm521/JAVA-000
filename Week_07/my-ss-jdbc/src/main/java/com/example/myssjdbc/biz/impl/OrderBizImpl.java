package com.example.myssjdbc.biz.impl;

import com.example.myssjdbc.biz.OrderBiz;
import com.example.myssjdbc.entity.Goods;
import com.example.myssjdbc.entity.Order;
import com.example.myssjdbc.entity.OrderGoodsDetail;
import com.example.myssjdbc.enums.OrderStatusEnum;
import com.example.myssjdbc.service.GoodsService;
import com.example.myssjdbc.service.OrderGoodsDetailService;
import com.example.myssjdbc.service.OrderService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/30 10:57 下午
 */
@Slf4j
@Component
public class OrderBizImpl implements OrderBiz {
    @Resource
    private OrderService orderService;
    @Resource
    private OrderGoodsDetailService orderGoodsDetailService;
    @Resource
    private GoodsService goodsService;

    @Override
    public Long placeOrder(Long userId, Long... goodsIds) throws Exception {
        Objects.requireNonNull(userId, "用户不能为空！");
        Objects.requireNonNull(goodsIds, "商品不能为空！");
        if (goodsIds.length == 0) {
            log.error("商品不能为空！");
            throw new Exception("商品不能为空！");
        }
        List<Long> list = Lists.newArrayList(goodsIds);
        // 1.查询出所有商品信息
        List<Goods> goodsList = list.stream().map(goodsService::selectById).collect(Collectors.toList());
        // 2.库存检查
        Goods soldOutGoods = goodsList.parallelStream().filter(o -> o.getCount() <= 0).findFirst().orElse(null);
        if (soldOutGoods != null) {
            log.error("商品已售罄！goods:{}", soldOutGoods);
            throw new Exception("商品" + soldOutGoods.getName() + "已售罄！");
        }
        // 3.计算订单金额
        double orderSumMoney = goodsList.parallelStream().mapToDouble(o -> o.getPrice().doubleValue()).sum();
        double orderMoney = goodsList.parallelStream().mapToDouble(o -> o.getMarketingPrice().doubleValue()).sum();

        Order order = new Order();
        order.setUserId(userId);
        order.setOrderMoney(BigDecimal.valueOf(orderMoney));
        order.setOrderSumMoney(BigDecimal.valueOf(orderSumMoney));
        final Long orderId = orderService.initOrder(order);
        for (Goods o : goodsList) {
            // 减少库存
            goodsService.updateCount(o.getId(), -1L);
            // 生成订单详情，保存商品快照
            OrderGoodsDetail orderGoodsDetail = new OrderGoodsDetail();
            orderGoodsDetail.setOrderId(orderId);
            orderGoodsDetail.setGoodsId(o.getId());
            orderGoodsDetail.setSnapshotPrice(o.getPrice());
            orderGoodsDetail.setSnapshotRealPayment(o.getMarketingPrice());
            orderGoodsDetailService.create(orderGoodsDetail);
        }
        orderService.updateOrderStatus(orderId, OrderStatusEnum.FINISH);
        return orderId;
    }
}
