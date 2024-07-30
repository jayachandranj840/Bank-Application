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
<title>Manage Customers</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f0f4f7;
	color: #333;
	margin: 0;
	padding: 20px;
}

h2 {
	color: #3498db;
	text-align: center;
	margin-bottom: 30px;
}

.message {
	text-align: center;
	margin-bottom: 20px;
	font-weight: bold;
}

.message.success {
	color: #2ecc71;
}

.message.error {
	color: #e74c3c;
}

table {
	width: 100%;
	border-collapse: collapse;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	margin-bottom: 30px;
}

th, td {
	padding: 12px;
	text-align: left;
}

thead {
	background-color: #3498db;
	color: #fff;
}

th {
	font-weight: bold;
}

tr:nth-child(even) {
	background-color: #f9f9f9;
}

tr:hover {
	background-color: #f1f1f1;
}

button {
	background-color: #3498db;
	border: none;
	color: #fff;
	padding: 10px 15px;
	border-radius: 5px;
	font-size: 14px;
	cursor: pointer;
	transition: background-color 0.3s ease, transform 0.3s ease;
	margin: 2px;
}

button:hover {
	background-color: #2980b9;
	transform: scale(1.05);
}

.delete-button {
	background-color: #e74c3c;
}

.delete-button:hover {
	background-color: #c0392b;
}

.edit-button {
	background-color: #f39c12;
}

.edit-button:hover {
	background-color: #e67e22;
}

a.back-to-dashboard {
	display: inline-block;
	background-color: #3498db;
	color: #fff;
	padding: 10px 15px;
	border-radius: 5px;
	font-size: 14px;
	text-decoration: none;
	text-align: center;
	margin: 20px auto; /* Center the link */
	transition: background-color 0.3s ease, transform 0.3s ease;
}

a.back-to-dashboard:hover {
	background-color: #2980b9;
	transform: scale(1.05);
}

/* Center the "Back to Dashboard" link */
.center {
	text-align: center;
}
</style>
<script>
    function deleteCustomer(accountNo) {
        if (confirm("Are you sure you want to delete this customer?")) {
            fetch('ManageCustomerServlet?action=delete&accountNo=' + accountNo, {
                method: 'POST'
            })
            .then(response => response.text())
            .then(data => {
                alert(data);
                location.reload();
            })
            .catch(error => {
                alert('Error: ' + error.message);
            });
        }
    }

    function editCustomer(accountNo) {
        fetch('ManageCustomerServlet', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                action: 'edit',
                accountNo: accountNo
            })
        })
        .then(response => response.text())
        .then(data => {
            // handle the response, could be a redirection or HTML content
            document.open();
            document.write(data);
            document.close();
        })
        .catch(error => {
            alert('Error: ' + error.message);
        });
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
	<h2>Manage Customers</h2>

	<!-- Display messages or errors -->
	<%
    if (request.getAttribute("message") != null) {
    %>
	<div class="message success"><%=request.getAttribute("message")%></div>
	<%
    }
    %>
	<%
    if (request.getAttribute("error") != null) {
    %>
	<div class="message error"><%=request.getAttribute("error")%></div>
	<%
    }
    %>

	<!-- Customer List Table -->
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
				<th>Actions</th>
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
				<td>
					<button class="edit-button"
						onclick="editCustomer('<%=customer.getAccountNo()%>')">Edit</button>
					<button class="delete-button"
						onclick="deleteCustomer('<%=customer.getAccountNo()%>')">Delete</button>
				</td>
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
	<div class="center">
		<a href="adminDashboard.jsp" class="back-to-dashboard">Back to
			Dashboard</a>
	</div>
</body>
</html>
