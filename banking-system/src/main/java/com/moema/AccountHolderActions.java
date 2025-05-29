package com.moema;

public class AccountHolderActions {

    public static void checkBalance(Account acc) {
        System.out.println(acc.getUsername() + ", your current balance is: $" + acc.getBalance());
    }

    public static void withdraw(Account acc, double amount) {
        if (acc.withdraw(amount)) {
            System.out.println("Withdrawn $" + amount + ". New balance: $" + acc.getBalance());
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public static void deposit(Account acc, double amount) {
        acc.deposit(amount);
        System.out.println("Deposited $" + amount + ". New balance: $" + acc.getBalance());
    }
}