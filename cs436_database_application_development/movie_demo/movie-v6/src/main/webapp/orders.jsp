<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="domain.*"%>
<%@page import="java.util.*"%>
<%@page import="config.Config"%>
<%@page import="service.UserService"%>
<%
if (session.getAttribute("user") == null) {
	response.sendRedirect("login.jsp");
}
%>
<%
Config conf = new Config();
UserService us = conf.getUserService();
ArrayList<Order> orders = (ArrayList<Order>) us.getAllOrders();
User u = (User) session.getAttribute("user");
List<Order> userOrders = new ArrayList<Order>();
String email = u.getEmail();
if (orders != null) {
	for (Order o : orders) {
		if (o.getEmail().equals(email)) {
	userOrders.add(o);
		} else {
	continue;
		}
	}
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Orders</title>
<%@include file="/includes/header.jsp"%>
</head>
<body>
	<%@include file="includes/navbar.jsp"%>

	<div class="container">
		<div class="card-header my-3">All Orders</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Date</th>
					<th scope="col">Movie Name</th>
					<th scope="col">Movie Price</th>
				</tr>
			</thead>
			<tbody>

				<%
				if (userOrders != null) {
					for (Order o : userOrders) {
						Movie x = us.getMovieById(o.getMovieId());
				%>
				<tr>
					<td><%=o.getDate()%></td>
					<td><%=x.getName()%></td>
					<td><%=x.getPrice()%></td>
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