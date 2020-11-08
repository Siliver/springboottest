package com.siliver.ch1.service;

import com.siliver.ch1.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    public User findUser(int id);
    public Integer addUser(User user);
    public List<User> getAllUser(int start,int end);
    public User getUser(String name);
    public User getUser(String name,Integer departmentId);
    public Page<User> queryUser(Integer departmentId, Pageable page);
    public Page<User> queryUser2(Integer departmentId,Pageable page);
    public List<User> getByExample(String name) ;
    public void updateUser();


    //————————————————————————
    //ch9
    //————————————————————————
    public int getCredit(int userId);
    public boolean updateUser(User user);
}
