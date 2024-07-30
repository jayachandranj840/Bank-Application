<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setDateHeader("Expires", 0); // Proxies.
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Check Balance</title>
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
	flex-direction: column;
}

.container {
	background-color: #fff;
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	padding: 30px;
	max-width: 400px;
	width: 100%;
	box-sizing: border-box;
}

h2 {
	color: #3498db;
	margin-bottom: 20px;
	text-align: center;
}

.message {
	color: #2ecc71;
	margin-top: 10px;
	text-align: center;
}

.error {
	color: #e74c3c;
	margin-top: 10px;
	text-align: center;
}

.button-container {
	display: flex;
	justify-content: center;
	margin-top: 20px;
}

button {
	padding: 10px 20px;
	font-size: 16px;
	color: #fff;
	background-color: #3498db;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	transition: background-color 0.3s ease, transform 0.3s ease;
}

button:hover {
	background-color: #2980b9;
	transform: scale(1.05);
}

.button-link {
	display: inline-block;
	padding: 10px 20px;
	font-size: 16px;
	color: #fff;
	background-color: #3498db;
	border-radius: 5px;
	text-decoration: none;
	text-align: center;
	transition: background-color 0.3s ease, transform 0.3s ease;
	margin-top: 20px;
}

.button-link:hover {
	background-color: #2980b9;
	transform: scale(1.05);
}
</style>
</head>
<body>
	<div class="container">
		<h2>Check Balance</h2>
		<form action="checkBalanceServlet" method="post">
			<div class="button-container">
				<button type="submit">Check Balance</button>
			</div>
		</form>
		<%
		if (request.getAttribute("error") != null) {
		%>
		<p class="error"><%=request.getAttribute("error")%></p>
		<%
		}
		%>
		<%
		if (request.getAttribute("balance") != null) {
		%>
		<p class="message">
			Your balance is: Rs.<%=request.getAttribute("balance")%></p>
		<%
		}
		%>
	</div>
	<a href="customerDashboard.jsp" class="button-link">Back to
		Dashboard</a>
</body>
</html>
