package com.jetbrains.test.boot4.client;

import com.jetbrains.test.boot4.http.sdk.Quote;
import org.springframework.web.service.annotation.GetExchange;

interface QuoteClient {

    @GetExchange(url = "http://localhost:8080/api/quote")
    Quote getQuote();

}
