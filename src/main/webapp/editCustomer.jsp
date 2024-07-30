<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="com.bank.model.Customer"%>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setDateHeader("Expires", 0); // Proxies.
%>
<%
if (session.getAttribute("admin") == null) {
	response.sendRedirect("adminLogin.jsp");
	return;
}
%>

<form id="editCustomerForm" action="ManageCustomerServlet?action=update"
	method="post">
	<input type="hidden" name="accountNo"
		value="<%=((Customer) request.getAttribute("customer")).getAccountNo()%>">

	<div class="form-container">
		<h2>Edit Customer Details</h2>
		<table>
			<tr>
				<td><label for="fullName">Full Name:</label></td>
				<td><input type="text" id="fullName" name="fullName"
					value="<%=((Customer) request.getAttribute("customer")).getFullName()%>"
					required></td>
			</tr>
			<tr>
				<td><label for="address">Address:</label></td>
				<td><input type="text" id="address" name="address"
					value="<%=((Customer) request.getAttribute("customer")).getAddress()%>"
					required></td>
			</tr>
			<tr>
				<td><label for="mobileNo">Mobile No:</label></td>
				<td><input type="text" id="mobileNo" name="mobileNo"
					value="<%=((Customer) request.getAttribute("customer")).getMobileNo()%>"
					required></td>
			</tr>
			<tr>
				<td><label for="email">Email:</label></td>
				<td><input type="email" id="email" name="email"
					value="<%=((Customer) request.getAttribute("customer")).getEmail()%>"
					required></td>
			</tr>
			<tr>
				<td><label for="accountType">Account Type:</label></td>
				<td><input type="text" id="accountType" name="accountType"
					value="<%=((Customer) request.getAttribute("customer")).getAccountType()%>"
					required></td>
			</tr>
			<tr>
				<td><label for="dob">Date of Birth:</label></td>
				<td><input type="date" id="dob" name="dob"
					value="<%=((Customer) request.getAttribute("customer")).getDateOfBirth()%>"
					required></td>
			</tr>
			<tr>
				<td><label for="idProof">ID Proof:</label></td>
				<td><input type="text" id="idProof" name="idProof"
					value="<%=((Customer) request.getAttribute("customer")).getIdProof()%>"
					required></td>
			</tr>
			<tr>
				<td colspan="2"><button type="submit">Update</button></td>
			</tr>
		</table>
	</div>
</form>

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

.form-container {
	background-color: #fff;
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	padding: 30px;
	max-width: 600px;
	width: 100%;
}

h2 {
	color: #2ecc71;
	margin-bottom: 20px;
	text-align: center;
}

table {
	width: 100%;
	border-collapse: separate;
	border-spacing: 0 10px;
}

td {
	padding: 10px;
}

label {
	font-size: 16px;
	color: #555;
	display: block;
}

input[type="text"], input[type="email"], input[type="date"], input[type="number"]
	{
	padding: 12px;
	border: 1px solid #ddd;
	border-radius: 5px;
	font-size: 16px;
	box-sizing: border-box;
	width: 100%;
}

input[type="text"]:focus, input[type="email"]:focus, input[type="date"]:focus
	{
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
	width: 100%;
}

button:hover {
	background-color: #27ae60;
	transform: scale(1.05);
}
</style>
