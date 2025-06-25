package com.example.demo.sink;

import com.example.demo.domain.EnrichedTransaction;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.StringJoiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CsvWriterService {

    private static final Logger log = LoggerFactory.getLogger(CsvWriterService.class);

    @Value("${csv.output.path:/tmp/transactions-output.csv}")
    private String csvFilePath;

    private Path path;

    @PostConstruct
    private void initialize() {
        try {
            this.path = Paths.get(csvFilePath);
            Files.createDirectories(path.getParent());

            if (Files.notExists(path)) {
                log.info("Creating new sink file at: {}", path.toAbsolutePath());
                String header = "TXN_ID,CARD_ID,AMOUNT,HOLDER_NAME,USER_ID,ADDRESS,STATUS\n";
                Files.writeString(path, header, StandardOpenOption.CREATE_NEW);
            } else {
                log.info("Sink file already exists at: {}", path.toAbsolutePath());
            }
        } catch (IOException e) {
            log.error("Failed to initialize CSV sink file", e);
            throw new IllegalStateException("Failed to initialize CSV sink file", e);
        }
    }

    public synchronized void writeTransaction(EnrichedTransaction transaction) {
        StringJoiner joiner = new StringJoiner(",");
        String line = joiner.add(String.valueOf(transaction.id()))
            .add(String.valueOf(transaction.cashCard().id()))
            .add(String.valueOf(transaction.cashCard().amountRequestedForAuth()))
            .add(transaction.cardHolderData().name())
            .add(transaction.cardHolderData().userId().toString())
            .add(transaction.cardHolderData().address())
            .add(transaction.approvalStatus().name())
            .toString();

        try {
            Files.writeString(this.path, line + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error("Failed to write transaction {} to CSV file", transaction.id(), e);
        }
    }
}