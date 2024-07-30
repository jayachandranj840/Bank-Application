<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Customer Login</title>
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
	max-width: 400px;
	width: 100%;
	text-align: center;
}

h2 {
	color: #3498db;
	margin-bottom: 20px;
}

form {
	display: flex;
	flex-direction: column;
	gap: 15px;
}

input[type="text"], input[type="password"] {
	padding: 12px;
	border: 1px solid #ddd;
	border-radius: 5px;
	font-size: 16px;
	box-sizing: border-box;
	width: 100%;
}

input[type="text"]:focus, input[type="password"]:focus {
	border-color: #3498db;
	outline: none;
	box-shadow: 0 0 5px rgba(52, 152, 219, 0.3);
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
}

button:hover {
	background-color: #2980b9;
	transform: scale(1.05);
}

.error {
	color: #e74c3c;
	margin-top: 10px;
}

.buttons {
	margin-top: 20px;
}

.btn {
	display: inline-block;
	padding: 10px 20px;
	margin: 10px;
	font-size: 16px;
	color: #ffffff;
	background-color: #007bff;
	border: none;
	border-radius: 5px;
	text-decoration: none;
	transition: background-color 0.3s ease;
}
</style>
</head>
<body>
	<div class="container">
		<h2>Customer Login</h2>
		<form action="CustomerLoginServlet" method="post">
			<input type="text" name="accountNo" placeholder="Account Number"
				required /> <input type="password" name="password"
				placeholder="Password" required />
			<button type="submit">Login</button>
			<div class="buttons">
				<a href="index.jsp" class="btn">Home Page</a>
			</div>
			<%
			if (request.getAttribute("errorMessage") != null) {
			%>
			<p class="error"><%=request.getAttribute("errorMessage")%></p>
			<%
			}
			%>
		</form>
	</div>
</body>
</html>
