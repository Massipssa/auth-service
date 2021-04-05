package com.anonymizer.auth.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Datasource {

    @Bean
    @ConfigurationProperties("spring.datasource")
    // ??
    public HikariDataSource hikariDataSource() {
        return DataSourceBuilder
                .create()
                //.driverClassName("org.postgresql.Driver")
                .type(HikariDataSource.class)
                .build();
    }
}
