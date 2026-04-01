package com.banking.banking_simulator.controller;

import com.banking.banking_simulator.service.AlertService;
import com.banking.banking_simulator.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;
    private final AlertService alertService;

    public ReportController(ReportService reportService, AlertService alertService) {
        this.reportService = reportService;
        this.alertService = alertService;
    }

    // Generate Report for an Account
    @GetMapping("/{accountNumber}")
    public String generateReport(@PathVariable String accountNumber) {
        return reportService.generateReport(accountNumber);
    }

    // List All Generated Reports
    @GetMapping("/list")
    public List<String> listReports() {
        return reportService.listReports();
    }

    // Get Low Balance Accounts
    @GetMapping("/alerts/low-balance")
    public List<String> getLowBalanceAccounts() {
        return alertService.getLowBalanceAccounts();
    }
}