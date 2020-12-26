<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QUÊN MẬT KHẨU</title>
<base href="${pageContext.servletContext.contextPath}/">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<style>
.ctn {
	width: 70%;
	margin-top: 10px;
	border: 3px solid #dc3545;
	border-radius: 20px;
	background-color: #e9ebee; 
}

button {
	background-color: green;
	color: white;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	cursor: pointer;
	width: 100%;
	border-radius: 10px; 
}

button:hover {
	opacity: 0.8;
}

.kc {
	font-size: 17px;
	margin-bottom: 3px;
	margin-left: 4px;
}

.errors {
	color: red; font-style: italic;
}

.admin {
	width: 50%;
	margin: auto;
}
</style>
</head>
<body>
	<%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	%>
	<div class="container rounded mt-3" style="background-color: #5f4a4c; width: 200px; height: 30px;margin-left: 0;">
				<p style="font-size: 15px;color: #777; text-align: center">
					<a href="user/login.htm" style="color: white;">
						<i class="fas fa-arrow-left"></i>Quay lại đăng nhập
					</a>
				</p>
	</div>
	<div class="container ctn">
		<br>
		<h4 style="text-align: center;">Quên mật khẩu</h4>
		<hr>
		<div class="container">
			<div class="admin">
				<p class="errors" style="text-align: center; font-size: 18px;">${message}</p>
				<form action="user/forgotPassword.htm" method="post">
						<div class="form-group">
							<label class="kc">Nhập tên đăng nhập của bạn</label>
							<br>
							<input name="username" type="text" class="form-control"/>
						</div>
						<div class="form-group">
							<label class="kc">Nhập email của bạn</label>
							<br>
							<input name="email" type="text" class="form-control"/>
						</div>
						<button type="submit">Gửi</button>
					</form>
				</div>
			</div>
			<hr>
	</div>
	<br>
	<!-- 	thư viện    -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
	<script src="https://kit.fontawesome.com/66b27f5cfd.js"
		crossorigin="anonymous"></script>
</body>
</html>