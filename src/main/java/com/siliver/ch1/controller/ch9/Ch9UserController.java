package com.siliver.ch1.controller.ch9;

import com.siliver.ch1.entity.User;
import com.siliver.ch1.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/Ch9")
public class Ch9UserController {
    private Log log= LogFactory.getLog(this.getClass());

    @Autowired
    UserService userService;

    @RequestMapping("/ch9user/{id}")
    public @ResponseBody String getUser(@PathVariable Integer id){
        return String.valueOf(userService.getCredit(id));
    }

    @RequestMapping("/ch9user/{id}/{name}")
    public @ResponseBody String updateUser(@PathVariable Integer id,@PathVariable String name){
        User user=new User();
        user.setId(id);
        user.setName(name);
        userService.updateUser(user);
        return "{\"success\":true}";
    }
}
