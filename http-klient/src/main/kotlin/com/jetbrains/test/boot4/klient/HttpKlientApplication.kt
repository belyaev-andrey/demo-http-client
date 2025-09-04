package com.jetbrains.test.boot4.klient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class HttpKlientApplication

fun main(args: Array<String>) {
    runApplication<HttpKlientApplication>(*args)
}
