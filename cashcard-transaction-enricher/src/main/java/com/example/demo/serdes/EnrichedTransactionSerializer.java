package com.example.demo.serdes;

import com.example.demo.domain.EnrichedTransaction;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class EnrichedTransactionSerializer implements Serializer<EnrichedTransaction> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public byte[] serialize(String topic, EnrichedTransaction data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            System.out.println("Error occured while serializing transaction: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {}
}
