package com.jetbrains.test.boot4.server.quote;

import java.util.List;
import java.util.Optional;

public interface QuoteProvider {
    Optional<Quote> findQuote();

    List<Quote> findAllQuotes();
}
