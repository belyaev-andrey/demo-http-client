package com.jetbrains.test.boot4.server.quote;

import com.jetbrains.test.boot4.http.sdk.QuoteDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = QuoteController.class)
@Import(QuoteControllerVersioningConfig.class)
class QuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private QuoteRepository quoteRepository;

    @MockitoBean
    private QuoteProviderDb quoteProviderDb;

    @MockitoBean
    private QuoteProviderFallback quoteProviderFallback;

    @MockitoBean
    private QuoteService quoteService;

    @Test
    @DisplayName("GET /api/quote returns a random quote as JSON and calls service once")
    void getQuote_returnsJsonFromService() throws Exception {
        // Arrange
        QuoteDto quote = new QuoteDto(
                "Stay hungry, stay foolish.",
                "Steve Jobs",
                "Stanford Commencement 2005"
        );
        given(quoteService.getRandomQuote()).willReturn(quote);

        // Act & Assert
        mockMvc.perform(get("/api/quote")
                        .accept(MediaType.APPLICATION_JSON)
                        .apiVersion("1.0")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.quote", is("Stay hungry, stay foolish.")))
                .andExpect(jsonPath("$.author", is("Steve Jobs")))
                .andExpect(jsonPath("$.source", is("Stanford Commencement 2005")));

        Mockito.verify(quoteService, times(1)).getRandomQuote();
    }
}

