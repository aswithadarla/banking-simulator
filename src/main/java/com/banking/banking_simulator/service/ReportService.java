package com.banking.banking_simulator.service;

import com.banking.banking_simulator.model.Transaction;
import com.banking.banking_simulator.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    private final TransactionRepository transactionRepository;

    public ReportService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // Generate Report for an Account
    public String generateReport(String accountNumber) {
        List<Transaction> transactions = transactionRepository
                .findByAccountNumber(accountNumber);

        String fileName = "reports/report_" + accountNumber + "_" +
                LocalDateTime.now().toString().replace(":", "-") + ".txt";

        try {
            // Create reports folder if not exists
            new java.io.File("reports").mkdirs();

            FileWriter writer = new FileWriter(fileName);
            writer.write("=== Transaction Report ===\n");
            writer.write("Account Number: " + accountNumber + "\n");
            writer.write("Generated At: " + LocalDateTime.now() + "\n");
            writer.write("==========================\n\n");

            for (Transaction t : transactions) {
                writer.write("Type: " + t.getType() + "\n");
                writer.write("Amount: " + t.getAmount() + "\n");
                writer.write("Description: " + t.getDescription() + "\n");
                writer.write("Timestamp: " + t.getTimestamp() + "\n");
                writer.write("--------------------------\n");
            }

            writer.close();
            return "Report generated: " + fileName;

        } catch (IOException e) {
            return "Error generating report: " + e.getMessage();
        }
    }

    // List All Generated Reports
    public List<String> listReports() {
        java.io.File reportsFolder = new java.io.File("reports");
        if (!reportsFolder.exists()) {
            return List.of("No reports found");
        }
        String[] files = reportsFolder.list();
        if (files == null || files.length == 0) {
            return List.of("No reports found");
        }
        return List.of(files);
    }
}