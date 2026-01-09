package com.jetbrains.test.boot4.server.quote;

import java.util.List;
import java.util.Optional;

class QuoteProviderDb implements QuoteProvider {

    private final QuoteRepository quoteRepository;

    QuoteProviderDb(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Override
    public Optional<Quote> findQuote() {
        return quoteRepository.findRandom();
    }

    @Override
    public List<Quote> findAllQuotes() {
        return quoteRepository.findAll();
    }

}
