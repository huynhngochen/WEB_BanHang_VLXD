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
#select {
    border: 2px solid #909090;
    padding: 10px 15px;
    border-radius: 4px;
    color: #909090;
}
</style>
<%@ include file="../hen/header_template.jsp"%>
<!-- Container fluid  -->
<!-- ============================================================== -->
<div class="container-fluid">
	<div class="row" style="padding-bottom: 0;">
		<div class="col-md-3">
			<form action="${pageContext.request.contextPath}/admin/order/searchUser.htm" id="find"
				style="display: block;">
				<input autocomplete="off" type="text" name="searchUser" style="width: 90%;"
					placeholder="Nhập mã khách hàng!">
				<button type="submit">
					<i class="fas fa-search"></i>
				</button>
			</form>
		</div>
		<div class="col-md-3">
			<form action="${pageContext.request.contextPath}/admin/order/search.htm" id="find"
				style="display: block;">
				<input autocomplete="off" type="text" name="search" style="width: 90%;"
					placeholder="Nhập mã đơn hàng!">
				<button type="submit">
					<i class="fas fa-search"></i>
				</button>
			</form>
		</div>
		<div class="col-md-6">
			<form action="${pageContext.request.contextPath}/admin/order/searchStatus.htm" id="find-status">
				<c:choose>
					<c:when test="${sessionScope.user.getRole().getId() == 7}">
						<select id="select" name="status">
							<option value="2">Đang xử lý</option>
							<option value="3">Đang giao hàng</option>
							<option value="4">Giao thành công</option>
							<option value="5">Đã hủy</option>
						</select>
					</c:when>
					<c:otherwise>
						<select id="select" name="status">
							<option value="1" id="role">Đặt thành công</option>
							<option value="2">Đang xử lý</option>
							<option value="3">Đang giao hàng</option>
							<option value="4">Giao thành công</option>
							<option value="5">Đã hủy</option>
						</select>
					</c:otherwise>
				</c:choose>
				<button id="filter" type="submit" class="btn btn-outline-dark">Lọc theo
					trạng thái</button>
			</form>
		</div>


	</div>
	<!-- RECENT SALES -->
	<!-- ============================================================== -->
	<div class="row" style="padding-top: 20px;">
		<div class="col-md-12 col-lg-12 col-sm-12">
			<div class="white-box">
				<div class="d-md-flex mb-3">
					<h3 class="box-title mb-0">List Order</h3>
				</div>
				<div class="table-responsive">
					<table class="table no-wrap table-hover">
						<thead class="thead-light text-center">
							<tr>
								<th>ID ĐH</th>
								<th>Ngày Đặt</th>
								<th>Tổng Tiền</th>
								<th>Hình thức TT</th>
								<th>TT Thanh Toán</th>
								<th>Trạng thái ĐH</th>
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
									<td>ĐH ${o.id}</td>
									<td><f:formatDate pattern="dd-MM-yyyy"
											value="${o.created}" /></td>
									<td><f:formatNumber value="${o.amount}" type="number" /></td>

									<c:choose>
										<c:when test="${o.payment == 1}">
											<td>TT khi nhận</td>
										</c:when>
										<c:otherwise>
											<td>TT online</td>
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test="${o.status_payment == false}">
											<td>Chưa TT</td>
										</c:when>
										<c:otherwise>
											<td>Đã TT</td>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${o.status_delivery == 1}">
											<td>ĐH Thành Công</td>
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
									<td>${o.userid2.thongtinuser.fullname}</td>
									<td><a class="click"
										href="${pageContext.request.contextPath}/admin/order/detailOrder/${o.id}.htm">Chi
											Tiết ĐH</a></td>
									<td><a class="click"
										href="${pageContext.request.contextPath}/admin/order/update/${o.id}.htm">Xử
											Lí ĐH</a></td>
									<c:choose>
										<c:when test="${o.status_payment == true}">
											<td><a class="click" href="order/export/${o.id}.htm">In
													Hóa Đơn</a></td>
										</c:when>
										<c:otherwise>
											<td></td>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>
						</tbody>
					</table>


				</div>
				<div class="btn-group">
					<a class="btn"> <i class="fa fa-angle-left"></i>
					</a>
					<c:forEach var="i" begin="0" end="${totalitem}">
						<a class="btn"
							href="${pageContext.request.contextPath}/admin/admin-home/${i+1}.htm">
							<c:out value="${i+1}" />
						</a>
					</c:forEach>
					<a class="btn"> <i class="fa fa-angle-right"></i>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- ============================================================== -->

<%@ include file="../hen/footer_template.jsp"%>

<script>
	$(document).ready(function(){
		
		/* $('select').val(3); */
	});
</script>