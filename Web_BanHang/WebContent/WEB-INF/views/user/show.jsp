<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
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

.product-item {
	position: relative;
	box-sizing: border-box;
	overflow: hidden;
	margin: 15px 0px;
}

.product-item .product-name {
	margin-bottom: 10px; 
    color: #f94c43;
    padding: 0px;
    max-height: 50px;
    box-sizing: border-box;
    overflow: hidden;
    /* text-align: center; */
}

.product-item .price {
margin-bottom: 10px;
	font-weight: bold;
	/* text-align: center; */
}

a.buy-now {
	/* width: 255px; */
	text-decoration: none;
	padding: 2px;
	font-size: 18px;
	color: white;
	text-align: center;
	background: #f94c43;
	position: absolute;
	right: 0px;
	bottom: 2px;
}

 .buy-now:hover {
 	background-color: #474747;
/* 	transition: all 0.5s;
	position: absolute;
	 bottom: 0px;  */
}
.product-item:hover {
	box-shadow: 2px 2px 2px 2px rgb(170 157 157);
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
		<div class="container mb-4">
			<div class="row">
				<div class="col-md-3">
					<img src="${pageContext.request.contextPath}/images/1.PNG">
				</div>
				<div class="col-md-3">
					<img alt="not show" src="${pageContext.request.contextPath}/images/2.PNG">
				</div>
				<div class="col-md-3">
					<img src="${pageContext.request.contextPath}/images/3.PNG">
				</div>
				<div class="col-md-3">
					<img src="${pageContext.request.contextPath}/images/4.PNG">
				</div>
			</div>
		</div>
		<!-- end header -->
		<div id="wp-content">
			<div class="container">
			<h3 class="text-center">${product[0].loaihang.name}</h3>
			<hr>
				<div class="row">
					<c:forEach var="p" items="${product}">
						<div class="col-md-3 col-sm-6 product-item">
							<a href="user/detail-product/${p.id}.htm"><img
								style="max-width: 100%"
								src="${pageContext.request.contextPath}/images/${p.images_link}"></a>
							<div class="product-name">${p.name }</div>
							<div class="price">
								<c:if test="${p.discount > 0 }">
									<f:formatNumber minFractionDigits="0"
										value="${p.price *(100-p.discount) / 100}" type="number" /> đ
										<p style="margin-bottom: 0px;">
										<del>
											<f:formatNumber minFractionDigits="0" value="${p.price}"
												type="number" />
											đ
										</del>
									</p>
								</c:if>
								<c:if test="${p.discount == 0 }">
									<f:formatNumber minFractionDigits="0" value="${p.price}"
										type="number" /> đ
									</c:if>
							</div>
							<c:if test="${p.in_stock == 0}">
								<a href="user/cart/addCart/${p.id}.htm" class="buy-now"
									style="pointer-events: none; cursor: default; text-decoration: none; color: black;">Mua
									ngay</a>
							</c:if>
							<c:if test="${p.in_stock > 0}">
								<a href="user/cart/addCart/${p.id}.htm" class="buy-now">Mua
									ngay</a>
							</c:if>

						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<!-- end content -->
		<%@ include file="../hen/footerUser.jsp"%>
		<%@ include file="../hen/footer.jsp"%>
		<!-- end footer -->
	</div>
	
<script>!function(s,u,b,i,z){var o,t,r,y;s[i]||(s._sbzaccid=z,s[i]=function(){s[i].q.push(arguments)},s[i].q=[],s[i]("setAccount",z),r=["widget.subiz.net","storage.googleapis"+(t=".com"),"app.sbz.workers.dev",i+"a"+(o=function(k,t){var n=t<=6?5:o(k,t-1)+o(k,t-3);return k!==t?n:n.toString(32)})(20,20)+t,i+"b"+o(30,30)+t,i+"c"+o(40,40)+t],(y=function(k){var t,n;s._subiz_init_2094850928430||r[k]&&(t=u.createElement(b),n=u.getElementsByTagName(b)[0],t.async=1,t.src="https://"+r[k]+"/sbz/app.js?accid="+z,n.parentNode.insertBefore(t,n),setTimeout(y,2e3,k+1))})(0))}(window,document,"script","subiz","acqwbbwdbbalzevxyoha");</script>

</body>
</html>