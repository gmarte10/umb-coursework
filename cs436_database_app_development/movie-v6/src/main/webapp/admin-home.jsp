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
<title>Admin Home</title>
<%@include file="includes/header.jsp"%>
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
	UserService us = conf.getUserService();
	session.setAttribute("user-service", us);
	ArrayList<Movie> movies = (ArrayList<Movie>) us.getMovies();
	%>
	<div class="container">
		<div class="card-header my-3">All Movies</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Id</th>
					<th scope="col">Name</th>
					<th scope="col">Price</th>
					<th scope="col">Genre</th>
					<th scope="col">Year</th>
					<th scope="col">Content Rating</th>
					<th scope="col">Director</th>
					<th scope="col">Image</th>
					<th scope="col">Video</th>
					<th scope="col">Remove</th>
				</tr>
			</thead>
			<tbody>

				<%
				if (movies != null) {
					for (Movie m : movies) {
				%>
				<tr>
					<td><%=m.getId()%></td>
					<td><%=m.getName()%></td>
					<td><%=m.getPrice()%></td>
					<td><%=m.getGenre()%></td>
					<td><%=m.getYear()%></td>
					<td><%=m.getContentRating()%></td>
					<td><%=m.getDirector()%></td>
					<td><%=m.getImage()%></td>
					<td><%=m.getVideo()%></td>
					<td><a href="RemoveMovieServlet?id=<%=m.getId()%>"
						class="btn btn-sm btn-danger">Remove</a></td>
				</tr>
				<%
				}
				}
				%>

			</tbody>
		</table>
	</div>

	<div class="container">
		<div class="card w-500 mx-auto my-5">
			<div class="card-header text-center">Add a Movie</div>
			<div class="card-body">
				<form action="AddMovieServlet">
					<div class="form-group">
						<label>Movie Name (30 char)</label> <input type="text"
							class="form-control" name="add-name" placeholder="Enter Name"
							required>
					</div>
					<div class="form-group">
						<label>Id</label> <input type="text" class="form-control"
							name="add-id" placeholder="Enter ID" required>
					</div>
					<div class="form-group">
						<label>Price</label> <input type="text" class="form-control"
							name="add-price" placeholder="Enter Price" required>
					</div>
					<div class="form-group">
						<label>Genre</label> <input type="text" class="form-control"
							name="add-genre" placeholder="Enter Genre" required>
					</div>
					<div class="form-group">
						<label>Year</label> <input type="text" class="form-control"
							name="add-year" placeholder="Enter Year" required>
					</div>
					<div class="form-group">
						<label>Content Rating</label> <input type="text"
							class="form-control" name="add-rating" placeholder="Enter ID"
							required>
					</div>
					<div class="form-group">
						<label>Director</label> <input type="text" class="form-control"
							name="add-director" placeholder="Enter Director" required>
					</div>
					<div class="form-group">
						<label>Image</label> <input type="text" class="form-control"
							name="add-image" placeholder="Enter Image" required>
					</div>
					<div class="form-group">
						<label>Video</label> <input type="text" class="form-control"
							name="add-video" placeholder="Enter Video" required>
					</div>
					<div class="form-group">
						<label>Rent Price (future implementation)</label> <input
							type="text" class="form-control" name="add-rent"
							placeholder="Enter Rent Price" required>
					</div>
					<div class="text-center">
						<button type="submit" class="btn btn-primary">Add Movie</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<%@include file="includes/footer.jsp"%>
</body>
</html>