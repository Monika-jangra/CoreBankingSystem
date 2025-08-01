package Dao;

import DbConnection.DbConnection;
import Model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDao {
    public boolean createAccount(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (name, balance, user_id, account_number) VALUES (?, ?, ?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getName());
            ps.setDouble(2, account.getBalance());
            ps.setInt(3, account.getUserId());
            ps.setString(4, account.getAccountNumber());
            ps.executeUpdate();
            return true;
        }
    }

    public Account getAccountByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getInt("account_id"),
                        rs.getString("name"),
                        rs.getDouble("balance"),
                        rs.getInt("user_id"),
                        rs.getString("account_number")
                );
            }
        }
        return null;
    }

    public boolean updateBalance(int accountId, double newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newBalance);
            ps.setInt(2, accountId);
            ps.executeUpdate();
            return true;
        }
    }

    public int getAccountIdByUserId(int userId) throws SQLException {
        String sql = "SELECT account_id FROM accounts WHERE user_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("account_id");
            }
        }
        return -1;
    }

    public String getAccountNumber(int accountId) throws SQLException {
        String sql = "SELECT account_number FROM accounts WHERE account_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("account_number");
            }
        }
        return "N/A";
    }
}
