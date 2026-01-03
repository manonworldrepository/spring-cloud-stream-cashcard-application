package com.example.demo.controller;

import com.example.demo.domain.CashCard;
import com.example.demo.domain.Transaction;
import com.example.demo.ondemand.CashCardTransactionOnDemand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(IndexController.class)
@Import(CashCardTransactionOnDemand.class)
class CashCardControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturn202WhenTransactionIsPublished() {
        Transaction transaction = new Transaction(99L, new CashCard(1L, "sarah1", 100.00));

        webTestClient
                .post()
                .uri("/pub")
                .body(Mono.just(transaction), Transaction.class)
                .exchange()
                .expectStatus().isOk();
    }
}
