package com.example.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;

import com.example.demo.transaction.api.CashCard;
import com.example.demo.transaction.api.Transaction;

@ApplicationModuleTest
public class CashCardControllerTests {

    @Test
    void postShouldSendTransactionAsAMessage(Scenario scenario) throws IOException {
        Transaction postedTransaction = new Transaction(123L, new CashCard(1L, "Manon2", 1.00));

        scenario.stimulate(() -> this.restTemplate.postForEntity(
                        "/pub",
                        postedTransaction, Transaction.class))
                .andWaitForEventOfType(Transaction.class)
                .toArriveAndVerify(message -> {
                    assertThat(message.id()).isNotNull();
                    assertThat(message.cashCard().owner()).isEqualTo("Manon2");
                });
    }

    @org.springframework.beans.factory.annotation.Autowired
    private org.springframework.boot.test.web.client.TestRestTemplate restTemplate;

}