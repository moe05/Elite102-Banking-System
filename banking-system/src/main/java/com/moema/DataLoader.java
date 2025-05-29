package com.moema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataLoader {
public static Map<String, Account> loadAccounts(String filePath) {
    Map<String, Account> accounts = new HashMap<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length < 5) {
                System.out.println("Skipping invalid line: " + line);
                continue;
            }

            String username = parts[0];
            String password = parts[1];
            double balance = 0.0;
            try {
                balance = Double.parseDouble(parts[2]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid balance for user: " + username + " in line: " + line);
                continue;
            }

            String accountType = parts[3];
            String status = parts[4];

            Account acc = new Account(username, Passwords.hashPassword(password), balance, accountType, status);
            accounts.put(username, acc);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return accounts;
}

}

