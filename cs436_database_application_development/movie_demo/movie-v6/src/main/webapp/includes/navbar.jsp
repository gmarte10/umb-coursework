<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<div class="container">
		<a class="navbar-brand" href="home.jsp">Online Movie Store</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav ml-auto">
				<li class="nav-item active"><a class="nav-link"
					href="home.jsp">Home </a></li>
				<li class="nav-item"><a class="nav-link" href="cart.jsp">Cart</a>
				</li>
				<% if (request.getSession().getAttribute("user") != null) {%>
					<li class="nav-item"><a class="nav-link" href="orders.jsp">Orders</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="LogoutServlet">Logout</a></li>
				<%}else { %>
					<li class="nav-item"><a class="nav-link" href="login.jsp">Login</a></li>
				<%}
				%>
					
			</ul>
			<form action="FilterServlet" class="form-inline my-2 my-lg-0">
		      <input class="form-control mr-sm-2" name="search" type="search" placeholder="Search" aria-label="Search">
		      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
		    </form>

		</div>
	</div>
</nav>