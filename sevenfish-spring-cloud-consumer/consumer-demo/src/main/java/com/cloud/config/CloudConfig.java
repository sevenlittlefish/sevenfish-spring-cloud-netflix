package com.cloud.config;

import brave.sampler.Sampler;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CloudConfig {

    /**
     * ribbon模式请求
     * @return
     */
    @Bean
    //告诉spring cloud创建一个支持ribbon的RestTemplate类
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     * 负载均衡策略，IRule实现类
     * @return
     */
    @Bean
    public IRule rule(){
        return new RandomRule();
    }

    /**
     * zipkin采样器
     * @return
     */
    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}
