<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Registration Success</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #ecf0f1;
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
	text-align: center;
}

h2 {
	color: #27ae60;
	margin-bottom: 20px;
}

p {
	font-size: 16px;
	margin: 10px 0;
}

.info {
	font-weight: bold;
}

a {
	display: inline-block;
	margin-top: 20px;
	padding: 12px 20px;
	color: #fff;
	background-color: #3498db;
	border-radius: 5px;
	text-decoration: none;
	font-size: 16px;
	transition: background-color 0.3s ease, transform 0.3s ease;
}

a:hover {
	background-color: #2980b9;
	transform: scale(1.05);
}
</style>
</head>
<body>
	<div class="container">
		<h2>Customer Registered Successfully</h2>
		<p class="info">Account Number: ${accountNo}</p>
		<p class="info">Temporary Password: ${tempPassword}</p>
		<p>The customer can log in using these details.</p>
		<a href="adminDashboard.jsp">Back to Dashboard</a>
	</div>
</body>
</html>
