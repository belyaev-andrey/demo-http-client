package com.jetbrains.test.boot4.server.quote;

import com.jetbrains.test.boot4.http.sdk.Quote;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Quotes", description = "Operations related to quotes")
class QuoteController {

    private final QuoteService quoteService;

    QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping(path = "/quote", produces = "application/json")
    @Operation(summary = "Get random quote", description = "Returns a random quote from the storage")
    @ApiResponse(responseCode = "200", description = "Random quote returned",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Quote.class)))
    @ApiResponse(responseCode = "404", description = "No quote found", content = @Content(mediaType = "text/plain"))
    ResponseEntity<Quote> getQuote() {
        Quote result = quoteService.getRandomQuote();
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "/quote", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Add a new quote", description = "Creates a new quote entry")
    @ApiResponse(responseCode = "201", description = "Quote created")
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "text/plain"))
    ResponseEntity<Quote> addQuote(@RequestBody Quote quote) {
        quoteService.addQuote(quote);
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
