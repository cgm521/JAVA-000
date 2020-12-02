package com.example.mydal.db;

import com.example.mydal.BaseTest;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/2 10:20 下午
 */

class MyDataSourceUtilsTest extends BaseTest {

    @Test
    public void batchInsert() throws SQLException {
        Connection connection = MyDataSourceUtils.getInstance().getConnection();
        Long mobile = 130012224001L;
        long startTime = System.currentTimeMillis();
        PreparedStatement ps = connection.prepareStatement("insert into user (name,mobile,password,create_date,modify_date)value (?,?,?,?,?)");
        for (int i = 0; i < 1000000; i++) {
            ps.setString(1,"name");
            ps.setString(2, String.valueOf(mobile++));
            ps.setString(3,"123456");
            ps.setDate(4,new Date(System.currentTimeMillis()));
            ps.setDate(5,new Date(System.currentTimeMillis()));
            ps.executeUpdate();
        }
        System.out.println(System.currentTimeMillis()-startTime);
    }

    @Test
    public void statementBatchInsert() throws SQLException {
        Connection connection = MyDataSourceUtils.getInstance().getConnection();
        connection.setAutoCommit(false);
        long mobile = 130012224001L;
        long startTime = System.currentTimeMillis();
        PreparedStatement ps = connection.prepareStatement("insert into user (name,mobile,password,create_date,modify_date)value (?,?,?,?,?)");
        for (int j = 0; j < 1000; j++) {
            for (int i = 0; i < 1000; i++) {
                ps.setString(1, "name");
                ps.setString(2, String.valueOf(mobile++));
                ps.setString(3, "123456");
                ps.setDate(4, new Date(System.currentTimeMillis()));
                ps.setDate(5, new Date(System.currentTimeMillis()));
                ps.addBatch();
            }
            ps.executeBatch();
            connection.commit();
        }
        connection.close();
        System.out.println(System.currentTimeMillis()-startTime);
    }

    @Test
    public void statementBatchInsert2() throws SQLException {
        Connection connection = MyDataSourceUtils.getInstance().getConnection();
        connection.setAutoCommit(false);
        long mobile = 130013224001L;
        long startTime = System.currentTimeMillis();
        PreparedStatement ps = connection.prepareStatement("insert into user (name,mobile,password,create_date,modify_date)value (?,?,?,?,?)");
        for (int j = 0; j < 100; j++) {
            for (int i = 0; i < 10000; i++) {
                ps.setString(1, "name");
                ps.setString(2, String.valueOf(mobile++));
                ps.setString(3, "123456");
                ps.setDate(4, new Date(System.currentTimeMillis()));
                ps.setDate(5, new Date(System.currentTimeMillis()));
                ps.addBatch();
            }
            ps.executeBatch();
            connection.commit();
        }
        connection.close();
        System.out.println(System.currentTimeMillis()-startTime);
    }

    @Test
    public void statementBatchInsert3() throws SQLException {
        Connection connection = MyDataSourceUtils.getInstance().getConnection();
        connection.setAutoCommit(false);
        long mobile = 130014224001L;
        long startTime = System.currentTimeMillis();
        PreparedStatement ps = connection.prepareStatement("insert into user (name,mobile,password,create_date,modify_date)value (?,?,?,?,?)");
        for (int i = 0; i < 1000000; i++) {
            ps.setString(1, "name");
            ps.setString(2, String.valueOf(mobile++));
            ps.setString(3, "123456");
            ps.setDate(4, new Date(System.currentTimeMillis()));
            ps.setDate(5, new Date(System.currentTimeMillis()));
            ps.addBatch();
        }
        ps.executeBatch();
        connection.commit();
        connection.close();
        System.out.println(System.currentTimeMillis() - startTime);
    }
}