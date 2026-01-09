package com.jetbrains.test.boot4.server.quote;

import java.util.List;
import java.util.Optional;

public class QuoteProviderFallback implements QuoteProvider {
    @Override
    public Optional<Quote> findQuote() {
        return Optional.of(new Quote(-1L, "Today is not a good day for wise quotes", "Server", "Error Handler"));
    }

    @Override
    public List<Quote> findAllQuotes() {
        return List.of();
    }
}
