package com.siliver.ch1.controller.ch7;

import com.siliver.ch1.config.EnvConfig;
import com.siliver.ch1.config.ServerConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

@Controller
public class PropertyController {
    private Log log= LogFactory.getLog(PropertyController.class);

    @Autowired
    EnvConfig envConfig;

    @Autowired
    ServerConfig serverConfig;

    @RequestMapping("/sayacess.html")
    public String say(){
        log.info("acess");
        return "/static/index.html";
    }



    @RequestMapping("/showenvch7.html")
    public @ResponseBody String env(){

        return "port:"+envConfig.getServerPort();
    }

    /**
     *
     * @param port 通过@Value 进行数据注入
     * @return
     */
    @RequestMapping("/showenv.html")
    public @ResponseBody String value(@Value("9090") int port){
        return "port:"+port;
    }

    @RequestMapping("/showserver.html")
    public @ResponseBody String value(){
        return "port"+serverConfig.getPort()+"contextPath:"+serverConfig.getServlet().getPath();
    }
}
