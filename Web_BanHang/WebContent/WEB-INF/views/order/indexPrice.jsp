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
</style>

<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->
<div class="container-fluid">
<a href="${pageContext.request.contextPath}/admin/order/indexCount.htm"
		class="btn btn-outline-dark mb-4">Back</a>
	<div class="row" >
		<div class="col-md-6">
			<div class="box" style="padding-top: 20px; padding-bottom: 50px;">
				<h3 class="box-title"
					style="font-size: 16px; font-weight: bold; text-align: center;">THỐNG
					KÊ GIÁ BÁN</h3>

				<form:form
					action="${pageContext.request.contextPath}/admin/order/productBuy.htm"
					method="post">
					<input autocomplete="off" type="text" name="search"
						required="required" placeholder="Nhập mã sản phẩm">
					<br>
					<b>Từ Ngày</b>
					<input style="width: 150px" type="date" name="date1"
						id="datefield_buy" max="">
					<b>Đến Ngày</b>
					<input style="width: 150px" type="date" name="date2"
						id="datefield1_buy" max="">
					<br>
					<button id="submit" type="submit"
						style="margin-top: -32px; left: 90%;" class="btn btn-outline-dark">Thống
						kê</button>

				</form:form>
				<p class="errors"></p>
			</div>
		</div>
		<div class="col-md-6">
			<div class="box" style="padding-top: 20px; padding-bottom: 50px;">
				<h3 class="box-title"
					style="font-size: 16px; font-weight: bold; text-align: center;">THỐNG
					KÊ GIÁ NHẬP</h3>

				<form:form
					action="${pageContext.request.contextPath}/admin/order/productImport.htm"
					method="post">
					<input autocomplete="off" type="text" name="search"
						required="required" placeholder="Nhập mã sản phẩm">
					<br>
					<b>Từ Ngày</b>
					<input style="width: 150px" type="date" name="date1"
						id="datefield_import" max="">
					<b>Đến Ngày</b>
					<input style="width: 150px" type="date" name="date2"
						id="datefield1_import" max="">
					<br>
					<button id="submit" type="submit"
						style="margin-top: -32px; left: 90%;" class="btn btn-outline-dark">Thống
						kê</button>

				</form:form>
				<p class="errors"></p>
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
		document.getElementById("datefield_buy").setAttribute("max", today);
		document.getElementById("datefield1_buy").setAttribute("max", today);
		document.getElementById("datefield_import").setAttribute("max", today);
		document.getElementById("datefield1_import").setAttribute("max", today);
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