package com.siliver.ch1.controller.ch2;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.siliver.ch1.entity.User;
import com.siliver.ch1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/json")
public class JsonController {
    @Autowired
    UserService userService;

    @RequestMapping("/user/{id}.json")
    public @ResponseBody User showUserInfo(@PathVariable Long id){
        //User user=userService.getUserById(id);
        return null;
    }

    @RequestMapping("/now.json")
    public @ResponseBody Map datetime(){
        //JsonsonConfig 配置了序列化日期
        Map map=new HashMap();
        map.put("time",new Date());
        return map;
    }

    @RequestMapping("/data.json")
    public @ResponseBody Map datetime(@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd",timezone = "GMT+8") Date date){
        //MvcConfigurer 配置了处理查询参数到日期类型的映射
        Map map=new HashMap();
        map.put("time",date);
        return map;
    }
}
