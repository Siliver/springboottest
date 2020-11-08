package com.siliver.ch1.controller.ch4;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siliver.ch1.controller.ch2.JacksonSampleController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.StringWriter;

@Controller
@RequestMapping("/stream")
public class JacksonStreamController {
    private static final Logger logger= LoggerFactory.getLogger(JacksonSampleController.class);

    @Qualifier("getObjectMapper")
    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping("/parser.html")
    public @ResponseBody String parser() throws IOException {
        String json="{\"name\":\"lijz\",\"id\":10}";
        //获取类型转换工厂类
        JsonFactory f=objectMapper.getFactory();
        //初始化键值对
        String key=null,value=null;
        //类型转换对象
        JsonParser parser=f.createParser(json);
        //创建token对象
        JsonToken token=parser.nextToken();
        //获取流流向的下一个token
        token=parser.nextToken();
        //获取到的token是字段名称时，键名称获取当前的转换名称
        if (token==JsonToken.FIELD_NAME){
            key=parser.getCurrentName();
        }
        //获取下一个token名称
        token=parser.nextToken();
        //获取下一个value值
        value=parser.getValueAsString();
        //关闭转换对象
        parser.close();
        //返回键值对象字符串类型
        return key+","+value;
    }

    @RequestMapping("/generator.html")
    public @ResponseBody String generator() throws IOException {
        JsonFactory f=objectMapper.getFactory();
        //转换输出到字符串写入类型
        StringWriter sw=new StringWriter();
        //创建Json转换构造生成器，用于Json类型转换
        JsonGenerator g=f.createGenerator(sw);
        //写入静态对象开始
        g.writeStartObject();
        //写入字段
        g.writeStringField("name","小明");
        g.writeEndObject();
        g.close();
        return sw.toString();
    }
}
