package com.moema;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class AdminActions {

public static void openAccount(Map<String, Account> accounts, AccountDataHandling dao) throws SQLException {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter the username for the new account: ");
    String username = scanner.nextLine().trim();

    if (accounts.containsKey(username)) {
        System.out.println("Account for " + username + " already exists!");
    } else {
        System.out.print("Enter the password for the new account: ");
        String plainPassword = scanner.nextLine().trim();
        String hashedPassword = Passwords.hashPassword(plainPassword);

        System.out.print("Enter the initial balance for the account: ");
        double balance = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter the account type (savings/checking): ");
        String accountType = scanner.nextLine().trim();

        System.out.print("Enter the account status (active/inactive): ");
        String status = scanner.nextLine().trim();

        Account newAccount = new Account(username, hashedPassword, balance, accountType, status);
        accounts.put(username, newAccount); 
        dao.saveAccount(newAccount);

        System.out.println("A new account for " + username + " has been created and saved to the database!");
    }
}


    public static void closeAccount(Map<String, Account> accounts, String username) {
        if (accounts.containsKey(username)) {
            accounts.remove(username);
            System.out.println("Account for " + username + " has been closed.");
        } else {
            System.out.println("Account for " + username + " does not exist.");
        }
    }

    public static void modifyAccount(Map<String, Account> accounts, Scanner scanner) {
        System.out.println("Which account would you like to modify?");

        for (String username : accounts.keySet()) {
            System.out.println("- " + username);
        }

        System.out.print("Enter the username of the account you want to modify: ");
        String username = scanner.nextLine().trim();
        Account accountToModify = accounts.get(username);

        if (accountToModify != null) {
            System.out.println("You have selected " + username + "'s account.");
            System.out.println("Which attribute would you like to modify?");
            System.out.println("1. Balance");
            System.out.println("2. Status");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter the new balance: ");
                    double newBalance = scanner.nextDouble();
                    accountToModify.setBalance(newBalance);
                    System.out.println("The balance for " + username + " has been updated to $" + newBalance);
                    break;
                case 2:
                    System.out.print("Enter the new status (active/inactive): ");
                    String newStatus = scanner.nextLine().trim().toLowerCase();
                    if (newStatus.equals("active") || newStatus.equals("inactive")) {
                        accountToModify.setStatus(newStatus);
                        System.out.println("The status for " + username + " has been updated to " + newStatus);
                    } else {
                        System.out.println("Invalid status. Please enter 'active' or 'inactive'.");
                    }
                    break;
                case 3:
                    System.out.println("Exiting modification.");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } else {
            System.out.println("Account for " + username + " does not exist.");
        }
    }
}
