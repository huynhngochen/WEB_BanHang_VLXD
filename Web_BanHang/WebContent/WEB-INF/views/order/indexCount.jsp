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

form#countUser {
	display: flex;
	justify-content: center;
	align-items: center;
}

input[name="search"] {
	margin-bottom: 10px;
	border: 1px solid #333;
	border-radius: 30px;
	width: 300px;
	padding: 7px;
	margin-left: 20px;
}

button#submit {
	margin-left: 30px;
	border-radius: 5px;
	margin-top: -10px;
}
a.link {
	font-weight: bold;
}
a.link:hover {
	color: #f33155;
}
a.link:nth-child(odd){
	display: inline-block;
	margin-top: 30px;	
	
}
a.link:nth-child(even) {
	display: inline-block;
    margin-left: 50px;
}
</style>

<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->
<div class="container-fluid">
	<div class="row" style="padding-bottom: 20px;">
		<div class="col-md-6">
			<div class="box white-box analytics-info"
				style="padding: 27px 35px 0;">
				<h3 class="box-title">DOANH THU HÔM NAY</h3>
				<ul class="list-inline two-part d-flex align-items-center mb-0">
					<li>
						<div id="sparklinedash">
							<canvas width="67" height="30"
								style="display: inline-block; width: 67px; height: 30px; vertical-align: top;"></canvas>
						</div>
					</li>
					<li class="ml-auto"><span class="counter text-success"><f:formatNumber
								minFractionDigits="0" value="${count}" type="number" /></span></li>
				</ul>

				<a class="link" href="${pageContext.request.contextPath}/admin/order/indexSales.htm">
					THỐNG KÊ DOANH THU SẢN PHẨM </a> 
				<a class="link" href="${pageContext.request.contextPath}/admin/order/indexPrice.htm">
					THỐNG KÊ GIÁ SẢN PHẨM </a> 
				<a class="link" href="${pageContext.request.contextPath}/admin/order/indexInstock.htm">
					THỐNG KÊ TỒN KHO SẢN PHẨM </a> 
				<a class="link" href="${pageContext.request.contextPath}/admin/order/indexProfit.htm">
					THỐNG KÊ LỢI NHUẬN SẢN PHẨM </a>
			</div>
		</div>
		<div class="col-md-6">
			<div class="box" style="padding:48px 0;">
				<h3 class="box-title"
					style="font-size: 16px; font-weight: bold; text-align: center; margin-bottom: 20px">
					THỐNG KÊ TỔNG ĐƠN HÀNG, GIÁ TRỊ MUA CỦA KHÁCH</h3>

				<form:form id="countUser"
					action="${pageContext.request.contextPath}/admin/order/countSumId.htm"
					method="post">
					<input autocomplete="off" type="text" name="search"
						required="required" placeholder="Nhập số điện thoại khách hàng">
					<br>
					<button id="submit" type="submit" class="btn btn-outline-dark">Thống
						kê</button>

				</form:form>
			</div>
		</div>
	</div>
</div>
<!-- end content -->


<%@ include file="../hen/footer_template.jsp"%>


<script type="text/javascript">
		$(document).ready(function() {

			if ('${message}') {
				alert('${message}');
			}
			var income = $
			{
				tongnhap
			}
			;
			var exports = $
			{
				tongxuat
			}
			;

			var total = income - exports;
			$('.income').text(addPeriod(income));
			$('.export').text(addPeriod(exports));
			$('.total').text(addPeriod(total));

			if ('${result}') {
				alert('${result}');
			}
			$('select').change(function() {
				$('#in_stock').text($('select').val());
			});
		});

		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth() + 1; //January is 0!
		var yyyy = today.getFullYear();
		if (dd < 10) {
			dd = '0' + dd
		}
		if (mm < 10) {
			mm = '0' + mm
		}

		today = yyyy + '-' + mm + '-' + dd;
		document.getElementById("datefield").setAttribute("max", today);
		document.getElementById("datefield1").setAttribute("max", today);
		document.getElementById("datefield_byid").setAttribute("max", today);
		document.getElementById("datefield1_byid").setAttribute("max", today);
		document.getElementById("datefield_all").setAttribute("max", today);
		document.getElementById("datefield1_all").setAttribute("max", today);
		
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