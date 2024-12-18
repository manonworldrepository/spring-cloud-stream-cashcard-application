package com.example.demo;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@SpringBootTest(classes = E2EApplication.class)
@ContextConfiguration
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.example.demo",
    plugin = {"pretty", "html:target/cucumber-reports.html"},
    tags = "not @Ignore"
)
public class CucumberTestRunner {}