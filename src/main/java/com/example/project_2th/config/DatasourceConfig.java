package com.example.project_2th.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {

    @Bean
    public DataSource mySqlDataSource()
    {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/project?serverTimezone=UTC&characterEncoding=UTF-8&useSSL=false");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("dkwktm45");
        return dataSourceBuilder.build();
    }
}
