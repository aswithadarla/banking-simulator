# 🏦 Banking Transaction Simulator

A full-stack banking transaction simulator built with **Java Spring Boot** and **MySQL**, featuring a clean HTML/CSS/JS frontend.

---

## 🚀 Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Core programming language |
| Spring Boot 3.2 | Application framework |
| Spring Data JPA | Database ORM |
| MySQL 8.0 | Persistent database |
| HTML/CSS/JavaScript | Frontend |
| Maven | Build tool |

---

## ✨ Features

### Account Management
- Create, Read, Update, Delete accounts
- Account types: SAVINGS, CURRENT, CHECKING
- Activate/Deactivate accounts
- Search accounts by name
- Account creation date tracking

### Transaction Processing
- Deposit money
- Withdraw money with overdraft protection
- Transfer between accounts
- Unique transaction reference numbers
- Balance before and after tracking
- Full transaction history
- Mini statement last 5 transactions

### Reports and Alerts
- Generate text file reports
- Download reports
- Print reports
- Low balance alert tracker
- Scheduled hourly balance checks
- Bank summary statistics

### Error Handling
- Overdraft protection
- Inactive account validation
- Duplicate account prevention
- Negative amount validation
- Same account transfer prevention

---

## 📁 Project Structure
```
banking-simulator/
├── src/main/java/com/banking/
│   ├── controller/
│   │   ├── AccountController.java
│   │   ├── TransactionController.java
│   │   └── ReportController.java
│   ├── model/
│   │   ├── Account.java
│   │   └── Transaction.java
│   ├── repository/
│   │   ├── AccountRepository.java
│   │   └── TransactionRepository.java
│   ├── service/
│   │   ├── AccountService.java
│   │   ├── TransactionService.java
│   │   ├── ReportService.java
│   │   └── AlertService.java
│   └── exception/
│       ├── GlobalExceptionHandler.java
│       └── InsufficientBalanceException.java
├── src/main/resources/
│   ├── static/
│   │   ├── index.html
│   │   ├── accounts.html
│   │   ├── transactions.html
│   │   ├── history.html
│   │   ├── reports.html
│   │   └── style.css
│   └── application.properties
└── pom.xml
```

---

## ⚙️ Setup Instructions

### Prerequisites
- Java 17
- MySQL 8.0
- Maven 3.9+
- IntelliJ IDEA

### Steps

1. Clone the repository
```bash
git clone https://github.com/yourusername/banking-simulator.git
```

2. Create MySQL database
```sql
CREATE DATABASE bankingdb;
```

3. Update application.properties
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bankingdb
spring.datasource.username=root
spring.datasource.password=your_password
```

4. Run the application
```bash
./mvnw spring-boot:run
```

5. Open browser
```
http://localhost:8080
```

---

## 🔗 API Endpoints

### Accounts
| Method | Endpoint | Description |
|---|---|---|
| POST | /api/accounts | Create account |
| GET | /api/accounts | Get all accounts |
| GET | /api/accounts/{id} | Get account by ID |
| GET | /api/accounts/search?name= | Search by name |
| GET | /api/accounts/summary | Bank summary |
| PUT | /api/accounts/{id} | Update account |
| DELETE | /api/accounts/{id} | Delete account |
| PUT | /api/accounts/{id}/deactivate | Deactivate |
| PUT | /api/accounts/{id}/reactivate | Reactivate |

### Transactions
| Method | Endpoint | Description |
|---|---|---|
| POST | /api/transactions/deposit | Deposit |
| POST | /api/transactions/withdraw | Withdraw |
| POST | /api/transactions/transfer | Transfer |
| GET | /api/transactions | All transactions |
| GET | /api/transactions/account/{no} | By account |
| GET | /api/transactions/mini/{no} | Mini statement |

### Reports
| Method | Endpoint | Description |
|---|---|---|
| GET | /api/reports/{accountNumber} | Generate report |
| GET | /api/reports/list | List reports |
| GET | /api/reports/alerts/low-balance | Low balance |

---

## 👩‍💻 Developer

**D. Aswitha**
- Project: Java Banking Transaction Simulator
- Tech: Spring Boot + MySQL + HTML/CSS/JS

---

## 📄 License

This project is for educational purposes.