package com.siliver.ch1.controller.ch4;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @RequestMapping("/index.do")
    public ModelAndView index(){
        ModelAndView view=new ModelAndView("/index.btl");
        view.addObject("name","lijz");
        return view;
    }

    @RequestMapping("/test.do")
    public ModelAndView test(){
        ModelAndView view=new ModelAndView("/test.html");
        return view;
    }
}
