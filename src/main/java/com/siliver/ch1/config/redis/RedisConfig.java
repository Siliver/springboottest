package com.siliver.ch1.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    /**
     * 通过工厂类创建redis模板类对象,使用Jackson进行序列化
     * @param factory
     * @return
     */
    @Bean("jsonRedisTemplate")
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<Object,Object> template=new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    /**
     * 通过工厂类进行redis模板类对象创建，使用StringRedis进行序列化
     * @param factory
     * @return
     */
    @Bean("strKeyRedisTemplate")
    public RedisTemplate<Object,Object> strKeyRedisTemplate(RedisConnectionFactory factory){
        RedisTemplate<Object,Object> template=new RedisTemplate<>();
        template.setConnectionFactory(factory);
        RedisSerializer<String> stringRedisSerializer=new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        return template;
    }
}
