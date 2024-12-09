package com.example.demo.sink;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class TestChannelConfiguration {

    @Bean
    public MessageChannel sinkToConsoleIn() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel sinkToConsoleOut() {
        return new DirectChannel();
    }
}