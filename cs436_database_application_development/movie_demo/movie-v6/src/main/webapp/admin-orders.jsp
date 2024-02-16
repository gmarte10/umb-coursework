<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="domain.*"%>
<%@page import="java.util.*"%>
<%@page import="config.Config"%>
<%@page import="service.UserService"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Orders</title>
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
	UserService us = (UserService) session.getAttribute("user-service");
	ArrayList<Order> orders = (ArrayList<Order>) us.getAllOrders();
	List<Order> userOrders = new ArrayList<Order>();
	%>
	<div class="container">
		<div class="card-header my-3">All Orders</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Order Id</th>
					<th scope="col">Email</th>
					<th scope="col">Date</th>
					<th scope="col">Movie Name</th>
					<th scope="col">Movie Price</th>
					<th scope="col">Remove</th>
				</tr>
			</thead>
			<tbody>

				<%
				if (orders != null) {
					for (Order o : orders) {
						Movie x = us.getMovieById(o.getMovieId());
				%>
				<tr>
					<td><%=o.getId()%></td>
					<td><%=o.getEmail()%></td>
					<td><%=o.getDate()%></td>
					<td><%=x.getName()%></td>
					<td><%=x.getPrice()%></td>
					<td><a href="RemoveOrderServlet?id=<%=o.getId()%>"
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