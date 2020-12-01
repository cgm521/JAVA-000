package com.example.mydal.annotation;

import com.example.mydal.db.DbContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/12/1 9:59 下午
 */
@Slf4j
@Aspect
@Component
public class ReadOnlyAspect implements Ordered {
    @Pointcut("@annotation(readOnly) || execution(public * com.example.mydal.service.*.select*(..))")
    public void pointcut(ReadOnly readOnly) {

    }

    @Around("pointcut(readOnly)")
    public Object setRead(ProceedingJoinPoint joinPoint, ReadOnly readOnly) throws Throwable {
        try {
            DbContextHolder.setDbType(DbContextHolder.READ);
            return joinPoint.proceed();
        } finally {
            //清楚DbType一方面为了避免内存泄漏，更重要的是避免对后续在本线程上执行的操作产生影响
            DbContextHolder.clearDbType();
            log.info("清除threadLocal");
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
