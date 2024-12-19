package com.example.demo.stepdef;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Background {
    @When("My source application is ready")
    public void mySourceApplicationIsReady() {

        /**
         * TODO:
         * [] Install actuator
         * [] assert on actuator health endpoint
         */
    }

    @Then("My enricher application should be ready")
    public void myEnricherApplicationShouldBeReady() {
        /**
         * TODO:
         * [] Install actuator
         * [] assert on actuator health endpoint
         */
    }

    @Then("My sink application should be ready")
    public void mySinkApplicationShouldBeReady() {
        /**
         * TODO:
         * [] Install actuator
         * [] assert on actuator health endpoint
         */
    }

}