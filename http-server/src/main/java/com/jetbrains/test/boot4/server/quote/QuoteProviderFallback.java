package com.jetbrains.test.boot4.server.quote;

public class QuoteProviderFallback implements QuoteProvider {
    @Override
    public QuoteEntity findQuote() {
        return new QuoteEntity(-1L, "Today is not a good day for wise quotes", "Server", "Error Handler");
    }
}
