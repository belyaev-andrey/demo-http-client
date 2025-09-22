package com.jetbrains.test.boot4.client;

import com.jetbrains.test.boot4.http.sdk.Quote;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ApiVersionInserter;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.registry.ImportHttpServices;

import java.util.List;

@SpringBootApplication
@ImportHttpServices(QuoteClient.class)
class HttpClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(HttpClientApplication.class, args);
    }

    @Bean
    public RestClientHttpServiceGroupConfigurer groupConfigurer() {
        return groups -> groups.forEachClient((group, builder) ->
                builder.apiVersionInserter(ApiVersionInserter.useHeader("X-API-Version"))
        );
    }
}

@HttpExchange(url = "http://localhost:8080/api", accept = "application/json")
interface QuoteClient {

    @GetExchange(url = "/quote")
    Quote fetchRandomQuote();

    @GetExchange(url = "/quote", version = "2.0")
    List<Quote> fetchRandomQuotes();

}

@RestController
@RequestMapping("/api")
class QuoteController {

    private final QuoteClient client;

    QuoteController(QuoteClient client) {
        this.client = client;
    }
    @GetMapping("quote")
    public ResponseEntity<Quote> getQuote() {
        return ResponseEntity.ok(client.fetchRandomQuote());
    }
    @GetMapping("quotes")
    public ResponseEntity<List<Quote>> getQuotes() {
        return ResponseEntity.ok(client.fetchRandomQuotes());
    }

}
