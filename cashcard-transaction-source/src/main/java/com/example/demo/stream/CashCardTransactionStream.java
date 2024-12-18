package com.example.demo.stream;

import com.example.demo.domain.Transaction;
import com.example.demo.service.DataSourceService;
import java.util.function.Supplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CashCardTransactionStream {

    @Bean
    public Supplier<Transaction> approvalRequest(DataSourceService dataSource) {
        return dataSource::getData;
    }

    @Bean
    public DataSourceService dataSourceService() {
        return new DataSourceService();
    }
}