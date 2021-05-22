package com.cloud.client;

import com.cloud.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "provider-order-service")
public interface FeignOrderClient {

    @RequestMapping("create")
    void create(Order order);
}
