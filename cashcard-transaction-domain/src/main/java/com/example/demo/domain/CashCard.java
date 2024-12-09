package com.example.demo.domain;

public record CashCard(
    Long id,
    String owner,
    Double amountRequestedForAuth
) {}