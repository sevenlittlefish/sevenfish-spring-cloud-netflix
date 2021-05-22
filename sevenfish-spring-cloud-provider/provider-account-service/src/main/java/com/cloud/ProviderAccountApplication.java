package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//eureka客户端
//@EnableEurekaClient
//服务发现
@EnableDiscoveryClient
@EnableFeignClients
@EnableJpaRepositories
public class ProviderAccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderAccountApplication.class,args);
    }
}
