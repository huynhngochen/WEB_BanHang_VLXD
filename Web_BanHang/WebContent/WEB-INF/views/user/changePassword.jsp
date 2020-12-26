<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/asset/fonts/fontawesome/css/all.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/asset/css/reset.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/asset/css/global.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/asset/css/style.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/asset/css/styleManagerAccount.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/js/Jquery-3.5.1.js">
<link
	href="https://fonts.googleapis.com/css2?family=Quicksand:wght@300;400;500;515;600;700&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/bootstrap/css/bootstrap.min.css">
<script
	src="${pageContext.request.contextPath}/asset/js/Jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath}/sset/js/popper.min.js"></script>
<script
	src="${pageContext.request.contextPath}/asset/bootstrap/js/bootstrap.min.js"></script>
<title>Manager Account</title>
<base href="${pageContext.servletContext.contextPath}/">
<style type="text/css">
.kc {
	font-size: 17px;
	margin-bottom: 3px;
	margin-left: 4px;
}
</style>
</head>
<body>
	<div id="wrapper">
		<%@ include file="../hen/headerUser.jsp"%>
		<hr>
		<!-- end header -->
		<div id="wp-content">
			<!-- Menu quản lí tài khoản -->
			<div class="container">
				<div class="row">
					<div class="col-md-4 border-right p-4">
						<p>
							Xin Chào : <a href="user/managerAccount.htm"
								style="font-size: 18px !important; margin-left: 10px; font-weight: 700; color: #FF9800; text-decoration: none">
								${sessionScope.user.getUsername()}</a>
						</p>
						<div class="menu-account text-center">
							<ul>
								<li><a href="user/managerAccount.htm"><span
										class="mr-3"> <img
											src="${pageContext.request.contextPath}/images/account.png"></span>
										Thông tin tài khoản</a></li>
								<li><a href="user/mycart.htm"><span
										style="margin-right: 32px;"> <img
											src="${pageContext.request.contextPath}/images/supermarketmini.png"></span>
										Quản lý đơn hàng</a></li>
								<li><a href="user/logout.htm"
									class="float-right"><span style="margin-left: 62px;"
										class="float-left"> <img
											src="${pageContext.request.contextPath}/images/logout.png"></span>
										Đăng xuất</a></li>
							</ul>
						</div>
					</div>
					<!-- end menu quản lí tài khoản -->
					<div class="col-md-8">
						<h4 style="text-align: center;">Thay đổi mật khẩu</h4>
						<hr>
						<p style="color: red">${message}</p>
						<form action="user/changePassword.htm" method="post">
							<div class="form-group">
								<label class="kc">Nhập mật khẩu cũ của bạn</label> <br> <input
									name="oldpass" type="password" class="form-control" />
							</div>
							<div class="form-group">
								<label class="kc">Mật khẩu mới</label> <br> <input
									name="newpass" type="password" class="form-control"
									maxlength="14" />
							</div>
							<div class="form-group">
								<label class="kc">Xác nhận mật khẩu mới</label> <br> <input
									name="renewpass" type="password" class="form-control"
									maxlength="14" />
							</div>
							<a href="user/managerAccount.htm"
								class="btn btn-outline-dark float-right ml-4">Hủy</a>
							<button class="btn btn-outline-dark float-right"
								type="submit">Thay đổi</button>
							
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- end content -->
		<%@ include file="../hen/footerUser.jsp"%>
	</div>
	
	</body>
</html>