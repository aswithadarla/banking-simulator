package com.banking.banking_simulator.repository;

import com.banking.banking_simulator.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Get all transactions by account number
    List<Transaction> findByAccountNumber(String accountNumber);

    // Mini Statement — Last 5 transactions
    List<Transaction> findTop5ByAccountNumberOrderByTimestampDesc(String accountNumber);

}