<%@ page pageEncoding="utf-8"%>
<style>
.img {
	width: 80%;
	height: auto;
}
</style>
<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->
<div class="container-fluid text-center">
	<div class="row">
		<div class="col-lg-12">
			<h3>Quản lí danh mục slider</h3>
			<a class="btn btn-outline-success float-right mb-4"
				href="${pageContext.request.contextPath}/admin/slider/insert.htm">Thêm mới</a>
			<table class="table no-wrap table-hover">
				<thead class="thead-light text-center">
					<tr>
						<th>Mã quảng cáo</th>
						<th>Hình ảnh</th>
						<th>Thao tác</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="s" items="${slider}">
						<tr class="text-center">
							<td width="150px">${s.id}</td>
							<td><img
								src="${pageContext.request.contextPath}/images/${s.image}"
								class="img"></td>
							<td width="92px"><a href="${pageContext.request.contextPath}/admin/slider/delete/${s.id}.htm"
								onclick="return confirm('Bạn có chắc muốn xóa Slider?');">Xóa</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<%@ include file="../hen/footer_template.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		if ('${message}') {
			alert('${message}');
			window.history.back();
		}
	});
</script>