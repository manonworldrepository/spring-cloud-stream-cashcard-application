package com.example.demo.stream;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import com.example.demo.service.DataSourceService;
import org.springframework.messaging.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;
import com.example.demo.domain.CashCard;
import com.example.demo.domain.Transaction;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({TestChannelBinderConfiguration.class, DataSourceService.class})
@ContextConfiguration(classes=com.example.demo.TransactionSourceApplication.class)
class TransactionSourceApplicationTests {

    private final DataSourceService dataSourceService;

    @Autowired
    public TransactionSourceApplicationTests(DataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    @Test
    void basicCashCardSupplier1(@Autowired OutputDestination outputDestination) throws IOException {
        Message<byte[]> result = outputDestination.receive(5000, "approvalRequest-out-0");
        assertThat(result).isNotNull();

        ObjectMapper objectMapper = new ObjectMapper();
        Transaction transaction = objectMapper.readValue(result.getPayload(), Transaction.class);

        assertThat(transaction.id()).isNotNull();
        assertThat(transaction.cashCard()).isNotNull();
    }
}