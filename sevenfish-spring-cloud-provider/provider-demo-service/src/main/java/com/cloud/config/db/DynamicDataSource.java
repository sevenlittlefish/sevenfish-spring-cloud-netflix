package com.cloud.config.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * #setTargetDataSources：设置数据源集合
 * #setDefaultTargetDataSource：设置默认数据源
 * #determineCurrentLookupKey：寻找与当前值相匹配的数据源
 * #determineTargetDataSource：真正返回数据源的方法，首先根据用户重写的determineCurrentLookupKey方法
 * 从setTargetDataSources设置的数据源集合中找到相匹配的数据源，找到则直接返回，
 * 没有找到则返回setDefaultTargetDataSource设置的默认数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceHolder.getSourceType();
    }
}
