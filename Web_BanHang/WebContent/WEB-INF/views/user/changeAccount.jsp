<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
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
<script src="${pageContext.request.contextPath}/asset/js/popper.min.js"></script>
<script
	src="${pageContext.request.contextPath}/asset/bootstrap/js/bootstrap.min.js"></script>
<title>Manager Account</title>
<base href="${pageContext.servletContext.contextPath}/">
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
						<a href="user/buynow.htm" class="btn btn-outline-dark">Quay lại Mua Hàng</a>
						<h4 style="text-align: center;">Chỉnh sửa thông tin cá nhân</h4>
						<hr>
						<p style="color: red"></p>
						<form:form action="user/changeAccount.htm" modelAttribute="infouser" method="post"
							enctype="multipart/form-data">
							<div class="form-group d-none">
								<label>Tên đăng nhập</label>
								<form:input readonly="true" path="id" class="form-control"
									autocomplete="off" style="width: 100%;" />
								<%-- <form:errors path="id" element="div" style="color: red" /> --%>
							</div>
							<div class="form-group">
								<label>Họ Tên</label>
								<form:input path="fullname" class="form-control"
									autocomplete="off" style="width: 100%;" required="required"/>
								<form:errors path="fullname" element="div" style="color: red" />
							</div>
							<div class="form-group">
								<label>Email</label>
								<form:input path="email" class="form-control" autocomplete="off"
									style="width: 100%;" required="required"/>
								<form:errors path="email" element="div" style="color: red" />
							</div>
							<div class="form-group">
								<label>SDT</label>
								<form:input type="number" path="sdt" required="required"
									class="form-control" autocomplete="off"
									style="width: 100%;" />
								<form:errors path="sdt" element="div" style="color: red" />
							</div>
							<div class="form-group">
								<label>Địa Chỉ</label>
								<form:input path="address" class="form-control" required="required"
									autocomplete="off" style="width: 100%;" />
								<form:errors path="address" element="div" style="color: red" />
							</div>
							<div class="form-group">
								<label>Phường</label>
								<select id="phuong" class="form-control" name="ward" required="required">
									<option value="">-------SELECT----------</option>
									<c:forEach var="w" items="${ward}">
											<option value="${w.id}">${w.name}</option>
									</c:forEach>
								</select> 
							</div>
							<div class="form-group">
								<label>Quận</label>
								<select id="select" class="form-control" name="district" required="required">
									<option value="">-------SELECT----------</option>
									<c:forEach var="d" items="${district}">
											<option value="${d.id}">${d.name}</option>
									</c:forEach>
								</select> 
							</div>
							
							<a href="user/managerAccount.htm"
								class="btn btn-outline-dark float-right ml-4">Hủy</a>
							<button id="btnchangeAccount" type="submit"
								class="btn btn-outline-dark float-right">Lưu</button>
							
						</form:form>
					</div>
				</div>
			</div>
		</div>

		<!-- end content -->
	<%@ include file="../hen/footerUser.jsp"%>
	</div>
	<!-- end footer -->
	<!-- JS -->
	   <script type="text/javascript">
   $(document).ready(function (){
		if('${infouser.quanphuong.quans.id}'){
			  $('#select').val('${infouser.quanphuong.quans.id}');
		}
		if('${infouser.quanphuong.phuongs.id}'){
			 $('#phuong').val('${infouser.quanphuong.phuongs.id}');
		} 
	   
	  
	   if('${message1}'){
		   alert('${message1}');
	   }
	   if('${message}'){
			   if(confirm("${message}. Bạn có muốn quay lại trang đặt hàng")){
				   window.location.replace("user/buynow.htm");
			   }
			   else{
				   window.location.replace("user/managerAccount.htm");
			   }
		   }
		/*  $('#select').change(function(){
			$('input[name="id_quan"]').val($('#select').val());
		})
		$('select#phuong').change(function(){
			$('input[name="id_phuong"]').val($('#phuong').val());
			 
		}); */
   });
   </script>
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="js/main.js"></script>
	</body>
</html>