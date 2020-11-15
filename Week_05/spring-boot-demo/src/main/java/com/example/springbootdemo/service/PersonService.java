package com.example.springbootdemo.service;

import com.example.springbootdemo.entity.Person;
import com.example.springbootdemo.jdbcExp.PreparedStatementExp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/15 11:19 下午
 */
@RestController
public class PersonService {
    @Resource
    private DataSource dataSource;

    @PostMapping("/insert")
    public void insertAndUpdateAndGet(@RequestBody Person person) throws SQLException {
        person.setId(System.currentTimeMillis());
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatementExp.insert(conn, person);
            System.out.println(">>>>>insert");
            PreparedStatementExp.getById(conn, person.getId());
            if (person.getAge() == 10) {
                throw new Exception("rollback");
            }
            person.setAge(person.getAge() + 10);
            PreparedStatementExp.updateById(conn, person);
            System.out.println(">>>>>update");
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            e.printStackTrace();
        }
    }
}
