<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="service.UserService"%>
<%@page import="config.Config"%>
<%@page import="java.util.*"%>
<%@page import="domain.*"%>
<%
if (session.getAttribute("user") == null) {
	response.sendRedirect("login.jsp");
}
%>
<%
Config conf = new Config();
UserService us = conf.getUserService();
List<Movie> movies = us.getMovies();
List<Movie> cartMovies = new ArrayList<>();
cartMovies = (ArrayList<Movie>) session.getAttribute("search-list");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search</title>
<%@include file="includes/header.jsp"%>
</head>
<body>
	<%@include file="includes/navbar.jsp"%>

	<%
	if (session.getAttribute("user") == null) {
		response.sendRedirect("login.jsp");
	}
	%>

	<div class="container">
		<div class="card-header my-3">All Products</div>
		<div class="row">
			<%
			if (!cartMovies.isEmpty()) {
				for (Movie p : cartMovies) {
			%>
			<div class="col-md-3 my-3">
				<div class="card w-100">
					<a href="item.jsp"><img class="card-img-top"
						src="images/<%=p.getImage()%>" alt="Card image cap"></a>
					<div class="card-body">
						<h5 class="card-title"><%=p.getName()%></h5>
						<h6 class="price">
							Price: $<%=p.getPrice()%></h6>
						<h6 class="category">
							Genre:
							<%=p.getGenre()%></h6>
						<div class="mt-3 d-flex justify-content-between">
							<a class="btn btn-dark" href="AddToCartServlet?id=<%=p.getId()%>">Add
								to Cart</a>
						</div>
					</div>
				</div>
			</div>
			<%
			}
			} else {
			out.println("There is no proucts");
			}
			%>

		</div>
	</div>



	<%@include file="includes/footer.jsp"%>
</body>
</html>