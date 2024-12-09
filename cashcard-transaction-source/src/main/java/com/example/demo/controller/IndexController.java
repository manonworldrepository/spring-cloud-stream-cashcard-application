package com.example.demo.controller;

import com.example.demo.domain.Transaction;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.ondemand.CashCardTransactionOnDemand;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class IndexController {

    private final CashCardTransactionOnDemand cashCardTransactionOnDemand;

    public IndexController(@Autowired CashCardTransactionOnDemand cashCardTransactionOnDemand) {
        this.cashCardTransactionOnDemand = cashCardTransactionOnDemand;
    }

    @PostMapping(path = "/pub")
    public void publish(@RequestBody Transaction transaction) {
        this.cashCardTransactionOnDemand.publishOnDemand(transaction);
    }
}