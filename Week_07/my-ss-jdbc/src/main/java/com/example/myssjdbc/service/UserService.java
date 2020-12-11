package com.example.myssjdbc.service;

import com.example.myssjdbc.entity.User;

import java.util.List;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/1 11:43 下午
 */

public interface UserService {
    List<User> selectAll();
}
