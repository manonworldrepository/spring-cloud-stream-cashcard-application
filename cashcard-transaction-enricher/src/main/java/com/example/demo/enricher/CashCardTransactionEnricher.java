package com.example.demo.enricher;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import com.example.demo.service.EnrichmentService;
import java.util.function.Function;
import com.example.demo.domain.EnrichedTransaction;
import com.example.demo.domain.Transaction;

@Configuration
public class CashCardTransactionEnricher {

    @Bean
    EnrichmentService enrichmentService() {
        return new EnrichmentService();
    }

    @Bean
    public Function<Transaction, EnrichedTransaction> enrichTransaction(EnrichmentService enrichmentService) {
        return transaction -> {
            return enrichmentService.enrichTransaction(transaction);
        };
    }

}
