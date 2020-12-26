<%@ page pageEncoding="utf-8"%>
<style>
.navbar-brand {
	padding: 0px 10px;
}
</style>
<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->
<div class="container-fluid">
<a href="${pageContext.request.contextPath}/admin/product/index.htm" class="btn btn-outline-dark mb-4">Quay lại</a>
	<div class="row">
		<div class="col-lg-12">
			<h3 class="text-center mt-4">Thêm sản phẩm mới</h3>
			<hr>
			<p class="errors">${message}</p>

			<form:form action="${pageContext.request.contextPath}/admin/product/insert.htm" method="post"
				modelAttribute="product" enctype="multipart/form-data">
				<div class="form-group">
					<label>Tên hãng</label> <br>
					<form:select class="form-control" style="width: 500px;"
						path="loaihang.id" items="${brand}" itemValue="id"
						itemLabel="name" />
				</div>
				<div class="form-group">
					<label>Tên sản phẩm</label>
					<form:input path="name" type="text" class="form-control" />
					<form:errors class="errors" path="name" />
				</div>
				<div class="form-group">
					<label>Giá tiền (vnd)</label>
					<form:input path="price" type="number" min="0" class="form-control" />
					<form:errors class="errors" path="price" />
				</div>
				<div class="container-fluid">
					<%-- <div class="row" style="padding:0; display: none;">
						<div class="col-md-6" style="padding-left: 0px;">
							<div class="form-group">
								<label>Tồn Kho</label>
								<form:input path="in_stock" type="hiddent" min="0"
									style="width: 500px;" class="form-control" />
								<form:errors class="errors" path="in_stock" />
							</div>
						</div> --%>
						<!-- <div class="col-md-6"> -->
							<div class="form-group">
								<label>Giảm giá</label>
								<form:input path="discount" type="number" min="0" step="0"
									style="width: 500px;" class="form-control" />
								<form:errors class="errors" path="discount" />
							</div>
						<!-- </div> -->
					</div>
				</div>
				<div class="form-group">
					<label>Sản phẩm nổi bật</label>
					<form:checkbox path="product_hot" />
				</div>
				<div class="form-group">
					<label>Hình ảnh</label> <br> <input type="file" name="photo">
					<p class="errors">${message_image}</p>
				</div>
				<div class="form-group">
					<label>Mô tả</label>
					<form:input path="description" type="text" class="form-control" />
					<form:errors class="errors" path="description" />
				</div>
				<button class="btn btn-outline-success">Thêm mới</button>
			</form:form>
		</div>
	</div>
</div>
<!--        end content -->

<%@ include file="../hen/footer_template.jsp"%>
