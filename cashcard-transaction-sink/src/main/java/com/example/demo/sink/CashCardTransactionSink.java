package com.example.demo.sink;

import org.springframework.context.annotation.Configuration;
import com.example.demo.domain.EnrichedTransaction;
import java.util.function.Consumer;
import org.springframework.context.annotation.Bean;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class CashCardTransactionSink {

    public static final String CSV_FILE_PATH = System.getProperty("user.dir") + "/build/tmp/transactions-output.csv";

    @Bean
    public Consumer<EnrichedTransaction> cashCardTransactionFileSink() {
        return enrichedTransaction -> {
            log.info("Sinking enriched transaction: {}", enrichedTransaction);

            String csvLine = String.format("%d,%d,%.2f,%s,%s,%s,%s\n",
                enrichedTransaction.id(),
                enrichedTransaction.cashCard().id(),
                enrichedTransaction.cashCard().amountRequestedForAuth(),
                enrichedTransaction.cardHolderData().name(),
                enrichedTransaction.cardHolderData().userId(),
                enrichedTransaction.cardHolderData().address(),
                enrichedTransaction.approvalStatus());

            Path path = Paths.get(CSV_FILE_PATH);

            try {
                ensureDirectoryExists(path);
                Files.writeString(path, csvLine, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    private void ensureDirectoryExists(Path path) throws IOException {
        Files.createDirectories(path.getParent());
    }
}