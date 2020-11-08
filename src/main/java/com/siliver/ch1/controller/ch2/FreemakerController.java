package com.siliver.ch1.controller.ch2;

import com.siliver.ch1.entity.User;
import com.siliver.ch1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/freemarker")
public class FreemakerController {
    @Autowired
    UserService userService;

    @GetMapping("/showuser.html")
    public ModelAndView showUserInfo(Long id){
        ModelAndView view=new ModelAndView();
        //User user=userService.getUserById(id);
        User user=new User();
        view.addObject("user",user);
        view.setViewName("/userinfo");
        return view;
    }
}
