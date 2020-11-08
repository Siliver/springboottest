package com.siliver.ch1.controller.ch6;

import com.siliver.ch1.entity.User;
import com.siliver.ch1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/ch6")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/finduser.html")
    public @ResponseBody String findUser(int userId){
        User user=userService.findUser(userId);
        return user.getName();
    }

    @RequestMapping("adduser.html")
    public @ResponseBody String addUser(User user){
        int id=userService.addUser(user);
        return String.valueOf(id);
    }

    @RequestMapping("/alluser.html")
    public @ResponseBody List<User> allUser(int page, int size){
        List<User> list=userService.getAllUser(page,size);
        return list;
    }

    @RequestMapping("/getuser.html")
    public @ResponseBody User getUser(String name){
        User user=  userService.getUser(name);
        return user;
    }

    @RequestMapping("/getdepartuser.html")
    public @ResponseBody String getDepartmentUser(String name,Integer deptId){
        User user=  userService.getUser(name, deptId);
        return user==null?"":String.valueOf(user.getName());
    }

    @RequestMapping("/pagequery.html")
    public @ResponseBody Page<User> pageQuery(Integer deptId,int page,int size){
        PageRequest pr = PageRequest.of(page,size);
		//Page<User> users =  userService.queryUser(deptId, pr);
        Page<User> users =  userService.queryUser2(deptId, pr);
        //return String.valueOf(users.getTotalElements());
        return users;
    }

    @RequestMapping("/example.html")
    public @ResponseBody String example(String name){
        List<User> users = userService.getByExample(name);
        return String.valueOf(users.size());
    }

    @RequestMapping("/test.html")
    public @ResponseBody String test(){
        userService.updateUser();
        return "success";
    }
}
