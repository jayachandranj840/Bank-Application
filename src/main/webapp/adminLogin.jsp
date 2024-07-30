<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Admin Login</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f9;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
}

.container {
	background-color: #fff;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	border-radius: 8px;
	padding: 20px;
	width: 300px;
	text-align: center;
}

h2 {
	color: #333;
	margin-bottom: 20px;
}

label {
	display: block;
	margin: 10px 0 5px;
	color: #555;
	text-align: left;
}

input[type="text"], input[type="password"] {
	width: calc(100% - 20px);
	padding: 10px;
	margin-bottom: 15px;
	border: 1px solid #ccc;
	border-radius: 5px;
	box-sizing: border-box;
}

button {
	background-color: #007bff;
	color: #fff;
	border: none;
	border-radius: 5px;
	padding: 10px 20px;
	font-size: 16px;
	cursor: pointer;
	transition: background-color 0.3s ease;
	width: 100%;
}

button:hover {
	background-color: #0056b3;
}

.error {
	color: #ff0000;
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
		<h2>Admin Login</h2>
		<form action="adminLogin" method="post">
			<label for="username">Username:</label> <input type="text"
				id="username" name="username" required> <label
				for="password">Password:</label> <input type="password"
				id="password" name="password" required>

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
