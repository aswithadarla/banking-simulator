package com.banking.banking_simulator.controller;

import com.banking.banking_simulator.model.Account;
import com.banking.banking_simulator.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Create Account
    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    // Get All Accounts
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // Search Account By Name
    @GetMapping("/search")
    public List<Account> searchByName(@RequestParam String name) {
        return accountService.searchAccountsByName(name);
    }

    // Get Account Summary
    @GetMapping("/summary")
    public String getAccountSummary() {
        return accountService.getAccountSummary();
    }

    // Get Account By Account Number
    @GetMapping("/number/{accountNumber}")
    public Account getAccountByNumber(@PathVariable String accountNumber) {
        return accountService.getAccountByNumber(accountNumber);
    }

    // Get Account By Id
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    // Get Balance
    @GetMapping("/{id}/balance")
    public String getBalance(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        return "Balance for " + account.getHolderName() + " : " + account.getBalance();
    }

    // Deactivate Account
    @PutMapping("/{id}/deactivate")
    public String deactivateAccount(@PathVariable Long id) {
        accountService.deactivateAccount(id);
        return "Account deactivated successfully";
    }

    // Reactivate Account
    @PutMapping("/{id}/reactivate")
    public String reactivateAccount(@PathVariable Long id) {
        accountService.reactivateAccount(id);
        return "Account reactivated successfully";
    }

    // Update Account
    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable Long id, @RequestBody Account account) {
        return accountService.updateAccount(id, account);
    }

    // Delete Account
    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return "Account deleted successfully";
    }
}