package com.cloud.service;

import com.cloud.hystrix.FeignDemoClientFallbackFactory;
import com.cloud.hystrix.FeignDemoClientHystrix;
import com.cloud.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "provider-demo-service",fallback = FeignDemoClientHystrix.class)
//@FeignClient(value = "provider-demo-service",fallbackFactory = FeignDemoClientFallbackFactory.class)
public interface FeignDemoClient {

    @GetMapping("products")
    List<Product> listProducts();
}
