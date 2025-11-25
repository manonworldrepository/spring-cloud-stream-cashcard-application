package com.example.demo.sink;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.awaitility.Awaitility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.UUID;

import com.example.demo.domain.ApprovalStatus;
import com.example.demo.domain.CardHolderData;
import com.example.demo.domain.CashCard;
import com.example.demo.domain.EnrichedTransaction;
import com.example.demo.domain.Transaction;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class CashCardTransactionSinkTests {

    private static final String SINK_TOPIC = "enrichTransaction-out-0";
    private static final Path SINK_FILE_PATH = Paths.get(CashCardTransactionSink.CSV_FILE_PATH);

    @Container
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.0"));

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.cloud.stream.kafka.binder.brokers", kafka::getBootstrapServers);
    }

    @Autowired
    private KafkaTemplate<String, EnrichedTransaction> kafkaTemplate;

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(SINK_FILE_PATH);
    }

    @Test
    void shouldSinkTransactionToFile() {
        Transaction transaction = new Transaction(1L, new CashCard(123L, "Manon Mccallister", 1.00));
        CardHolderData cardHolderData = new CardHolderData(UUID.randomUUID(), transaction.cashCard().owner(), "123 Main Street");
        EnrichedTransaction enrichedTransaction = new EnrichedTransaction(transaction.id(), transaction.cashCard(), ApprovalStatus.APPROVED, cardHolderData);

        kafkaTemplate.send(SINK_TOPIC, enrichedTransaction);

        Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
            File file = SINK_FILE_PATH.toFile();
            assertThat(file).exists();
            String content = Files.readString(SINK_FILE_PATH);
            assertThat(content).contains("Manon Mccallister");
            assertThat(content).contains(ApprovalStatus.APPROVED.name());
        });
    }
}