package com.siliver.ch1.rest.ch10;

import org.beetl.ext.simulate.WebSimulate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v2")
public class OrderApi2Crontroller {
    @Autowired
    WebSimulate webSimulate;

    @RequestMapping("/**")
    public void simluateJson(HttpServletRequest request, HttpServletResponse response){
        webSimulate.execute(request,response);
    }
}
