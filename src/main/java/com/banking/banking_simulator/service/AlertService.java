package com.banking.banking_simulator.service;

import com.banking.banking_simulator.model.Account;
import com.banking.banking_simulator.repository.AccountRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertService {

    private final AccountRepository accountRepository;

    // Threshold — alert if balance below this
    private static final double LOW_BALANCE_THRESHOLD = 500.00;

    public AlertService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Check balance after every transaction
    public void checkAndAlert(Account account) {
        if (account.getBalance() < LOW_BALANCE_THRESHOLD) {
            sendAlert(account);
        }
    }

    // Runs automatically every 1 hour
    @Scheduled(fixedRate = 3_600_000)
    public void scheduledBalanceCheck() {
        List<Account> accounts = accountRepository.findAll();
        for (Account account : accounts) {
            if (account.getBalance() < LOW_BALANCE_THRESHOLD) {
                sendAlert(account);
            }
        }
    }

    // Get Low Balance Accounts
    public List<String> getLowBalanceAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .filter(a -> a.getBalance() < LOW_BALANCE_THRESHOLD)
                .map(a -> "Account: " + a.getAccountNumber() +
                        " | Holder: " + a.getHolderName() +
                        " | Balance: " + a.getBalance())
                .collect(Collectors.toList());
    }

    // Send Alert
    private void sendAlert(Account account) {
        System.out.println("⚠️ LOW BALANCE ALERT!");
        System.out.println("Account: " + account.getAccountNumber());
        System.out.println("Holder: " + account.getHolderName());
        System.out.println("Balance: " + account.getBalance());
        System.out.println("Please add funds immediately!");
        System.out.println("----------------------------------");
    }
}