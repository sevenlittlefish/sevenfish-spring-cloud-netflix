package com.cloud.controller;

import com.cloud.common.Result;
import com.cloud.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

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
