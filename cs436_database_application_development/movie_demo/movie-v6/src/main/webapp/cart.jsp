<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="domain.*"%>
<%@page import="java.util.*"%>

<%
if (session.getAttribute("user") == null) {
	response.sendRedirect("login.jsp");
}
%>
<%
List<Movie> cart_list = (ArrayList<Movie>) session.getAttribute("movies-cart");
int total = 0;
if (cart_list != null) {
	for (Movie m : cart_list) {
		total += m.getPrice();
	}
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cart</title>
<%@include file="includes/header.jsp"%>
<style type="text/css">
.table tbody td {
	vertical-align: middle;
}

.btn-incre, .btn-decre {
	box-shadow: none;
	font-size: 25px;
}
</style>
</head>
<body>

	<%@include file="/includes/navbar.jsp"%>
	<div class="container my-3">
		<div class="d-flex py-3">
			<h3>
				Total Price:
				$<%=total%>
			</h3>
			<a class="mx-3 btn btn-primary" href="CheckoutServlet">Check Out</a>
		</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Name</th>
					<th scope="col">Category</th>
					<th scope="col">Price</th>
					<th scope="col">Cancel</th>
				</tr>
			</thead>
			<tbody>
				<%
				if (cart_list != null) {
					for (Movie c : cart_list) {
				%>
				<tr>
					<td><%=c.getName()%></td>
					<td><%=c.getGenre()%></td>
					<td>$<%=c.getPrice()%></td>
					<td><a href="RemoveFromCartServlet?id=<%=c.getId()%>"
						class="btn btn-sm btn-danger">Remove</a></td>
				</tr>

				<%
				}
				}
				%>
			</tbody>
		</table>
	</div>

	<%@include file="/includes/footer.jsp"%>
</body>
</html>