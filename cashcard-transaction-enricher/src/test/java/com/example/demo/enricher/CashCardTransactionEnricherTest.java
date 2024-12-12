package com.example.demo.enricher;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;

import com.example.demo.domain.CashCard;
import com.example.demo.domain.Transaction;

@SpringBootTest
@ContextConfiguration(classes=com.example.demo.TransactionEnricherApplication.class)
@ImportAutoConfiguration(TestChannelBinderConfiguration.class)
@TestPropertySource(properties = {
    "spring.cloud.stream.bindings.enrichTransaction-in-0.destination=enrichTransactionInput",
    "spring.cloud.stream.bindings.enrichTransaction-out-0.destination=enrichTransactionOutput"
})
@Import(TestChannelConfiguration.class)
public class CashCardTransactionEnricherTest {

    @Autowired
    private InputDestination inputDestination;

    @Autowired
    private OutputDestination outputDestination;

    @Test
    void enrichmentServiceShouldAddDataToTransactions() throws IOException {
        Transaction transaction = new Transaction(1L, new CashCard(123L, "sarah1", 1.00));
        Message<Transaction> message = MessageBuilder.withPayload(transaction).build();

        inputDestination.send(message);

        Message<byte[]> result = outputDestination.receive(5000);

        assertThat(result).isNotNull();
    }
}