<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="domain.*"%>
<%@page import="java.util.*"%>
<%@page import="config.Config"%>
<%@page import="service.AdminService"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Users</title>
<%@include file="/includes/header.jsp"%>
</head>
<body>
	<%@include file="includes/admin-navbar.jsp"%>
	<%
	if (session.getAttribute("admin") == null) {
		response.sendRedirect("admin-login.jsp");
	}
	%>
	<%
	Config conf = (Config) session.getAttribute("config");
	AdminService as = (AdminService) session.getAttribute("admin-service");
	ArrayList<User> users = (ArrayList<User>) as.getAllUsers();
	%>
	<div class="container">
		<div class="card-header my-3">All Users</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Email</th>
					<th scope="col">Password</th>
					<th scope="col">Remove</th>
				</tr>
			</thead>
			<tbody>

				<%
				if (users != null) {
					for (User x : users) {
				%>
				<tr>
					<td><%=x.getEmail()%></td>
					<td><%=x.getPass()%></td>
					<td><a href="RemoveUserServlet?user-email=<%=x.getEmail()%>"
						class="btn btn-sm btn-danger">Remove</a></td>
				</tr>
				<%
				}
				}
				%>

			</tbody>
		</table>
	</div>
	<%@include file="includes/footer.jsp"%>
</body>
</html>