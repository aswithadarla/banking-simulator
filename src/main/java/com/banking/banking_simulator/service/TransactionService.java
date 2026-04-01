package com.banking.banking_simulator.service;

import com.banking.banking_simulator.model.Transaction;
import com.banking.banking_simulator.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // Save Transaction
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Get All Transactions
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // Get Transactions By Account Number
    public List<Transaction> getTransactionsByAccountNumber(String accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber);
    }
    public List<Transaction> getMiniStatement(String accountNumber) {
        return transactionRepository
                .findTop5ByAccountNumberOrderByTimestampDesc(accountNumber);
    }
}