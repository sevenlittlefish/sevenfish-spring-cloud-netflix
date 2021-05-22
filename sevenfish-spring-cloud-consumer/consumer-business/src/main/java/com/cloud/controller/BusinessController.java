package com.cloud.controller;

import com.cloud.client.FeignDemoClient;
import com.cloud.common.Result;
import com.cloud.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("business")
public class BusinessController {

    @Autowired
    private FeignDemoClient demoClient;

    @Autowired
    private BusinessService businessService;

    @GetMapping("products")
    public Object feignProducts(){
        Map<String,Object> result = new HashMap<>(2);
        result.put("list",demoClient.listProducts());
        return result;
    }

    @PostMapping("purchase")
    public Object purchase(@RequestParam("userId")String userId,
                           @RequestParam("commodityCode")String commodityCode,
                           @RequestParam("num")Integer num){
        try {
            return businessService.purchase(userId, commodityCode, num);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }
}
