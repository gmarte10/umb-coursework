<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<div class="container">
		<a class="navbar-brand" href="admin-home.jsp">Admin Online Movie Store</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav ml-auto">
				<li class="nav-item active"><a class="nav-link"
					href="admin-home.jsp">Home </a></li>
				<li class="nav-item"><a class="nav-link" href="admin-orders.jsp">All Orders</a>
				</li>
				<% if (request.getSession().getAttribute("admin") != null) {%>
					<li class="nav-item"><a class="nav-link" href="admin-users.jsp">All Users</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="AdminLogoutServlet">Logout</a></li>
				<%}else { %>
					<li class="nav-item"><a class="nav-link" href="admin-login.jsp">Login</a></li>
				<%}
				%>
					
			</ul>

		</div>
	</div>
</nav>