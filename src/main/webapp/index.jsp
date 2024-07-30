<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Bank Application</title>
<link rel="stylesheet" href="styles.css">
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f8f9fa;
	margin: 0;
	padding: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
}

.container {
	text-align: center;
	background-color: #ffffff;
	padding: 40px;
	border-radius: 10px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h1 {
	color: #343a40;
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

.btn:hover {
	background-color: #0056b3;
}
</style>
</head>
<body>
	<div class="container">
		<h1>Welcome to the Bank Application</h1>
		<div class="buttons">
			<a href="customerLogin.jsp" class="btn">Customer Login</a> <a
				href="adminLogin.jsp" class="btn">Admin Login</a>
		</div>
	</div>
</body>
</html>
