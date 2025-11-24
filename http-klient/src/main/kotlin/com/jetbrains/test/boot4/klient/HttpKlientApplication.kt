package com.jetbrains.test.boot4.klient

import com.jetbrains.test.boot4.http.sdk.QuoteDto
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
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

@HttpExchange
interface QuoteKlient {

    @GetExchange("quote", version = "1.0")
    fun fetchRandomQuote(): QuoteDto

    @GetExchange("quote", version = "2.0")
    fun fetchRandomQuotes(): List<QuoteDto>

}

@RestController
@RequestMapping("/api/kotlin", produces = [MediaType.APPLICATION_JSON_VALUE])
class QuoteController(private val klient: QuoteKlient) {
    @GetMapping("quote")
    fun fetchRandomQuote(): ResponseEntity<QuoteDto> = ResponseEntity.ok(klient.fetchRandomQuote())

    @GetMapping("quotes")
    fun fetchRandomQuotes(): ResponseEntity<List<QuoteDto>> = ResponseEntity.ok(klient.fetchRandomQuotes())

}