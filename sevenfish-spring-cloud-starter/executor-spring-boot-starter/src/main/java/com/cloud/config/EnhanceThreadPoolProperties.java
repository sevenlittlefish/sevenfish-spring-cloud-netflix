package com.cloud.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "enhance.thread.pool")
public class EnhanceThreadPoolProperties {

    private int corePoolSize = 8;
    private int maximumPoolSize = 100;
    private int keepAliveTime = 60;
    private int queueSize = 100;
    private String prefixThreadName = "default-thread-";
    private String threadPoolName = "enhance-thread-pool";

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public int getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(int keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public String getPrefixThreadName() {
        return prefixThreadName;
    }

    public void setPrefixThreadName(String prefixThreadName) {
        this.prefixThreadName = prefixThreadName;
    }

    public String getThreadPoolName() {
        return threadPoolName;
    }

    public void setThreadPoolName(String threadPoolName) {
        this.threadPoolName = threadPoolName;
    }
}
