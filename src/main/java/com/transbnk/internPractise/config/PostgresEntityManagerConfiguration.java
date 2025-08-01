//package com.transbnk.internPractise.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        basePackages = "", // Paste the path of the Repository defined the repository package
//        entityManagerFactoryRef = "postgresEntityManagerFactoryBean",
//        transactionManagerRef = "postgresTransactionManager"
//)
//public class PostgresEntityManagerConfiguration {
//
//    @Bean(name = "entityManagerFactory")
//    LocalContainerEntityManagerFactoryBean postgresEntityManagerFactoryBean(
//            EntityManagerFactoryBuilder entityManagerFactoryBuilder,
//            @Qualifier("postgresSqlDatasource") DataSource dataSource
//    ) {
//        return entityManagerFactoryBuilder
//                .dataSource(dataSource)
//                .packages("") //Copy & Paste the path of the entity package you want to scan
//                .build();
//    }
//
//    @Bean
//    PlatformTransactionManager postgresTransactionManager(
//           @Qualifier("postgresEntityManagerFactoryBean") LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean
//    ) {
//        assert localContainerEntityManagerFactoryBean.getObject() != null;
//        return new JpaTransactionManager(
//                localContainerEntityManagerFactoryBean.getObject());
//    }
//}
