package com.example.tradeasseta;

import com.example.tradeasseta.dao.UserAssetMapper;
import com.example.tradeasseta.entity.UserAsset;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class TradeAssetAApplicationTests {
    @Resource
    private UserAssetMapper userAssetMapper;
    @Test
    void contextLoads() {
    }

    @Test
    public void user() {
        UserAsset tom = userAssetMapper.findByName("tom");
        System.out.println(tom);

    }
}
