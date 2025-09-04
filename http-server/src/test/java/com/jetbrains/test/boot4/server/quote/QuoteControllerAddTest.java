package com.jetbrains.test.boot4.server.quote;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("")); // controller builds empty body

        // Verify persistence happened (transaction will roll back after test)
        long after = quoteRepository.count();
        org.assertj.core.api.Assertions.assertThat(after).isEqualTo(before + 1);
    }
}
