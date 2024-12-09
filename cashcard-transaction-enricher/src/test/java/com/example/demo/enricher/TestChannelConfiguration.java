package com.example.demo.enricher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class TestChannelConfiguration {

    @Bean
    public MessageChannel enrichTransactionIn() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel enrichTransactionOut() {
        return new DirectChannel();
    }
}