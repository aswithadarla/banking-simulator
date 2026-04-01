package com.banking.banking_simulator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique Transaction Reference Number
    @Column(unique = true)
    private String referenceNumber;

    private String accountNumber;
    private String type;        // DEPOSIT, WITHDRAWAL, TRANSFER_IN, TRANSFER_OUT
    private double amount;
    private String description;

    // Balance Before and After Transaction
    private double balanceBefore;
    private double balanceAfter;

    private LocalDateTime timestamp;
}