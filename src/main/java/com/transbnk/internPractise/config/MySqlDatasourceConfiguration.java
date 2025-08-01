//package com.transbnk.internPractise.config;
//
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class MySqlDatasourceConfiguration {
//    @ConfigurationProperties("spring.datasource.mysql")
//    @Bean
//    public DataSourceProperties mySqlDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    public DataSource mysqlDatasource() {
//        // The below is the use case 1
////        DriverManagerDataSource dataSource = new DriverManagerDataSource();
////        dataSource.setUsername(mySqlDataSourceProperties().getUsername());
////        dataSource.setPassword(mySqlDataSourceProperties().getPassword());
////        dataSource.setUrl(mySqlDataSourceProperties().getUrl());
////        dataSource.setDriverClassName(mySqlDataSourceProperties().getDriverClassName());
//
//        // The below is the use case 2
//        return mySqlDataSourceProperties().initializeDataSourceBuilder().build();
//    }
//
//}
