package com.bank.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import com.bank.util.CustomerDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {

	private CustomerDAO customerDAO;

	@Override
	public void init() throws ServletException {
		customerDAO = new CustomerDAO();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String accountNo = (String) session.getAttribute("accountNo");
		BigDecimal amount = new BigDecimal(request.getParameter("amount"));

		if (amount.compareTo(BigDecimal.ZERO) > 0) {
			try {
				if (customerDAO.deposit(accountNo, amount)) {
					request.setAttribute("message", "Deposit of Rs" + amount + " successful.");
					request.setAttribute("success", true);
				} else {
					request.setAttribute("error", "Failed to deposit.");
					request.setAttribute("success", false);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("error", "Database error: " + e.getMessage());
				request.setAttribute("success", false);
			}
		} else {
			request.setAttribute("error", "Amount must be greater than zero.");
			request.setAttribute("success", false);
		}

		request.getRequestDispatcher("/customerDashboard.jsp").forward(request, response);
	}
}
