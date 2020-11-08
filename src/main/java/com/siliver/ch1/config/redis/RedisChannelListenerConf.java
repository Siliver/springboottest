package com.siliver.ch1.config.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.UnsupportedEncodingException;

@Configuration
public class RedisChannelListenerConf {
    /**
     * 创建自定义监听适配器
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(){
        MessageListenerAdapter adapter=new MessageListenerAdapter(new MyRedisChannelListener());
        adapter.setSerializer(new JdkSerializationRedisSerializer());
        return adapter;
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,MessageListenerAdapter adapter){
        RedisMessageListenerContainer container=new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //订阅所有news.*频道消息
        container.addMessageListener(adapter,new PatternTopic("new.*"));
        return container;
    }
}

/**
 * 实现redis监听方法
 */
class MyRedisChannelListener implements MessageListener{
    Log log= LogFactory.getLog(this.getClass());

    public void onMessage(Message message,byte[] pattern){
        byte[] channel=message.getChannel();
        byte[] bs=message.getBody();
        try {
            String content=new String(bs,"UTF-8");
            String p=new String(channel,"UTF-8");
            log.info("get"+content+" from "+p);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
