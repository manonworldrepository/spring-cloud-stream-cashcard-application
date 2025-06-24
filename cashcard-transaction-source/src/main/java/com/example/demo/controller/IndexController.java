package com.example.demo.controller;

import com.example.demo.domain.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.demo.ondemand.CashCardTransactionOnDemand;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

@RestController
public class IndexController {

    private final CashCardTransactionOnDemand cashCardTransactionOnDemand;

    public IndexController(@Autowired CashCardTransactionOnDemand cashCardTransactionOnDemand) {
        this.cashCardTransactionOnDemand = cashCardTransactionOnDemand;
    }

    @PostMapping(path = "/pub")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> publish(@RequestBody Transaction transaction) {
        System.out.println(this.getClass().getName() + ": " + transaction);
        this.cashCardTransactionOnDemand.publishOnDemand(transaction);

        return Mono.empty();
    }
}