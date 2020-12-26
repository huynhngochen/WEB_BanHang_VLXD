<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Login</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/js/Jquery-3.5.1.js">
    <!-- Font Icon -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}//fonts/material-icon/css/material-design-iconic-font.min.css">
<script
	src="${pageContext.request.contextPath}/asset/js/Jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath}/asset/js/popper.min.js"></script>
    <!-- Main css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}//css1/style.css">
    <base href="${pageContext.servletContext.contextPath}/">
</head>
<body>
	<div class="container rounded" style="background-color: #5f4a4c; width: 200px; height: 30px;margin-left: 0;">
				<p style="font-size: 15px;color: #777; text-align: center">
					<a href="user/index.htm" style="color: white;">
						<i class="fas fa-arrow-left"></i>Quay lại trang chủ
					</a>
				</p>
	</div>
        <!-- Sing in  Form -->
        <section class="sign-in">
            <div class="container">
                <div class="signin-content">
                    <div class="signin-image">
                        <figure><img src="images/signin-image.jpg" alt="sing up image"></figure>
                        <a href="user/register.htm" class="signup-image-link">Tạo tài khoản</a>
                    </div>
					
                    <div class="signin-form">
                        <h2 class="form-title">Đăng nhập</h2>
                         <span style="color: red"></span>
                        <form method="POST" class="register-form" action="user/login.htm">
                        <span style="color: red">${message}</span>
                            <div class="form-group">
                                <label for="your_name"><i class="zmdi zmdi-account material-icons-name"></i></label>
                                <input type="text" required="required" name="your_name" id="your_name" placeholder="Tên đăng nhập" autocomplete="off"/>
                               <%--  <span style="color: red">${message}</span> --%>
                            </div>
                            <div class="form-group">
                                <label for="your_pass"><i class="zmdi zmdi-lock"></i></label>
                                <input type="password" required="required" name="your_pass" id="your_pass" placeholder="Mật khẩu" autocomplete="off"/>
                           		<span style="color: red"></span>
                            </div>
                            <a href="user/forgotPassword.htm" style="color: black">Quên mật khẩu</a>                             
                            <div class="form-group form-button">
                                <input type="submit" class="form-submit" value="Đăng nhập"/>
                            </div>
                        </form>
                        <div class="social-login">
                            <span class="social-label">Đăng nhập với</span>
                            <ul class="socials">
                                <li><a href="${pageContext.request.contextPath}/login-facebook"><i class="display-flex-center zmdi zmdi-facebook"></i></a></li>
                                <!-- <li><a href="#"><i class="display-flex-center zmdi zmdi-twitter"></i></a></li> -->
                                <li><a href="user/login.htm"><i class="display-flex-center zmdi zmdi-google"></i></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </section>

    <!-- JS -->
	<script src="vendor/jquery/jquery.min.js"></script>
    <script src="js/main.js"></script>
</body>
</html>