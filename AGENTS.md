# Project Description

`demo-http-client` is a multi-module Maven project built with Spring Boot 4 that demonstrates modern HTTP client/server patterns, API versioning, Spring Security, PostgreSQL persistence, and Spring AOT support. The project uses Java 24 and showcases both Java and Kotlin implementations of HTTP clients.

# Project Structure

## Module Overview

1. **http-sdk** - Shared types and DTOs used across all modules
   - Contains `QuoteDto` record for inter-module communication
   - Plain Java module with no Spring dependencies
   
2. **http-server** - Spring Boot REST API server
   - Exposes quote management endpoints with API versioning
   - PostgreSQL persistence via Spring Data JDBC and Flyway
   - Spring Security with HTTP Basic authentication
   - OpenAPI documentation via SpringDoc
   - Spring AOT support with buildpacks
   - Docker Compose integration for development

3. **http-client** - Java HTTP client application
   - Demonstrates Spring's declarative HTTP client using `@HttpExchange`
   - Uses `RestClient` with API versioning via `API-Version` header
   - Exposes proxy endpoints at `/api/java/*`

4. **http-klient** - Kotlin HTTP client application
   - Kotlin implementation of the HTTP client
   - Uses Spring's `@HttpExchange` with Kotlin syntax
   - Exposes proxy endpoints at `/api/kotlin/*`

## Server API (http-server)

### Endpoints

- `GET /api/quote` - Returns random quote(s), supports API versioning via `API-Version` header
  - Version `1.0+`: returns one random quote
  - Version `2.0`: returns a list of two random quotes
- `POST /api/quote` - Creates a new quote (requires ADMIN role)
- `GET /api/all` - Returns all quotes (requires ADMIN role)

### Security Model

- Authentication: Spring Security HTTP Basic
- Users and roles loaded from PostgreSQL database
- `POST /api/quote` requires role `ADMIN`
- `GET /api/all` requires role `ADMIN`
- Other endpoints are public

### Data & Persistence

- PostgreSQL database with Spring Data JDBC
- Flyway migrations in `http-server/src/main/resources/db/migration`
- Quote reads use database provider with fallback to in-memory provider on failure
- Docker Compose integration for local development
  - PostgreSQL on port 50829
  - Server available via `client-dev` profile on port 8080
  - Remote debugging available on port 5005

### Spring AOT Support

- AOT compilation enabled via Maven profile (`-Paot`)
- Docker image built with Bellsoft buildpacks
- **Important**: Spring Boot Docker Compose integration is incompatible with AOT
  - Local AOT runs require `localdb` profile
  - Building AOT Docker image requires manually starting PostgreSQL first
- `AotConfiguration` class enables AOT processing without runtime database access

## Client Applications

Both `http-client` (Java) and `http-klient` (Kotlin) demonstrate:
- Declarative HTTP clients using Spring's `@HttpExchange` annotation
- `RestClient` with programmatic configuration
- API versioning via `ApiVersionInserter.useHeader("API-Version")`
- Spring Boot 4 features for HTTP service registration

# Code Layout

## http-server
- `http-server/src/main/java/com/jetbrains/test/boot4/server/quote/` - Quote domain, controller, service, providers, repository
- `http-server/src/main/java/com/jetbrains/test/boot4/server/security/` - User, Role, UserRepository, CustomUserDetailsService
- `http-server/src/main/java/com/jetbrains/test/boot4/server/config/` - SecurityConfig, OpenApiConfig, ErrorControllerAdvice, AotConfiguration
- `http-server/src/test/java/com/jetbrains/test/boot4/server/quote/` - Controller and service tests with Testcontainers
- `http-server/compose.yaml` - Docker Compose configuration with PostgreSQL and optional server (via `client-dev` profile)

## http-client
- `http-client/src/main/java/com/jetbrains/test/boot4/client/HttpClientApplication.java` - Main application with QuoteClient interface and QuoteController

