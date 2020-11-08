package com.siliver.ch1.controller.ch2;

import com.siliver.ch1.entity.User;
import com.siliver.ch1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/model")
public class ModelAndViewController {
    @Autowired
    UserService userService;

    @GetMapping("/{userid}/get.html")
    public String getUser(@PathVariable Long userid, Model model){
        //User userinfo=userService.getUserById(userid);
        model.addAttribute("user",new User());
        return "/userinfo.btl";
    }

    @GetMapping("/{userid}/get2.html")
    public ModelAndView getUser2(@PathVariable Long userid,ModelAndView view){
        //User user=userService.getUserById(userid);
        view.addObject("user",new User());
        view.setViewName("/userinfo.btl");
        return view;
    }
}
