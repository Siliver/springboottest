package com.siliver.ch1.controller.form;

import com.siliver.ch1.entity.Order;
import com.siliver.ch1.entity.OrderDetail;

import java.util.List;

public class OrderPostForm {
    public Order order;
    public List<OrderDetail> details;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }
}
