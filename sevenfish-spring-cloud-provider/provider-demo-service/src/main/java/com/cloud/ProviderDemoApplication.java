package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//eureka客户端
//@EnableEurekaClient
//服务发现
@EnableDiscoveryClient
@EnableCircuitBreaker
public class ProviderDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderDemoApplication.class,args);
        //使用下面代码指定端口需要注释掉配置文件里server.port配置
//        int port = 0;
//        int defaultPort = 8806;
//        Future<Integer> future = ThreadPoolUtil.getThreadPool().submit(() -> {
//            System.out.println("请于10秒内输入端口号，超过10秒默认使用端口号：" + defaultPort);
//            Scanner sc = new Scanner(System.in);
//            String portStr = sc.nextLine();
//            return Integer.parseInt(portStr);
//        });
//        try {
//            port = future.get(10, TimeUnit.SECONDS);
//        } catch (InterruptedException | ExecutionException | TimeoutException e) {
//            e.printStackTrace();
//            port = defaultPort;
//        }
//        new SpringApplicationBuilder(ProviderDemoServiceApplication.class).properties("server.port="+port).run(args);
    }
}
