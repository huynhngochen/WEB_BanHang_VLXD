<%@ page pageEncoding="utf-8"%>

<style type="text/css">
.hen1:HOVER {
	color: white;
	background-color: #212529;
}

.hen2:HOVER {
	color: white;
	background-color: green;
}

.img {
	max-width: 100%;
}

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
	width: 300px;
	padding: 5px;
	margin-left: 20px;
}

.total {
	position: absolute;
	right: 40px;
	font-size: 20px;
}
</style>

<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->
<div class="container-fluid">

<a href="${pageContext.request.contextPath}/admin/order/indexSales.htm"
		class="btn btn-outline-dark mb-4">Back</a>
		
	<div class="row" style="padding-top: 20px;">
		<div class="col-lg-12">
			<div class="box white-box analytics-info" style="padding: 22px;">
				<div style="margin-bottom: 40px;">
					<h2 class="show_date">
						THỐNG KÊ DOANH THU SẢN PHẨM TỪ
						<f:formatDate pattern="dd-MM-yyyy" value="${date_start }" />
						ĐẾN
						<f:formatDate pattern="dd-MM-yyyy" value="${date_finish}" />
					</h2>
				</div>
				<table class="table no-wrap table-hover">
					<thead class="thead-light text-center">
						<tr>
							<th>Mã HH</th>
							<th>Tên Hàng Hóa</th>
							<th>Tổng Bán</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${countAll}">
							<tr class="text-center">
								<td>${item.product.id}</td>
								<td class="text-left">${item.product.name}</td>
								<td><f:formatNumber minFractionDigits="0"
										value="${item.total}" type="number" /></td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div>
					<span style="font-size: 20px; font-weight: bold;">TỔNG LỢI
						NHUẬN</span> <span class="total"></span>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- end content -->


<%@ include file="../hen/footer_template.jsp"%>


<script type="text/javascript">
	 $(document).ready(function() {
		var totalPrice = ${total};
	 	$('.total').text(addPeriod(totalPrice));
		if ('${message}') {
			alert('${message}');
		}
		
	}); 
	function addPeriod(nStr) {
        nStr += '';
        x = nStr.split('.');
        x1 = x[0];
        x2 = x.length > 1 ? '.' + x[1] : '';
        var rgx = /(\d+)(\d{3})/;
        while (rgx.test(x1)) {
            x1 = x1.replace(rgx, '$1' + '.' + '$2');
        }
        return x1 + x2;
    }
</script>