package com.example.springbootdemo;

import com.example.springbootdemostarter.prop.Student;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@SpringBootApplication
public class SpringBootDemoApplication {
    @Resource
    private Student student;
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringBootDemoApplication.class, args);
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        System.out.println("datasource is :" + dataSource);
        //检查数据库是否是hikar数据库连接池
        if (!(dataSource instanceof HikariDataSource)) {
            System.err.println(" Wrong datasource type :"
                    + dataSource.getClass().getCanonicalName());
            System.exit(-1);
        }
        try {
            Connection connection = dataSource.getConnection();
            ResultSet rs = connection.prepareStatement("SELECT 1", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE).executeQuery();
            if (rs.first()) {
                System.out.println("Connection OK!");
            } else {
                System.out.println("Something is wrong");
            }

        } catch (SQLException e) {
            System.out.println("FAILED");
            e.printStackTrace();
            System.exit(-2);
            // TODO: handle exception
        }
    }


    @GetMapping("/get")
    public Student get() {
        System.out.println(">>>>> web student get ");
        return student;
    }
}
