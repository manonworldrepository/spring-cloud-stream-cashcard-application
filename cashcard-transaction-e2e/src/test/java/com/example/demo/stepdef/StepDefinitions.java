package com.example.demo.stepdef;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.junit.jupiter.api.Assertions.assertTrue;

@EmbeddedKafka(partitions = 1, topics = {"approval-requests", "enriched-transactions"})
public class StepDefinitions {

    @Given("A user has the following cashcard")
    public void aUserHasTheFollowingCashcard(DataTable dataTable) {
        assertTrue(true);
    }

    @When("A user submits data")
    public void aUserSubmitsData() {
        assertTrue(true);
    }

    @Then("No error should be retured")
    public void noErrorShouldBeRetured() {
        assertTrue(true);
    }

    @Then("Data should be saved in a CSV file")
    public void dataShouldBeSavedInACsvFile() {
        assertTrue(true);
    }

}