package com.cloud.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource(){
        return new DruidDataSource();
    }

    /**
     * 需要将 DataSourceProxy 设置为主数据源，否则事务无法回滚
     * @param druidDataSource
     * @return
     */
    @Primary
    @Bean("dataSource")
    public DataSource dataSource(DruidDataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }

    /*@Primary
    @Bean("dataSourceProxy")
    public DataSourceProxy dataSource(DruidDataSource druidDataSource){
        return  new DataSourceProxy(druidDataSource);
    }

    @Bean("jdbcTemplate")
    @ConditionalOnBean(DataSourceProxy.class)
    public JdbcTemplate jdbcTemplate(DataSourceProxy dataSourceProxy) {
        return new JdbcTemplate(dataSourceProxy);
    }*/
}
