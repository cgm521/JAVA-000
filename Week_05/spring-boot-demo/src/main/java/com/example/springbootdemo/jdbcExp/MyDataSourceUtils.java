package com.example.springbootdemo.jdbcExp;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/6/6 7:14 下午
 */


public class MyDataSourceUtils implements DataSource {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String PWD = "123456";
    private static final String URL = "jdbc:mysql://localhost:3306/test";

    //定义一个数据库连接
    private static Connection conn = null;
    private static ThreadLocal<Connection> connContainer = new ThreadLocal<Connection>();
    private static class MyDataSource{
        private static DataSource dataSource = new MyDataSourceUtils();
    }
    public static DataSource getInstance() {
        return MyDataSource.dataSource;
    }
    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(USER, PWD);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        System.out.println("my getConnection");
        //获取连接对象
//        conn = connContainer.get();
        try {
//            if(conn == null) {
                Class.forName(DRIVER);
                conn = DriverManager.getConnection(URL, username, password);
//                connContainer.set(conn);
//            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    //关闭连接
    public static void closeConnection() {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}