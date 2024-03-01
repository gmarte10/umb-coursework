<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Login</title>
<%@include file="includes/header.jsp"%>
</head>
<body>
	<div class="container">
		<div class="card w-50 mx-auto my-5">
			<div class="card-header text-center">Admin Login</div>
			<div class="card-body">
				<form action="AdminLoginServlet" method="post">
					<div class="form-group">
						<label>Username</label> <input type="text" class="form-control"
							name="admin-login-username" placeholder="Enter Username" required>
					</div>
					<div class="form-group">
						<label>Password</label> <input type="password"
							class="form-control" name="admin-login-password"
							placeholder="Enter Password" required>
					</div>
					<div class="text-center">
						<button type="submit" class="btn btn-primary">Login</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@include file="includes/footer.jsp"%>
</body>
</html>