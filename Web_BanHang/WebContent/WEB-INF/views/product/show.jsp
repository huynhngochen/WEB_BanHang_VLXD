<%@ page pageEncoding="utf-8"%>
<style type="text/css">
.errors {
	color: red;
}

.img {
	height: 300px;
	width: 500px;
}

.name {
	font-size: 18px;
	background: #474747;
	color: #ffffff;
	padding: 5px;
	margin: 10px;
}

.hen {
	font-size: 18px;
	font-weight: bold;
}

.navbar-brand {
	padding: 0px 10px;
}
</style>

<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->
<div class="container-fluid">
	<a href="${pageContext.request.contextPath}/admin/product/index.htm"
		class="btn btn-outline-dark mb-4">Quay lại</a>
	<div class="row">
		<div class="col-lg-12">
			<hr>
			<h3 class="text-center mb-4">Thông tin sản phẩm</h3>
			<div class="row">
				<div class="col-md-6 border-right">
					<img
						src="${pageContext.request.contextPath}/images/${product.images_link}"
						class="img">
				</div>
				<div class="col-md-6">
					<p class="name">${product.name}</p>
					<hr>
					<p class="hen">
						Giá : <span style="padding-left: 20px"> <f:formatNumber
								minFractionDigits="0" value="${product.price}" type="number" />
							đ
						</span>
					</p>
					<p class="hen">Tồn kho: ${product.in_stock }</p>
					<p class="hen">Số lượng đã xuất: ${product.count_buy }</p>
					<p class="hen">Loại sản phẩm: ${product.loaihang.name}</p>
					<p class="hen">Đặc Điểm Nổi Bật</p>
					<p>- ${product.description}</p>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- end content -->
<%@ include file="../hen/footer_template.jsp"%>
