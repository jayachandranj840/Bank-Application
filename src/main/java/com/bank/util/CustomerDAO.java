package com.bank.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.bank.model.Admin;
import com.bank.model.Customer;

public class CustomerDAO {
	private static Connection getConnection() throws SQLException {
		return DatabaseUtility.getConnection();
	}

	// Check if the provided credentials are valid
	public boolean isValidCustomer(String accountNo, String password) {
		String sql = "SELECT password FROM customers WHERE accountNo = ?";
		try (Connection connection = DatabaseUtility.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, accountNo);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				String hashedPassword = resultSet.getString("password");
				return PasswordUtility.checkPassword(password, hashedPassword); // Check password with hashed password
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Customer getCustomerUsername(String strAccountNo) {
		Customer customer = null;
		try (Connection conn = DatabaseUtility.getConnection()) {
			String query = "SELECT * FROM customers WHERE accountNo = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, strAccountNo);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String strfullName = rs.getString("fullName");
				customer = new Customer(strfullName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	// Register a new customer
	public boolean registerCustomer(Customer customer) throws SQLException {
		String sql = "INSERT INTO customers (accountNo, fullName, address, mobileNo, email, accountType, balance, dob, idProof, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, customer.getAccountNo());
			stmt.setString(2, customer.getFullName());
			stmt.setString(3, customer.getAddress());
			stmt.setString(4, customer.getMobileNo());
			stmt.setString(5, customer.getEmail());
			stmt.setString(6, customer.getAccountType());
			stmt.setBigDecimal(7, customer.getBalance());
			stmt.setDate(8, Date.valueOf(customer.getDateOfBirth()));
			stmt.setString(9, customer.getIdProof());
			stmt.setString(10, customer.getPassword());
			return stmt.executeUpdate() > 0;
		}
	}

	// Validate customer login
	public boolean validateCustomerLogin(String accountNo, String password) throws SQLException {
		String sql = "SELECT * FROM customers WHERE accountNo = ? AND password = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, accountNo);
			stmt.setString(2, password); // Password should be hashed before calling this method
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next();
			}
		}
	}

	// Get customer by account number
	public static Customer getCustomerByAccountNo(String accountNo) throws SQLException {
		String sql = "SELECT * FROM customers WHERE accountNo = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, accountNo);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Customer(rs.getString("accountNo"), rs.getString("fullName"), rs.getString("address"),
							rs.getString("mobileNo"), rs.getString("email"), rs.getString("accountType"),
							rs.getBigDecimal("balance"), rs.getDate("dob").toLocalDate(), rs.getString("idProof"),
							rs.getString("password"));
				}
				return null;
			}
		}
	}

	// Update customer details
	public boolean updateCustomer(Customer customer) throws SQLException {
		String sql = "UPDATE customers SET fullName = ?, address = ?, mobileNo = ?, email = ?, accountType = ?, dob = ?, idProof = ? WHERE accountNo = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, customer.getFullName());
			stmt.setString(2, customer.getAddress());
			stmt.setString(3, customer.getMobileNo());
			stmt.setString(4, customer.getEmail());
			stmt.setString(5, customer.getAccountType());
			stmt.setDate(6, Date.valueOf(customer.getDateOfBirth()));
			stmt.setString(7, customer.getIdProof());
			stmt.setString(8, customer.getAccountNo());
			return stmt.executeUpdate() > 0;
		}
	}

	// Get all customers
	public List<Customer> getAllCustomers() throws SQLException {
		List<Customer> customers = new ArrayList<>();
		String sql = "SELECT * FROM customers";
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				customers.add(new Customer(rs.getString("accountNo"), rs.getString("fullName"), rs.getString("address"),
						rs.getString("mobileNo"), rs.getString("email"), rs.getString("accountType"),
						rs.getBigDecimal("balance"), rs.getDate("dob").toLocalDate(), rs.getString("idProof"),
						rs.getString("password")));
			}
		}
		return customers;
	}

	// Deposit money into customer's account
	public boolean deposit(String accountNo, BigDecimal amount) throws SQLException {
		String sql = "UPDATE customers SET balance = balance + ? WHERE accountNo = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setBigDecimal(1, amount);
			stmt.setString(2, accountNo);
			boolean updated = stmt.executeUpdate() > 0;

			if (updated) {
				// Log the transaction
				logTransaction(accountNo, "Deposit", amount);
			}
			return updated;
		}
	}

	// Withdraw money from customer's account
	public boolean withdraw(String accountNo, BigDecimal amount) throws SQLException {
		String updateQuery = "UPDATE customers SET balance = balance - ? WHERE accountNo = ?";
		try (Connection connection = DatabaseUtility.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
			preparedStatement.setBigDecimal(1, amount);
			preparedStatement.setString(2, accountNo);
			return preparedStatement.executeUpdate() > 0;
		}
	}

	// Close a customer's account
	// Delete customer
	public boolean closeAccount(String accountNo) throws SQLException {
		String sqlDeleteTransactions = "DELETE FROM transactions WHERE accountNo = ?";
		String sqlDeleteCustomer = "DELETE FROM customers WHERE accountNo = ?";

		try (Connection conn = getConnection()) {
			// Start a transaction
			conn.setAutoCommit(false);

			// Delete transactions related to the customer
			try (PreparedStatement stmtDeleteTransactions = conn.prepareStatement(sqlDeleteTransactions)) {
				stmtDeleteTransactions.setString(1, accountNo);
				stmtDeleteTransactions.executeUpdate();
			}

			// Delete customer from customers table
			boolean deleted;
			try (PreparedStatement stmtDeleteCustomer = conn.prepareStatement(sqlDeleteCustomer)) {
				stmtDeleteCustomer.setString(1, accountNo);
				deleted = stmtDeleteCustomer.executeUpdate() > 0;
			}

			// Commit the transaction
			conn.commit();
			return deleted;
		} catch (SQLException e) {
			e.printStackTrace();
			// Rollback the transaction in case of an error
			try (Connection conn = getConnection()) {
				conn.rollback();
			} catch (SQLException rollbackException) {
				rollbackException.printStackTrace();
			}
			throw e;
		}
	}

	// Log a transaction
	private void logTransaction(String accountNo, String type, BigDecimal amount) throws SQLException {
		String sql = "INSERT INTO transactions (accountNo, type, amount) VALUES (?, ?, ?)";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, accountNo);
			stmt.setString(2, type);
			stmt.setBigDecimal(3, amount);
			stmt.executeUpdate();
		}
	}

	// Add a password reset token
	public boolean addPasswordResetToken(String accountNo, String token, Timestamp expiryDate) throws SQLException {
		String sql = "INSERT INTO password_reset_tokens (accountNo, token, expiryDate) VALUES (?, ?, ?)";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, accountNo);
			stmt.setString(2, token);
			stmt.setTimestamp(3, expiryDate);
			return stmt.executeUpdate() > 0;
		}
	}

	// Validate a password reset token
	public boolean validatePasswordResetToken(String token) throws SQLException {
		String sql = "SELECT * FROM password_reset_tokens WHERE token = ? AND expiryDate > NOW()";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, token);
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next();
			}
		}
	}

	// Reset a customer's password
	public boolean resetCustomerPassword(String accountNo, String newPassword) throws SQLException {
		String sql = "UPDATE customers SET password = ? WHERE accountNo = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, newPassword);
			stmt.setString(2, accountNo);
			boolean updated = stmt.executeUpdate() > 0;

			if (updated) {
				// Delete the reset token after successful password change
				String sqlDeleteToken = "DELETE FROM password_reset_tokens WHERE accountNo = ?";
				try (PreparedStatement stmtDeleteToken = conn.prepareStatement(sqlDeleteToken)) {
					stmtDeleteToken.setString(1, accountNo);
					stmtDeleteToken.executeUpdate();
				}
			}
			return updated;
		}
	}

	public boolean isPasswordValid(String accountNo, String oldPassword) throws SQLException {
		try (Connection connection = DatabaseUtility.getConnection()) {
			String query = "SELECT password FROM customers WHERE accountNo = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				preparedStatement.setString(1, accountNo);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						String hashedPassword = resultSet.getString("password");
						return PasswordUtility.hashPassword(oldPassword).equals(hashedPassword);
					}
				}
			}
		}
		return false;
	}

	public boolean updatePassword(String accountNo, String newPassword) throws SQLException {
		try (Connection connection = DatabaseUtility.getConnection()) {
			String query = "UPDATE customers SET password = ? WHERE accountNo = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				preparedStatement.setString(1, newPassword);
				preparedStatement.setString(2, accountNo);
				return preparedStatement.executeUpdate() > 0;
			}
		}
	}

	public void recordTransaction(String accountNo, String type, BigDecimal amount) throws SQLException {
		String insertQuery = "INSERT INTO transactions (accountNo, type, amount) VALUES (?, ?, ?)";
		try (Connection connection = DatabaseUtility.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
			preparedStatement.setString(1, accountNo);
			preparedStatement.setString(2, type);
			preparedStatement.setBigDecimal(3, amount);
			preparedStatement.executeUpdate();
		}
	}

	public BigDecimal getBalance(String accountNo) throws SQLException {
		String query = "SELECT balance FROM customers WHERE accountNo = ?";
		try (Connection connection = DatabaseUtility.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, accountNo);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getBigDecimal("balance");
				}
			}
		}
		return BigDecimal.ZERO; // or throw an exception if the account number is not found
	}
}
