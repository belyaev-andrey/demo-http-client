package com.jetbrains.test.boot4.client;

import com.jetbrains.test.boot4.http.sdk.Quote;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.restclient.RestClientCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ApiVersionInserter;
import org.springframework.web.client.RestClient;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.registry.HttpServiceGroup;
import org.springframework.web.service.registry.ImportHttpServices;

import java.util.List;

@SpringBootApplication
@ImportHttpServices(types = QuoteClient.class, clientType = HttpServiceGroup.ClientType.REST_CLIENT)
class HttpClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(HttpClientApplication.class, args);
    }
}

@HttpExchange(url = "http://localhost:8080/api")
interface QuoteClient {

    @GetExchange(url = "/quote", version = "1.0")
    Quote fetchRandomQuote();

    @GetExchange(url = "/quote", version = "2.0")
    List<Quote> fetchRandomQuotes();

}

@RestController
@RequestMapping(value = "/api/java")
class QuoteController {

    private final QuoteClient client;

    QuoteController(QuoteClient client) {
        this.client = client;
    }

    @GetMapping(value = "quote")
    public ResponseEntity<Quote> getQuote() {
        return ResponseEntity.ok(client.fetchRandomQuote());
    }
    @GetMapping(value = "quotes")
    public ResponseEntity<List<Quote>> getQuotes() {
        return ResponseEntity.ok(client.fetchRandomQuotes());
    }

}

/**
 * Programmatic configuration. Properties can be used as an alternative.
 */
@Configuration
class ApiVersioningInserterForQuote implements RestClientCustomizer {
    @Override
    public void customize(RestClient.Builder restClientBuilder) {
        restClientBuilder.apiVersionInserter(ApiVersionInserter.useHeader("API-Version"));
    }
}