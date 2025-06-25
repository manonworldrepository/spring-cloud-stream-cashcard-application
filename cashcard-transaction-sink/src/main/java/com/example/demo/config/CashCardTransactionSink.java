package com.example.demo.config;

import com.example.demo.domain.EnrichedTransaction;
import com.example.demo.sink.CsvWriterService;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CashCardTransactionSink {

    private static final Logger log = LoggerFactory.getLogger(CashCardTransactionSink.class);

    @Bean
    public Consumer<EnrichedTransaction> sinkToConsole() {
        return enrichedTransaction -> log.info("Transaction Received: {}", enrichedTransaction);
    }

    @Bean
    // Inject the new CsvWriterService, which handles all file logic
    public Consumer<EnrichedTransaction> cashCardTransactionFileSink(CsvWriterService writerService) {
        return writerService::writeTransaction;
    }
}