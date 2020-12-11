package com.example.myssjdbc.biz;

import com.example.myssjdbc.BaseTest;
import com.example.myssjdbc.entity.Goods;
import com.example.myssjdbc.entity.User;
import com.example.myssjdbc.service.GoodsService;
import com.example.myssjdbc.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/1 12:12 上午
 */

class OrderBizTest extends BaseTest {
    @Resource
    private OrderBiz orderBiz;
    @Resource
    private UserService userService;
    @Resource
    private GoodsService goodsService;

    private static List<User> users;
    private static List<Goods> goods;

    @BeforeEach
    void before() {
        users = userService.selectAll();
        goods = goodsService.selectAll();
    }

    @Test
    void placeOrder() throws Exception {
        Random random = new Random();
        int userNum = random.nextInt(users.size());
        User user = users.get(userNum);
        int goodsNum = random.nextInt(goods.size());
        Goods goods = OrderBizTest.goods.get(goodsNum);
        Long orderId = orderBiz.placeOrder(user.getId(), goods.getId());
        System.out.println(orderId);
    }

    @Test
    void batchPlaceOrder() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            Random random = new Random();
            int userNum = random.nextInt(users.size());
            User user = users.get(userNum);
            int goodsNum = random.nextInt(goods.size());
            Goods goods = OrderBizTest.goods.get(goodsNum);
            try {
                Long orderId = orderBiz.placeOrder(user.getId(), goods.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }
}