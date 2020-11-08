package com.siliver.ch1.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * springboot环境配置类
 */
@Configuration
@ConditionalOnJava(range = ConditionalOnJava.Range.EQUAL_OR_NEWER,value = JavaVersion.ELEVEN)
public class EnvConfig implements BeanPostProcessor {
    private static Logger logger= LoggerFactory.getLogger(EnvConfig.class);

    @Autowired
    private Environment env;

    public int getServerPort(){
        return env.getProperty("server.port",Integer.class);
    }

    public Object postProcessBeforeInitialization(Object bean,String beanName){
        if (bean instanceof URLTestBean){
            logger.info("=========== "+beanName);
        }
        return bean;
    }
}
