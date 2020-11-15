package com.example.springbootdemo.jdbcExp;

import com.example.springbootdemo.entity.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/15 5:41 下午
 */

public class JdbcTemplateExp {
    private static class MyJdbcTemplate {
        private static JdbcTemplate jdbcTemplate = new JdbcTemplate(MyDataSourceUtils.getInstance());
    }

    public static JdbcTemplate getInstance() {
        return MyJdbcTemplate.jdbcTemplate;
    }

    public static void main(String[] args) throws IllegalAccessException {
        long id = System.currentTimeMillis();
        Person person = new Person();
        person.setId(id);
        person.setName("mm");
        person.setAge(11);
        System.out.println(insert("person", person));
        person.setAge(12);
        System.out.println(updateById("person", person, id));
        System.out.println(getById("person", id, new BeanPropertyRowMapper<>(Person.class)));
    }

    public static <T> T getById(String table, Long id, RowMapper<T> rowMapper) {
        JdbcTemplate jdbcTemplate = getInstance();
        T t = jdbcTemplate.queryForObject("SELECT * FROM " + table + " WHERE id=?", rowMapper, id);
        return t;
    }

    public static <T> Integer insert(String table, T param) throws IllegalAccessException {
        Class<?> aClass = param.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        StringBuilder fields = new StringBuilder("(");
        StringBuilder place = new StringBuilder("(");
        Object[] params = new Object[declaredFields.length];
        for (int i = 0; i < declaredFields.length; i++) {
            if (i != 0) {
                fields.append(",");
                place.append(",");
            }
            fields.append(declaredFields[i].getName());
            place.append("?");
            declaredFields[i].setAccessible(true);

            params[i] = declaredFields[i].get(param);
        }
        fields.append(")");
        place.append(")");
        String sql = "insert into " + table + fields + " value " + place;
        return getInstance().update(sql, params);
    }

    public static <T> Integer updateById(String table, T param, Long id) throws IllegalAccessException {
        Class<?> aClass = param.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        StringBuilder fields = new StringBuilder("");
        Object[] params = new Object[declaredFields.length];
        for (int i = 0; i < declaredFields.length; i++) {
            if (i != 0) {
                fields.append(",");
            }
            fields.append(declaredFields[i].getName()).append(" = ? ");
            declaredFields[i].setAccessible(true);

            params[i] = declaredFields[i].get(param);
        }
        String sql = "update " + table + " set " + fields + " where id = " + id;
        return getInstance().update(sql, params);
    }
}
