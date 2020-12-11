package com.example.myssjdbc.service.impl;

import com.example.myssjdbc.dao.OrderGoodsDetailMapper;
import com.example.myssjdbc.entity.OrderGoodsDetail;
import com.example.myssjdbc.enums.SequenceEnum;
import com.example.myssjdbc.service.OrderGoodsDetailService;
import com.example.myssjdbc.support.SequenceSupport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/30 10:53 下午
 */
@Service
public class OrderGoodsDetailServiceImpl implements OrderGoodsDetailService {
    @Resource
    private OrderGoodsDetailMapper orderGoodsDetailMapper;
    @Resource
    private SequenceSupport sequenceSupport;

    @Override
    public Long create(OrderGoodsDetail orderGoodsDetail) {
        Long id = sequenceSupport.getSequence(SequenceEnum.ORDER_GOODS_DETAILS);
        orderGoodsDetail.setId(id);
        orderGoodsDetailMapper.insert(orderGoodsDetail);
        return id;
    }
}
