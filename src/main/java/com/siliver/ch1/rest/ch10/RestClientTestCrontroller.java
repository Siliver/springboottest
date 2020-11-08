package com.siliver.ch1.rest.ch10;

import com.siliver.ch1.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/test")
public class RestClientTestCrontroller {

    /**
     * 读取配置文件信息
     */
    @Value("${api.order}")
    String base;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @GetMapping("/get/{orderId}")
    public @ResponseBody Order testGetOrder(@PathVariable String orderId){
        //rest请求模板客户端
        RestTemplate client=restTemplateBuilder.build();
        String uri="/order/{orderId}";

        ResponseEntity<Order> responseEntity=client.getForEntity(uri,Order.class,orderId);
        HttpHeaders headers=responseEntity.getHeaders();
        Order order=responseEntity.getBody();
        return order;
    }

    @GetMapping("/getorders")
    public @ResponseBody List<Order> queryOrder(){
        RestTemplate client=restTemplateBuilder.build();
        String uri = base+"/orders?offset={offset}";
        Integer offset = 1;
        ParameterizedTypeReference<List<Order>> typeRef = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<Order>> rs = client.exchange(uri, HttpMethod.GET, null, typeRef, offset);
        List<Order> order = rs.getBody();
        return order;
    }

    @GetMapping("/addorder")
    public @ResponseBody String testAddOrder(){
        RestTemplate client=restTemplateBuilder.build();
        String uri=base+"/order";
        Order order=new Order();
        order.setName("test");
        HttpEntity<Order> body=new HttpEntity<>(order);
        String ret=client.postForObject(uri,body,String.class);
        return ret;
    }
}
