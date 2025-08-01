//package com.transbnk.internPractise.config;
//
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class PostgresSqlDatasourceConfiguration {
//    @ConfigurationProperties("spring.datasource.pg")
//    @Bean
//    public DataSourceProperties postgresSqlDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
////    @Primary // This makes the primary DB to used.
//    @Bean
//    public DataSource postgresSqlDatasource() {
//        // The below is the use case 1
////        DriverManagerDataSource dataSource = new DriverManagerDataSource();
////        dataSource.setUsername(mySqlDataSourceProperties().getUsername());
////        dataSource.setPassword(mySqlDataSourceProperties().getPassword());
////        dataSource.setUrl(mySqlDataSourceProperties().getUrl());
////        dataSource.setDriverClassName(mySqlDataSourceProperties().getDriverClassName());
//
//        // The below is the use case 2
//        return postgresSqlDataSourceProperties().initializeDataSourceBuilder().build();
//    }
//}
