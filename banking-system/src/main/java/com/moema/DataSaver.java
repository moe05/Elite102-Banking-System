package com.moema;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class DataSaver {

    public static void saveAccounts(String path, Map<String, Account> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Account acc : accounts.values()) {
                String line = acc.getUsername() + "," + acc.getBalance() + "," +
                              acc.getAccountType() + "," + acc.getStatus();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Accounts saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }
}
