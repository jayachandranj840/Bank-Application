package com.bank.servlet;

import java.io.IOException;

import com.bank.model.Customer;
import com.bank.util.CustomerDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CustomerLoginServlet")
public class CustomerLoginServlet extends HttpServlet {

	private CustomerDAO customerDAO;

	@Override
	public void init() throws ServletException {
		// Initialize CustomerDAO
		customerDAO = new CustomerDAO();
	}

	@Override              
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String accountNo = request.getParameter("accountNo");
		String password = request.getParameter("password");
		if (customerDAO.isValidCustomer(accountNo, password)) {
			Customer customer = customerDAO.getCustomerUsername(accountNo);
			HttpSession session = request.getSession();
			session.setAttribute("customer", customer);
			session.setAttribute("accountNo", accountNo);
			// Redirect to customer dashboard
			response.sendRedirect("customerDashboard.jsp");
		} else {
			// Set an error message and forward back to login page
			request.setAttribute("errorMessage", "Invalid account number or password.");
			request.getRequestDispatcher("customerLogin.jsp").forward(request, response);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Redirect to the login page for GET requests
		request.getRequestDispatcher("customerLogin.jsp").forward(request, response);
	}
}