## http-klient
- `http-klient/src/main/kotlin/com/jetbrains/test/boot4/klient/HttpKlientApplication.kt` - Kotlin main application with QuoteKlient interface

## http-sdk
- `http-sdk/src/main/java/com/jetbrains/test/boot4/http/sdk/QuoteDto.java` - Shared DTO record

# Build & Runtime

## Maven Commands
- `mvn clean install` - Build all modules from root directory
- `mvn spring-boot:build-image -Paot` - Build AOT-compiled Docker image (from http-server directory)

## Running with Maven

### http-server

**Option 1: Run locally with Maven**
```shell
cd http-server
mvn spring-boot:run
```
- Uses Spring Boot Docker Compose integration to automatically start PostgreSQL container
- Application runs locally (not in Docker) on port 8080
- PostgreSQL runs in Docker on port 50829
- The `http-server` service in compose.yaml has `client-dev` profile and won't start automatically
- Docker daemon must be running

**Option 2: Run in Docker with Docker Compose**
```shell
cd http-server

# Start PostgreSQL first (required for AOT build)
docker compose up -d postgresql

# Build the Docker image with AOT compilation
mvn spring-boot:build-image -Paot

# Stop PostgreSQL and start both services
docker compose down
docker compose --profile client-dev up
```
- **Important**: AOT compilation requires a running PostgreSQL instance because Spring Boot Docker Compose integration is incompatible with AOT
- Builds AOT-compiled image tagged as `jb/http-server:latest`
- Both PostgreSQL and http-server run in Docker containers
- Server available on port 8080
- PostgreSQL available on port 50829
- Remote debugging available on port 5005
- Server runs with `aot` Spring profile active

### http-client (Java client)
```shell
# First, ensure http-server is running
cd http-client
mvn spring-boot:run
```
- Requires http-server to be running on port 8080
- Client proxy endpoints available at `http://localhost:8081/api/java/*`
- Defaults to port 8081 (configure via `server.port` if needed)

### http-klient (Kotlin client)
```shell
# First, ensure http-server is running
cd http-klient
mvn spring-boot:run
```
- Requires http-server to be running on port 8080
- Client proxy endpoints available at `http://localhost:8082/api/kotlin/*`
- Defaults to port 8082 (configure via `server.port` if needed)

## Docker Compose

All Docker Compose commands should be run from the `http-server` directory.

**Start PostgreSQL only:**
```shell
cd http-server
docker compose up -d postgresql
```

**Start both PostgreSQL and http-server:**
```shell
cd http-server
# Build image first (if not already built)
# Note: PostgreSQL must be running during AOT build
docker compose up -d postgresql
mvn spring-boot:build-image -Paot
docker compose down

# Start services with client-dev profile
docker compose --profile client-dev up
```
- Requires pre-built `jb/http-server:latest` image
- AOT build requires running PostgreSQL (Docker Compose integration incompatible with AOT)
- Server runs with `aot` Spring profile
- Remote debugging available on port 5005

## AOT Compilation
```shell
# Build with AOT
mvn clean package -Paot

# Run with AOT locally (requires running PostgreSQL)
mvn spring-boot:run -Paot
```

# Agent Guidance

## General
- This is a multi-module Maven project; changes should consider inter-module dependencies
- Shared types in `http-sdk` are used by all other modules
- Java 24 language features are available across all modules
- Spring Boot 4 features should be preferred

## When modifying http-server
- Preserve API versioning behavior on `/api/quote`
- Keep security rules aligned with `SecurityConfig`
- Update Flyway migrations for schema changes
- Maintain fallback behavior in `QuoteService` for resilience
- Tests should cover controller behavior, service fallback, and database flows
- AOT compatibility should be preserved (check `AotConfiguration`)

## When modifying clients
- Both Java and Kotlin clients should remain functionally equivalent
- API versioning configuration must match server expectations
- Base URL configuration in `RestClientCustomizer` or properties

## When modifying http-sdk
- Changes affect all modules; coordinate breaking changes carefully
- Keep DTOs simple and serialization-friendly
- Avoid adding dependencies to this module
