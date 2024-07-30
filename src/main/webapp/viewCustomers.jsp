<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="com.bank.model.Customer"%>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setDateHeader("Expires", 0); // Proxies.
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>View Customers</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f5f5f5;
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
	max-width: 1200px; /* Increased max-width for better table fit */
	width: 100%;
	box-sizing: border-box;
	overflow-x: auto;
	/* Added for horizontal scrolling on smaller screens */
	overflow-y: auto; /* Added for vertical scrolling */
	max-height: 80vh; /* Set max-height for vertical scrolling */
}

h2 {
	color: #2980b9;
	margin-bottom: 20px;
	text-align: center;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-bottom: 20px;
}

th, td {
	padding: 12px 15px;
	text-align: left;
}

thead {
	background-color: #3498db;
	color: #fff;
}

th {
	border-bottom: 2px solid #2980b9;
}

tbody tr:nth-child(odd) {
	background-color: #f9f9f9;
}

tbody tr:nth-child(even) {
	background-color: #fff;
}

tbody tr:hover {
	background-color: #f1c40f;
	color: #fff;
}

a {
	display: block;
	text-align: center;
	margin-top: 20px;
	padding: 12px 20px;
	color: #fff;
	background-color: #27ae60;
	border-radius: 5px;
	text-decoration: none;
	font-size: 16px;
	transition: background-color 0.3s ease, transform 0.3s ease;
}

a:hover {
	background-color: #2ecc71;
	transform: scale(1.05);
}

.no-customers {
	text-align: center;
	color: #e74c3c;
	font-size: 18px;
	font-weight: bold;
}

/* Responsive design for smaller screens */
@media ( max-width : 768px) {
	th, td {
		padding: 8px 10px;
		font-size: 14px;
	}
	h2 {
		font-size: 20px;
	}
}
</style>
</head>
<body>
	<%
	if (session.getAttribute("admin") == null) {
		response.sendRedirect("adminLogin.jsp");
		return;
	}
	%>

	<div class="container">
		<h2>View Customers</h2>
		<table>
			<thead>
				<tr>
					<th>Account No</th>
					<th>Full Name</th>
					<th>Address</th>
					<th>Mobile No</th>
					<th>Email</th>
					<th>Account Type</th>
					<th>Date of Birth</th>
					<th>ID Proof</th>
				</tr>
			</thead>
			<tbody>
				<%
				List<Customer> customers = (List<Customer>) request.getAttribute("customers");
				if (customers != null && !customers.isEmpty()) {
					for (Customer customer : customers) {
				%>
				<tr>
					<td><%=customer.getAccountNo()%></td>
					<td><%=customer.getFullName()%></td>
					<td><%=customer.getAddress()%></td>
					<td><%=customer.getMobileNo()%></td>
					<td><%=customer.getEmail()%></td>
					<td><%=customer.getAccountType()%></td>
					<td><%=customer.getDateOfBirth()%></td>
					<td><%=customer.getIdProof()%></td>
				</tr>
				<%
				}
				} else {
				%>
				<tr>
					<td colspan="9" class="no-customers">No customers found</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
		<a href="adminDashboard.jsp">Back to Dashboard</a>
	</div>
</body>
</html>
