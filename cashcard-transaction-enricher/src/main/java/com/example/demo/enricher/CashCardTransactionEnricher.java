package com.example.demo.enricher;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import com.example.demo.service.EnrichmentService;
import com.example.demo.domain.EnrichedTransaction;
import com.example.demo.domain.Transaction;
import reactor.core.publisher.Flux;
import java.util.function.Function;
import java.util.List;

@Configuration
public class CashCardTransactionEnricher {

    @Bean
    EnrichmentService enrichmentService() {
        return new EnrichmentService();
    }

    @Bean
    public Function<Flux<List<Transaction>>, Flux<EnrichedTransaction>> enrichTransaction(EnrichmentService enrichmentService) {
        return transaction ->
            transaction.flatMap(Flux::fromIterable)
                .map(enrichmentService::enrichTransaction);
    }

}
