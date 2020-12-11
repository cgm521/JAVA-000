package com.example.mydal.dao;

import com.example.mydal.entity.User;
import com.example.mydal.enums.SequenceEnum;
import com.example.mydal.support.SequenceSupport;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
        Long mobile = 13000000200L;
        String password = "123456";
        String[] names = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        int index = 0;
        long startTime = System.currentTimeMillis();
        for (String element : names) {
            for (String item : names) {
                for (String value : names) {
                    for (String s : names) {
                        String name = element + item + value + s;
                        User user = new User();
                        user.setId(sequenceSupport.getSequence(SequenceEnum.USER));
                        user.setName(name);
                        mobile++;
                        user.setMobile(String.valueOf(mobile));
                        user.setPassword(password);
                        userMapper.insert(user);
                        if (index++ >= 1000000) {
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("end....");
        System.out.println(System.currentTimeMillis() - startTime);
    }

    @Test
    void selectByPrimaryKey() {
    }
}