package com.siliver.ch1.rest.ch10;

import com.siliver.ch1.entity.Order;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/vi")
public class OrderApiCrontroller {
    @RequestMapping("/order/{orderId}")
    public Order getOrder(@PathVariable String orderId){
        Order order=new Order();
        order.setId("O170501-"+orderId);
        return order;
    }

    @GetMapping("/order")
    public List<Order> getOrder(Integer offset){
        Order order=new Order();
        order.setId("O170501-1");
        List<Order> list=new ArrayList<Order>();
        list.add(order);
        return list;
    }

    @PostMapping("/order")
    public String addOrder(@RequestBody Order order){
        return "{\"success\":true,\"message\":\"添加成功\"}";
    }

    @PostMapping("/orders")
    public String batchAdd(@RequestBody List<Order> order){
        return "{success:true,message:\"批量添加成功\"}";
    }

    @PutMapping("/order")
    public String updateOrder(Order order){
        return "{\"success\":true,\"message\":\"更新成功\"}";
    }

    @DeleteMapping("/order/{orderId}")
    public String cancelOrder(@PathVariable("orderId") String id){
        return "{\"success\":true,\"message\":\"订单取消成功\"}";
    }
}
