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
<title>Movie Details</title>
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
	Movie m = (Movie) session.getAttribute("item-movie");
	%>
	<div class="container">
		<div class="card w-500 mx-auto my-5">
			<div class="card-header text-center">
				<h1><%=m.getName()%></h1>
			</div>
			<div class="card-body">
				<div class="text-center">
					<iframe width="560" height="315"
						src="https://www.youtube.com/embed/<%=m.getVideo()%>"
						title="YouTube video player" frameborder="0"
						allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
						allowfullscreen></iframe>
				</div>
				<div class="text-center">
					<div class="card-body">
						<h5 class="card-title"><%=m.getName()%></h5>
						<h6 class="price">
							Price: $<%=m.getPrice()%></h6>
						<h6 class="genre">
							Genre:
							<%=m.getGenre()%></h6>
						<h6 class="year">
							Year: <%=m.getYear()%></h6>
						<h6 class="director">
							Director:
							<%=m.getDirector()%></h6>
						<h6 class="content">
							Content Rating:
							<%=m.getContentRating()%></h6>

						<a class="btn btn-dark" href="AddToCartServlet?id=<%=m.getId()%>">Add
							to Cart</a>

					</div>
				</div>

			</div>
		</div>
	</div>
	<%@include file="includes/footer.jsp"%>
</body>
</html>