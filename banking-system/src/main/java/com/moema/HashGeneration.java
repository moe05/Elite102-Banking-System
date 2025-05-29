package com.moema;

import java.io.*;

public class HashGeneration {

    public static void main(String[] args) {
        String inputFile = "C:\\Users\\moema\\banking-system\\src\\main\\java\\com\\moema\\Account.txt";
        String outputFile = "C:\\Users\\moema\\banking-system\\src\\main\\java\\com\\moema\\AccountHashed.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             PrintWriter pw = new PrintWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 5) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                String username = parts[0];
                String plaintextPassword = parts[1].trim();
                String hashedPassword = Passwords.hashPassword(plaintextPassword);
                String balance = parts[2];
                String accountType = parts[3];
                String status = parts[4];

                String updatedLine = String.join(",", username, hashedPassword, balance, accountType, status);
                pw.println(updatedLine);
            }

            System.out.println("All passwords hashed and saved to " + outputFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
