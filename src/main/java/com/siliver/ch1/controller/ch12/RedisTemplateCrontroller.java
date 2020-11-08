package com.siliver.ch1.controller.ch12;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/redis")
public class RedisTemplateCrontroller {
    @Qualifier("redisTemplate")
    private RedisTemplate client;

    @RequestMapping("/simpletest.html")
    public @ResponseBody String simpleSet(){
        client.opsForValue().set("key-0","hello");
        client.opsForValue().set("key-1", User.getSampleUser());
        return "success";
    }

    @RequestMapping("/simpleget.html")
    public @ResponseBody String simpleGet(){
        String value=(String) client.opsForValue().get("key-0");
        User user=(User) client.opsForValue().get("key-1");
        Map<String,Object> result=new HashMap<>();
        result.put("value",value);
        result.put("user",user);
        return result.toString();
    }

    /**
     * 内部静态类，测试使用
     */
    public static class User implements Serializable{
        int id;
        String name;
        Date date=new Date();

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public static User getSampleUser(){
            User user=new User();
            user.setId(123);
            user.setName("adc");
            return user;
        }
    }
}
