package com.bank.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.bank.model.Customer;
import com.bank.util.CustomerDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ManageCustomerServlet")
public class ManageCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerDAO customerDAO;

	@Override
	public void init() throws ServletException {
		customerDAO = new CustomerDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			handleListCustomers(request, response);
		} catch (SQLException | ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String accountNo = request.getParameter("accountNo");

		try {
			switch (action) {
			case "delete":
				handleDeleteCustomer(request, response, accountNo);
				break;
			case "edit":
				handleEditCustomer(request, response, accountNo);
				break;
			case "update":
				handleUpdateCustomer(request, response);
				break;
			default:
				handleListCustomers(request, response);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("An error occurred: " + e.getMessage());
		}
	}

	private void handleDeleteCustomer(HttpServletRequest request, HttpServletResponse response, String accountNo)
			throws SQLException, IOException {
		boolean deleted = customerDAO.closeAccount(accountNo);
		if (deleted) {
			response.getWriter().write("Customer deleted successfully.");
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("Failed to delete the customer.");
		}
	}

	private void handleEditCustomer(HttpServletRequest request, HttpServletResponse response, String accountNo)
			throws SQLException, IOException, ServletException {
		Customer customer = customerDAO.getCustomerByAccountNo(accountNo);
		if (customer != null) {
			request.setAttribute("customer", customer);
			request.getRequestDispatcher("/editCustomer.jsp").forward(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("Customer not found.");
		}
	}

	private void handleUpdateCustomer(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		// Get form parameters
		String accountNo = request.getParameter("accountNo");
		String fullName = request.getParameter("fullName");
		String address = request.getParameter("address");
		String mobileNo = request.getParameter("mobileNo");
		String email = request.getParameter("email");
		String accountType = request.getParameter("accountType");
		String dob = request.getParameter("dob");
		String idProof = request.getParameter("idProof");
		// Validate the Date of Birth format
		try {
			Date sqlDate = Date.valueOf(dob);
			Customer customer = new Customer();
			customer.setAccountNo(accountNo);
			customer.setFullName(fullName);
			customer.setAddress(address);
			customer.setMobileNo(mobileNo);
			customer.setEmail(email);
			customer.setAccountType(accountType);
			customer.setDateOfBirth(sqlDate.toLocalDate());
			customer.setIdProof(idProof);

			boolean updated = customerDAO.updateCustomer(customer);
			if (updated) {
				response.setContentType("text/html");
				response.getWriter().write("<script type='text/javascript'>");
				response.getWriter().write("alert('Customer updated successfully.');");
				response.getWriter().write("window.location.href = 'manageCustomers';");
				response.getWriter().write("</script>");
			} else {
				response.setContentType("text/html");
				response.getWriter().write("<script type='text/javascript'>");
				response.getWriter().write("alert('Failed to update the customer.');");
				response.getWriter().write("window.location.href = 'manageCustomers';");
				response.getWriter().write("</script>");
			}
		} catch (IllegalArgumentException e) {
			response.setContentType("text/html");
			response.getWriter().write("<script type='text/javascript'>");
			response.getWriter().write("alert('Invalid date format for Date of Birth.');");
			response.getWriter().write("window.location.href = 'manageCustomers';");
			response.getWriter().write("</script>");
		}
	}

	private void handleListCustomers(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		List<Customer> customers = customerDAO.getAllCustomers();
		request.setAttribute("customers", customers);
		request.getRequestDispatcher("/manageCustomers.jsp").forward(request, response);
	}
}
