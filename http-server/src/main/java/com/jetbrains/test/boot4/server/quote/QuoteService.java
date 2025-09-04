package com.jetbrains.test.boot4.server.quote;

import com.jetbrains.test.boot4.http.sdk.Quote;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class QuoteService {
    private final QuoteRepository repository;

    QuoteService(QuoteRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    Quote getRandomQuote() {
        QuoteEntity e = repository.findRandom().orElseThrow(() -> new QuoteNotFoundException("No quotes found"));
        return new Quote(e.getText(), e.getAuthor(), e.getSource());
    }

    @Transactional
    Quote addQuote(Quote quote) {
        QuoteEntity saved = repository.save(new QuoteEntity(null, quote.quote(), quote.author(), quote.source()));
        return new Quote(saved.getText(), saved.getAuthor(), saved.getSource());
    }
}
