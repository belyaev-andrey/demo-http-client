package com.jetbrains.test.boot4.server.quote;

import com.jetbrains.test.boot4.http.sdk.QuoteDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Quote entity;
        try {
            entity = quoteProviderDb.findQuote();
            if (entity.getSource().isBlank()) {
                throw new IllegalStateException("A quote is not trusted");
            }
        } catch (Exception e) {
            log.error("Error fetching quote from DB", e);
            entity = quoteProviderFallback.findQuote();
        }
        return new QuoteDto(entity.getText(), entity.getAuthor(), entity.getSource());
    }

    @Transactional
    QuoteDto addQuote(QuoteDto quote) {
        Quote saved = repository.save(new Quote(null, quote.quote(), quote.author(), quote.source()));
        return new QuoteDto(saved.getText(), saved.getAuthor(), saved.getSource());
    }
}
