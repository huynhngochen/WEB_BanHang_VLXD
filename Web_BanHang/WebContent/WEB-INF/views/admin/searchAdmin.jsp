<%@ page pageEncoding="utf-8"%>
<style>
.navbar-brand {
	padding: 0 10px;
}
table td a {
color: #f33155;
}
table td a:hover {
color: #000;
text-decoration: underline;
}
</style>
<%@ include file="../hen/header_template.jsp"%>
<hr>
<!-- end header -->
<!-- Menu quản lí tài khoản -->
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12">
			
			<h3 class="text-center">KẾT QUẢ TÌM KIẾM ${message}</h3>
			<div class="row">
				<div class="col-md-6">
					<form
						action="${pageContext.request.contextPath}/admin/searchAdmin.htm"
						id="find">
						<input autocomplete="off" type="text" name="search"
							placeholder="Bạn muốn tìm gì?">
						<button type="submit">
							<i class="fas fa-search"></i>
						</button>
					</form>
				</div>
				<div class="col-md-6">
					<a class="btn btn-outline-success mb-4"
						href="${pageContext.request.contextPath}/admin/managerAccount-insert.htm">Thêm
						mới</a>
				</div>
			</div>
			<table class="table table-bordered table-striped">
				<thead class="thead-dark text-center">
					<tr>
						<th>Tên đăng nhập</th>
						<th>Mật khẩu</th>
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
						<tr class="text-center">
							<td>${u.username}</td>
							<td>${u.password}</td>
							<td>${u.thongtinuser.fullname}</td>
							<td>${u.thongtinuser.email}</td>
							<td>${u.thongtinuser.sdt}</td>
							<td>${u.thongtinuser.address}</td>
							<td>${u.role.name}</td>
							<td><a
								href="${pageContext.request.contextPath}/admin/managerAccount-update/${u.thongtinuser.id}.htm">Chỉnh
									sửa thông tin cá nhân</a></td>
							<td><a
								href="${pageContext.request.contextPath}/admin/managerAccount-delete/${u.thongtinuser.id}.htm">Xóa</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<!-- end content -->
<%@ include file="../hen/footer_template.jsp"%>
