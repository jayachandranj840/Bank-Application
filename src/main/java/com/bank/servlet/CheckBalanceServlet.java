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

@WebServlet("/checkBalanceServlet")
public class CheckBalanceServlet extends HttpServlet {

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
            if (accountNo != null) {
                // Retrieve the balance from the DAO
                BigDecimal balance = customerDAO.getBalance(accountNo);
                request.setAttribute("balance", balance);
            } else {
                request.setAttribute("error", "No account found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
        }

        // Forward the request to the JSP page to display the balance or error
        request.getRequestDispatcher("/checkBalance.jsp").forward(request, response);
    }
}
