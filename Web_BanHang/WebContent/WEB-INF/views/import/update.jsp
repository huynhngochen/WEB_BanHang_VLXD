<%@ page pageEncoding="utf-8"%>
<style>
.navbar-brand {
	padding: 0px 10px;
}

.submit {
	text-align: center;
	cursor: pointer;
}
</style>

<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->

<div class="container-fluid">

	<a href="${pageContext.request.contextPath}/import/index.htm"
		class="btn btn-outline-dark mb-4">Quay lại</a>

	<div class="row">
		<div class="col-lg-12">
			<div id="form-add">
				<form action="${pageContext.request.contextPath}/import/test.htm" id="find"
				style="display: block;">
				<input type="hidden" value="${id_phieunhap}" name="id_phieunhap">
				<input autocomplete="off" type="text" name="search"
					placeholder="Nhập mã hàng hóa!" style="margin-bottom: 20px;">
				<button type="submit">
					<i class="fas fa-search"></i>
				</button>
			</form>
			<table class="table table-bordered table-sm table-hover">
				<thead class="text-center">
					<tr>
						<th>Mã sản phẩm</th>
						<th>Tên sản phẩm</th>
						<th>Tồn kho</th>
						<th>Số lượng nhập</th>
						<th>Giá nhập</th>
						<th>Thao tác</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="product" items="${product}">
						<form action="${pageContext.request.contextPath}/import/add/${product.id}.htm" method="post">
							<tr class="text-center">
								<td>${product.id}</td>
								<td>${product.name}</td>
								<td>${product.in_stock}</td>
								<td><input type="number" name="qty" /></td>
								<td><input type="number" name="price" /></td>
								<td><input type="submit" value="Chọn"></td>
								<td><input type="hidden" value="${id_phieunhap}" name="id_phieunhap"></td>
							</tr>
						</form>
					</c:forEach>
				</tbody>
			</table>
			</div>
			<button id="add" class="btn btn-outline-success">Thêm sản phẩm</button>
			<h3 class="text-center mt-4">Cập Nhật Phiếu Nhập</h3>
			<hr>
			<%-- <p class="errors">${message}</p> --%>
			
			<table class="table table-bordered table-sm table-hover">
				<thead class="text-center">
					<tr>
						<th>Mã sản phẩm</th>
						<th>Tên sản phẩm</th>
						<th>Tồn kho</th>
						<th>Số lượng nhập</th>
						<th>Giá nhập</th>
						<th>Thao tác</th>
					</tr>
				</thead>
				<tbody>
					<%
						int i = 1;
					%>
					<c:forEach var="detail" items="${detail}">
						<form id="update" method="post"
							action="${pageContext.request.contextPath}/import/update/${id_phieunhap}.htm">
						<tr>
							<td><%=i %></td>
							<td>${detail.hanghoas.id}</td>
							<td>${detail.hanghoas.name}</td>
							<td>
							<input type="hidden" name="hanghoaid" value="${detail.hanghoas.id }">
										<input name="number"
										style="width: 40px" type="number" value="${detail.qty}"
										min="1">
							</td>
							<td><input name="price" style="width: 100px" type="number"
								value="${detail.price}" min="0"></td>
							<td class="submit" style="padding-right: 0px">
								
								<button id="btn-update-cart" type="submit">
									<i class="fas fa-sync"></i>
								</button>
							</td>
							
						</tr>
						<%i++; %>
						</form>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!--        end content -->
	</div>
</div>
<%@ include file="../hen/footer_template.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		if('${message}'){
			   alert('${message}');
		}
		$('#form-add').hide();
		/* $('.submit').click(function() {
			 alert($('input[name="hanghoaid"]').val());
			$('form#update').submit();
		}); */
		$('#add').click(function(){
			$('#form-add').show();
		});
		if ('${active}') {
			$('#form-add').show();
		}
	});
</script>