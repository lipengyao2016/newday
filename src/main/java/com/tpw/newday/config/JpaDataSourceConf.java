package com.tpw.newday.config;



import com.tpw.newday.service.UserServiceImpl;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactorySec",
        transactionManagerRef="transactionManagerSec",
        basePackages = {"com.tpw.newday.dao"}) //设置Repository所在位置
public class JpaDataSourceConf {

    private static final Log logger = LogFactory.getLog(JpaDataSourceConf.class);
    @Autowired
    private JpaProperties jpaProperties;

//    @Autowired
//    private HibernateProperties hibernateProperties;

//    @Resource
//    EntityManagerFactoryBuilder builder;

    @Bean(name="axaDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.primary")
    public DataSource axaDataSource() {
        DataSource dataSource =  DataSourceBuilder.create().build();
        logger.info(" primary dsn:" );

        return  dataSource;
    }


//    @Bean(name = "entityManagerSec")
//    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
//        return entityManagerFactorySec(builder).getObject().createEntityManager();
//    }

    @Primary
    @Bean(name = "entityManagerFactorySec")
    public LocalContainerEntityManagerFactoryBean entityManagerFactorySec(EntityManagerFactoryBuilder builder
            , @Qualifier("axaDataSource") DataSource dataSource) {
        HikariDataSource d2 = (HikariDataSource) dataSource;
        logger.info(" 22 " + d2.getJdbcUrl() );
        HibernateProperties hibernateSettings = new HibernateProperties();
        Map<String, Object> properties = hibernateSettings.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
        return builder.dataSource(dataSource)
                 .properties(properties)
                .packages("com.tpw.newday.bean") //设置实体类所在位置
                .persistenceUnit("secPersistenceUnit")
                .build();
    }

    @Primary
    @Bean(name="transactionManagerSec")
    PlatformTransactionManager transactionManagerSec(@Qualifier("entityManagerFactorySec") EntityManagerFactory propertyEntityManagerFactory ){
        logger.info(" 33 " );
        return new JpaTransactionManager(propertyEntityManagerFactory);
    }
}