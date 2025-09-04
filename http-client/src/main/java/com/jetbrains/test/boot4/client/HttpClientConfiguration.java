package com.jetbrains.test.boot4.client;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@ImportHttpServices(QuoteClient.class)
class HttpClientConfiguration {


}
