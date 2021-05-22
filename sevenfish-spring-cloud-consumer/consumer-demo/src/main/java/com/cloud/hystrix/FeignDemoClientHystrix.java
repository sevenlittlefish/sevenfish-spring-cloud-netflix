package com.cloud.hystrix;

import com.cloud.pojo.Product;
import com.cloud.service.FeignDemoClient;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class FeignDemoClientHystrix implements FeignDemoClient {

    @Override
    public List<Product> listProducts() {
        List<Product> list = new ArrayList<>();
        list.add(new Product(-1,"异常商品", BigDecimal.ZERO));
        return list;
    }
}
