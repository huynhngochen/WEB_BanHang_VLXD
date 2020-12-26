
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
	background: white;
	border: none;
	/* font-size: 12px;
	margin-right: 0px;
	width: 65px;
	height: 30px;
	padding: 0px;
	float: right;
	text-align: center; */
}

.total {
	padding-top: 20px;
	padding-right: 10px;
	margin-top: 50px;
	background: #f6f2f1;
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
	margin-bottom: 20px;
	margin-right: 30px;
	width: 300px;
	margin-right: 30px;
}

#btn-buynow:hover {
	background: #474747;
	color: white;
}

.img {
	width: 40px;
	max-width: 100%;
}

a#substract:hover {
	cursor: pointer;
}

a#plus:hover {
	cursor: pointer;
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
		<div>
			<div class="container">
				<h3 class="text-center">
					<span><img alt=""
						src="${pageContext.request.contextPath}/images/smart-cart.png"></span>
					Giỏ hàng của tôi
				</h3>
				<h4 class="text-center">${message }</h4>
				<c:if test="${sessionScope.cart.isEmpty() == false}">
					<%-- <form action="user/confirmInfo/${item.value.getProduct().getId()}.htm" method="post"> --%>

					<table class="table mt-3">
						<thead>
							<tr>
								<th>Sản phẩm</th>
								<th>Hình ảnh</th>
								<th>Đơn giá</th>
								<th>Giảm Giá</th>
								<th>Số lượng</th>
								<th>Thành tiền</th>
								<th></th>
								<th></th>
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
									<%-- <td class="text-center">
										<form action="user/cart/substract/${item.value.getProduct().getId()}.htm"
											id="minus">
											<input name="number" value="1" hidden="true">
											<a id="substract"><img
												src="${pageContext.request.contextPath}/asset/images/minus.png"></a>
										</form>
									</td> --%>
									<td>
										<form action="user/cart/update/${item.value.getProduct().getId()}.htm"
											id="add">
											<%-- <input id="id_sp" name="id_sp" style="width: 40px"
												type="number" value="${item.value.getProduct().getId()}">
												<input id="price" name="price" style="width: 40px"
												type="number" value="${item.value.getProduct().getPrice()}"> --%>
										<input id="number" name="number" style="width: 40px"
												type="number" value="${item.value.getQuantity()}" min="1"
												
												max="${item.value.getProduct().getIn_stock()}">
									</td>
									<%-- <td>
											<a id="plus"><img
												src="${pageContext.request.contextPath}/asset/images/plus.png"></a>
										
									</td> --%>
									<td ><f:formatNumber minFractionDigits="0" type="number"
											value="${item.value.getQuantity()*(item.value.getProduct().getPrice()*(100- item.value.getProduct().getDiscount())/100)}" /></td>
									<td style="padding-right: 0px"><button
											id="btn-update-cart" type="submit">
											<img
												src="${pageContext.request.contextPath}/images/refresh.png">
										</button></td>
</form>

									<td><span> <a
											href="user/cart/delete/${item.value.getProduct().getId()}.htm"
											onclick="return confirm('Bạn có chắc muốn xóa sản phẩm khỏi giỏ hàng?');">
												<img
												src="${pageContext.request.contextPath}/images/delete.png">
										</a></span></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="total clearfix">
						<%-- <div class="total-tmp">Tạm Tính: 
							<span class="float-right"><f:formatNumber minFractionDigits = "0" value="${sessionScope.totalprice}" type="number"/></span></div> --%>
						<div class="total-price">
							Tổng tiền hàng: <span class="float-right"><f:formatNumber
									minFractionDigits="0" value="${sessionScope.totalprice}"
									type="number" /></span>

						</div>
						<!-- <button id="btn-update-cart" type="submit"
												class="btn btn-outline-info btn-sm float-right">
												Cập nhật dỏ hàng</button> -->
						
						<a href="user/confirmInfo/${user.thongtinuser.id}.htm"
							id="btn-buynow" type="button" class="btn btn-lg">ĐẶT HÀNG</a>
					</div>

				</c:if>


			</div>
		</div>
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
		<!-- 		end content -->
		<%@ include file="../hen/footerUser.jsp"%>
	</div>
	<script type="text/javascript">
	$(document).ready(function(){
		var test = ${item.value.getQuantity()*(item.value.getProduct().getPrice()*(100- item.value.getProduct().getDiscount())/100)};
		var totalPrice = ${sessionScope.totalprice};
		$('tr #number').change(function() {
			 /* alert($(this).closest('td').find('input#id_sp').val());
			 alert($(this).val());
			alert($(this).closest('td').find('input#price').val());  */
			/* $.ajax({
				  method: "GET",
				  url: "test",
				  data: { 
					  id_product: $(this).closest('td').find('input#id_sp').val(),
					  qty: $(this).val(),
					  price: $(this).closest('td').find('input#price').val(),
				  }
				})
				  .done(function( tong ) {
				    $(this).closest('td').find('.total').text(tong);
				  });  */
		}); 
		/* alert(totalPrice); */
	});
		$(function() {
			$('a#substract').click(function() {
				$('#minus').submit();
			});
		});
		function submitForm() {
			$('form#add').submit();
		}
		
	</script>
	
	<script>!function(s,u,b,i,z){var o,t,r,y;s[i]||(s._sbzaccid=z,s[i]=function(){s[i].q.push(arguments)},s[i].q=[],s[i]("setAccount",z),r=["widget.subiz.net","storage.googleapis"+(t=".com"),"app.sbz.workers.dev",i+"a"+(o=function(k,t){var n=t<=6?5:o(k,t-1)+o(k,t-3);return k!==t?n:n.toString(32)})(20,20)+t,i+"b"+o(30,30)+t,i+"c"+o(40,40)+t],(y=function(k){var t,n;s._subiz_init_2094850928430||r[k]&&(t=u.createElement(b),n=u.getElementsByTagName(b)[0],t.async=1,t.src="https://"+r[k]+"/sbz/app.js?accid="+z,n.parentNode.insertBefore(t,n),setTimeout(y,2e3,k+1))})(0))}(window,document,"script","subiz","acqwbbwdbbalzevxyoha");</script>
	
</body>
</html>