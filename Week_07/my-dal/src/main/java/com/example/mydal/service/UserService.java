package com.example.mydal.service;

import com.example.mydal.entity.User;

import java.util.List;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/1 11:43 下午
 */

public interface UserService {
    List<User> selectAll();
}
