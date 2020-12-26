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
</head>
<body>
<div id="wrapper">
		<%@ include file="../hen/headerUser.jsp"%>
		<hr>

		<!-- end header -->
		<div class="wp=content" style="margin-bottom: 30px;">
			<h3 class="text-center">
				<span><img alt=""
					src="${pageContext.request.contextPath}/images/smart-cart.png"></span>
				Xác nhận giỏ hàng
			</h3>
			<hr>
			<div class="container">
				<a href="user/managerAccount.htm" class="errors">${message1}</a>
				<h4>${message}</h4>
			</div>
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<c:if test="${sessionScope.cart.isEmpty() == false}">
							<table class="table ">
								<thead>
									<tr>
										<th>Sản phẩm</th>
										<th>Hình ảnh</th>
										<th>Đơn giá</th>
										<th>Giảm Giá</th>
										<th>Số lượng</th>
										<th>Thành tiền</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="item" items="${sessionScope.cart}">
										<tr>
											<td>${item.value.getProduct().getName()}</td>
											<td><img class="img"
												src="${pageContext.request.contextPath}/images/${item.value.getProduct().getImages_link()}">
											</td>
											<td class="text-center"><f:formatNumber
													minFractionDigits="0"
													value="${item.value.getProduct().getPrice()}" type="number" /></td>
											<td>${item.value.getProduct().getDiscount()}%</td>
											<td class="text-center">${item.value.getQuantity()}<%-- <form
												action="user/cart/update/${item.value.getProduct().getId() }.htm">
												<input name="number" style="width: 40px" type="number"
													value="${item.value.getQuantity()}">
												<button id="btn-update-cart" type="submit"
													class="btn btn-outline-info btn-sm float-right">Cập
													Nhật</button>
											</form> --%>
											</td>
											<td><f:formatNumber minFractionDigits="0"
													value="${item.value.getQuantity() * (item.value.getProduct().getPrice()*(100- item.value.getProduct().getDiscount()) / 100)}"
													type="number" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<div class="col-md-12">
								<div class="total clearfix">
									<div class="total-price float-right">
										Tổng tiền hàng: <span><f:formatNumber
												minFractionDigits="0" value="${sessionScope.totalprice}"
												type="number" /></span>
									</div>
								</div>
							</div>
					</div>
				</div>
					<div class="col-md-12 mt-4">
					<div class="payment clearfix">
						<form:form action="user/buy.htm" modelAttribute="order"
							method="post">
							<input type="radio" name="payment" checked="checked" value="online" id="online">
							<label>Thanh toán chuyển khoản</label> 
							<input type="radio" name="payment"  value="offline" id="offline"> 
							<label>Thanh toán khi nhận hàng</label>
							<div class="info-payment">
								<p>Chủ tài khoản: <b>Huỳnh Ngọc Hẹn</b></p>
								<p>STK: <b>123456789</b></p>
								<p>Chi nhánh ngân hàng:<b>Techcombank Quận 9</b></p>
							</div>
							<div class="form-group mt-3" style="display: inline-block;">
								<label>Ghi chú thêm</label> <input class="form-control"
									style="display: inline-block; width: 500px; height: 60px"
									type="text" name="message">
							</div>
							<button id="btn-buynow" type="submit"
								class="btn btn-lg float-right">ĐẶT HÀNG</button>

						</form:form>
					</div>
				</div>
				</c:if>
			</div>
			<!-- 			end xác nhận giỏ hàng -->
		</div>

		<%@ include file="../hen/hen.jsp"%>
		<!-- 		end content -->
		<%@ include file="../hen/footerUser.jsp"%>
	</div>
</body>
</html>