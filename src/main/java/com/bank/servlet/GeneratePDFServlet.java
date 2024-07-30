package com.bank.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bank.model.Transaction;
import com.bank.util.DatabaseUtility;
import com.bank.util.PDFUtility;
import com.itextpdf.text.DocumentException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/GeneratePDFServlet")
public class GeneratePDFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String accountNo = (String) session.getAttribute("accountNo");
		String accountHolderName = null;
		BigDecimal currentBalance = BigDecimal.ZERO;
		ArrayList<Transaction> transactions = new ArrayList<>();

		try (Connection connection = DatabaseUtility.getConnection()) {
			// Get account holder name and current balance
			String accountQuery = "SELECT fullName, balance FROM customers WHERE accountNo = ?";
			try (PreparedStatement accountStmt = connection.prepareStatement(accountQuery)) {
				accountStmt.setString(1, accountNo);
				ResultSet accountRs = accountStmt.executeQuery();
				if (accountRs.next()) {
					accountHolderName = accountRs.getString("fullName");
					currentBalance = accountRs.getBigDecimal("balance");
				}
			}

			// Get transactions
			String transactionQuery = "SELECT * FROM transactions WHERE accountNo = ? ORDER BY date DESC LIMIT 10";
			try (PreparedStatement transactionStmt = connection.prepareStatement(transactionQuery)) {
				transactionStmt.setString(1, accountNo);
				ResultSet transactionRs = transactionStmt.executeQuery();
				while (transactionRs.next()) {
					Transaction transaction = new Transaction();
					transaction.setTransactionDate(transactionRs.getDate("date"));
					transaction.setTransactionType(transactionRs.getString("type"));
					transaction.setAmount(transactionRs.getDouble("amount"));
					transactions.add(transaction);
				}
			}

			// Set attributes for JSP
			request.setAttribute("accountHolderName", accountHolderName);
			request.setAttribute("accountNumber", accountNo);
			request.setAttribute("currentBalance", currentBalance);
			request.setAttribute("transactions", transactions);

			// Generate PDF if the request is for PDF generation
			if ("POST".equalsIgnoreCase(request.getMethod())) {
				byte[] pdfData = PDFUtility.generateTransactionPDF(accountHolderName, accountNo, currentBalance,
						transactions);
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "attachment; filename=MiniStatement.pdf");
				response.getOutputStream().write(pdfData);
			} else {
				request.getRequestDispatcher("/generatePDF.jsp").forward(request, response);
			}
		} catch (SQLException | DocumentException e) {
			throw new ServletException("Error generating PDF", e);
		}
	}
}
