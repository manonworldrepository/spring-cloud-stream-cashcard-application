package com.example.demo.service;

import com.example.demo.domain.CashCard;
import com.example.demo.domain.Transaction;
import java.util.concurrent.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DataSourceService {

    private static final Logger log = LoggerFactory.getLogger(DataSourceService.class);

    public Transaction getData() {
        var random = ThreadLocalRandom.current();

        CashCard cashCard = new CashCard(
            random.nextLong(),
            "sarah1",
            random.nextDouble(100.00)
        );

        Transaction transaction = new Transaction(random.nextLong(), cashCard);

        log.info("Generated new transaction data: ID {}", transaction.id());

        return transaction;
    }
}