package com.example.demo.stepdef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Background {

    @When("My source application is ready")
    public void mySourceApplicationIsReady() {
        assertTrue(true);
    }

    @Then("My enricher application should be ready")
    public void myEnricherApplicationShouldBeReady() {
        assertTrue(true);
    }

    @Then("My sink application should be ready")
    public void mySinkApplicationShouldBeReady() {
        assertTrue(true);
    }

    @Given("A user has the following cashcard")
    public void aUserHasTheFollowingCashcard(DataTable dataTable) {
        assertTrue(true);
    }

    @When("A user submits data")
    public void a_user_submits_data() {
        assertTrue(true);
    }

    @Then("No error should be retured")
    public void noErrorShouldBeRetured() {
    }

    @Then("Data should be saved in a CSV file")
    public void dataShouldBeSavedInACsvFile() {
        assertTrue(true);
    }

}