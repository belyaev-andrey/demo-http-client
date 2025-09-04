package com.jetbrains.test.boot4.klient

import com.jetbrains.test.boot4.http.sdk.Quote
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange
import org.springframework.web.service.registry.ImportHttpServices

@SpringBootApplication
@ImportHttpServices(QuoteKlient::class)
open class HttpKlientApplication

fun main(args: Array<String>) {
    runApplication<HttpKlientApplication>(*args)
}

@HttpExchange("http://localhost:8080/api/")
interface QuoteKlient {

    @GetExchange("quote")
    fun fetchRandomQuote(): Quote

}

@RestController
@RequestMapping("/api")
class QuoteController(private val klient: QuoteKlient) {
    @GetMapping("quote")
    fun fetchRandomQuote() = klient.fetchRandomQuote()
}