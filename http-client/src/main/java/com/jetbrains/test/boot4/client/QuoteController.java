package com.jetbrains.test.boot4.client;

import com.jetbrains.test.boot4.http.sdk.Quote;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

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
