package com.example.springbootdemostarter.service;

import com.example.springbootdemostarter.prop.Student;
import lombok.Data;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/15 4:42 下午
 */
@Data
public class StudentService {
    private Integer id;
    private String name;

    public StudentService(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
