package com.jetbrains.test.boot4.client;

import com.jetbrains.test.boot4.http.sdk.Quote;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.service.registry.HttpServiceClient;

@SpringBootApplication
class HttpClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(HttpClientApplication.class, args);
    }

    @Bean
    ApplicationRunner runner(QuoteClient client) {
        return args -> {
            Quote quote = client.getQuote();
            System.out.println(quote);
        };
    }

}
