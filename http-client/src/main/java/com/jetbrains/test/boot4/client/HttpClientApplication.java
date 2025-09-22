package com.jetbrains.test.boot4.client;

import com.jetbrains.test.boot4.http.sdk.Quote;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.registry.ImportHttpServices;

@SpringBootApplication
@ImportHttpServices(QuoteClient.class)
class HttpClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(HttpClientApplication.class, args);
    }
}

@HttpExchange(url = "http://localhost:8080/api", accept = "application/json")
interface QuoteClient {

    @GetExchange(url = "/quote")
    Quote fetchRandomQuote();

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

}
