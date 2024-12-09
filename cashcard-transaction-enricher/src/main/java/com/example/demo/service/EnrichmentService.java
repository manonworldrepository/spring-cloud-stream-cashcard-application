package com.example.demo.service;

import java.util.UUID;

import com.example.demo.domain.ApprovalStatus;
import com.example.demo.domain.CardHolderData;
import com.example.demo.domain.EnrichedTransaction;
import com.example.demo.domain.Transaction;

public class EnrichmentService {
    public EnrichedTransaction enrichTransaction(Transaction transaction) {
        return new EnrichedTransaction(
            transaction.id(),
            transaction.cashCard(),
            ApprovalStatus.APPROVED,
            new CardHolderData(
                UUID.randomUUID(),
                transaction.cashCard().owner(),
                "123 Main street"
            )
        );
    }
}