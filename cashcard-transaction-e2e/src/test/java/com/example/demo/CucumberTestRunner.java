package com.example.demo;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@SpringBootTest(classes = E2EApplication.class)
@CucumberContextConfiguration
@ContextConfiguration()
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.example.demo",
    plugin = {"pretty", "json:target/cucumber-reports.json"},
    tags = "not @Ignore"
)
public class CucumberTestRunner {
}