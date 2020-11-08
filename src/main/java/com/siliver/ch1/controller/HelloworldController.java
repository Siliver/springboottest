package com.siliver.ch1.controller;

import com.siliver.ch1.annotation.Function;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloworldController {

    @RequestMapping("/sayhello.html")
    @Function
    public @ResponseBody String say(String name){
        return "hello"+name;
    }

    @RequestMapping("/sayhello2.html")
    public @ResponseBody String say2(){
        return "hello world!";
    }

    @RequestMapping("say3.html")
    public @ResponseBody String say3(String name){
        return "hello world"+name;
    }
}
