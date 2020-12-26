<%@ page pageEncoding="utf-8"%>
<style>
.img {
	width: 100px;
	height: 100px;
}
</style>
<%@ include file="../hen/header_template.jsp"%>

<div class="container-fluid text-center">
	<div class="row">
		<div class="col-lg-12">

			<h3>Quản lí danh mục hàng hóa</h3>
			<hr>
			<a class="btn btn-outline-success float-right mb-4"
				href="${pageContext.request.contextPath}/admin/brand/insert.htm">Thêm mới</a>
			<table class="table no-wrap table-hover">
				<thead class="thead-light text-center">
					<tr>
						<th>Mã loại hàng</th>
						<th>Tên danh mục</th>
						<th>Hình ảnh</th>
						<th>Thao tác</th>
						<th>Thao tác</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="b" items="${brand}">
						<tr class="text-center">
							<td>${b.id}</td>
							<td>${b.name}</td>
							<td><img
								src="${pageContext.request.contextPath}/images/${b.images}"
								class="img"></td>
							<td><a href="${pageContext.request.contextPath}/admin/brand/update/${b.id}.htm">Chỉnh Sửa</a></td>
							<td><a href="${pageContext.request.contextPath}/admin/brand/delete/${b.id}.htm"
								onclick="return confirm('Bạn có chắc muốn xóa danh mục?');">Xóa</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<!--  end content -->
<%@ include file="../hen/footer_template.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		if ('${message}') {
			alert('${message}');
			window.history.back();
		}
	});
</script>