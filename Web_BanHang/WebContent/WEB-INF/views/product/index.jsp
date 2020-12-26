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

.img {
	max-width: 100%;
	width: 100px;
}
.btn-group a {
border: 1px solid rgba(120, 130, 140, 0.13);
}
.btn-group a:hover {
	background: rgba(120, 130, 140, 0.9);
	color: #fff;
	cursor: pointer;
}
</style>
</head>

<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->
<div class="container-fluid">
	<div class="row">
			<div class="col-lg-12">
				<div class="dropdown dropright text-center">
					<button type="button"
						class="btn btn-outline-warning dropdown-toggle"
						data-toggle="dropdown" style="position: absolute; left: 550px;">
						SẢN PHẨM</button>
					<div class="dropdown-menu">
						<c:forEach var="b" items="${brand}">
							<a class="dropdown-item hen2"
								href="${pageContext.request.contextPath}/admin/product/finding/${b.id}.htm"> <b>${b.name}</b>
							</a>
							<hr>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<div class="row" style="padding-bottom: 0; padding-top: 20px;">
			<div class="col-md-6">
				<form action="${pageContext.request.contextPath}/admin/product/search.htm" id="find">
					<input autocomplete="off" type="text" name="search"
						placeholder="Bạn muốn tìm gì?">
					<button type="submit">
						<i class="fas fa-search"></i>
					</button>
				</form>
			</div>
			<div class="col-md-6">
				<a class="btn btn-outline-success mb-4 mt-2"
					href="${pageContext.request.contextPath}/admin/product/insert.htm">Thêm mới</a>
			</div>
		</div>
	<div class="row" style="padding-top: 0;">
		<div class="col-md-12 col-lg-12 col-sm-12">
			<div class="white-box">
				<div class="d-md-flex mb-3">
					<h3 class="box-title mb-0">List Product</h3>
				</div>
				<div class="table-responsive">
					<table class="table no-wrap table-hover">
						<thead class="thead-light text-center">
							<tr>
								<th>STT</th>
								<th>Tên Hàng</th>
								<th>Giá Bán</th>
								<th>Giảm Giá</th>
								<th>Số Lượng Đã Bán</th>
								<th>Tồn Kho</th>
								<th>Sản phẩm nổi bật</th>
								<th>Loại sản phẩm</th>
								<!-- <th>Hình ảnh</th> -->
								<th>Thao tác</th>
								<th>Thao tác</th>
								<th>Thao tác</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="p" items="${product}">
								<tr class="text-center">
									<td>${p.id}</td>
									<td class="text-left">${p.name}</td>
									<td class="text-right"><f:formatNumber minFractionDigits="0"
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
									<td class="text-left">${p.loaihang.name}</td>
									<%-- <td><img
										src="${pageContext.request.contextPath}/images/${p.images_link}"
										class="img"></td> --%>
									<td><a href="${pageContext.request.contextPath}/admin/product/update/${p.id}.htm">Chỉnh
											sửa</a></td>
									<td><a href="${pageContext.request.contextPath}/admin/product/delete/${p.id}.htm"
										onclick="return confirm('Bạn có chắc muốn xóa sản phẩm?');">Xóa</a>

									</td>
									<td><a href="${pageContext.request.contextPath}/admin/product/show/${p.id}.htm">Xem</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>

				<div class="btn-group mt-2">
					<a class="btn"><i class="fa fa-angle-left"></i></a>
					<c:forEach var="i" begin="0" end="${totalitem}">
						<a class="btn"
							href="${pageContext.request.contextPath}/admin/product/index/${i+1}.htm"><c:out
								value="${i+1}" /></a>
					</c:forEach>
					<a class="btn"><i class="fa fa-angle-right"></i></a>
				</div>
			</div>

		</div>
	</div>
</div>

<!-- end content -->

<%@ include file="../hen/footer_template.jsp"%>

<!--  end wrapper -->
<script type="text/javascript">
	$(document).ready(function() {
		if ('${message}') {
			alert('${message}');
			window.history.back();
		}
	});
</script>
