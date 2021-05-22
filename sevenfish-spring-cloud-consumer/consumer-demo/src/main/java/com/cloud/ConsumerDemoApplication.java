package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//eureka客户端
//@EnableEurekaClient
//服务发现
@EnableDiscoveryClient
//采用Feign模式
@EnableFeignClients
//断路器
@EnableCircuitBreaker
public class ConsumerDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerDemoApplication.class,args);
    }
}
