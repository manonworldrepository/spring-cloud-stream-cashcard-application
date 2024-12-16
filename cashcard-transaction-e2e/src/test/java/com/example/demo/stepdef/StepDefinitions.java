package com.example.demo.stepdef;

import org.springframework.kafka.test.context.EmbeddedKafka;

@EmbeddedKafka(partitions = 1, topics = {"approval-requests", "enriched-transactions"})
public class StepDefinitions {


}