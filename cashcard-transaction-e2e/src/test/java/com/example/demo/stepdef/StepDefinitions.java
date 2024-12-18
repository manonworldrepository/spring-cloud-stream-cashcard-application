package com.example.demo.stepdef;

import com.example.demo.controller.IndexController;
import com.example.demo.domain.CashCard;
import com.example.demo.domain.Transaction;
import com.example.demo.enricher.CashCardTransactionEnricher;
import com.example.demo.ondemand.CashCardTransactionOnDemand;
import com.example.demo.sink.CashCardTransactionSink;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.awaitility.Awaitility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@ContextConfiguration
@CucumberContextConfiguration
@SpringBootTest(classes = StepDefinitions.OnDemandTestConfig.class, properties = {
        "spring.cloud.function.definition=enrichTransaction;cashCardTransactionFileSink",
        "spring.cloud.stream.bindings.approvalRequest-out-0.destination=approval-requests",
        "spring.cloud.stream.bindings.enrichTransaction-in-0.destination=approval-requests",
        "spring.cloud.stream.bindings.enrichTransaction-out-0.destination=enriched-transactions",
        "spring.cloud.stream.bindings.cashCardTransactionFileSink-in-0.destination=enriched-transactions"
    },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class StepDefinitions {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<Transaction> response;

    private Transaction transaction;

    @Given("A user has the following cashcard")
    public void aUserHasTheFollowingCashcard(DataTable userSubmission) {
        Map<String, String> userInput = userSubmission.asMap();
        String owner = userInput.get("Owner");
        String amountRequested = userInput.get("Amount requested for authorization");
        transaction = new Transaction(1L, new CashCard(1L, owner, Double.valueOf(amountRequested)));
    }

    @When("A user submits data")
    public void aUserSubmitsData() throws IOException {
        Path path = Paths.get(CashCardTransactionSink.CSV_FILE_PATH);
        Files.deleteIfExists(path);

        response = restTemplate.postForEntity("http://localhost:" + port + "/pub", transaction, Transaction.class);
    }

    @Then("No error should be returned")
    public void noErrorShouldBeReturned() {
        assertEquals(HttpStatus.OK, response.getStatusCode(), "HTTP response status is not OK");
    }

    @Then("Data should be saved in a CSV file")
    public void dataShouldBeSavedInACsvFile() throws Exception {
        Path path = Paths.get(CashCardTransactionSink.CSV_FILE_PATH);

        Awaitility.await()
            .atMost(60, TimeUnit.SECONDS)
            .pollInterval(5, TimeUnit.SECONDS)
            .until(() -> Files.exists(path));

        List<String> lines = Files.readAllLines(path);
        String csvLine = lines.getFirst();

        assertThat(csvLine).contains(transaction.cashCard().owner());
        assertThat(csvLine).contains(String.valueOf(transaction.cashCard().amountRequestedForAuth()));
    }

    @EnableAutoConfiguration
    @Import({
        IndexController.class,
        CashCardTransactionOnDemand.class,
        CashCardTransactionEnricher.class,
        CashCardTransactionSink.class
    })
    public static class OnDemandTestConfig {}
}
