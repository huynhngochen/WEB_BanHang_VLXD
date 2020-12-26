<%@ page pageEncoding="utf-8"%>
<style>
.navbar-brand {
	padding: 0px 10px;
}
select {
    border: 1px solid #333;
    padding: 8px;
    border-radius: 3px;
}
</style>
		<%@ include file="../hen/header_template.jsp"%>
		<!-- end header -->
			<div class="container-fluid">

				<div class="row">
					<div class="col-md-12">

						<h3 class="text-center mb-3">DANH SÁCH ĐƠN HÀNG</h3>
						<form action="${pageContext.request.contextPath}/admin/order/search.htm" id="find"
							style="display: block;">
							<input autocomplete="off" type="text" name="search"
								placeholder="Nhập mã đơn hàng!">
							<button type="submit">
								<i class="fas fa-search"></i>
							</button>
						</form>
						<form action="${pageContext.request.contextPath}/admin/order/searchStatus.htm" id="find-status">
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
						<table class="table no-wrap table-hover">
							<thead class="thead-light text-center">
								<tr>
									<th>ID Đơn Hàng</th>
									<th>Ngày Đặt</th>
									<th>Tổng Tiền</th>
									<th>Hình Thức TT</th>
									<th>Trạng Thái TT</th>
									<th>Trạng thái ĐH</th>
									<th>NV Xử lý</th>
									<th>NV Giao Hàng</th>
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
												<td>TT khi nhận</td>
											</c:when>
											<c:otherwise>
												<td>TT Online</td>
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
										<td><a href="${pageContext.request.contextPath}/admin/order/detailOrder/${o.id}.htm">Chi Tiết ĐH</a></td>
										<td><a href="${pageContext.request.contextPath}/admin/order/update/${o.id}.htm">Xử Lí
												ĐH</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="btn-group">
							<a class="btn"><i class="fa fa-angle-left"></i></a>
							<c:forEach var="i" begin="0" end="${totalitem}">
								<a class="btn"
									href="${pageContext.request.contextPath}/admin/shipper/${i+1}.htm"><c:out
										value="${i+1}" /></a>
							</c:forEach>
							<a class="btn"><i class="fa fa-angle-right"></i></a>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		<!-- end content -->

		<%@ include file="../hen/footer_template.jsp"%>

	<!--  end wrapper -->
	
	<script type="text/javascript">
		$(document).ready(function(){
			 $('#icon-menu').css('visibility', 'hidden');
			 /* var status = ${order.status_delivery}; */
	   		
		});
	</script>
</body>
</html>