package com.banking.banking_simulator.repository;

import com.banking.banking_simulator.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    // Search by name (case insensitive)
    List<Account> findByHolderNameContainingIgnoreCase(String holderName);

}