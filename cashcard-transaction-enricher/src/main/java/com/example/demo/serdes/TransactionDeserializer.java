package com.example.demo.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.domain.Transaction;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class TransactionDeserializer implements Deserializer<Transaction> {

    private final ObjectMapper objectMapper = new ObjectMapper(); // Create once and reuse

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) { /* Noop */ }

    @Override
    public Transaction deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            return objectMapper.readValue(data, Transaction.class);
        } catch (IOException e) {
            System.err.println("Error deserializing JSON for topic " + topic + ": " + e.getMessage());

            throw new SerializationException("Can't deserialize data [" + new String(data) + "] from topic [" + topic + "]", e);
        }
    }

    @Override
    public void close() { /* Noop */ }
}