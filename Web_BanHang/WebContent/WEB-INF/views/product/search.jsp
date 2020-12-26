<%@ page pageEncoding="utf-8"%>
<style>
.navbar-brand {
	padding: 0px 10px;
}
</style>
<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->
<div class="container_fluid">

<a href="${pageContext.request.contextPath}/admin/product/index.htm" class="btn btn-outline-dark m-2">Back</a>
	
	<div class="container-fluid" style="margin-bottom: 20px">
		
		<div class="row">
			<div class="col-md-12">
				<form action="${pageContext.request.contextPath}/admin/product/search.htm" id="find">
					<input autocomplete="off" type="text" name="search"
						placeholder="Bạn muốn tìm gì?">
					<button type="submit">
						<i class="fas fa-search"></i>
					</button>
				</form>
			</div>
		</div>
		<div class="row" style="padding: 0;">
			<div class="col-md-12">
				<div style="margin-bottom: 20px; margin-left: 10px">
					<h2 class="text-center" style="color: #717670;">
						<b>Kết quả tìm kiếm sản phẩm</b>
						<p>${message}</p>
					</h2>
				</div>
			</div>
		</div>
		<div class="row text-center" style="padding-top: 10px;">
			<c:if test="${product.isEmpty() == false}">
			<h3 class="text-center">Danh mục sản phẩm</h3>
				<table class="table table-bordered table-sm table-hover">
					<thead class="thead-light text-center">
						<tr>
							<th>STT</th>
							<th>Tên Hàng</th>
							<th>Giá Bán</th>
							<th>Giảm Giá</th>
							<th>Số Lượng Đã Bán</th>
							<th>Tồn Kho</th>
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
								<td><a href="${pageContext.request.contextPath}/admin/product/update/${p.id}.htm">Chỉnh
										sửa</a></td>
								<td><a href="${pageContext.request.contextPath}/admin/product/delete/${p.id}.htm">Xóa</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
	</div>
</div>
<!--        end content -->

<%@ include file="../hen/footer_template.jsp"%>