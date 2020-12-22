package com.example.tradeasseta;

import com.example.tradeasseta.dao.UserAssetMapper;
import com.example.tradeasseta.entity.UserAsset;
import com.example.tradeassetapi.service.UserAssetService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class TradeAssetAApplicationTests {
    @Resource
    private UserAssetMapper userAssetMapper;
    @Resource
    private UserAssetService userAssetService;
    @Test
    void contextLoads() {
    }

    @Test
    public void user() {
        UserAsset tom = userAssetMapper.findByName("tom");
        System.out.println(tom);

    }

    @Test
    public void assetsSubtract() {
        UserAsset tom = userAssetMapper.findByName("tom");
        userAssetService.assetsSubtract(tom.getId(), 7L, 1L);
    }
}
