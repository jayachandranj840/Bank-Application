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
<title>Close Account</title>
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
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	padding: 20px;
	max-width: 400px;
	width: 100%;
	box-sizing: border-box;
	text-align: center;
}

h2 {
	color: #e74c3c;
	margin-bottom: 20px;
}

form {
	display: flex;
	flex-direction: column;
	gap: 10px;
}

p {
	margin: 10px 0;
}

button {
	background-color: #e74c3c;
	border: none;
	color: #fff;
	padding: 10px 15px;
	border-radius: 5px;
	font-size: 16px;
	cursor: pointer;
	transition: background-color 0.3s ease;
}

button:hover {
	background-color: #c0392b;
}

.error {
	color: #e74c3c;
}

.success {
	color: #2ecc71;
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
	<div class="container">
		<h2>Close Account</h2>
		<form action="CloseAccountServlet" method="post">
			<p>Are you sure you want to close your account? This action
				cannot be undone.</p>
			<button type="submit">Close Account</button>

			<% if (request.getAttribute("error") != null) { %>
			<p class="error"><%= request.getAttribute("error") %></p>
			<% } %>
			<% if (request.getAttribute("message") != null) { %>
			<p class="success"><%= request.getAttribute("message") %></p>
			<% } %>
		</form>
		<!-- Back to Dashboard Link -->
		<a href="customerDashboard.jsp" class="back-to-dashboard">Back to
			Dashboard</a>
	</div>
</body>
</html>
