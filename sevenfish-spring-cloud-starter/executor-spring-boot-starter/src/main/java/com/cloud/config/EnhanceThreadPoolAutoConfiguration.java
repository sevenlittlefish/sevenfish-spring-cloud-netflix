package com.cloud.config;

import com.cloud.executor.EnhanceThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@EnableConfigurationProperties({EnhanceThreadPoolProperties.class})
public class EnhanceThreadPoolAutoConfiguration {

    private final Logger logger = LoggerFactory.getLogger(EnhanceThreadPoolAutoConfiguration.class);

    @Resource
    private EnhanceThreadPoolProperties enhanceThreadPoolProperties;

    @Bean
    public ThreadPoolExecutor enhanceExecutor() {
        return new EnhanceThreadPoolExecutor(
                null,
                enhanceThreadPoolProperties.getCorePoolSize(),
                enhanceThreadPoolProperties.getMaximumPoolSize(),
                enhanceThreadPoolProperties.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(enhanceThreadPoolProperties.getQueueSize()),
                new ThreadFactory() {
                    private final AtomicInteger sequence = new AtomicInteger(0);

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName(enhanceThreadPoolProperties.getPrefixThreadName() + sequence.incrementAndGet());
                        thread.setUncaughtExceptionHandler((t, e) -> logger.error(t.getName() + " run error:", e));
                        return thread;
                    }
                },
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
