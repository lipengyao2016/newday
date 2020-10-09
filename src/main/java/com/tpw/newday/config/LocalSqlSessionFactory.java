package com.tpw.newday.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Component
public class LocalSqlSessionFactory {

    @Resource(name = "local_db_dataSource")
    DataSource localDBDataSource;

    @Bean(name = "local_db_sqlSessionFactory")
    // @Qualifier表示查找Spring容器中名字为localdbDataSource的对象
    public SqlSessionFactory getSqlSessionFactory( )
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(localDBDataSource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/localdb/*.xml"));
        return bean.getObject();
    }

}
