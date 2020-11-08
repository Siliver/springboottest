package com.siliver.ch1.controller.ch2;

import com.siliver.ch1.controller.form.OrderPostForm;
import com.siliver.ch1.entity.User;
import com.siliver.ch1.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/javabean")
public class JavaBeanController {
    private static final Logger logger= LoggerFactory.getLogger(JavaBeanController.class);

    @Autowired
    UserService userService;

    @GetMapping("/update.json")
    @ResponseBody
    public String updateUser(User user){
        logger.info(user.getName());
        logger.info(user.getId().toString());
        return "name:"+user.getName()+"id:"+user.getId();
    }

    @GetMapping("/update2.json")
    @ResponseBody
    public String updateUser2(Integer id,String name){
        logger.info(id.toString());
        logger.info(name);
        return "name:"+name+"id:"+id;
    }

    @GetMapping("/update3.json")
    @ResponseBody
    public String updateUser3(@RequestParam(name = "id",required = true) Integer id,String name){
        logger.info(id.toString());
        logger.info(name);
        return "name:"+name+"id:"+id;
    }

    @ResponseBody
    @PostMapping("/saveOrder.json")
    public String saveOrder(OrderPostForm form){
        String orderid=form.getOrder().getId();
        String ordername=form.getOrder().getName();
        long detailid=form.getDetails().stream().findFirst().get().getId();
        String detailname=form.getDetails().stream().findFirst().get().getName();
        return "orderid"+orderid+"ordername"+ordername+"detailid"+detailid+"detailname"+detailname;
    }

    @PostMapping("/savejsonorder.json")
    @ResponseBody
    public String saveOrderByJson(@RequestBody User user){
        return "name:"+user.getName()+"id:"+user.getId();
    }
}
