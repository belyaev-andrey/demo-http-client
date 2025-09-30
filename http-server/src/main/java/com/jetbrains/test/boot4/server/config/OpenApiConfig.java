package com.jetbrains.test.boot4.server.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "HTTP Server API",
                version = "v1",
                description = "OpenAPI specification for the Demo HTTP Server",
                contact = @Contact(name = "Demo Team", email = "demo@example.com"),
                license = @License(name = "Apache 2.0")
        )
)
@Configuration
class OpenApiConfig {
}
