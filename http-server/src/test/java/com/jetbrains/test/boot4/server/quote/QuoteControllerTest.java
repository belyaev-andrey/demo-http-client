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

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
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

    @Test
    @DisplayName("GET /api/all returns all quotes as JSON")
    void getAllQuotes_returnsAllQuotesFromService() throws Exception {
        // Arrange
        List<QuoteDto> quotes = List.of(
                new QuoteDto("Quote 1", "Author 1", "Source 1"),
                new QuoteDto("Quote 2", "Author 2", "Source 2"),
                new QuoteDto("Quote 3", "Author 3", "Source 3")
        );
        given(quoteService.getAllQuotes()).willReturn(quotes);

        // Act & Assert
        mockMvc.perform(get("/api/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .apiVersion("1.0")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].quote", is("Quote 1")))
                .andExpect(jsonPath("$[0].author", is("Author 1")))
                .andExpect(jsonPath("$[0].source", is("Source 1")))
                .andExpect(jsonPath("$[1].quote", is("Quote 2")))
                .andExpect(jsonPath("$[2].quote", is("Quote 3")));

        Mockito.verify(quoteService, times(1)).getAllQuotes();
    }

    @Test
    @DisplayName("GET /api/all returns 404 when no quotes exist")
    void getAllQuotes_returns404WhenEmpty() throws Exception {
        // Arrange
        given(quoteService.getAllQuotes()).willReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/api/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .apiVersion("1.0")
                )
                .andExpect(status().isNotFound());

        Mockito.verify(quoteService, times(1)).getAllQuotes();
    }
}

