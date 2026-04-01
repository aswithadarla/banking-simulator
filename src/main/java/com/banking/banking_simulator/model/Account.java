package com.banking.banking_simulator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;
    private String holderName;
    private String email;
    private double balance;
    private boolean active;

    // Account Type — SAVINGS, CURRENT, CHECKING
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    // Account Creation Date
    private LocalDateTime createdAt;

    // Last Transaction Date
    private LocalDateTime lastTransactionAt;

    // Enum for Account Type
    public enum AccountType {
        SAVINGS,
        CURRENT,
        CHECKING
    }
}