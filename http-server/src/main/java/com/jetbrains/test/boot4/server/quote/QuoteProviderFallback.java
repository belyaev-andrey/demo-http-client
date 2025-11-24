package com.jetbrains.test.boot4.server.quote;

public class QuoteProviderFallback implements QuoteProvider {
    @Override
    public Quote findQuote() {
        return new Quote(-1L, "Today is not a good day for wise quotes", "Server", "Error Handler");
    }
}
