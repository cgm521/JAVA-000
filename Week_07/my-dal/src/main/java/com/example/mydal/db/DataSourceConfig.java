package com.example.mydal.db;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:wb-cgm503374
 * @Description 数据源配置
 * @Date:Created in 2020/12/1 9:52 下午
 */

@Configuration
@MapperScan(basePackages = "com.example.mydal.dao", sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {
    @Value("${mysql.datasource.type-aliases-package}")
    private String typeAliasesPackage;
    @Value("${mysql.datasource.mapper-locations}")
    private String mapperLocation;

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "mysql.datasource.write")
    public DataSource writeDataSource() {
//        return new HikariDataSource();
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "mysql.datasource.read1")
    public DataSource read1() {
//        return new HikariDataSource();
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DynamicDataSource dataSource() {
        DynamicDataSource proxy = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DbContextHolder.WRITE, writeDataSource());
        targetDataSources.put(DbContextHolder.READ + "1", read1());
        proxy.setDefaultTargetDataSource(writeDataSource());
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource());
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 实体类对应的位置
        bean.setTypeAliasesPackage(typeAliasesPackage);
        // mybatis的XML的配置
        bean.setMapperLocations(resolver.getResources(mapperLocation));
//        bean.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
        return bean.getObject();
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
