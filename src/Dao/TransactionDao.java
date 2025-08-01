package Dao;

import DbConnection.DbConnection;
import Model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {
    public boolean recordTransaction(Transaction txn) throws SQLException {
        String sql = "INSERT INTO transactions (account_id, type, amount) VALUES (?, ?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, txn.getAccountId());
            ps.setString(2, txn.getType());
            ps.setDouble(3, txn.getAmount());
            ps.executeUpdate();
            return true;
        }
    }

    public List<Transaction> getTransactions(int accountId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction txn = new Transaction(
                        rs.getInt("transaction_id"),
                        accountId,
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("timestamp").toString()
                );
                transactions.add(txn);
            }
        }
        return transactions;
    }

    public List<Transaction> getLastMonthTransactions(int accountId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_id = ? AND timestamp >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction txn = new Transaction(
                        rs.getInt("transaction_id"),
                        accountId,
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("timestamp").toString()
                );
                transactions.add(txn);
            }
        }
        return transactions;
    }
}
