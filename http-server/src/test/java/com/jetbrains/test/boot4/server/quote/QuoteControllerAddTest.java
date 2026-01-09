package com.jetbrains.test.boot4.server.quote;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Import({QuoteControllerVersioningConfig.class, DatabaseTestcontainersConfig.class})
class QuoteControllerAddTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuoteRepository quoteRepository;

    @Test
    @DisplayName("POST /api/quote creates a quote in DB and returns 201 (integration test)")
    void addQuote_createsQuoteAndReturnsCreated() throws Exception {
        // Arrange
        long before = quoteRepository.count();

        String jsonBody = "{" +
                "\"quote\":\"Imagination is more important than knowledge.\"," +
                "\"author\":\"Albert Einstein\"," +
                "\"source\":\"Interview, 1929\"" +
                "}";

        // Act & Assert
        mockMvc.perform(post("/api/quote")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .apiVersion("1.0")
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.id", isA(Number.class)));

        // Verify persistence happened (transaction will roll back after test)
        long after = quoteRepository.count();
        org.assertj.core.api.Assertions.assertThat(after).isEqualTo(before + 1);
    }
}
