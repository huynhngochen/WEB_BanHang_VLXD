<%@ page pageEncoding="utf-8"%>
<style>
.navbar-brand {
padding: 0px 10px;
}
#find {
width: 100%;
    margin-right: 20px;
}
</style>
<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->
<div class="container-fluid">
<a href="${pageContext.request.contextPath}/admin/admin-user.htm" class="btn btn-outline-dark mb-4">Back</a>
        	
	<div class="row">
	
		<form action="${pageContext.request.contextPath}/admin/search.htm" id="find">
						<input autocomplete="off" type="text" name="search"
							placeholder="Bạn muốn tìm gì?">
						<button type="submit">
							<i class="fas fa-search"></i>
						</button>
		</form>
		
		

		<div class="container-fluid" style="margin-bottom: 20px">
			
			<div class="row">
				<div class="d-md-flex mb-3">
					<h3 class="box-title mb-0">Quản lý người dùng</h3>
				</div>
				<table class="table table-bordered table-sm table-hover">
					<thead class="thead-dark text-center">
						<tr>
							<th>Tên đăng nhập</th>
							<th>Mật khẩu</th>
							<th>Họ tên</th>
							<th>Email</th>
							<th>SDT</th>
							<th>Địa chỉ</th>
							<th>Quyền truy cập</th>
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
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<!-- 		end content -->
<%@ include file="../hen/footer_template.jsp"%>
<!-- end footer -->
