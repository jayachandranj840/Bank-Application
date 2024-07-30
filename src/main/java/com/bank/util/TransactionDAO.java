package com.bank.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bank.model.Transaction;

public class TransactionDAO {

	public boolean addTransaction(Transaction transaction) {
		try (Connection conn = DatabaseUtility.getConnection()) {
			String query = "INSERT INTO transactions (account_no, transaction_date, transaction_type, amount, balance_after_transaction) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setLong(1, transaction.getAccountNo());
			stmt.setDate(2, new java.sql.Date(transaction.getTransactionDate().getTime()));
			stmt.setString(3, transaction.getTransactionType());
			stmt.setDouble(4, transaction.getAmount());
			stmt.setDouble(5, transaction.getBalanceAfterTransaction());

			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Transaction> getLast10Transactions(int accountNo) {
		List<Transaction> transactions = new ArrayList<>();
		try (Connection conn = DatabaseUtility.getConnection()) {
			String query = "SELECT * FROM transactions WHERE account_no = ? ORDER BY transaction_date DESC LIMIT 10";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setLong(1, accountNo);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int transactionId = rs.getInt("transaction_id");
				Date transactionDate = rs.getDate("transaction_date");
				String transactionType = rs.getString("transaction_type");
				int amount = rs.getInt("amount");
				double balanceAfterTransaction = rs.getDouble("balance_after_transaction");

				Transaction transaction = new Transaction(transactionId, accountNo, transactionDate, transactionType,
						amount, balanceAfterTransaction);
				transactions.add(transaction);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactions;
	}
}
