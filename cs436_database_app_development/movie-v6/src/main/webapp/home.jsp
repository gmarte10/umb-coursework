<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="service.UserService"%>
<%@page import="config.Config"%>
<%@page import="java.util.*"%>
<%@page import="domain.*"%>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HomePage</title>
<%@include file="includes/header.jsp"%>
</head>
<body>
	<%@include file="includes/navbar.jsp"%>
	<%
	if (session.getAttribute("user") == null) {
		response.sendRedirect("login.jsp");
	}
	%>
	<%
	Config conf = (Config) session.getAttribute("config-user");
	UserService us = (UserService) session.getAttribute("user-service");
	List<Movie> movies = us.getMovies();
	session.setAttribute("movies", movies);
	%>
	<div class="container">
		<div class="card-header my-3">All Movies</div>
		<div class="row">
			<%
			if (!movies.isEmpty()) {
				for (Movie p : movies) {
			%>
			<div class="col-md-3 my-3">
				<div class="card w-100">
					<a href="ItemServlet?id=<%=p.getId()%>"><img
						class="card-img-top" src="images/<%=p.getImage()%>"
						alt="Card image cap"></a>
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
			out.println("There are no products");
			}
			%>
		</div>
	</div>



	<%@include file="includes/footer.jsp"%>
</body>
</html>