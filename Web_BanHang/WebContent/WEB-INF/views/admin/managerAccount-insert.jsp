<%@ page pageEncoding="utf-8"%>
<style>
.navbar-brand {
padding: 0 10px;
}
</style>
<%@ include file="../hen/header_template.jsp"%>

<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12">
		<a id="back" href="" class="btn btn-outline-dark mb-4">Quay lại</a>
			<h3 class="text-center">Thêm tài khoản mới</h3>
			<hr>
			<p class="errors">${message}</p>
			<form:form action="${pageContext.request.contextPath}/admin/managerAccount-insert.htm" method="post"
				modelAttribute="user" enctype="multipart/form-data">
				<div class="form-group">
					<label>Tên đăng nhập</label>
					<form:input path="username" class="form-control" autocomplete="off" />
					<%-- <form:input path="username" type="text" class="form-control"  autocomplete="off"/> --%>
					<form:errors class="errors" path="username" />
				</div>
				<div class="form-group">
					<label>Mật khẩu</label>
					<form:input path="password" type="password" class="form-control" />
					<form:errors class="errors" path="password" />
				</div>
				<div class="form-group">
					<label>Quyền truy cập</label>
					<form:select class="form-control" path="role.id" items="${role}"
						itemValue="id" itemLabel="name" />
				</div>
				<button type="submit" class="btn btn-outline-success float-right">Thêm
					mới</button>
			</form:form>
		</div>
	</div>
</div>
<%@ include file="../hen/footer_template.jsp"%>


<script>
	$(document).ready(function(){
		$('#back').click(function(){
			window.history.back();
		});
	});
</script>