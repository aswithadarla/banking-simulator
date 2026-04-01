package com.banking.banking_simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BankingSimulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingSimulatorApplication.class, args);
    }

}