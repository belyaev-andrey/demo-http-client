package com.jetbrains.test.boot4.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
class HttpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HttpServerApplication.class, args);
    }

}
