package com.example.myssjdbc.dao;

import com.example.myssjdbc.BaseTest;
import com.example.myssjdbc.entity.Goods;
import com.example.myssjdbc.enums.GoodsStatusEnum;
import com.example.myssjdbc.enums.SequenceEnum;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/30 12:05 上午
 */
class GoodsMapperTest extends BaseTest {
    @Resource
    private GoodsMapper goodsMapper;

    @Test
    void insert() {
        Goods goods = new Goods();
        goods.setId(sequenceSupport.getSequence(SequenceEnum.GOODS));
        goods.setName("商品");
        goods.setPrice(BigDecimal.valueOf(6000));
        goods.setCount(1000L);
        goods.setMarketingPrice(BigDecimal.valueOf(5500));
        goods.setStatus(GoodsStatusEnum.NORMAL.getCode());
        goodsMapper.insert(goods);
    }

    @Test
    void batchInsert() {
        String name = "商品";
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            double v = random.nextDouble();
            v = v * 1000;
            Goods goods = new Goods();
            goods.setId(sequenceSupport.getSequence(SequenceEnum.GOODS));
            goods.setName(name + i);
            goods.setPrice(BigDecimal.valueOf(v));
            goods.setMarketingPrice(BigDecimal.valueOf(v * random.nextDouble()));
            goods.setStatus(GoodsStatusEnum.NORMAL.getCode());
            goods.setCount(100000L);
            goodsMapper.insert(goods);
        }
    }
}