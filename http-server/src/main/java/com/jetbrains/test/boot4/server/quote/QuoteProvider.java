package com.jetbrains.test.boot4.server.quote;

import com.jetbrains.test.boot4.http.sdk.Quote;

public interface QuoteProvider {
    QuoteEntity findQuote();
}
