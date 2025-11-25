package com.example.demo.enricher;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import com.example.demo.service.EnrichmentService;
import com.example.demo.domain.EnrichedTransaction;
import com.example.demo.domain.Transaction;
import java.util.function.Function;
import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class CashCardTransactionEnricher {

    @Bean
    EnrichmentService enrichmentService() {
        return new EnrichmentService();
    }

    @Bean
    public Function<Transaction, EnrichedTransaction> enrichTransaction(EnrichmentService enrichmentService) {
        return transaction -> {
            log.info("Received transaction for enrichment: {}", transaction);

            EnrichedTransaction enrichedTransaction = enrichmentService.enrichTransaction(transaction);

            log.info("Producing enriched transaction: {}", enrichedTransaction);

            return enrichedTransaction;
        };
    }

}
