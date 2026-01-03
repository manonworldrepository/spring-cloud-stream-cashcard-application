package com.example.demo.sink;

import com.example.demo.domain.ApprovalStatus;
import com.example.demo.domain.CardHolderData;
import com.example.demo.domain.CashCard;
import com.example.demo.domain.EnrichedTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class CashCardTransactionSinkTests {

    @Autowired
    private InputDestination input;

    @Test
    void cashCardTransactionSinkTest() throws IOException {
        CashCard cashCard = new CashCard(123L, "sarah1", 100.00);
        CardHolderData cardHolderData = new CardHolderData(UUID.randomUUID(), "Sarah", "123 Main St");
        EnrichedTransaction enrichedTransaction = new EnrichedTransaction(1L, cashCard, ApprovalStatus.APPROVED, cardHolderData);

        input.send(new GenericMessage<>(enrichedTransaction));

        Path path = Paths.get(System.getProperty("user.dir") + "/build/tmp/transactions-output.csv");
        assertThat(Files.exists(path)).isTrue();
        assertThat(Files.readString(path)).contains("1,123,100.00,Sarah");
    }
}
