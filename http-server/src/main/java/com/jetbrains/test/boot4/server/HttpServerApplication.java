package com.jetbrains.test.boot4.server;

import com.jetbrains.test.boot4.server.quote.QuoteProviderRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(QuoteProviderRegistrar.class)
public class HttpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HttpServerApplication.class, args);
    }

}

