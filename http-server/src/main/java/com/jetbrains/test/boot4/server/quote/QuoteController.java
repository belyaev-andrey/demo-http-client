package com.jetbrains.test.boot4.server.quote;

import com.jetbrains.test.boot4.http.sdk.Quote;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
class QuoteController {

    private final QuoteService quoteService;

    QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping(path = "/quote", produces = "application/json")
    ResponseEntity<Quote> getQuote() {
        Quote result = quoteService.getRandomQuote();
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "/quote", consumes = "application/json", produces = "application/json")
    ResponseEntity<Quote> addQuote(@RequestBody Quote quote) {
        Quote created = quoteService.addQuote(quote);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

@ControllerAdvice
class ErrorControllerAdvice {

    @ExceptionHandler(QuoteNotFoundException.class)
    public ResponseEntity<String> handleQuoteNotFoundException(QuoteNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
