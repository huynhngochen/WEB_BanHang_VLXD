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

.post-thumb img {
	transition: 0.25s;
}

.post-thumb:hover img {
	transform: scale(1.05);
}

.post-title a:hover {
	color: black;
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

#btn-buynow {
	background: #f94c43;
	color: white;
	/* float: right; */
	margin-top: 20px;
	margin-bottom: 20px;
	margin-right: 30px;
	width: 300px;
	margin-right: 30px;
}

#btn-buynow:hover {
	background: #474747;
	color: white;
}

.errors {
	color: red;
	font-size: 16px;
	font-weight: bold;
}
</style>
</head>
<body>
	<div id="wrapper">
		<%@ include file="../hen/headerUser.jsp"%>
		<hr>
		<div>
		<!-- Modal -->
	  	<form action="${initParam['posturl']}" method="post" id="payment_online">
				<input type="hidden" name="upload" value="1" />
				<input type="hidden" name="return" value="${initParam['returnurl']}" />
				<input type="hidden" name="cmd" value="_xclick" />
				<input type="hidden" name="business" value="${initParam['business']}">
				
				 <input type="hidden" name="custom" value="${order.id }" />
				<input type="hidden" name="item_name" value="Đơn hàng ${order.id }"
						readonly="readonly">
				<input type="hidden" name="quantity" value="1" readonly="readonly"
						readonly="readonly"/>
				<input type="hidden" name="amount" readonly="readonly"
								value="${order.amount/23000 }">
				<input type="submit" id="btn_payment" value="Payment" style="display: none;"/>
		</form>
		<div class="container text-center">
			<button type="button" id="btn_continue" style="margin: 0 auto;padding: 20px;"
			class="btn btn-large btn-outline-success">Click để tiếp tục thanh toán</button>
		</div>
		
		</div>
<!-- 		end content -->
		<%@ include file="../hen/footerUser.jsp"%>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#btn_continue').click(function(){
				$('form#payment_online').submit();
			});
		});
	</script>
	
</body>
</html>