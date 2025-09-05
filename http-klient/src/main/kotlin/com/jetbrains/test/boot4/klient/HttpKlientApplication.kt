package com.jetbrains.test.boot4.klient

import com.jetbrains.test.boot4.http.sdk.Quote
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.registry.HttpServiceGroup
import org.springframework.web.service.registry.ImportHttpServices

@SpringBootApplication
@ImportHttpServices(QuoteKlient::class, clientType = HttpServiceGroup.ClientType.REST_CLIENT)
open class HttpKlientApplication

fun main(args: Array<String>) {
    runApplication<HttpKlientApplication>(*args)
}

@HttpExchange(url = "http://localhost:8080/api/")
interface QuoteKlient {

    @GetExchange("quote")
    fun fetchRandomQuote(): Quote

    @GetExchange("quote", version = "2.0")
    fun fetchRandomQuotes(): List<Quote>

}

@RestController
@RequestMapping("/api")
class QuoteController(private val klient: QuoteKlient) {
    @GetMapping("quote", produces = ["application/json"])
    fun fetchRandomQuote() = klient.fetchRandomQuote()

    @GetMapping("quotes", produces = ["application/json"])
    fun fetchRandomQuotes() = klient.fetchRandomQuotes()

}