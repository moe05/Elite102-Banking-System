package com.moema;

public class Account {
    private String username;
    private String password;
    private double balance;
    private String accountType;
    private String status;

    public Account(String username, String password, double balance, String accountType, String status) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.accountType = accountType;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public boolean withdraw(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }
        
}