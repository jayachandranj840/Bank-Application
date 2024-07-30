<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.bank.model.Transaction"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setDateHeader("Expires", 0); // Proxies.
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Generate Mini Statement</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f0f4f7;
	color: #333;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
}

.container {
	background-color: #fff;
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	padding: 30px;
	max-width: 600px;
	width: 100%;
	text-align: center;
}

h2 {
	color: #3498db;
	margin-bottom: 20px;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-bottom: 20px;
}

th, td {
	border: 1px solid #ddd;
	padding: 8px;
	text-align: left;
}

th {
	background-color: #f2f2f2;
	color: #333;
}

button {
	background-color: #3498db;
	border: none;
	color: #fff;
	padding: 12px 20px;
	border-radius: 5px;
	font-size: 16px;
	cursor: pointer;
	transition: background-color 0.3s ease, transform 0.3s ease;
	width: 100%;
}

button:hover {
	background-color: #2980b9;
	transform: scale(1.05);
}

.error {
	color: #e74c3c;
	margin-top: 10px;
}

.success {
	color: #2ecc71;
	margin-top: 10px;
}

/* Styles for the Back to Dashboard link */
a.back-to-dashboard {
	display: inline-block;
	background-color: #3498db;
	color: #fff;
	padding: 10px 15px;
	border-radius: 5px;
	font-size: 14px;
	text-decoration: none;
	text-align: center;
	margin-top: 20px; /* Add margin for spacing */
	transition: background-color 0.3s ease, transform 0.3s ease;
}

a.back-to-dashboard:hover {
	background-color: #2980b9;
	transform: scale(1.05);
}
</style>
</head>
<body>
	<%
	if (session.getAttribute("accountNo") == null) {
		response.sendRedirect("customerLogin.jsp");
		return;
	}
	%>
	<div class="container">
		<h2>Generate Mini Statement</h2>
		<%
		String accountHolderName = (String) request.getAttribute("accountHolderName");
		String accountNumber = (String) request.getAttribute("accountNumber");
		BigDecimal currentBalance = (BigDecimal) request.getAttribute("currentBalance");
		ArrayList<Transaction> transactions = (ArrayList<Transaction>) request.getAttribute("transactions");
		%>
		<p>
			<strong>Account Holder Name:</strong>
			<%=accountHolderName%>
		</p>
		<p>
			<strong>Account Number:</strong>
			<%=accountNumber%>
		</p>
		<p>
			<strong>Current Balance:</strong>
			<%=currentBalance%>
		</p>
		<%
		if (transactions != null && !transactions.isEmpty()) {
		%>
		<table>
			<tr>
				<th>Date</th>
				<th>Type</th>
				<th>Amount</th>
			</tr>
			<%
			for (Transaction transaction : transactions) {
			%>
			<tr>
				<td><%=transaction.getTransactionDate()%></td>
				<td><%=transaction.getTransactionType()%></td>
				<td><%=transaction.getAmount()%></td>
			</tr>
			<%
			}
			%>
		</table>
		<%
		} else {
		%>
		<p>No transactions found.</p>
		<%
		}
		%>
		<form action="GeneratePDFServlet" method="post">
			<button type="submit">Generate PDF</button>
			<%
			if (request.getAttribute("error") != null) {
			%>
			<p class="error"><%=request.getAttribute("error")%></p>
			<%
			}
			%>
			<%
			if (request.getAttribute("message") != null) {
			%>
			<p class="success"><%=request.getAttribute("message")%></p>
			<%
			}
			%>
		</form>
		<a href="customerDashboard.jsp" class="back-to-dashboard">Back to
			Dashboard</a>
	</div>
</body>
</html>
