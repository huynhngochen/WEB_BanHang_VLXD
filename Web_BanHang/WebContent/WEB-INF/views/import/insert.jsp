<%@ page pageEncoding="utf-8"%>
<style>
.navbar-brand {
	padding: 0px 10px;
}
</style>

<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->
<div class="container-fluid">

<a href="${pageContext.request.contextPath}/import/index.htm" class="btn btn-outline-dark mb-4">Quay lại</a>

	<div class="row">
		<div class="col-lg-12">

			<h3 class="text-center mt-4">Lập Phiếu Nhập</h3>
			<hr>
			<p class="errors">${message}</p>
			<form action="${pageContext.request.contextPath}/import/searchProduct.htm" id="find"
				style="display: block;">
				<input autocomplete="off" type="text" name="search"
					placeholder="Nhập mã hàng hóa!">
				<button type="submit">
					<i class="fas fa-search"></i>
				</button>
			</form>
			<div class="clearfix"></div>
			<br>
			<hr>
			<table class="table table-bordered table-sm table-hover">
				<thead class="text-center">
					<tr>
						<th>Mã sản phẩm</th>
						<th>Tên sản phẩm</th>
						<th>Tồn kho</th>
						<th>Số lượng nhập</th>
						<th>Giá nhập</th>
						<th>Thao tác</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="product" items="${product}">
						<form action="${pageContext.request.contextPath}/import/insert/${product.id}.htm" method="post">
							<tr class="text-center">
								<td>${product.id}</td>
								<td>${product.name}</td>
								<td>${product.in_stock}</td>
								<td><input type="number" name="qty" /></td>
								<td><input type="number" name="price" /></td>
								<td><input type="submit" value="Chọn"></td>
							</tr>
						</form>
					</c:forEach>
				</tbody>
			</table>

			<hr>
			<h3 class="text-center mt-4">Chi Tiết Phiếu Nhập</h3>
			<table class="table table-bordered table-sm table-hover">
				<thead class="text-center">
					<tr>
						<th>Mã sản phẩm</th>
						<th>Tên sản phẩm</th>
						<th>Số lượng nhập</th>
						<th>Giá nhập</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${sessionScope.importList}">
						<tr class="text-center">
							<td>${item.value.getProduct().getId()}</td>
							<td>${item.value.getProduct().getName()}</td>
							<td>${item.value.getQuantity()}</td>
							<td>${item.value.getPrice()}</td>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<form:form action="${pageContext.request.contextPath}/import/insert.htm" method="post"
				class="form-submit" modelAttribute="imports"
				enctype="multipart/form-data">
				<div class="form-group">
					<label>Chọn nhà cung cấp</label> <br>
					<form:select path="nhacungcap.id" class="form-control">
						<form:option value="">-------SELECT----------</form:option>
						<form:options items="${provider}" itemValue="id" itemLabel="name" />
					</form:select>
					<%-- <form:select class="form-control" path="nhacungcap.id"
							items="${provider}" itemValue="id" itemLabel="name"/> --%>
				</div>
				<button class="btn btn-outline-success text-center">Thêm
					mới</button>
			</form:form>
		</div>
	</div>
</div>
<!--        end content -->

<%@ include file="../hen/footer_template.jsp"%>
