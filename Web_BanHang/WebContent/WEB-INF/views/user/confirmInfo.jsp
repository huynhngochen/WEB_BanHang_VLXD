<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<base href="${pageContext.servletContext.contextPath}/">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/fonts/fontawesome/css/all.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/css/reset.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/css/global.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/css/style.css">


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
<title>TaTa</title>
<style>
.modal-content .row .col-md-5 {
	position: relative;
	overflow: hidden;
	padding: 0;
	background-size: cover;
	background-repeat: no-repeat;
	background-color: #e6e6e6;
	background-image: url("asset/images/images-login.PNG");
}

.modal-content .row .col-md-7 {
	background: #8581816b;
}

.modal-footer {
	justify-content: center;
	font-size: 25px;
}

.modal-footer span {
	color: #666666;
}

button.btn {
	color: #666666;
	font-weight: bold;
	display: block;
	line-height: 35px;
}

.hen {
	width: 1140px;
	margin: 0px auto;
}

.brand-item {
	width: 360px;
	height: 300px;
	text-align: center;
	margin-left: 10px;
	margin-bottom: 10px;
	padding: 25px;
	box-sizing: border-box;
}

.brand-item img {
	max-width: 100%;
	max-height: 100%;
}

.brand-item a {
	text-decoration: none;
	color: #474747;
	font-weight: bold;
}

.brand-item .post-title {
	font-size: 20px;
	display: block;
	margin-top: 10px;
}

.name {
	background: #474747;
	color: white;
	text-align: center;
	font-weight: bold;
	font-size: 18px;
	padding: 8px;
}

#btn-update-cart {
	font-size: 12px;
	margin-right: 0px;
	width: 65px;
	height: 30px;
	padding: 0px;
	float: right;
	text-align: center;
}

.total {
	margin-top: 50px;
	background: #ddd8cd;
}

.total-tmp {
	padding: 5px;
	padding-left: 700px;
	font-size: 20px;
	font-weight: bold;
	border-bottom: 1px solid #605e56;
}

.total-price {
	padding: 5px;
	padding-left: 700px;
	font-size: 20px;
	font-weight: bold;
}

#btn-buynow, #update {
	background: #f94c43;
	color: white;
	margin-top: 20px;
	margin-bottom: 20px;
	margin-right: 30px;
	width: 300px;
	margin-right: 30px;
}

#btn-buynow:hover, #update:hover {
	background: #474747;
	color: white;
}
.errors {
	color: red;
	font-size: 16px;
	font-weight: bold;
	font-style: italic;
}
.box {
	border: 1px solid #474747;
	padding: 15px;
	margin-bottom: 15px;
}
form {
display: inline-block;
}
.btn-buy {
    background: #f94c43;
    color: white !important;
    margin-top: 20px;
    margin-bottom: 20px;
    margin-right: 30px;
    width: 300px;
    margin-right: 30px;
    line-height: 24px !important;
}
.btn-buy:hover {
    background: #474747;
}
</style>
</head>
<body>
	<div id="wrapper">
		<%@ include file="../hen/headerUser.jsp"%>
		<%@ include file="../hen/slider.jsp"%>
		<!--   end slider -->
	
		<!-- end header -->
		<div>
			<!-- 			XÁC NHẬN THÔNG TIN GIAO HÀNG -->
			<div class="container">
				<hr>
					<h3>Xác nhận thông tin giao hàng của bạn</h3>
					<p style="color: red; font-style: italic;">(Vui lòng kiểm tra lại thông tin để tránh sai sót)</p>
					<div class="box">
						<%-- <p>Tên Nhận Hàng: <b>${infouser.getId()}</b></p> --%>
						<p>Tên Nhận Hàng: <b>${infouser.getFullname()}</b></p>
						<p>SDT Liên Hệ: <b>${infouser.getSdt()}</b></p>
						<p>Email: <b>${infouser.getEmail()}</b></p>
						<p>Địa chỉ nhận hàng: <b>${infouser.getAddress()}</b></p>
						<p style="color: red; font-style: italic;">${message}</p>
					</div> 
					<a href="user/changeAccount/${id}.htm" id="update" class="btn ">Chỉnh sửa lại thông tin giao hàng</a>
					<form action="user/buynow.htm">
						<input name="st" value="" type="hidden">
						<input name="tx" value="" type="hidden">
						<button type="submit" class="btn btn-buy">Đặt Hàng</button>
					</form>
					<!-- <a href="user/buynow.htm" id="btn-buynow" class="btn">Đặt Hàng</a> -->
			</div>
		</div>
<!-- 		end content -->
		<hr>
		<div class="container mb-4">
			<div class="row">
				<div class="col-md-3">
					<img src="${pageContext.request.contextPath}/images/1.PNG">
				</div>
				<div class="col-md-3">
					<img alt="not show"
						src="${pageContext.request.contextPath}/images/2.PNG">
				</div>
				<div class="col-md-3">
					<img src="${pageContext.request.contextPath}/images/3.PNG">
				</div>
				<div class="col-md-3">
					<img src="${pageContext.request.contextPath}/images/4.PNG">
				</div>
			</div>
		</div>
	<%@ include file="../hen/footerUser.jsp"%>
	</div>
	
	
 <script type="text/javascript">
   $(document).ready(function (){
	   if('${message}'){
		  alert('${message}');
		  window.location.href = "${pageContext.request.contextPath}/user/managerAccount.htm";
	  } 
   });
  </script>
</body>
</html>