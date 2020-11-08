package com.siliver.ch1.conf;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Aspect
@Configuration
public class AOPConfig {
    private final Logger logger= LoggerFactory.getLogger(AOPConfig.class);

    @Around("@within(org.springframework.stereotype.Controller)")
    public Object functionAccessCheck(final ProceedingJoinPoint pjp) throws Throwable {
        try{
            Object[] args=pjp.getArgs();
            logger.info("args"+ Arrays.asList(args));
            Object o=pjp.proceed();
            logger.info("return :"+o);
            return o;
        }catch (Throwable e){
            throw e;
        }
    }
}
