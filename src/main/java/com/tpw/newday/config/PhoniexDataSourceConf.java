package com.tpw.newday.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
// 配置mybatis的接口类放的地方
@MapperScan(basePackages = "com.tpw.newday.phoniex_dao", sqlSessionFactoryRef = "phoniex_db_sqlSessionFactory")
public class PhoniexDataSourceConf {

    // 将这个对象放入Spring容器中
    @Bean(name = "phoniex_db_dataSource")
    // 读取application.properties中的配置参数映射成为一个对象
    // prefix表示参数的前缀
    @ConfigurationProperties(prefix = "spring.datasource.phoniex")
    public DataSource getDateSource1() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "phoniex_db_sqlSessionFactory")
    // @Qualifier表示查找Spring容器中名字为localdbDataSource的对象
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("phoniex_db_dataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapping_phoniex/*.xml"));
        return bean.getObject();
    }

    @Bean("phoniex_db_sqlSessionTemplate")
    public SqlSessionTemplate getSqlsessiontemplate(@Qualifier("phoniex_db_sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        try {
            return new SqlSessionTemplate(sqlSessionFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
