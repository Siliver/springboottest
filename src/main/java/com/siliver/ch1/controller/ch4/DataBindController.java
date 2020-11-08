package com.siliver.ch1.controller.ch4;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siliver.ch1.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DataBindController {
    @Qualifier("getObjectMapper")
    @Autowired
    ObjectMapper mapper;

    /**
     * 进行用户集合返回
     * @param list
     * @return
     */
    @RequestMapping("/updateUsers.json")
    public @ResponseBody String say(@RequestBody List<User> list){
        StringBuilder str=new StringBuilder();
        for (User user:list){
            str.append(user.getName()).append(" ");
        }
        return str.toString();
    }

    /**
     * 进行造假的用户信息解析，通过Jackson进行数据变更转换和字符换传送
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/customize.json")
    public @ResponseBody String customize() throws JsonProcessingException {
        String jsonInput="[{\"id\":2,\"name\":\"xiandafu\"},{\"id\":3,\"name\":\"lucy\"}]";
        JavaType type=getCollectionType(List.class,User.class);
        List<User> list=mapper.readValue(jsonInput,type);
        return String.valueOf(list.size());
    }

    public JavaType getCollectionType(Class<?> collectionClass,Class<?>... elementClasses){
        return mapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
    }

    @RequestMapping("/dept.json")
    public @ResponseBody Department getDepartment(){
        return new Department(1);
    }

    class Department{

        Map map=new HashMap();

        int id;

        public Department(int id){
            this.id=id;
            map.put("newAttr",1);
        }

        @JsonAnyGetter
        public Map<String,Object> getOtherProperties(){
            return map;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
