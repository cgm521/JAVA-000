package com.example.springbootdemostarter.prop;



import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;


@ConfigurationProperties(prefix = "my.student")
public class Student implements Serializable {
    
    private Integer id;
    private String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
