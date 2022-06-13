package com.cloud.executor;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.dianping.cat.status.StatusExtension;
import com.dianping.cat.status.StatusExtensionRegister;
import org.slf4j.MDC;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;

public class EnhanceThreadPoolExecutor extends ThreadPoolExecutor {

    private String threadPoolName = "enhance-thread-pool";
    private final Map<String, Transaction> txMap = new ConcurrentHashMap<>();

    public EnhanceThreadPoolExecutor(String threadPoolName, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        if (threadPoolName != null && !threadPoolName.isEmpty()) this.threadPoolName = threadPoolName;
        registerStatusExtension();
    }

    public EnhanceThreadPoolExecutor(String threadPoolName, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        if (threadPoolName != null && !threadPoolName.isEmpty()) this.threadPoolName = threadPoolName;
        registerStatusExtension();
    }

    public EnhanceThreadPoolExecutor(String threadPoolName, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        if (threadPoolName != null && !threadPoolName.isEmpty()) this.threadPoolName = threadPoolName;
        registerStatusExtension();
    }

    public EnhanceThreadPoolExecutor(String threadPoolName, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        if (threadPoolName != null && !threadPoolName.isEmpty()) this.threadPoolName = threadPoolName;
        registerStatusExtension();
    }

    @Override
    public void execute(Runnable command) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        super.execute(() -> {
            if (contextMap != null) {
                MDC.setContextMap(contextMap);
            }
            try {
                command.run();
            } finally {
                MDC.clear();
            }
        });
    }

    @Override
    public Future<?> submit(Runnable task) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return super.submit(() -> {
            if (contextMap != null) {
                MDC.setContextMap(contextMap);
            }
            try {
                task.run();
            } finally {
                MDC.clear();
            }
        });
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return super.submit(() -> {
            T result;
            if (contextMap != null) {
                MDC.setContextMap(contextMap);
            }
            try {
                result = task.call();
            } finally {
                MDC.clear();
            }
            return result;
        });
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        Runnable runnable = () -> {
            if (contextMap != null) {
                MDC.setContextMap(contextMap);
            }
            try {
                task.run();
            } finally {
                MDC.clear();
            }
        };
        return super.submit(runnable, result);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        String threadName = Thread.currentThread().getName();
        Transaction transaction = Cat.newTransaction(threadPoolName, threadPoolName);
        txMap.put(threadName, transaction);
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        String threadName = Thread.currentThread().getName();
        Transaction transaction = txMap.get(threadName);
        transaction.setStatus(Message.SUCCESS);
        if (t != null) {
            Cat.logError(t);
            transaction.setStatus(t);
        }
        transaction.complete();
        txMap.remove(threadName);
    }

    public void registerStatusExtension() {
        ThreadPoolExecutor executor = this;
        StatusExtension extension = new StatusExtension() {
            @Override
            public String getId() {
                return threadPoolName;
            }

            @Override
            public String getDescription() {
                return "Thread pool monitoring";
            }

            @Override
            public Map<String, String> getProperties() {
                Map<String, String> map = new LinkedHashMap<>();
                map.put("activeCount", String.valueOf(executor.getActiveCount()));
                map.put("completedTaskCount", String.valueOf(executor.getCompletedTaskCount()));
                map.put("taskCount", String.valueOf(executor.getTaskCount()));
                map.put("queueSize", String.valueOf(executor.getQueue().size()));
                map.put("active/core", String.valueOf((float) executor.getActiveCount() / (float) executor.getCorePoolSize()));
                map.put("active/max", String.valueOf((float) executor.getActiveCount() / (float) executor.getMaximumPoolSize()));
                map.put("coreSize", String.valueOf(executor.getCorePoolSize()));
                map.put("maxSize", String.valueOf(executor.getMaximumPoolSize()));
                map.put("largestPoolSize", String.valueOf(executor.getLargestPoolSize()));
                map.put("keepAliveTime", String.valueOf(executor.getKeepAliveTime(TimeUnit.SECONDS)));
                return map;
            }
        };
        StatusExtensionRegister.getInstance().register(extension);
    }
}
