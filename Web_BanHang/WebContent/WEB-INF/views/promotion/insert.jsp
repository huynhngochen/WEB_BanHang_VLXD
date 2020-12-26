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
			<h3 class="text-center mt-4">Tạo Chương Trình KM</h3>
			<hr>
			<p class="errors">${message}</p>

			<form:form action="${pageContext.request.contextPath}/promotion/insert.htm" method="post"
				modelAttribute="promotions" enctype="multipart/form-data">

				<div class="form-group">
					<label>TênChương Trình KM</label>
					<form:input path="name" type="text" class="form-control" />
					<form:errors class="errors" path="name" />
				</div>
				<div class="form-group">
					<label>Ngày BD</label> <input type="date" name="date_start"
						class="form-control">
					<%-- <form:input path="date_start" type="date" class="form-control" id="datefield" max=""/>
						<form:errors class="errors" path="date_start" /> --%>
				</div>
				<div class="form-group">
					<label>Ngày KT</label> <input type="date" name="date_finish"
						class="form-control">
					<%-- <form:input path="date_finish" type="date" class="form-control" />
						<form:errors class="errors" path="date_finish" / --%>
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
				<button class="btn btn-outline-success">Thêm mới</button>
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