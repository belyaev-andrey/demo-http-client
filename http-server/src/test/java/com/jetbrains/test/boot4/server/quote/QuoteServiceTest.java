package com.jetbrains.test.boot4.server.quote;

import com.jetbrains.test.boot4.http.sdk.QuoteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuoteServiceTest {

    @Mock
    private QuoteRepository repository;

    @Mock
    private QuoteProvider quoteProviderDb;

    @Mock
    private QuoteProvider quoteProviderFallback;

    private QuoteService quoteService;

    @BeforeEach
    void setUp() {
        quoteService = new QuoteService(repository, quoteProviderDb, quoteProviderFallback);
    }

    @Test
    @DisplayName("getRandomQuote returns quote from DB provider when source is not blank")
    void getRandomQuote_withValidSource_returnsQuoteFromDb() {
        // Arrange
        Quote dbQuote = new Quote("Test quote", "Test author", "Test source");
        given(quoteProviderDb.findQuote()).willReturn(Optional.of(dbQuote));

        // Act
        QuoteDto result = quoteService.getRandomQuote();

        // Assert
        assertThat(result.quote()).isEqualTo("Test quote");
        assertThat(result.author()).isEqualTo("Test author");
        assertThat(result.source()).isEqualTo("Test source");
        verify(quoteProviderDb, times(1)).findQuote();
        verify(quoteProviderFallback, never()).findQuote();
    }

    @Test
    @DisplayName("getRandomQuote returns fallback quote when DB quote has blank source")
    void getRandomQuote_withBlankSource_returnsFallbackQuote() {
        // Arrange
        Quote dbQuote = new Quote("DB quote", "DB author", "");
        Quote fallbackQuote = new Quote("Fallback quote", "Fallback author", "Fallback source");
        given(quoteProviderDb.findQuote()).willReturn(Optional.of(dbQuote));
        given(quoteProviderFallback.findQuote()).willReturn(Optional.of(fallbackQuote));

        // Act
        QuoteDto result = quoteService.getRandomQuote();

        // Assert
        assertThat(result.quote()).isEqualTo("Fallback quote");
        assertThat(result.author()).isEqualTo("Fallback author");
        assertThat(result.source()).isEqualTo("Fallback source");
        verify(quoteProviderDb, times(1)).findQuote();
        verify(quoteProviderFallback, times(1)).findQuote();
    }

    @Test
    @DisplayName("getRandomQuote returns fallback quote when DB provider throws exception")
    void getRandomQuote_withException_returnsFallbackQuote() {
        // Arrange
        Quote fallbackQuote = new Quote("Fallback quote", "Fallback author", "Fallback source");
        given(quoteProviderDb.findQuote()).willThrow(new RuntimeException("DB error"));
        given(quoteProviderFallback.findQuote()).willReturn(Optional.of(fallbackQuote));

        // Act
        QuoteDto result = quoteService.getRandomQuote();

        // Assert
        assertThat(result.quote()).isEqualTo("Fallback quote");
        assertThat(result.author()).isEqualTo("Fallback author");
        assertThat(result.source()).isEqualTo("Fallback source");
        verify(quoteProviderDb, times(1)).findQuote();
        verify(quoteProviderFallback, times(1)).findQuote();
    }

    @Test
    @DisplayName("addQuote saves quote to repository")
    void addQuote_savesQuoteToRepository() {
        // Arrange
        QuoteDto quoteDto = new QuoteDto("New quote", "New author", "New source");
        Quote savedQuote = new Quote(1L, "New quote", "New author", "New source");
        given(repository.save(any(Quote.class))).willReturn(savedQuote);

        // Act
        Quote result = quoteService.addQuote(quoteDto);

        // Assert
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getText()).isEqualTo("New quote");
        assertThat(result.getAuthor()).isEqualTo("New author");
        assertThat(result.getSource()).isEqualTo("New source");
        verify(repository, times(1)).save(any(Quote.class));
    }
}
