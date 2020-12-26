<%@ page pageEncoding="utf-8"%>
<style>
.navbar-brand {
padding: 0px 10px;
}
</style>
		<%@ include file="../hen/header_template.jsp"%>
		<!-- end header -->
			<div class="container-fluid">
				<div class="row">
					<div class="col-md-12">
						<h3 class="text-center float-left">Quản lý người dùng</h3>
						<form action="${pageContext.request.contextPath}/admin/search.htm" id="find">
							<input autocomplete="off" type="text" name="search" placeholder="Nhập tài khoản....">
							<button type="submit">
								<i class="fas fa-search"></i>
							</button>
						</form>
						<div class="clearfix"></div>
						<hr>
						<a class="btn btn-outline-success float-right mb-4 mt-2" id="insert-user"
							href="${pageContext.request.contextPath}/admin/managerAccount-insert.htm">Thêm mới</a>
						<table class="table table-bordered table-striped">
							<thead class="thead-light text-center">
								<tr>
									<th>Tên đăng nhập</th>
									<th>Họ tên</th>
									<th>Email</th>
									<th>SDT</th>
									<th>Địa chỉ</th>
									<th>Quyền truy cập</th>
									<th>Thao tác</th>
									<th>Thao tác</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="u" items="${users}">
									<c:if test="${u.role.id == 1}">
										<tr class="text-center">
											<td>${u.username}</td>
											<td>${u.thongtinuser.fullname}</td>
											<td>${u.thongtinuser.email}</td>
											<td>${u.thongtinuser.sdt}</td>
											<td>${u.thongtinuser.address}</td>
											<td>${u.role.name}</td>
											<td><a href="${pageContext.request.contextPath}/admin/managerAccount-update/${u.thongtinuser.id}.htm">Chỉnh sửa thông tin cá nhân</a></td>
											<td><a href="${pageContext.request.contextPath}/admin/managerAccount-delete/${u.thongtinuser.id}.htm"
												onclick="return confirm('Bạn có chắc muốn xóa tài khoản?');">Xóa</a></td>
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>

	<!-- 	end content -->
	<%@ include file="../hen/footer_template.jsp"%>

	<script type="text/javascript">
		$(document).ready(function() {
			var role = ${sessionScope.user.getRole().getId()};
			if ('${message}') {
				alert('${message}');
				window.history.back();
			}
			if(role == 6) {
   				$("a#insert-user").css("display", "none");
   			}
		});
	</script>
	