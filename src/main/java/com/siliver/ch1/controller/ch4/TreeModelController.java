package com.siliver.ch1.controller.ch4;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TreeModelController {

    @RequestMapping("/saybye.html")
    public @ResponseBody String say(){
        return "goodbye";
    }
}
