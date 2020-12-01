package com.example.mydal.service.impl;

import com.example.mydal.dao.UserMapper;
import com.example.mydal.entity.User;
import com.example.mydal.service.UserService;
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
