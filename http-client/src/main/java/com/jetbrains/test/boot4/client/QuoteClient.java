package com.jetbrains.test.boot4.client;

import com.jetbrains.test.boot4.http.sdk.Quote;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.ApiVersionInserter;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "http://localhost:8080/api", accept = "application/json")
interface QuoteClient {

    @GetExchange(url = "/quote")
    Quote fetchRandomQuote();

}

@Configuration(proxyBeanMethods = false)
class HttpClientsConfiguration {

    @Bean
    public RestClientHttpServiceGroupConfigurer groupConfigurer() {
        return groups -> groups.forEachClient((group, builder) ->
                builder.apiVersionInserter(ApiVersionInserter.useHeader("X-API-Version"))
        );
    }
}