package com.banking.banking_simulator.controller;

import com.banking.banking_simulator.model.Account;
import com.banking.banking_simulator.model.Transaction;
import com.banking.banking_simulator.service.AccountService;
import com.banking.banking_simulator.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    public TransactionController(AccountService accountService,
                                 TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    // Deposit
    @PostMapping("/deposit")
    public Account deposit(@RequestParam Long accountId,
                           @RequestParam double amount) {
        return accountService.deposit(accountId, amount);
    }

    // Withdraw
    @PostMapping("/withdraw")
    public Account withdraw(@RequestParam Long accountId,
                            @RequestParam double amount) {
        return accountService.withdraw(accountId, amount);
    }

    // Transfer
    @PostMapping("/transfer")
    public String transfer(@RequestParam Long fromAccountId,
                           @RequestParam Long toAccountId,
                           @RequestParam double amount) {
        accountService.transfer(fromAccountId, toAccountId, amount);
        return "Transfer successful";
    }

    // Get All Transactions
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // Get Transactions By Account Number
    @GetMapping("/account/{accountNumber}")
    public List<Transaction> getTransactionsByAccount(@PathVariable String accountNumber) {
        return transactionService.getTransactionsByAccountNumber(accountNumber);
    }
    @GetMapping("/mini/{accountNumber}")
    public List<Transaction> getMiniStatement(@PathVariable String accountNumber) {
        return transactionService.getMiniStatement(accountNumber);
    }
}