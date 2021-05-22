package com.cloud.config.db;

public class DataSourceHolder {

    private static ThreadLocal<DataSourceEnum> threadLocal = new ThreadLocal<>();

    public static void setSourceType(DataSourceEnum sourceType){
        threadLocal.set(sourceType);
    }

    public static DataSourceEnum getSourceType(){
        return threadLocal.get();
    }

    public static void removeSourceType(){
        threadLocal.remove();
    }
}
