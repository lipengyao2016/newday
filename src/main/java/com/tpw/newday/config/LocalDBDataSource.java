package com.tpw.newday.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class LocalDBDataSource {

    // 将这个对象放入Spring容器中
    @Bean(name = "local_db_dataSource")
    // 读取application.properties中的配置参数映射成为一个对象
    // prefix表示参数的前缀
    @ConfigurationProperties(prefix = "spring.datasource.localdb")
    public DataSource getDateSource1() {
        return DataSourceBuilder.create().build();
    }

}
