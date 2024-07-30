package com.bank.servlet;

import java.io.IOException;

import com.bank.model.Admin;
import com.bank.util.AdminDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {

	private AdminDAO adminDAO;

	@Override
	public void init() throws ServletException {
		// Initialize AdminDAO
		adminDAO = new AdminDAO();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Admin admin = adminDAO.getAdminByUsername(username);

		if (admin != null && admin.getPassword().equals(password)) { // Password should be hashed in real applications
			HttpSession session = request.getSession();
			session.setAttribute("admin", admin);
			// Redirect to admin dashboard
			response.sendRedirect("adminDashboard.jsp");
		} else {
			// Set an error message and forward back to login page
			request.setAttribute("errorMessage", "Invalid username or password.");
			request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Redirect to the login page for GET requests
		request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
	}
}
