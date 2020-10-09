package com.tpw.newday.config;



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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryLocalJpa",
        transactionManagerRef="transactionManagerLocalJpa",
        basePackages = {"com.tpw.newday.local_dao"}) //设置Repository所在位置
public class JpaLocalDbDataSourceConf {
    @Autowired
    private JpaProperties jpaProperties;

//    @Autowired
//    private HibernateProperties hibernateProperties;

//    @Resource
//    EntityManagerFactoryBuilder builder;

    @Bean(name="localJpaDataSource")
    @ConfigurationProperties(prefix="spring.datasource.localdb")
    public DataSource axaDataSource() {
        return DataSourceBuilder.create().build();
    }


//    @Bean(name = "entityManagerSec")
//    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
//        return entityManagerFactorySec(builder).getObject().createEntityManager();
//    }

    @Bean(name = "entityManagerFactoryLocalJpa")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryLocalJpa(EntityManagerFactoryBuilder builder
            , @Qualifier("localJpaDataSource") DataSource dataSource) {

        HibernateProperties hibernateSettings = new HibernateProperties();
        Map<String, Object> properties = hibernateSettings.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
        return builder.dataSource(dataSource)
                 .properties(properties)
                .packages("com.tpw.newday.local_bean") //设置实体类所在位置
                .persistenceUnit("secondaryPersistenceUnit")
                .build();
    }

    @Bean(name="transactionManagerLocalJpa")
    PlatformTransactionManager transactionManagerLocalJpa(@Qualifier("entityManagerFactoryLocalJpa") EntityManagerFactory propertyEntityManagerFactory ){
        return new JpaTransactionManager(propertyEntityManagerFactory);
    }
}