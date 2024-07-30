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
<title>Register Customer</title>
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
	color: #2ecc71;
	margin-bottom: 20px;
}

form {
	display: flex;
	flex-direction: column;
	gap: 15px;
}

label {
	font-size: 16px;
	color: #555;
	text-align: left;
}

input[type="text"], input[type="email"], input[type="number"], input[type="date"], select {
	padding: 12px;
	border: 1px solid #ddd;
	border-radius: 5px;
	font-size: 16px;
	box-sizing: border-box;
	width: 100%;
}

input[type="text"]:focus, input[type="email"]:focus, input[type="number"]:focus, input[type="date"]:focus, select:focus {
	border-color: #2ecc71;
	outline: none;
	box-shadow: 0 0 5px rgba(46, 204, 113, 0.3);
}

button {
	background-color: #2ecc71;
	border: none;
	color: #fff;
	padding: 12px 20px;
	border-radius: 5px;
	font-size: 16px;
	cursor: pointer;
	transition: background-color 0.3s ease, transform 0.3s ease;
}

button:hover {
	background-color: #27ae60;
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
</style>
<script>
function validateForm() {
	const fullName = document.getElementById('fullName').value.trim();
	const address = document.getElementById('address').value.trim();
	const mobileNo = document.getElementById('mobileNo').value;
	const email = document.getElementById('email').value;
	const idProof = document.getElementById('idProof').value;
	const dob = document.getElementById('dob').value;
	const accountType = document.getElementById('accountType').value;

	const namePattern = /^[A-Za-z\s]+$/;
	const mobileNoPattern = /^[0-9]{10}$/;
	const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	const idProofPattern = /^[0-9]{12}$/;

	let errorMessage = '';

	if (!namePattern.test(fullName) || fullName === '') {
		errorMessage += 'Full name must contain only alphabets and cannot be empty or whitespace.<br>';
	}

	if (address === '') {
		errorMessage += 'Address cannot be empty or contain only whitespace.<br>';
	}

	if (!mobileNoPattern.test(mobileNo)) {
		errorMessage += 'Please enter a valid 10-digit mobile number.<br>';
	}

	if (!emailPattern.test(email)) {
		errorMessage += 'Please enter a valid email address.<br>';
	}

	if (!idProofPattern.test(idProof)) {
		errorMessage += 'ID Proof must be a 12-digit number.<br>';
	}

	if (dob === '') {
		errorMessage += 'Date of Birth cannot be empty.<br>';
	} else {
		const birthDate = new Date(dob);
		const today = new Date();
		const age = today.getFullYear() - birthDate.getFullYear();
		const monthDifference = today.getMonth() - birthDate.getMonth();

		if (monthDifference < 0 || (monthDifference === 0 && today.getDate() < birthDate.getDate())) {
			age--;
		}

		if (age < 18 && accountType === 'Current') {
			errorMessage += 'Only Savings accounts can be opened for customers under 18 years old.<br>';
		}
	}

	if (errorMessage) {
		document.getElementById('error-message').innerHTML = errorMessage;
		return false;
	}

	// Perform additional server-side checks for uniqueness
	return true;
}
</script>
</head>
<body>
	<%
	if (session.getAttribute("admin") == null) {
		response.sendRedirect("adminLogin.jsp");
		return;
	}
	%>
	<div class="container">
		<h2>Register Customer</h2>
		<form action="registerCustomer" method="post" onsubmit="return validateForm()">
			<label for="fullName">Full Name:</label>
			<input type="text" id="fullName" name="fullName" required>

			<label for="address">Address:</label>
			<input type="text" id="address" name="address" required>

			<label for="mobileNo">Mobile No:</label>
			<input type="text" id="mobileNo" name="mobileNo" required>

			<label for="email">Email:</label>
			<input type="email" id="email" name="email" required>

			<label for="accountType">Account Type:</label>
			<select id="accountType" name="accountType" required>
				<option value="Savings">Saving</option>
				<option value="Current">Current</option>
			</select>

			<label for="initialBalance">Initial Balance:</label>
			<input type="number" id="initialBalance" name="initialBalance" step="0.01" required>

			<label for="dob">Date of Birth:</label>
			<input type="date" id="dob" name="dob" required>

			<label for="idProof">ID Proof:</label>
			<input type="text" id="idProof" name="idProof" required>

			<div id="error-message" class="error"></div>
			<button type="submit">Register</button>

			<%
			if (request.getAttribute("error") != null) {
			%>
			<p class="error"><%=request.getAttribute("error")%></p>
			<%
			}
			%>
		</form>
	</div>
</body>
</html>
