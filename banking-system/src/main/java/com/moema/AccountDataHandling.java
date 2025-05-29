package com.moema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountDataHandling {
    private Connection conn;

    public AccountDataHandling(Connection conn) {
        this.conn = conn;
    }

    public void saveAccount(Account acc) throws SQLException {
        String sql = "REPLACE INTO accounts (username, password, balance, account_type, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, acc.getUsername());
            stmt.setString(2, acc.getPassword());
            stmt.setDouble(3, acc.getBalance());
            stmt.setString(4, acc.getAccountType());
            stmt.setString(5, acc.getStatus());
            stmt.executeUpdate();
        }
    }
}
