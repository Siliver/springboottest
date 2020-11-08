package com.siliver.ch1.controller.ch2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siliver.ch1.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/modelattribute")
public class ModelAttributeController {
    private static final Logger logger= LoggerFactory.getLogger(ModelAttributeController.class);
    @Autowired
    UserService userService;

    @Qualifier("getObjectMapper")
    ObjectMapper objectMapper;

    /**
     * Controller方法中的公共方法，用于描述模型，调用方法时会先调用此方法
     * @param id 用户编号
     * @param model 用户模型
     */
    @ModelAttribute
    public void findUserById(@PathVariable Long id, Model model){
        //model.addAttribute("user",userService.getUserById(id));
    }

    @GetMapping("/{id}/get.json")
    @ResponseBody
    public String getUser(Model model) throws JsonProcessingException {
        logger.info(objectMapper.writeValueAsString(model.getAttribute("user")));
        return objectMapper.writeValueAsString(model.getAttribute("user"));
    }
}
