package com.jetbrains.test.boot4.client;

import com.jetbrains.test.boot4.http.sdk.Quote;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "http://localhost:8080/api", accept = "application/json")
interface QuoteClient {

    @GetExchange(url = "/quote")
    Quote fetchRandomQuote();

}
