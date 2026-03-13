package com.jetbrains.test.boot4.server;

import com.jetbrains.test.boot4.server.quote.QuoteProviderRegistrar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Import(QuoteProviderRegistrar.class)
public class HttpServerApplication {

    private static final Logger log = LoggerFactory.getLogger(HttpServerApplication.class);

    public static void main(String[] args) {
        var app = SpringApplication.run(HttpServerApplication.class, args);
        ConfigurableEnvironment environment = app.getEnvironment();
        String appName = environment.getProperty("spring.application.name");
        log.info("Application Name: {}", appName);
    }

}

