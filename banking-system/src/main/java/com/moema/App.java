package com.moema;

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
                Map<String, Account> accounts = DataLoader.loadAccounts("C:\\Users\\moema\\banking-system\\src\\main\\java\\com\\moema\\Account.txt");


        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to FG Bank!\n");

        String url = "jdbc:mysql://localhost:3306/bank_users1";
        String user = "root";
        String password = "Moises";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to MySQL successfully!");
System.out.println(Passwords.hashPassword("adminpass"));

            AccountDataHandling dao = new AccountDataHandling(conn);
            for (Account acc : accounts.values()) {
                dao.saveAccount(acc);
            }

            while (true) {
                System.out.print("Please enter your username (or type 'exit' to quit): ");
                String userName = scanner.nextLine().trim();
                if (userName.equalsIgnoreCase("exit")) break;
                

                Account userAccount = accounts.get(userName);

                if (userAccount == null) {
                    System.out.println("Sorry, that is an invalid username.\n");
                    continue;
                }

                String enteredPassword = null;
                Console console = System.console();
                if (console != null) {
                    char[] passwordChars = console.readPassword("Please enter your password: ");
                    enteredPassword = new String(passwordChars);
                } else {
                    System.out.print("Please enter your password (input will be visible): ");
                    enteredPassword = scanner.nextLine().trim();
                }

                String hashedInput = Passwords.hashPassword(enteredPassword);

                if (!userAccount.getPassword().equals(hashedInput)) {
                    System.out.println("Incorrect password!\n");
                    continue;
                }


                boolean loggedIn = true;
                while (loggedIn) {
                    if (userAccount.getAccountType().equals("admin")) {
                        System.out.println("\nWelcome administrator, " + userName + "!");
                        System.out.println("1. Open account");
                        System.out.println("2. Close account");
                        System.out.println("3. Modify account");
                        System.out.println("4. Log out");

                        int choice = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice) {
                            case 1:
                                AdminActions.openAccount(accounts, dao);
                                break;
                            case 2:
                                System.out.print("Enter the username of the account to close: ");
                                String closeUsername = scanner.nextLine().trim();
                                AdminActions.closeAccount(accounts, closeUsername);
                                break;
                            case 3:
                                AdminActions.modifyAccount(accounts, scanner);
                                break;
                            case 4:
                                loggedIn = false;
                                break;
                            default:
                                System.out.println("Invalid choice.");
                        }

                    } else if (userAccount.getStatus().equals("active")) {
                        System.out.println("\nHello, " + userName + "!");
                        System.out.println("1. Check balance");
                        System.out.println("2. Withdraw");
                        System.out.println("3. Deposit");
                        System.out.println("4. Log out");

                        int choice = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice) {
                            case 1:
                                AccountHolderActions.checkBalance(userAccount);
                                break;
                            case 2:
                                System.out.println("Enter amount to withdraw:");
                                double withdrawAmount = scanner.nextDouble();
                                AccountHolderActions.withdraw(userAccount, withdrawAmount);
                                break;
                            case 3:
                                System.out.println("Enter amount to deposit:");
                                double depositAmount = scanner.nextDouble();
                                AccountHolderActions.deposit(userAccount, depositAmount);
                                break;
                            case 4:
                                loggedIn = false;
                                break;
                            default:
                                System.out.println("Invalid choice.");
                        }
                    } else {
                        System.out.println("Account is not active.");
                        loggedIn = false;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }

        scanner.close();
    }
}