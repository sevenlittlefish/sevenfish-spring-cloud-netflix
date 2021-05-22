package com.cloud.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "provider-account-service")
public interface FeignAccountClient {

    @RequestMapping("changeMoney")
    void changeMoney(@RequestParam("userId") String userId, @RequestParam("money") Integer money);
}
