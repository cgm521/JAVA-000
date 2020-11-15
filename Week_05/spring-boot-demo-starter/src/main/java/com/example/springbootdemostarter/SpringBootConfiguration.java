package com.example.springbootdemostarter;

import com.example.springbootdemostarter.prop.Student;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(Student.class)
@ConditionalOnProperty(prefix = "my.student", name = "enabled", havingValue = "true", matchIfMissing = false)
public class SpringBootConfiguration {
    private Student student;

    public SpringBootConfiguration(Student student) {
        System.out.println(">>>>> SpringBootConfiguration init");
        this.student = student;
    }

    @Bean
    @ConditionalOnMissingBean
    public Student student() {
        System.out.println(">>>> student bean get");
        return student;
    }
}
