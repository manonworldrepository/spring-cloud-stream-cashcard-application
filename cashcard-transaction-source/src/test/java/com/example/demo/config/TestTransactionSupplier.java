package com.example.demo.config;

import com.example.demo.domain.CashCard;
import com.example.demo.domain.Transaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

@Configuration
public class TestTransactionSupplier {

    @Bean
    public Supplier<Transaction> supplyTestTransaction() {
        return () -> new Transaction(
                99L,
                new CashCard(99L, "sarah1", 0.0)
        );
    }
}