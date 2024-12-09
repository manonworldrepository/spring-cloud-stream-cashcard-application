package com.example.demo.ondemand;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.domain.Transaction;

@Configuration
public class CashCardTransactionOnDemand {

    private final StreamBridge streamBridge;

    public CashCardTransactionOnDemand(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishOnDemand(Transaction transaction) {
        this.streamBridge.send("approvalRequest-out-0", transaction);
    }
}