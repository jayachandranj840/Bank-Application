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

@WebServlet("/CloseAccountServlet")
public class CloseAccountServlet extends HttpServlet {

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

	    try {
	        BigDecimal currentBalance = customerDAO.getBalance(accountNo);

	        if (currentBalance.compareTo(BigDecimal.ZERO) != 0) {
	            request.setAttribute("error", "Account balance must be zero to close the account.");
	            request.getRequestDispatcher("/closeAccount.jsp").forward(request, response);
	        } else {
	            if (customerDAO.closeAccount(accountNo)) {
	                request.setAttribute("message", "Account closed successfully.");
	                session.invalidate();
	                response.sendRedirect("customerLogin.jsp");
	            } else {
	                request.setAttribute("error", "Failed to close the account.");
	                request.getRequestDispatcher("/customerDashboard.jsp").forward(request, response);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        request.setAttribute("error", "Database error: " + e.getMessage());
	        request.getRequestDispatcher("/closeAccount.jsp").forward(request, response);
	    }
	}

}
