package com.siliver.ch1.test.controller;

import com.siliver.ch1.controller.ch9.Ch9UserController;
import com.siliver.ch1.entity.User;
import com.siliver.ch1.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
//对需要测试的Controller进行模拟
@WebMvcTest(Ch9UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    UserService userService;

    @Test
    public void testMvc() throws Exception {
        int userId=10;
        int expectedCredit=100;
        //模拟测试userService
        given(this.userService.getCredit(anyInt())).willReturn(100);
        //http 调用
        mvc.perform(get("/user/{id}",userId)).andExpect(content().string(String.valueOf(expectedCredit)));
    }

    @Test
    public void updateUser() throws Exception {
        int userId=1;
        String name="hiliiz";
        int expectedCredit=100;
        given(this.userService.updateUser(any(User.class))).willReturn(true);
        String path="$.success";
        mvc.perform(get("/user/{id}/{name}",userId,name)).andExpect(jsonPath(path).value(true));
    }
}
