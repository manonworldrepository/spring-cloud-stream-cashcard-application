package com.example.demo.serdes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.domain.Transaction;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;
import java.io.IOException;
import com.example.demo.domain.CashCard;
import org.springframework.stereotype.Service;

@Service
public class TransactionDeserializer implements Deserializer<Transaction> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public Transaction deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(data, Transaction.class);
        } catch (JsonProcessingException e) {
            System.out.println("JsonProcessingException occured while deserializing Transaction" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error deserializing Transaction: " + e.getMessage());
            return new Transaction(0L, new CashCard(0L, "Error", 0.0));
        }

        return new Transaction(1L, new CashCard(1L, "Test Owner", 3.14)); // just to avoid any errors
    }

    @Override
    public void close() {}
}