package com.example.demo.serdes;

import org.apache.kafka.common.serialization.Serializer;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.AbstractMap;

public class FluxSerializer<K, V> implements Serializer<Flux<Map.Entry<K, V>>> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public byte[] serialize(String topic, Flux<Map.Entry<K, V>> data) {
        StringBuilder serializedData = new StringBuilder();

        data.toIterable().forEach(entry ->
                serializedData.append(entry.getKey()).append(":").append(entry.getValue()).append("\n")
        );

        return serializedData.toString().getBytes();
    }

    @Override
    public void close() {}
}