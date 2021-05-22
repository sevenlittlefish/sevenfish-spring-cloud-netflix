package com.cloud.service;

import com.cloud.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class RibbonDemoClient {

    private static final String PREFIX_URL = "http://provider-demo-service";

    @Autowired
    private RestTemplate restTemplate;

    public List<Product> listProducts() {
        return restTemplate.getForObject(PREFIX_URL+"/products",List.class);
    }
}
