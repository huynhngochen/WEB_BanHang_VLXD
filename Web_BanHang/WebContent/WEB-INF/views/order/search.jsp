<%@ page pageEncoding="utf-8"%>
<style>
.btn-group a {
	border: 1px solid rgba(120, 130, 140, 0.13);
}

.btn-group a:hover {
	background: rgba(120, 130, 140, 0.9);
	color: #fff;
	cursor: pointer;
}

.click {
	color: #f33155;
}

.click:hover {
	cursor: pointer;
	color: #000;
}

#filter {
	padding: 9px 15px;
	border-radius: 4px;
	border: 2px solid #909090;
	color: #909090;
	margin-top: -3px;
}

.select {
	border: 2px solid #909090;
	padding: 10px 15px;
	border-radius: 4px;
	color: #909090;
}
</style>
<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->
<div class="container-fluid">
	<h3 class="text-center mb-3">KẾT QUẢ TÌM KIẾM ${message}</h3>
	<div class="row">
		<div class="col-md-6">
			<form
				action="${pageContext.request.contextPath}/admin/order/search.htm"
				id="find">
				<input autocomplete="off" type="text" name="search"
					placeholder="Nhập mã đơn hàng!">
				<button type="submit">
					<i class="fas fa-search"></i>
				</button>
			</form>
		</div>
		<div class="col-md-6">
			<form
				action="${pageContext.request.contextPath}/admin/order/searchUser.htm"
				id="find" style="display: block;">
				<input autocomplete="off" type="text" name="searchUser"
					placeholder="Nhập mã khách hàng!">
				<button type="submit">
					<i class="fas fa-search"></i>
				</button>
			</form>
		</div>
	</div>
	<div class="row">
		<table class="table no-wrap table-hover">
			<thead class="thead-light text-center">
				<tr>
					<th>ID Đơn Hàng</th>
					<th>Ngày Đặt</th>
					<th>Tổng Tiền</th>
					<th>Hình Thức Thanh Toán</th>
					<th>Trạng Thái Thanh Toán</th>
					<th>Trạng thái đơn hàng</th>
					<th>Nhân Viên Tiếp Nhận</th>
					<th>Thao tác</th>
					<th>Thao tác</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="o" items="${order}">
					<tr class="text-center">
						<td>DH ${o.id}</td>
						<td><f:formatDate pattern="dd-MM-yyyy" value="${o.created}" /></td>
						<td><f:formatNumber value="${o.amount}" type="number" /></td>
						<c:choose>
							<c:when test="${o.payment == 1}">
								<td>Thanh toán khi nhận hàng</td>
							</c:when>
							<c:otherwise>
								<td>Chuyển khoản</td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${o.status_payment == false}">
								<td>Chưa Thanh Toán</td>
							</c:when>
							<c:otherwise>
								<td>Đã Thanh Toán</td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${o.status_delivery == 1}">
								<td>Đặt Hàng Thành Công</td>
							</c:when>
							<c:when test="${o.status_delivery == 2}">
								<td>Đang Xử Lý</td>
							</c:when>
							<c:when test="${o.status_delivery == 3}">
								<td>Đang Giao Hàng</td>
							</c:when>
							<c:when test="${o.status_delivery == 4}">
								<td>Giao Thành Công</td>
							</c:when>
							<c:otherwise>
								<td>Đã Hủy</td>
							</c:otherwise>
						</c:choose>
						<%-- <td>${o.statusdelivery.name}</td> --%>
						<td>${o.userid1.thongtinuser.fullname}</td>
						<td><a
							href="${pageContext.request.contextPath}/admin/order/detailOrder/${o.id}.htm">Chi
								Tiết ĐH</a></td>
						<td><a
							href="${pageContext.request.contextPath}/admin/order/update/${o.id}.htm">Xử
								Lí ĐH</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<!-- end content -->
<!-- 		footer -->
<%@ include file="../hen/footer_template.jsp"%>
<!-- 		end	footer -->


<script>
	$(document).ready(function() {
		if ('${message}') {
			$('.table').css('display', 'none');
		}
	});
</script>