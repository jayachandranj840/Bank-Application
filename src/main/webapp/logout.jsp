<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Logout</title>
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
	box-sizing: border-box;
	text-align: center;
}

h2 {
	color: #3498db;
	margin-bottom: 20px;
}

p {
	color: #555;
	margin-bottom: 20px;
}

.button-container {
	display: flex;
	justify-content: center;
	gap: 10px;
}

a {
	text-decoration: none;
	color: #fff;
	background-color: #3498db;
	padding: 12px 20px;
	border-radius: 5px;
	font-size: 16px;
	cursor: pointer;
	transition: background-color 0.3s ease, transform 0.3s ease;
}

a:hover {
	background-color: #2980b9;
	transform: scale(1.05);
}
</style>
</head>
<body>
<%
	// Invalidate the session if it exists
	if (session != null) {
		session.invalidate();
	}
	%>
	<div class="container">
		<h2>Logout Successful</h2>
		<p>You have been successfully logged out. Thank you for using our
			banking application.</p>
		<div class="button-container">
			<a href="index.jsp">Back to Home</a>
		</div>
	</div>
</body>
</html>
