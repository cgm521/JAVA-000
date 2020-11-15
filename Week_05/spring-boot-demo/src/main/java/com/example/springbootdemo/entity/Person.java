package com.example.springbootdemo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/15 9:14 下午
 */
@Data
public class Person implements Serializable {
//    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private Integer age;
}
