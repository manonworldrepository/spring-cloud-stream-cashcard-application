package com.example.demo.sink;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.awaitility.Awaitility;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

import com.example.demo.domain.ApprovalStatus;
import com.example.demo.domain.CardHolderData;
import com.example.demo.domain.CashCard;
import com.example.demo.domain.EnrichedTransaction;
import com.example.demo.domain.Transaction;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
@ImportAutoConfiguration(TestChannelBinderConfiguration.class)
@TestPropertySource(properties = {
        "spring.cloud.stream.bindings.sinkToConsole-in-0.destination=TransactionInput",
        "spring.cloud.stream.bindings.sinkToConsole-out-0.destination=enrichTransactionOutput",
        "spring.cloud.function.definition=sinkToConsole;cashCardTransactionFileSink"
})
@Import(TestChannelConfiguration.class)
@ContextConfiguration(classes=com.example.demo.TransactionSinkApplication.class)
public class CashCardTransactionSinkTests {

    private static final int AWAIT_DURATION = 10;

    @Test
    void cashCardSinkToConsole(@Autowired InputDestination inputDestination, CapturedOutput output) throws IOException {
        Transaction transaction = new Transaction(1L, new CashCard(123L, "Manon Mccallister", 1.00));
        EnrichedTransaction enrichedTransaction = new EnrichedTransaction(
                transaction.id(),
                transaction.cashCard(),
                ApprovalStatus.APPROVED,
                new CardHolderData(UUID.randomUUID(), transaction.cashCard().owner(), "123 Main Street")
        );

        Message<EnrichedTransaction> message = MessageBuilder.withPayload(enrichedTransaction).build();
        inputDestination.send(message);

        Awaitility.await()
                .atMost(Duration.ofSeconds(AWAIT_DURATION))
                .until(() -> output.toString().contains("Transaction Received: " + enrichedTransaction.toString()));
    }
}