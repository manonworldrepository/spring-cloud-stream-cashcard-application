package com.example.demo.serdes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.domain.Transaction;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;
import com.example.demo.domain.CashCard;
import org.springframework.stereotype.Service;

@Service
public class TransactionDeserializer implements Deserializer<Transaction> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) { /* Noop */ }

    @Override
    public Transaction deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(data, Transaction.class);
        } catch (Exception e) {
            return new Transaction(0L, new CashCard(0L, "Error", 0.0));
        }
    }

    @Override
    public void close() { /* Noop */ }
}