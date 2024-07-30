package com.bank.servlet;

import java.io.IOException;
import java.sql.SQLException;

import com.bank.util.CustomerDAO;
import com.bank.util.PasswordUtility;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/updatePasswordServlet")
public class UpdatePasswordServlet extends HttpServlet {

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
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");

		if (newPassword.length() >= 8) { // Ensure the new password meets minimum length requirements
			try {
				if (customerDAO.isPasswordValid(accountNo, oldPassword)) {
					if (customerDAO.updatePassword(accountNo, PasswordUtility.hashPassword(newPassword))) {
						request.setAttribute("message", "Password updated successfully.");
						request.setAttribute("success", true);
					} else {
						request.setAttribute("error", "Failed to update password.");
						request.setAttribute("success", false);
					}
				} else {
					request.setAttribute("error", "Old password is invalid.");
					request.setAttribute("success", false);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("error", "Database error: " + e.getMessage());
				request.setAttribute("success", false);
			}
		} else {
			request.setAttribute("error", "New password must be at least 8 characters long.");
			request.setAttribute("success", false);
		}
		request.getRequestDispatcher("/resetPassword.jsp").forward(request, response);
	}
}
