package com.siliver.ch1.controller.ch2;

import com.siliver.ch1.entity.User;
import com.siliver.ch1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/beetl")
public class BeetlController {
    @Autowired
    UserService userService;

    @RequestMapping("/index.html")
    public String say(Model model){
        model.addAttribute("name","hello world");
        return "/index.btl";
    }

    @RequestMapping("/showuser.html")
    public ModelAndView showUserInfo(Long id){
        ModelAndView view=new ModelAndView();
        //User user=userService.getUserById(id);
        User user=null;
        view.addObject("user",user);
        view.setViewName("/userinfo.btl");
        return view;
    }
}
