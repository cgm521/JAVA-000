package com.example.mydal.dao;

import com.example.mydal.entity.User;
import com.example.mydal.enums.SequenceEnum;
import com.example.mydal.support.SequenceSupport;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

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
    void insert() {
        User user = new User();
        user.setId(sequenceSupport.getSequence(SequenceEnum.USER));
        user.setName("a");
        user.setPassword("123");
        user.setMobile("13222222222");
        user.setCreateDate(new Date());
        user.setModifyDate(new Date());
        int insert = userMapper.insert(user);
        System.out.println("id:" + user.getId());
    }

    @Test
    void batchInsert() {
        Long mobile = 13000000000L;
        String password = "123456";
        String[] names = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        Random rand = new Random();
        for (int i = 0; i < 200; i++) {
            int n1 = rand.nextInt(names.length);
            int n2 = rand.nextInt(names.length);
            int n3 = rand.nextInt(names.length);
            String name = names[n1] + names[n2] + names[n3];
            User user = new User();
            user.setId(sequenceSupport.getSequence(SequenceEnum.USER));
            user.setName(name);
            mobile++;
            user.setMobile(String.valueOf(mobile));
            user.setPassword(password);
            userMapper.insert(user);

        }

    }

    @Test
    void selectByPrimaryKey() {
    }
}