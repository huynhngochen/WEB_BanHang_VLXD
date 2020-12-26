<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign Up</title>

    <!-- Font Icon -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}//fonts/material-icon/css/material-design-iconic-font.min.css">

    <!-- Main css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}//css1/style.css">
    <base href="${pageContext.servletContext.contextPath}/">
    <style type="text/css">
    	.errors {
			color: red; 
		}
    </style>
</head>
<body>
	<div class="container rounded" style="background-color: #5f4a4c; width: 200px; height: 30px;margin-left: 0;">
				<p style="font-size: 15px;color: #777; text-align: center">
					<a href="user/index.htm" style="color: white;">
						<i class="fas fa-arrow-left"></i>Quay lại trang chủ
					</a>
				</p>
	</div>
	<form:form action="user/register.htm" method="POST" modelAttribute="user">
        <!-- Sign up form -->
        <section class="signup">
            <div class="container">
                <div class="signup-content">
                    <div class="signup-form">
                    <p class="errors">${message}</p>
                        <h2 class="form-title">Đăng ký</h2>
                        <form class="register-form" id="register-form">
                            <div class="form-group">
                                <label for="name"><i class="zmdi zmdi-account material-icons-name"></i></label>
                                <input name="username" required="required" placeholder="Tên đăng nhập" autocomplete="off"/>
                                <form:errors path="username" element="div" style="color: red"/>
                            </div>
                            <div class="form-group">
                                <label for="password"><i class="zmdi zmdi-lock"></i></label>
                                <input name="password" required="required" type="password" placeholder="Mật khẩu" autocomplete="off"/>
                                <form:errors path="password" element="div" style="color: red"/>
                            </div>
                            
                            <div class="form-group">
                            	<label for="repassword"><i class="zmdi zmdi-lock"></i></label>
                               <input name="repassword" required="required" class="form-control" type="password" placeholder="Xác nhận mật khẩu" autocomplete="off" />
                             	<p class="errors">${message_pass}</p>
                            </div>
                            <div class="form-group form-button">
                                <input type="submit" name="signup" id="signup" class="form-submit" value="Đăng ký"/>
                            </div>
                         
                         </form>
                    </div>
					<div class="signup-image">
						 <figure><img src="images/signup-image.jpg" alt="sing up image"></figure>
                        <a href="user/login.htm" class="signup-image-link">Tôi đã có tài khoản</a>
					</div>
				</div>
                
            </div>
        </section>

    <!-- JS -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="js/main.js"></script>
    </form:form>
</body>