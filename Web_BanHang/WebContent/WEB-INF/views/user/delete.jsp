<%@ page pageEncoding="utf-8"%>
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

.post-thumb img {
	transition: 0.25s;
}

.post-thumb:hover img {
	transform: scale(1.05);
}

.post-title a:hover {
	color: black;
}

form#find input {
	margin-right: 0px;
}

.errors {
	color: red;
	font-weight: bold;
}

#button-delete {
	background: #f94c43;
	color: white;
	font-weight: bold;
	text-align: center;
	padding: 10px;
	margin-top: 20px;
	margin-right: 30px;
	width: 150px;
}
#button-delete:hover {
	background: white;
	border: 1px solid;
	color: red;
}

#button-back {
	border: 1px solid;
	color: red;
	font-weight: bold;
	text-align: center;
	padding: 10px;
	margin-top: 20px;
	margin-right: 30px;
	width: 150px;
	text-decoration: none;
}
#button-back:hover{
	background: #f94c43;
	color: white;
}
</style>
</head>
<body>
	<div id="wrapper">
		<%@ include file="../hen/headerUser.jsp"%>
		<hr>
		
		<!-- end header -->
		
		<div class="wp-content clearfix mb-4">
        	<div class="container">
        		
        		<h3>Bạn chắc chắn muốn hủy đơn ? Vui lòng kiểm tra lại trước khi xóa !</h3>
        		<p class="errors">${message}</p>
        		<h5>Mã đơn hàng: <b>DH${order.id }</b></h5>
        		<h5>Tổng tiền: <b><f:formatNumber minFractionDigits="0" 
        			value="${order.amount}"/> đ</b></h5>
        		<h5>Hình thức thanh toán: <b>${order.payment}</b></h5>
        		<h5>Ngày đặt: <b><f:formatDate pattern="dd-MM-yyyy" 
        			value="${order.created}" /></b></h5>
        		<form:form action="user/delete.htm" class="mt-3 d-flex" 
        			method="post" modelAttribute="order" enctype="multipart/form-data">
        			<div class="form-group d-none">
						<form:input path="id"/>
					</div>
					<div class="form-group d-none">
						<form:input  path="amount"/>
					</div>
					<div class="form-group d-none">
						<form:input path="payment" />
					</div>
					<div class="form-group d-none">
						<form:input path="status_delivery"  />
					</div>
					<div class="form-group d-none">
						<form:input path="created"/>
					</div>
					<div class="form-group d-none">
						<form:input path="userid.username"/>
					</div>
        			<button type="submit" class="btn mr-3" 
        				id="button-delete">Hủy đơn hàng</button>
        			<a href="user/mycart.htm" class="btn" type="button" 
        				id="button-back">Quay lại</a>
        		</form:form>
        	</div>
        </div>
        <%-- <%@ include file="../hen/hen.jsp"%> --%>
		<!-- end content -->
		<%@ include file="../hen/footerUser.jsp"%>
		<!-- end footer -->
	</div>
	
	<script type="text/javascript">
		
	</script>
</body>
</html>