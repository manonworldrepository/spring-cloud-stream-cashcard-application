package com.example.demo.stepdef;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Background {

    @LocalServerPort
    private int port; // FIXME assign a port number for each application and then use it here instead of this one

    private final RestTemplate restTemplate = new RestTemplate();

    @When("My source application is ready")
    public void mySourceApplicationIsReady() {
        String sourceHealthEndpoint = "http://localhost:" + port + "/actuator/health";
        String sourceHealthResponse = restTemplate.getForObject(sourceHealthEndpoint, String.class);
        assertEquals("{\"status\":\"UP\"}", sourceHealthResponse, "Source application is not healthy");
    }

    @Then("My enricher application should be ready")
    public void myEnricherApplicationShouldBeReady() {
        String enricherHealthEndpoint = "http://localhost:" + port + "/actuator/health";
        String enricherHealthResponse = restTemplate.getForObject(enricherHealthEndpoint, String.class);
        assertEquals("{\"status\":\"UP\"}", enricherHealthResponse, "Enricher application is not healthy");
    }

    @Then("My sink application should be ready")
    public void mySinkApplicationShouldBeReady() {
        String sinkHealthEndpoint = "http://localhost:" + port + "/actuator/health";
        String sinkHealthResponse = restTemplate.getForObject(sinkHealthEndpoint, String.class);
        assertEquals("{\"status\":\"UP\"}", sinkHealthResponse, "Sink application is not healthy");
    }

}