package com.example.demo.sink;

import org.springframework.context.annotation.Configuration;
import com.example.demo.domain.EnrichedTransaction;
import java.util.function.Consumer;
import org.springframework.context.annotation.Bean;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringJoiner;
import java.nio.file.StandardOpenOption;

@Configuration
public class CashCardTransactionSink {

    public static final String CSV_FILE_PATH = System.getProperty("user.dir") + "/build/tmp/transactions-output.csv";

    @Bean
    public Consumer<EnrichedTransaction> sinkToConsole() {
        return enrichedTransaction -> System.out.println("Transaction Received: " + enrichedTransaction);
    }

    @Bean
    public Consumer<EnrichedTransaction> cashCardTransactionFileSink() {
        return enrichedTransaction -> {
            StringJoiner joiner = new StringJoiner(",");
            StringJoiner enrichedTxnTextual = joiner.add(String.valueOf(enrichedTransaction.id()))
                .add(String.valueOf(enrichedTransaction.cashCard().id()))
                .add(String.valueOf(enrichedTransaction.cashCard().amountRequestedForAuth()))
                .add(enrichedTransaction.cardHolderData().name())
                .add(enrichedTransaction.cardHolderData().userId().toString())
                .add(enrichedTransaction.cardHolderData().address())
                .add(enrichedTransaction.approvalStatus().name());

            Path path = Paths.get(CSV_FILE_PATH);

            try {
                ensureSinkFileExists();
                Files.writeString(path, enrichedTxnTextual + "\n", StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    private void ensureSinkFileExists() throws IOException {
        boolean written = new File(CSV_FILE_PATH).createNewFile();

        if (!written) {
            throw new IOException("could not write the file");
        }
    }

}