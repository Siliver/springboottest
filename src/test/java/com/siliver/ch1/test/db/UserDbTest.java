package com.siliver.ch1.test.db;

import com.siliver.ch1.dao.UserDao;
import com.siliver.ch1.entity.User;
import com.siliver.ch1.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserDbTest {
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;

    @Test
    @Sql("classpath:com/bee/sample/ch9/test/db/user.sql")
    public void updateNameTest(){
        User user=new User();
        user.setId(1);
        user.setName("hello123");
        boolean success=userService.updateUser(user);
        User dbUser=userDao.unique(1);
        assertEquals(dbUser.getName(),"hello123");
    }
}
