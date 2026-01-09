package com.jetbrains.test.boot4.server.quote;

import com.jetbrains.test.boot4.http.sdk.QuoteDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
class QuoteService {
    private static final Logger log = LoggerFactory.getLogger(QuoteService.class);
    private final QuoteRepository repository;
    private final QuoteProvider quoteProviderDb;
    private final QuoteProvider quoteProviderFallback;

    QuoteService(QuoteRepository repository,
                 @Qualifier("quoteProviderDb") QuoteProvider quoteProviderDb,
                 @Qualifier("quoteProviderFallback") QuoteProvider quoteProviderFallback) {
        this.repository = repository;
        this.quoteProviderDb = quoteProviderDb;
        this.quoteProviderFallback = quoteProviderFallback;
    }

    @Transactional(readOnly = true)
    QuoteDto getRandomQuote() {
        Quote entity = fetchQuote()
                .filter(q -> !q.getSource().isBlank())
                .orElseGet(() -> {
                    log.error("Error fetching quote from DB");
                    return quoteProviderFallback.findQuote().orElseThrow(() -> new IllegalStateException("No quotes found in fallback provider"));
                });
        return new QuoteDto(entity.getText(), entity.getAuthor(), entity.getSource());
    }

    private Optional<Quote> fetchQuote() {
        try {
            return quoteProviderDb.findQuote();
        } catch (Exception e) {
            log.error("Exception during quote fetch from a DB", e);
            return Optional.empty();
        }

    }

    @Transactional
    Quote addQuote(QuoteDto quote) {
        return repository.save(new Quote(null, quote.quote(), quote.author(), quote.source()));
    }

    public List<QuoteDto> getAllQuotes() {
        return fetchQuotes().stream()
                .map(quote -> new QuoteDto(quote.getText(), quote.getAuthor(), quote.getSource()))
                .toList();
    }

    private List<Quote> fetchQuotes() {
        try {
            return quoteProviderDb.findAllQuotes();
        } catch (Exception e) {
            log.error("Exception during quotes fetch from a DB", e);
            return quoteProviderFallback.findAllQuotes();
        }
    }
}
