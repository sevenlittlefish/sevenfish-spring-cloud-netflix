package com.cloud.controller;

import com.cloud.entity.Order;
import com.cloud.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "create",consumes = "application/json")
    public void create(@RequestBody Order order){
        orderService.save(order);
    }
}
