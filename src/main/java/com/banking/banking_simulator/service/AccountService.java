package com.banking.banking_simulator.service;

import com.banking.banking_simulator.model.Account;
import com.banking.banking_simulator.model.Transaction;
import com.banking.banking_simulator.repository.AccountRepository;
import com.banking.banking_simulator.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AlertService alertService;

    public AccountService(AccountRepository accountRepository,
                          TransactionRepository transactionRepository,
                          AlertService alertService) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.alertService = alertService;
    }

    // Generate Unique Reference Number
    private String generateReferenceNumber() {
        return "TXN" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Create Account
    public Account createAccount(Account account) {
        if (accountRepository.findByAccountNumber(account.getAccountNumber()).isPresent()) {
            throw new RuntimeException("Account number already exists");
        }
        if (account.getAccountType() == null) {
            account.setAccountType(Account.AccountType.SAVINGS);
        }
        account.setCreatedAt(LocalDateTime.now());
        account.setActive(true);
        return accountRepository.save(account);
    }

    // Get All Accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Get Account By Id
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // Get Account By Account Number
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // Search Account By Name
    public List<Account> searchAccountsByName(String name) {
        return accountRepository.findByHolderNameContainingIgnoreCase(name);
    }

    // Get Account Summary
    public String getAccountSummary() {
        List<Account> accounts = accountRepository.findAll();
        double totalBalance = accounts.stream()
                .mapToDouble(Account::getBalance)
                .sum();
        return "Total Accounts: " + accounts.size() +
                " | Total Balance: ₹" + totalBalance;
    }

    // Deposit Money
    public Account deposit(Long id, double amount) {
        if (amount <= 0) {
            throw new RuntimeException("Deposit amount must be greater than zero");
        }
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if (!account.isActive()) {
            throw new RuntimeException("Account is inactive");
        }
        double balanceBefore = account.getBalance();
        account.setBalance(account.getBalance() + amount);
        account.setLastTransactionAt(LocalDateTime.now());
        Account updatedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setReferenceNumber(generateReferenceNumber());
        transaction.setAccountNumber(account.getAccountNumber());
        transaction.setType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setDescription("Amount deposited");
        transaction.setBalanceBefore(balanceBefore);
        transaction.setBalanceAfter(updatedAccount.getBalance());
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        alertService.checkAndAlert(updatedAccount);
        return updatedAccount;
    }

    // Withdraw Money
    public Account withdraw(Long id, double amount) {
        if (amount <= 0) {
            throw new RuntimeException("Withdrawal amount must be greater than zero");
        }
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if (!account.isActive()) {
            throw new RuntimeException("Account is inactive");
        }
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }
        double balanceBefore = account.getBalance();
        account.setBalance(account.getBalance() - amount);
        account.setLastTransactionAt(LocalDateTime.now());
        Account updatedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setReferenceNumber(generateReferenceNumber());
        transaction.setAccountNumber(account.getAccountNumber());
        transaction.setType("WITHDRAWAL");
        transaction.setAmount(amount);
        transaction.setDescription("Amount withdrawn");
        transaction.setBalanceBefore(balanceBefore);
        transaction.setBalanceAfter(updatedAccount.getBalance());
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        alertService.checkAndAlert(updatedAccount);
        return updatedAccount;
    }

    // Transfer Money
    public void transfer(Long fromAccountId, Long toAccountId, double amount) {
        if (amount <= 0) {
            throw new RuntimeException("Transfer amount must be greater than zero");
        }
        if (fromAccountId.equals(toAccountId)) {
            throw new RuntimeException("Cannot transfer to same account");
        }
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Sender account not found"));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));
        if (!fromAccount.isActive()) {
            throw new RuntimeException("Sender account is inactive");
        }
        if (!toAccount.isActive()) {
            throw new RuntimeException("Receiver account is inactive");
        }
        if (fromAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        double fromBalanceBefore = fromAccount.getBalance();
        double toBalanceBefore = toAccount.getBalance();

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        fromAccount.setLastTransactionAt(LocalDateTime.now());
        toAccount.setLastTransactionAt(LocalDateTime.now());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction t1 = new Transaction();
        t1.setReferenceNumber(generateReferenceNumber());
        t1.setAccountNumber(fromAccount.getAccountNumber());
        t1.setType("TRANSFER_OUT");
        t1.setAmount(amount);
        t1.setDescription("Transfer to " + toAccount.getAccountNumber());
        t1.setBalanceBefore(fromBalanceBefore);
        t1.setBalanceAfter(fromAccount.getBalance());
        t1.setTimestamp(LocalDateTime.now());
        transactionRepository.save(t1);

        Transaction t2 = new Transaction();
        t2.setReferenceNumber(generateReferenceNumber());
        t2.setAccountNumber(toAccount.getAccountNumber());
        t2.setType("TRANSFER_IN");
        t2.setAmount(amount);
        t2.setDescription("Transfer from " + fromAccount.getAccountNumber());
        t2.setBalanceBefore(toBalanceBefore);
        t2.setBalanceAfter(toAccount.getBalance());
        t2.setTimestamp(LocalDateTime.now());
        transactionRepository.save(t2);

        alertService.checkAndAlert(fromAccount);
        alertService.checkAndAlert(toAccount);
    }

    // Update Account
    public Account updateAccount(Long id, Account updatedAccount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setHolderName(updatedAccount.getHolderName());
        account.setBalance(updatedAccount.getBalance());
        account.setActive(updatedAccount.isActive());
        if (updatedAccount.getAccountType() != null) {
            account.setAccountType(updatedAccount.getAccountType());
        }
        return accountRepository.save(account);
    }

    // Delete Account
    public void deleteAccount(Long id) {
        accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        accountRepository.deleteById(id);
    }

    // Deactivate Account
    public void deactivateAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setActive(false);
        accountRepository.save(account);
    }

    // Reactivate Account
    public void reactivateAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setActive(true);
        accountRepository.save(account);
    }
}