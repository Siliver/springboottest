package com.siliver.ch1.dao;

import com.siliver.ch1.entity.User;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("UserDao")
@SqlResource("www.user")
public interface UserDao extends BaseMapper<User> {
    public List<User> selectSample(User query);
}