package com.example.demo.domain;

public record EnrichedTransaction(Long id,
      CashCard cashCard,
      ApprovalStatus approvalStatus,
      CardHolderData cardHolderData
) {
}