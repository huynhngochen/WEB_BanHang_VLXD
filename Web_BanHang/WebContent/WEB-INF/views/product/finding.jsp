<%@ page pageEncoding="utf-8"%>

<style type="text/css">
.hen1:HOVER {
	color: white;
	background-color: #212529;
}

.hen2:HOVER {
	color: white;
	background-color: green;
}
</style>

<%@ include file="../hen/header_template.jsp"%>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12">

			<!-- end header -->
			<h3 class="text-center">Danh mục sản phẩm</h3>
			<div class="dropdown dropright text-center">
				<button type="button"
					class="btn btn-outline-warning dropdown-toggle"
					data-toggle="dropdown">SẢN PHẨM</button>
				<div class="dropdown-menu float-right">
					<c:forEach var="b" items="${brand}">
						<a class="dropdown-item hen2"
							href="${pageContext.request.contextPath}/admin/product/finding/${b.id}.htm"> <b>${b.name}</b>
						</a>
						<hr>
					</c:forEach>
				</div>
			</div>
			<div style="margin-top: 30px; margin-bottom: 20px; margin-left: 10px">
				<h2 style="color: #717670;">
					<b>Kết quả tìm kiếm hãng ${product[0].loaihang.name}</b>
				</h2>
			</div>

			<div class="container-fluid" style="margin-bottom: 20px">
				<div class="row">
					<table class="table table-bordered table-sm table-hover">
						<thead class="thead-dark text-center">
							<tr>
								<th>STT</th>
								<th>Tên Hàng</th>
								<th>Giá Bán</th>
								<th>Giảm Giá</th>
								<th>Số Lượng Đã Bán</th>
								<th>Tồn Kho</th>
								<th>Sản phẩm nổi bật</th>
								<th>Thao tác</th>
								<th>Thao tác</th>
								<th>Thao tác</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="p" items="${product}">
								<tr class="text-center">
									<td>${p.id}</td>
									<td>${p.name}</td>
									<td><f:formatNumber minFractionDigits="0"
											value="${p.price}" type="number" /></td>
									<td>${p.discount}</td>
									<td>${p.count_buy}</td>
									<td>${p.in_stock}</td>
									<c:if test="${p.product_hot == true}">
										<td><input readonly="readonly" type="checkbox"
											checked="checked"></td>
									</c:if>
									<c:if test="${p.product_hot == false}">
										<td><input readonly="readonly" type="checkbox"></td>
									</c:if>
									<td><a href="${pageContext.request.contextPath}/admin/product/update/${p.id}.htm">Chỉnh
											sửa</a></td>
									<td><a href="${pageContext.request.contextPath}/admin/product/delete/${p.id}.htm">Xóa</a></td>
									<td><a href="${pageContext.request.contextPath}/admin/product/show/${p.id}.htm">Xem trước</a></td>
								</tr>

							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<!--        end content -->

<%@ include file="../hen/footer_template.jsp"%>
