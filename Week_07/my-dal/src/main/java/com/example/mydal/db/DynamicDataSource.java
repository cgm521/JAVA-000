package com.example.mydal.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Random;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/1 9:47 下午
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Value("${mysql.datasource.num}")
    private int num;

    @Override
    protected Object determineCurrentLookupKey() {
        String dbType = DbContextHolder.getDbType();
        if (dbType.equals(DbContextHolder.WRITE)) {
            logger.info("使用了写库");
            return dbType;
        }
        Random random = new Random(1);
        int index = random.nextInt(num);
        log.info("使用了读库{}", index);
        return DbContextHolder.READ + index;

    }
}
