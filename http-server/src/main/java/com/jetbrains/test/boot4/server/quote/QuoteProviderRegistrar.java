package com.jetbrains.test.boot4.server.quote;

import org.springframework.beans.factory.BeanRegistrar;
import org.springframework.beans.factory.BeanRegistry;
import org.springframework.core.env.Environment;

public class QuoteProviderRegistrar implements BeanRegistrar {

    @Override
    public void register(BeanRegistry registry, Environment env) {
        registry.registerBean("quoteProviderDb", QuoteProviderDb.class);
        registry.registerBean("quoteProviderFallback", QuoteProviderFallback.class);
    }
}
