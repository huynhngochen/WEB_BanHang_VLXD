<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
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
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/css/styleManagerAccount.css">


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
	float: right;
	margin-top: 20px;
	margin-bottom: 20px; margin-right : 30px;
	width: 300px;
	margin-right: 30px;
}

#btn-buynow:hover {
	background: #474747;
	color: white;
}
</style>
</head>
<body>
	<div id="wrapper">
		<%@ include file="../hen/headerUser.jsp"%>
		<!-- slider -->
		<%@ include file="../hen/slider.jsp"%>
		<!--   end slider -->
		<hr>
		<!-- end header -->
		<div id="wp-content">
			<div class="container">
				<div class="row">
					<div class="col-md-4 border-right p-4">
						<p>Xin Chào :  <a href="user/managerAccount.htm"
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
					<h4 class="text-center mb-4">Đơn Hàng Của Bạn</h4>
						<table class="table table-bordered table-hover">
							<thead class="thead-dark text-center">
								<tr>
									<th>Mã Đơn Hàng</th>
									<th>Ngày Đặt</th>
									<th>Trạng Thái Đơn Hàng</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="o" items="${order}">
									<tr>
										<th><a href="user/detailMycart/${o.id}.htm">ĐH${o.id}</a></th>
										<th><f:formatDate pattern="dd-MM-yyyy"
												value="${o.created}" /></th>
										<c:choose>
											<c:when test="${o.status_delivery == 0}">
												<th>Đã Hủy</th>
											</c:when>
											<c:when test="${o.status_delivery == 1}">
												<th>Đặt Hàng Thành Công</th>
											</c:when>
											<c:when test="${o.status_delivery == 2}">
												<th>Đang Xử Lý</th>
											</c:when>
											<c:when test="${o.status_delivery == 3}">
												<th>Đang Giao Hàng</th>
											</c:when>
											<c:when test="${o.status_delivery == 4}">
												<th>Giao Thành Công</th>
											</c:when>
											<c:otherwise>
												<th>Đã Hủy</th>
											</c:otherwise>
										</c:choose>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="btn-group">
							<a class="btn btn-default"><i class="fa fa-angle-left"></i></a>
							<c:forEach var="i" begin="0" end="${totalitem}">
								<a class="btn btn-default"
									href="${pageContext.request.contextPath}/user/mycart/${i+1}.htm"><c:out
										value="${i+1}" /></a>
							</c:forEach>
							<a class="btn btn-default"><i class="fa fa-angle-right"></i></a>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
<!-- 		end content -->
	<%@ include file="../hen/footerUser.jsp"%>
	</div>
	
	<script>!function(s,u,b,i,z){var o,t,r,y;s[i]||(s._sbzaccid=z,s[i]=function(){s[i].q.push(arguments)},s[i].q=[],s[i]("setAccount",z),r=["widget.subiz.net","storage.googleapis"+(t=".com"),"app.sbz.workers.dev",i+"a"+(o=function(k,t){var n=t<=6?5:o(k,t-1)+o(k,t-3);return k!==t?n:n.toString(32)})(20,20)+t,i+"b"+o(30,30)+t,i+"c"+o(40,40)+t],(y=function(k){var t,n;s._subiz_init_2094850928430||r[k]&&(t=u.createElement(b),n=u.getElementsByTagName(b)[0],t.async=1,t.src="https://"+r[k]+"/sbz/app.js?accid="+z,n.parentNode.insertBefore(t,n),setTimeout(y,2e3,k+1))})(0))}(window,document,"script","subiz","acqwbbwdbbalzevxyoha");</script>

</body>
</html>