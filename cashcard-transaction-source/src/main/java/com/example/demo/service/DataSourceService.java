package com.example.demo.service;

import com.example.demo.domain.CashCard;
import com.example.demo.domain.Transaction;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;

@Service
public class DataSourceService {

    public Transaction getData() {
        var random = ThreadLocalRandom.current();

        CashCard cashCard = new CashCard(
            random.nextLong(),
            "sarah1",
            random.nextDouble(100.00)
        );

        return new Transaction(random.nextLong(), cashCard);
    }
}