package com.example.myssjdbc.service.impl;

import com.example.myssjdbc.dao.GoodsMapper;
import com.example.myssjdbc.entity.Goods;
import com.example.myssjdbc.enums.GoodsStatusEnum;
import com.example.myssjdbc.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/30 11:01 下午
 */
@Slf4j
@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> selectAll() {
        return goodsMapper.selectAll();
    }

    @Override
    public Goods selectById(Long id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean updateCount(Long id, Long updateCount) throws Exception {
        Goods goods = selectById(id);
        long count = goods.getCount() + updateCount;
        goods = new Goods();
        if (count < 0) {
            log.error("商品库存不足");
            throw new Exception("商品库存不足");
        }
        if (count == 0) {
            goods.setStatus(GoodsStatusEnum.SOLD_OUT.getCode());
        }
        goods.setCount(count);
        goods.setId(id);
        return goodsMapper.updateByPrimaryKey(goods) > 0;
    }
}
