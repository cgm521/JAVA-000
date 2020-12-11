package com.example.myssjdbc.dao;

import com.example.myssjdbc.entity.User;
import com.example.myssjdbc.enums.SequenceEnum;
import com.example.myssjdbc.support.SequenceSupport;
import org.apache.shardingsphere.api.hint.HintManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

import java.util.Date;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/29 9:44 下午
 */
@SpringBootTest
class UserMapperTest {

    @Resource
    private UserMapper userMapper;
    @Resource
    private SequenceSupport sequenceSupport;

    @Test
    @Rollback(value = false)
    void insertSelect() {
        Long id = sequenceSupport.getSequence(SequenceEnum.USER);
        User user = new User();
        user.setId(id);
        user.setName("a");
        user.setPassword("123");
        user.setMobile("14222222226");
        user.setCreateDate(new Date());
        user.setModifyDate(new Date());
        int insert = userMapper.insert(user);
        // 主备延迟，强制采用录用到主库
        HintManager hintManager = HintManager.getInstance() ;
        hintManager.setMasterRouteOnly();
        System.out.println("id:" + user.getId());
        User userDb = userMapper.selectByPrimaryKey(id);
        System.out.println(userDb);
        userMapper.deleteByPrimaryKey(id);
    }

}