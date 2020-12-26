<%@ page pageEncoding="utf-8"%>
<style>
.navbar-brand {
	padding: 0px 10px;
}
</style>
<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->
<div class="container-fluid">

<a href="${pageContext.request.contextPath}/admin/slider/index.htm" 
				class="btn btn-outline-dark mb-4">Back</a>

	<div class="row text-center">
		<div class="col-lg-12">

			<h3>Thêm slider mới</h3>
			<hr>
			${message}
			<form:form action="${pageContext.request.contextPath}/admin/slider/insert.htm" method="post"
				modelAttribute="slider" enctype="multipart/form-data">
				<div class="form-group">
					<label class="kc">Hình ảnh</label> <br> <input type="file"
						name="photo">
					<p class="errors">${message_image}</p>
				</div>
				<button class="btn btn-outline-success">Thêm mới</button>
			</form:form>
		</div>
	</div>
</div>
<%@ include file="../hen/footer_template.jsp"%>