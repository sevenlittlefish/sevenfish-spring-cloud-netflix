package com.cloud.controller;

import com.cloud.common.Result;
import com.cloud.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class DemoProviderController {

    @Autowired
    private DemoService demoService;

    @GetMapping("products")
    public Object products(){
        return demoService.listProducts();
    }

    @GetMapping("users")
    public Object users(){
        return demoService.listUsers();
    }

    @PostMapping("modifyUser")
    public Object modifyUser(@RequestParam("id")Long id,
                             @RequestParam("name")String name,
                             @RequestParam("sex")Integer sex){
        demoService.modifyUser(id, name, sex);
        return Result.success();
    }
}
