package com.siliver.ch1.controller.ch2;

import com.siliver.ch1.entity.User;
import com.siliver.ch1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/urlmapper")
public class URLMapperController {
    @Autowired
    UserService userService;

    /**
     * 请求方式的匹配
     * @return
     */
    @RequestMapping(path = "/user/all/*.json",method = RequestMethod.GET)
    @ResponseBody
    public List<User> allUser(){
        return null;
    }

    @RequestMapping(path = "/user/{id}.json",method = RequestMethod.GET)
    @ResponseBody
    public User getById(@PathVariable Long id){
        return new User();
    }

    @RequestMapping(path = "/{userId}.json",produces = "application/json")
    @ResponseBody
    public User getUserByid(@PathVariable Long userId){
        return new User();
    }

    @RequestMapping(value = "/consumes/test.json",consumes = "application/json")
    @ResponseBody
    public User forJson(){
        return new User();
    }
}
