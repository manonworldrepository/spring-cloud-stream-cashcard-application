package com.example.demo.stepdef;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

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

}