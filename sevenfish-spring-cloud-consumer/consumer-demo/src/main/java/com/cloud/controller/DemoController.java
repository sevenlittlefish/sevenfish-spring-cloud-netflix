package com.cloud.controller;

import com.cloud.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("demo")
//加了此注解请求/actuator/bus-refresh刷新version才会生效
@RefreshScope
public class DemoController {

    @Autowired
    private RibbonDemoClient ribbonDemoClient;

    @Autowired
    private FeignDemoClient feignDemoClient;

    //SpringCloudConfig中的配置
    @Value("${version}")
    private String version;

    @GetMapping("ribbon/products")
    public Object ribbonProducts(){
        return ribbonDemoClient.listProducts();
    }

    @GetMapping("feign/products")
    public Object feignProducts(){
        Map<String,Object> result = new HashMap<>(2);
        result.put("list",feignDemoClient.listProducts());
        result.put("version",version);
        return result;
    }
}
