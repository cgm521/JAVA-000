package com.example.springbootdemo.jdbcExp;

import com.example.springbootdemo.entity.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/15 10:28 下午
 */

public class PreparedStatementExp {

    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        try {
            connection = MyDataSourceUtils.getInstance().getConnection();
            Person person = new Person();
            long id = System.currentTimeMillis();
            person.setId(id);
            person.setAge(99);
            person.setName("nn");
            connection.setAutoCommit(false);
            System.out.println(insert(connection, person));
            person.setAge(9999);
            System.out.println(updateById(connection,person));
            if (true)
                throw new Exception("rollback");
            getById(connection, id);
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            e.printStackTrace();
        }

    }

    public static void getById(Connection conn, Long id) {
        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM  person WHERE id=?");) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    System.out.print(rs.getString(i) + "\t");
                    if ((i == 2) && (rs.getString(i).length() < 8)) {
                        System.out.print("\t");
                    }
                }
                System.out.println("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Integer insert(Connection conn, Person person) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("insert into person(id,name,age) value (?,?,?)");) {
            ps.setLong(1, person.getId());
            ps.setString(2, person.getName());
            ps.setInt(3, person.getAge());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public static Integer updateById(Connection conn, Person person) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("update person set id = ? ,name = ? ,age = ?  where id = ?");) {
            ps.setLong(1, person.getId());
            ps.setString(2, person.getName());
            ps.setInt(3, person.getAge());
            ps.setLong(4, person.getId());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
