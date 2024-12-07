# Bank System

## Overview
This project is a Bank System developed for a project in Advanced Object-Oriented Programming with my partner Hannah Ayala. The system interacts with customers and managers to perform various banking operations.

---

[JavaDoc Comments](https://gerardos0.github.io/Bank-System/)

## Features

### Customer Features
- **Log In**: Password is based on the User ID.
- **View Balance**: Check the balance of accounts.
- **Deposit**: Add money to accounts.
- **Withdraw**: Remove money from accounts, subject to sufficient balance.
- **Transfer Money**: Move money between personal accounts.
- **Pay Someone**: Send money to another user.
- **Create User**: Create a new customer account.
- **Generate Transaction Report**: View transaction history.
- **Logout**: Log out and return to the main menu.

### Manager Features
- **Query Account Data**: Search for account information by customer name or account number.
- **Generate Transaction Report**: Generate transaction reports for a specific customer by name or account number.
- **Read Transaction File**: Read transaction data from a `Transaction.CSV` file.
- **Lock/Unlock Accounts**: Lock or unlock accounts based on customer name and account type.
- **Exit**: Return to the main menu.

---

## Account Types

### Customer Accounts
Each customer has the following account types:
- **Checking**
- **Savings**
- **Credit** (with a spending limit)

### Account Validations
- Requests are validated to ensure they are legitimate.
  - Example: If a customer’s Checking account has a balance of 0, they cannot withdraw money.

---

## Logging and Transactions
- All actions are logged.
- Transactions can be queried by customer name or ID.
- The system reads and writes the following files:
  - **Customer CSV File**: Generates customer objects with accounts based on the information read.
  - **Transaction CSV File**: Reads and executes the transaction list in the file.
  - **Result CSV File**: Writes information of all customers.

---
