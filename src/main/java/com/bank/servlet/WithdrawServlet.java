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

@WebServlet("/WithdrawServlet")
public class WithdrawServlet extends HttpServlet {

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

        try {
            BigDecimal currentBalance = customerDAO.getBalance(accountNo);

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                request.setAttribute("error", "Amount must be greater than zero.");
            } else if (amount.compareTo(currentBalance) > 0) {
                request.setAttribute("error", "Insufficient balance. Enter amount less than balance.");
            } else {
                if (customerDAO.withdraw(accountNo, amount)) {
                    BigDecimal newBalance = currentBalance.subtract(amount);
                    customerDAO.recordTransaction(accountNo, "Withdraw", amount);
                    request.setAttribute("message", "Amount withdrawn: " + amount + ". New balance: " + newBalance);
                } else {
                    request.setAttribute("error", "Failed to withdraw.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
        }

        request.getRequestDispatcher("/withdraw.jsp").forward(request, response);
    }
}
