package com.siliver.ch1.controller.ch2;

import com.siliver.ch1.entity.User;
import com.siliver.ch1.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user4")
public class Sample34Controller {
    private static final Logger logger= LoggerFactory.getLogger(Sample34Controller.class);

    @Autowired
    UserService userService;

    @GetMapping("/")
    public @ResponseBody String index(){
        return "hello";
    }

    @GetMapping(value = "/all1.json",consumes = "application/json")
    @ResponseBody
    public User forJson(){
        return new User();
    }

    @GetMapping(path = "/user/{userId}.json",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User getUser(@PathVariable Long userId, Model model){
        return new User();
    }

    @GetMapping(path = "/update.json",params = "action=save")
    @ResponseBody
    public String saveUser(){
        logger.info(this.getClass().getName()+": call save");
        return "save";
    }

    @GetMapping(path = "/update.json",params = "action=update")
    @ResponseBody
    public String updateUser(){
        logger.info(this.getClass().getName()+": call update");
        return "update";
    }
}
