package com.example.myssjdbc.service.impl;

import com.example.myssjdbc.dao.UserMapper;
import com.example.myssjdbc.entity.User;
import com.example.myssjdbc.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/1 11:44 下午
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }
}
