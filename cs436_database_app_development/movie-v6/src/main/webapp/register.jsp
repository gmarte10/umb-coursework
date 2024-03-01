<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Register</title>
<%@include file="includes/header.jsp"%>
</head>
<body>
	<div class="container">
		<div class="card w-50 mx-auto my-5">
			<div class="card-header text-center">User Register</div>
			<div class="card-body">
				<form action="RegisterServlet" method="post">
					<div class="form-group">
						<label>Enter you email:</label> <input type="email"
							class="form-control" name="register-email"
							placeholder="Enter Email" required>
					</div>
					<div class="form-group">
						<label>Enter the password you want:</label> <input type="password"
							class="form-control" name="register-password"
							placeholder="Enter Password" required>
					</div>
					<div class="text-center">
						<button type="submit" class="btn btn-primary">Register</button>
					</div>

				</form>
			</div>
		</div>
	</div>
	<%@include file="includes/footer.jsp"%>
</body>
</html>