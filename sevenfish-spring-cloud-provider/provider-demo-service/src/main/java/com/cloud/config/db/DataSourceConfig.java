package com.cloud.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Autowired
    JpaProperties jpaProperties;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DruidDataSource masterDataSource(){
        return new DruidDataSource();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DruidDataSource slaveDataSource(){
        return new DruidDataSource();
    }

    @Bean
    public DynamicDataSource dataSource(@Qualifier("masterDataSource") DruidDataSource masterDataSource,
                                        @Qualifier("slaveDataSource") DruidDataSource slaveDataSource){
        DynamicDataSource source = new DynamicDataSource();
        //默认数据源
        source.setDefaultTargetDataSource(masterDataSource);
        Map<Object,Object> targets = new HashMap<>(2);
        targets.put(DataSourceEnum.MASTER,masterDataSource);
        targets.put(DataSourceEnum.SLAVE,slaveDataSource);
        //数据源集合
        source.setTargetDataSources(targets);
        return source;
    }

    /**
     * EntityManagerFactory类似于Hibernate的SessionFactory，mybatis的SqlSessionFactory
     * 总之，在执行操作之前,我们总要获取一个EntityManager，这就类似于Hibernate的Session，mybatis的sqlSession.
     * @return
     */
    @Bean
    public EntityManagerFactory entityManagerFactory(DynamicDataSource dataSource){
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(adapter);
        factoryBean.setPackagesToScan("com.cloud.entity");
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaPropertyMap(jpaProperties.getProperties());
        //在完成了其它所有相关的配置加载以及属性设置后,才初始化
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    /**
     * 配置事务管理
     * @param entityManagerFactory
     * @return
     */
    @Bean("transactionManager")
    public PlatformTransactionManager platformTransactionManager(EntityManagerFactory entityManagerFactory){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
