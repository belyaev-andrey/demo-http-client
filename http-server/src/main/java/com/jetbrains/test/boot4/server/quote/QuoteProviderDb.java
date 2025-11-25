package com.jetbrains.test.boot4.server.quote;

class QuoteProviderDb implements QuoteProvider {

    private final QuoteRepository quoteRepository;

    QuoteProviderDb(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Override
    public Quote findQuote() {
        return quoteRepository.findRandom().orElseThrow(() -> new QuoteNotFoundException("No quotes found"));
    }
}
