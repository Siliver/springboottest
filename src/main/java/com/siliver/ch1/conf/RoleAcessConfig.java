package com.siliver.ch1.conf;

import com.siliver.ch1.annotation.Function;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Aspect
@Configuration
public class RoleAcessConfig {
    //创建slf4j日志对象
    private static final Logger loggger= LoggerFactory.getLogger(RoleAcessConfig.class);

    /**
     * 创建切面方法
     * @param pjp 切面对象
     * @param function 注解对象
     * @return
     */
    @Around("within(@org.springframework.stereotype.Controller *) && @annotation(function)")
    public Object functionAccessCheck(final ProceedingJoinPoint pjp, Function function) throws Throwable {
        if(function!=null){
            String functionName=function.value();
            if (!canAccess(functionName)){
                MethodSignature ms=(MethodSignature)pjp.getSignature();
                throw new RuntimeException("Can not Access"+ms.getMethod());
            }
        }
        Object o=pjp.proceed();
        return o;
    }

    protected boolean canAccess(String functionName){
        if (functionName.length()==0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 所有Controller方法的切面
     * @param pjp 被切面的对象
     * @return 返回结果
     * @throws Throwable
     */
    @Around("@within(org.springframework.stereotype.Controller)")
    public Object simpleAop(final ProceedingJoinPoint pjp) throws Throwable {
        try{
            Object[] args=pjp.getArgs();
            loggger.info("args:"+ Arrays.asList(args));
            Object o=pjp.proceed();
            loggger.info("return :"+o);
            return o;
        }catch (Throwable e){
            throw e;
        }
    }
}
