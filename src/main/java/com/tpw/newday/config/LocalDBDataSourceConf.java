package com.tpw.newday.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;

//表示这个类为一个配置类
@Configuration
// 配置mybatis的接口类放的地方
@MapperScan(basePackages = "com.tpw.newday.mapper.localdb", sqlSessionFactoryRef = "local_db_sqlSessionFactory")
public class LocalDBDataSourceConf {

    @Resource(name = "local_db_sqlSessionFactory")
    SqlSessionFactory localSqlSessionFactory;

    @Bean("local_db_sqlSessionTemplate")
    public SqlSessionTemplate getSqlsessiontemplate() {
        try {
            return new SqlSessionTemplate(localSqlSessionFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
