package com.jetbrains.test.boot4.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.dialect.JdbcDialect;
import org.springframework.data.jdbc.core.dialect.JdbcPostgresDialect;

@Configuration
public class AotConfiguration {
    @Bean
    JdbcDialect dialect() {
        return JdbcPostgresDialect.INSTANCE;
    }
}
