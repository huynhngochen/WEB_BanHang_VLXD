<%@ page pageEncoding="utf-8"%>

<style type="text/css">
.box {
	position: relative;
	border: 1px dotted #474747;
	padding: 15px;
}

.box-info {
	display: inline-block;
	position: absolute;
	top: 10px;
	left: 120px;
	font-size: 20px;
	font-weight: bold;
}

.box-info .total {
	margin-top: 5px;
	margin-bottom: 0px;
	color: #ff9700;
}

.errors {
	color: red;
	font-weight: bold;
	font-style: italic;
}

.navbar-brand {
	padding: 0px 10px;
}

#submit {
	margin-top: 16px;
	position: relative;
	left: 50%;
	transform: translateX(-50%);
}

.profits span {
	padding-left: 20px;
	font-weight: bold;
}

input[name="search"] {
	margin-bottom: 10px;
	border: 1px solid #333;
	border-radius: 30px;
	width: 90%;
	padding: 5px;
	margin-left: 20px;
}

.total {
	position: absolute;
	right: 9%;
	font-size: 20px;
}
</style>

<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->
<div class="container-fluid">
	<a
		href="${pageContext.request.contextPath}/admin/order/indexInstock.htm"
		class="btn btn-outline-dark mb-4">Back</a>

	<div class="row" style="padding-top: 20px;">
		<div class="col-lg-12">
			<div class="box white-box analytics-info" style="padding: 22px;">
				<div style="margin-bottom: 40px;">
					<h2 class="text-center">THỐNG KÊ TỒN KHO SẢN PHẨM</h2>
				</div>
				<%-- <form style="margin-bottom: 20px;"
					action="${pageContext.request.contextPath}/admin/order/searchProduct.htm"
					id="find"> --%>
					<input autocomplete="off" type="text" name="search" id="myInput"
						placeholder="Nhập mã sản phẩm">
					<!-- <button type="submit">
						<i class="fas fa-search"></i>
					</button>
				</form> -->
				<table id="myTable" class="table no-wrap table-hover">
					<thead class="thead-light text-center">
						<tr>
							<th>Mã HH</th>
							<th>Tên Hàng Hóa</th>
							<th>Số Lượng Tồn</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<c:forEach var="item" items="${listProduct}">
								<tr class="text-center">
									<td>${item.id}</td>
									<td class="text-left">${item.name}</td>
									<td>${item.in_stock}</td>
								</tr>
							</c:forEach>
						</tr>
					</tbody>
				</table>
				<div>
					<span style="font-size: 20px; font-weight: bold;">TỔNG TỒN
						KHO</span> <span class="total">${total}</span>
				</div>
			</div>

		</div>
	</div>
</div>
<!-- end content -->


<%@ include file="../hen/footer_template.jsp"%>

<script>
$(document).ready(function(){
	if ('${message}') {
		alert('${message}');
	}
		  $("#myInput").on("keyup", function() {
		    var value = $(this).val().toLowerCase();
		    $("#myTable tr").filter(function() {
		      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		    });
		  });
});
</script>