package com.example.demo.service;

import com.example.demo.domain.CashCard;
import com.example.demo.domain.Transaction;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class DataSourceService {

    public Transaction getData() {
        CashCard cashCard = new CashCard(
            new Random().nextLong(),
            "sarah1",
            new Random().nextDouble(100.00)
        );

        return new Transaction(new Random().nextLong(), cashCard);
    }
}