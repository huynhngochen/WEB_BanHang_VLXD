




<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.servletContext.contextPath}/">
<meta charset="utf-8">
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
	href="${pageContext.request.contextPath}/asset/css/adminhome.css">

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
<title>Quản Trị Admin</title>
<style type="text/css">
.hen1:HOVER {
	color: white;
	background-color: #212529;
}

.hen2:HOVER {
	color: white;
	background-color: green;
}

.img {
	max-width: 100%;
	width: 100px;
}

form#find input {
	float: left;
	width: 248px;
	padding: 9px 42px 9px 15px;
	color: #909090;
    border-radius: 50px;
    opacity: 0.8;
    outline: none;
    border: 1px solid #909090;
}

form#find button {
	position: absolute;
	top: 0px;
	left: 205px;
	background: none;
	border: none;
	color: #a2a1a1;
	padding: 9px 13px;
}

#find-status select {
	padding: 10px;
	width: 248px;
	color: #909090;
	border-radius: 10px;
    opacity: 0.8;
}

.table .thead-dark th {
background: grey;
    border-color: #b4afaf;
}
</style>
</head>
<body>
	<div id="wrapper">
		<%@ include file="../hen/nav.jsp"%>
		<!-- end nav -->
		<div id="wp-content" class="mt-4">
		<%@ include file="../hen/header.jsp"%>
		<!-- end header -->
			<div class="container-fluid">

				<div class="row">
					<div class="col-md-12">

						<h3 class="text-center mb-3">DANH SÁCH ĐƠN HÀNG</h3>
						<form action="admin/order/search.htm" id="find"
							style="display: block;">
							<input autocomplete="off" type="text" name="search"
								placeholder="Nhập mã đơn hàng!">
							<button type="submit">
								<i class="fas fa-search"></i>
							</button>
						</form>
						<form action="admin/order/searchStatus.htm" id="find-status">
							<c:choose>
								<c:when test="${sessionScope.user.getRole().getId() == 7}">
									<select name="status">
										<option value="2">Đang xử lý</option>
										<option value="3">Đang giao hàng</option>
										<option value="4">Giao thành công</option>
										<option value="5">Đã hủy</option>
									</select>
								</c:when>
								<c:otherwise>
									<select name="status">
										<option value="1" id="role">Đặt thành công</option>
										<option value="2">Đang xử lý</option>
										<option value="3">Đang giao hàng</option>
										<option value="4">Giao thành công</option>
										<option value="5">Đã hủy</option>
									</select>
								</c:otherwise>
							</c:choose>
							<button type="submit" class="btn btn-outline-dark">Lọc
								theo trạng thái</button>
						</form>
						<div class="clearfix"></div>
						<hr>
						<table class="table table-bordered table-sm table-hover">
							<thead class="thead-light text-center">
								<tr>
									<th>ID Đơn Hàng</th>
									<th>Ngày Đặt</th>
									<th>Tổng Tiền</th>
									<th>Hình Thức Thanh Toán</th>
									<th>TT Thanh Toán</th>
									<th>Trạng thái đơn hàng</th>
									<th>NV Tiếp Nhận</th>
									<th>NV Giao Hàng</th>
									<th>Thao tác</th>
									<th>Thao tác</th>
									<th>Thao tác</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="o" items="${order}">
									<tr class="text-center">
										<td>DH ${o.id}</td>
										<td><f:formatDate pattern="dd-MM-yyyy"
												value="${o.created}" /></td>
										<td><f:formatNumber value="${o.amount}" type="number" /></td>

										<c:choose>
											<c:when test="${o.payment == 1}">
												<th>Thanh toán khi nhận hàng</th>
											</c:when>
											<c:otherwise>
												<th>Chuyển khoản</th>
											</c:otherwise>
										</c:choose>

										<c:choose>
											<c:when test="${o.status_payment == false}">
												<th>Chưa Thanh Toán</th>
											</c:when>
											<c:otherwise>
												<th>Đã Thanh Toán</th>
											</c:otherwise>
										</c:choose>
										<c:choose>
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
										<%-- <td>${o.statusdelivery.name}</td> --%>
										<td>${o.userid1.thongtinuser.fullname}</td>
										<td>${o.userid2.thongtinuser.fullname}</td>
										<td><a href="admin/order/detailOrder/${o.id}.htm">Xem
												Chi Tiết ĐH</a></td>
										<td><a href="admin/order/update/${o.id}.htm">Xử Lí
												Đơn Hàng</a></td>
										<c:choose>
											<c:when test="${o.status_payment == true}">
												<td><a href="admin/order/export/${o.id}.htm">In Hóa Đơn</a></td>
											</c:when>
											<c:otherwise>
												<td></td>
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
									href="${pageContext.request.contextPath}/admin/admin-home/${i+1}.htm"><c:out
										value="${i+1}" /></a>
							</c:forEach>
							<a class="btn btn-default"><i class="fa fa-angle-right"></i></a>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- end content -->

		<%@ include file="../hen/footer.jsp"%>
	</div>
	<!--  end wrapper -->

</body>
</html>