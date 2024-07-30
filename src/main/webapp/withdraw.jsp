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
<title>Withdraw Money</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f5f5f5;
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
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	padding: 30px;
	max-width: 400px;
	width: 100%;
	box-sizing: border-box;
}

h2 {
	color: #e74c3c;
	margin-bottom: 20px;
	text-align: center;
}

form {
	display: flex;
	flex-direction: column;
}

label {
	margin-bottom: 5px;
	font-weight: bold;
	color: #555;
}

input[type="number"] {
	padding: 10px;
	margin-bottom: 20px;
	border: 2px solid #3498db;
	border-radius: 5px;
	font-size: 16px;
	color: #333;
	outline: none;
	box-sizing: border-box;
}

input[type="number"]:focus {
	border-color: #2980b9;
}

button {
	padding: 12px 20px;
	color: #fff;
	background-color: #e74c3c;
	border: none;
	border-radius: 5px;
	font-size: 16px;
	cursor: pointer;
	transition: background-color 0.3s ease, transform 0.3s ease;
	width: 100%;
}

button:hover {
	background-color: #c0392b;
	transform: scale(1.05);
}

.error {
	color: #e74c3c;
	margin-top: 10px;
	text-align: center;
}

.success {
	color: #2ecc71;
	margin-top: 10px;
	text-align: center;
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
	margin-left: auto; /* Align center horizontally */
	margin-right: auto; /* Align center horizontally */
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
		<h2>Withdraw Money</h2>
		<form action="WithdrawServlet" method="post">
			<label for="amount">Amount:</label> <input type="number" id="amount"
				name="amount" step="0.01" required
				placeholder="Enter amount to withdraw">
			<button type="submit">Withdraw</button>
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

