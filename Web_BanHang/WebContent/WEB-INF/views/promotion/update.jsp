<%@ page pageEncoding="utf-8"%>
<style>
.navbar-brand {
	padding: 0px 10px;
}
</style>

<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->
<div class="container-fluid">

<a href="${pageContext.request.contextPath}/promotion/index.htm" 
class="btn btn-outline-dark mb-4">Quay lại</a>

	<div class="row">
		<div class="col-lg-12">

			<h3 class=" text-center">Chỉnh sửa chương trình KM</h3>
			<hr>
			<p class="errors">${message}</p>
			<form:form action="${pageContext.request.contextPath}/promotion/update.htm" method="post"
				modelAttribute="promotions" enctype="multipart/form-data">
				<div class="form-group">
					<label>ID Khuyến mãi</label> <br>
					<form:input readonly="true" path="id" type="text"
						class="form-control" />
				</div>
				<div class="form-group">
					<label>Tên Chương Trình KM</label>
					<form:input path="name" type="text" class="form-control" />
					<form:errors class="errors" path="name" />
				</div>
				<div class="form-group">
					<label>Ngày BD</label> <input type="date" name="date1"
						value="${promotions.date_start}" class="form-control">
					<form:input hidden="true" path="date_start" type="date"
						class="form-control" />
				</div>
				<div class="form-group">
					<label>Ngày KT</label> <input type="date" name="date2"
						value="${promotions.date_finish}" class="form-control">
					<form:input hidden="true" path="date_finish" type="date"
						class="form-control" />
				</div>
				<div class="form-group">
					<label>% Giảm Giá</label>
					<form:input path="discount" type="number" min="0" max="100"
						class="form-control" />
					<form:errors class="errors" path="discount" />
				</div>
				<div class="form-group">
					<label>Mã Áp Dụng</label>
					<form:input path="makm" type="text" class="form-control" />
					<form:errors class="errors" path="makm" />
				</div>
				<button class="btn btn-outline-success">Cập nhật</button>
			</form:form>
			<hr>
		</div>
	</div>
</div>
<!--        end content -->

<%@ include file="../hen/footer_template.jsp"%>

<script type="text/javascript">
		if ('${message}') {
			alert('${message}');
		}
	</script>