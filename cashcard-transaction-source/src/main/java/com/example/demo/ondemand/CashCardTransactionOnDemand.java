package com.example.demo.ondemand;

import com.example.demo.domain.Transaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class CashCardTransactionOnDemand {

    private final Sinks.Many<Transaction> transactionSink = Sinks.many().multicast().onBackpressureBuffer();

    @Bean
    public Supplier<Flux<Transaction>> approvalRequest() {
        return transactionSink::asFlux;
    }

    public void publishOnDemand(Transaction transaction) {
        transactionSink.emitNext(transaction, Sinks.EmitFailureHandler.FAIL_FAST);
    }
}