package com.siliver.ch1.controller.ch2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.siliver.ch1.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/jackson")
public class JacksonSampleController {
    private static final Logger logger= LoggerFactory.getLogger(JacksonSampleController.class);

    @Qualifier("getObjectMapper")
    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping("/now.json")
    public @ResponseBody Map now(){
        Map mao=new HashMap();
        mao.put("date",new Date());
        return mao;
    }

    @GetMapping("/readtree.json")
    public @ResponseBody String readtree() throws JsonProcessingException {
        String json="{\"name\":\"lijz\":\"id\":10}";
        JsonNode node=objectMapper.readTree(json);

        String name=node.get("name").asText();
        int id=node.get("id").asInt();
        return "name:"+name+",id"+id;
    }

    @RequestMapping("/databind.json")
    public @ResponseBody String databind() throws JsonProcessingException {
        String json="{\"name\":\"lijz\",\"id\":10}";
        User user=objectMapper.readValue(json,User.class);
        return "name:"+user.getName()+",id:"+user.getId();
    }

    @GetMapping("/serialization.json")
    public @ResponseBody String custom() throws JsonProcessingException {
        User user=new User();
        user.setId(1);
        user.setName("小明");
        String str=objectMapper.writeValueAsString(user);
        return str;
    }

    @JsonIgnoreProperties({"id","photo"})
    public static class  SamplePojo{
        Long id;
        String name;
        byte[] photo;
        @JsonIgnore
        BigDecimal salary;
        Map<String,Object> otherProperties=new HashMap<String,Object>();

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getSalary() {
            return salary;
        }

        public byte[] getPhoto() {
            return photo;
        }

        public void setPhoto(byte[] photo) {
            this.photo = photo;
        }

        public void setSalary(BigDecimal salary) {
            this.salary = salary;
        }

        public Map<String, Object> getOtherProperties() {
            return otherProperties;
        }

        public void setOtherProperties(Map<String, Object> otherProperties) {
            this.otherProperties = otherProperties;
        }
    }

    public static class Usererializer extends JsonSerializer<User> {

        @Override
        public void serialize(User value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();;
            gen.writeStringField("user-name",value.getName());
            gen.writeEndObject();
        }
    }

    /**
     * 扩展用户反序列化接口
     */
    public class UserDeserializer extends JsonDeserializer<User> {

        @Override
        public User deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode node=p.getCodec().readTree(p);
            String name=node.get("user-name").asText();
            User user=new User();
            user.setName(name);
            return user;
        }
    }
}
