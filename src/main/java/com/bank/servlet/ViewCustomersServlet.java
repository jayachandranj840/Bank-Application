package com.bank.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.model.Customer;
import com.bank.util.DatabaseUtility;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ViewCustomersServlet")
public class ViewCustomersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    System.out.println("ViewCustomersServlet: doGet method called.");
		List<Customer> customers = new ArrayList<>();
		String sql = "SELECT * FROM customers";

		try (Connection connection = DatabaseUtility.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				Customer customer = new Customer();
				customer.setAccountNo(resultSet.getString("accountNo"));
				customer.setFullName(resultSet.getString("fullName"));
				customer.setAddress(resultSet.getString("address"));
				customer.setMobileNo(resultSet.getString("mobileNo"));
				customer.setEmail(resultSet.getString("email"));
				customer.setAccountType(resultSet.getString("accountType"));
				customer.setBalance(resultSet.getBigDecimal("balance"));
				customer.setDateOfBirth(resultSet.getDate("dob").toLocalDate());
				customer.setIdProof(resultSet.getString("idProof"));
				customers.add(customer);
			}
			request.setAttribute("customers", customers);
			request.getRequestDispatcher("/viewCustomers.jsp").forward(request, response);
		} catch (SQLException e) {
			throw new ServletException("Database error", e);
		}
	}
}
