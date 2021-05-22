package com.cloud.config.db;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DataSourceAop {

    @Pointcut("@annotation(com.cloud.config.db.DataSourceType)")
    public void pointCut(){}

    @Around("pointCut()")
    public Object chooseDataSource(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            MethodSignature ms = (MethodSignature)joinPoint.getSignature();
            Method method = ms.getMethod();
            DataSourceType dst = method.getAnnotation(DataSourceType.class);
            DataSourceHolder.setSourceType(dst.sourceType());
            System.out.println("方法名："+method.getName()+"，数据源："+dst.sourceType());
            return joinPoint.proceed();
        } finally {
            DataSourceHolder.removeSourceType();
        }
    }
}
