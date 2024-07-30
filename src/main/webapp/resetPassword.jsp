
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setDateHeader("Expires", 0); // Proxies.
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Reset Password</title>
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
}

h2 {
	color: #3498db;
	margin-bottom: 20px;
	text-align: center;
}

form {
	display: flex;
	flex-direction: column;
	gap: 15px;
}

label {
	font-weight: bold;
	margin-bottom: 5px;
	color: #555;
}

input {
	padding: 10px;
	border-radius: 5px;
	border: 1px solid #ccc;
	font-size: 16px;
}

button {
	background-color: #3498db;
	border: none;
	color: #fff;
	padding: 12px 20px;
	border-radius: 5px;
	font-size: 16px;
	cursor: pointer;
	transition: background-color 0.3s ease;
}

button:hover {
	background-color: #2980b9;
}

.error {
	color: #e74c3c;
	margin-top: 10px;
	text-align: center;
}

.message {
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
}

a.back-to-dashboard:hover {
	background-color: #2980b9;
	transform: scale(1.05);
}
</style>
<script>
function validatePassword(password) {
    const strongPasswordPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$/;
    if (!strongPasswordPattern.test(password)) {
        return 'Password must be at least 8 characters long, contain both uppercase and lowercase letters, include at least one numerical digit, and contain at least one special character.';
    }
    if (/\s/.test(password)) {
        return 'Password must not contain whitespaces.';
    }
    return '';
}

function validateForm() {
    const newPassword = document.getElementById('newPassword').value;
    const error = validatePassword(newPassword);
    if (error) {
        document.getElementById('error').innerText = error;
        return false;
    }
    return true;
}
</script>
</head>
<body>
	<%
if (session.getAttribute("accountNo") == null) {
    response.sendRedirect("customerLogin.jsp");
    return;
}
%>
	<div class="container">
		<h2>Reset Password</h2>
		<form action="updatePasswordServlet" method="post"
			onsubmit="return validateForm();">
			<label for="oldPassword">Old Password:</label> <input type="password"
				id="oldPassword" name="oldPassword" required> <label
				for="newPassword">New Password:</label> <input type="password"
				id="newPassword" name="newPassword" required>
			<button type="submit">Update Password</button>
			<p id="error" class="error">
				<%
            if (request.getAttribute("error") != null) {
            %>
				<%=request.getAttribute("error")%>
				<%
            }
            %>
			</p>
			<p class="message">
				<%
            if (request.getAttribute("message") != null) {
            %>
				<%=request.getAttribute("message")%>
				<%
            }
            %>
			</p>
		</form>
		<!-- Back to Dashboard Link -->
		<a href="customerDashboard.jsp" class="back-to-dashboard">Back to
			Dashboard</a>
	</div>
</body>
</html>
