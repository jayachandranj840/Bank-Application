package com.bank.servlet;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

import com.bank.util.DatabaseUtility;
import com.bank.util.PasswordUtility;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterCustomerServlet")
public class RegisterCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
	private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String DIGITS = "0123456789";
	private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String fullName = request.getParameter("fullName");
		String address = request.getParameter("address");
		String mobileNo = request.getParameter("mobileNo");
		String email = request.getParameter("email");
		String accountType = request.getParameter("accountType");
		String dob = request.getParameter("dob");
		String idProof = request.getParameter("idProof");
		double initialBalance;

		// Validate initial balance
		try {
			initialBalance = Double.parseDouble(request.getParameter("initialBalance"));
			if (initialBalance < 1000) {
				request.setAttribute("error", "Initial balance must be at least 1000.");
				request.getRequestDispatcher("/registerCustomer.jsp").forward(request, response);
				return;
			}
		} catch (NumberFormatException e) {
			request.setAttribute("error", "Invalid initial balance format.");
			request.getRequestDispatcher("/registerCustomer.jsp").forward(request, response);
			return;
		}

		Connection connection = null;

		try {
			// Check if mobile number and email are unique
			if (!isMobileNumberUnique(mobileNo)) {
				request.setAttribute("error", "Mobile number already exists.");
				request.getRequestDispatcher("registerCustomer.jsp").forward(request, response);
				return;
			}

			if (!isEmailUnique(email)) {
				request.setAttribute("error", "Email already exists.");
				request.getRequestDispatcher("registerCustomer.jsp").forward(request, response);
				return;
			}

			// Generate account number and temporary password
			String accountNo = generateAccountNumber();
			String tempPassword = generateTemporaryPassword(); // Ensure this method exists

			// Get database connection
			connection = DatabaseUtility.getConnection();

			// Insert customer into the database
			String sql = "INSERT INTO customers (accountNo, fullName, address, mobileNo, email, accountType, balance, dob, idProof, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, accountNo);
				statement.setString(2, fullName);
				statement.setString(3, address);
				statement.setString(4, mobileNo);
				statement.setString(5, email);
				statement.setString(6, accountType);
				statement.setDouble(7, initialBalance);
				statement.setString(8, dob);
				statement.setString(9, idProof);
				statement.setString(10, PasswordUtility.hashPassword(tempPassword));

				int rowsInserted = statement.executeUpdate();

				if (rowsInserted > 0) {
					request.setAttribute("accountNo", accountNo);
					request.setAttribute("tempPassword", tempPassword);
					request.getRequestDispatcher("/registerSuccess.jsp").forward(request, response);
				} else {
					request.setAttribute("error", "Failed to register customer.");
					request.getRequestDispatcher("/registerCustomer.jsp").forward(request, response);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException("Database error: " + e.getMessage(), e);
		} finally {
			DatabaseUtility.closeConnection(connection);
		}
	}

	private String generateAccountNumber() throws SQLException {
		String accountNumber;
		Random random = new Random();
		do {
			// Generate a random 14-digit number
			accountNumber = String.format("%014d", random.nextLong() % 10000000000000000L);
			if (accountNumber.length() > 14) {
				accountNumber = accountNumber.substring(accountNumber.length() - 14); // Trim to 14 digits if longer
			}
		} while (!isAccountNumberUnique(accountNumber));
		return accountNumber;
	}

	private boolean isAccountNumberUnique(String accountNumber) throws SQLException {
		Connection connection = DatabaseUtility.getConnection();
		String query = "SELECT COUNT(*) FROM customers WHERE accountNo = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, accountNumber);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt(1) == 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isMobileNumberUnique(String mobileNo) throws SQLException {
		Connection connection = DatabaseUtility.getConnection();
		String query = "SELECT COUNT(*) FROM customers WHERE mobileNo = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, mobileNo);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt(1) == 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isEmailUnique(String email) throws SQLException {
		Connection connection = DatabaseUtility.getConnection();
		String query = "SELECT COUNT(*) FROM customers WHERE email = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, email);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt(1) == 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String generateTemporaryPassword() {
		String allCharacters = LOWER_CASE + UPPER_CASE + DIGITS + SYMBOLS;
		SecureRandom random = new SecureRandom();
		StringBuilder password = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			int index = random.nextInt(allCharacters.length());
			password.append(allCharacters.charAt(index));
		}
		return password.toString();
	}
}