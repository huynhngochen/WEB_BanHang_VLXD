<%@ page pageEncoding="utf-8"%>

<style>
.box {
	position: relative;
	border: 1px dotted #474747;
	
	text-align: center;
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
.box-title {
margin-bottom: 20px;
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
	left: -84%;
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
#submitAll {
    left: unset;
    position: unset;
    transform: unset;
    display: inline-block;
    margin: 0 auto;
    margin-left: 20px;
}
</style>

<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->
<div class="container-fluid">
	<a href="${pageContext.request.contextPath}/admin/order/indexCount.htm"
		class="btn btn-outline-dark mb-4">Back</a>
	<div class="row" style="padding-bottom: 10px;">
		<div class="col-md-6">
			<div class="box" >
				<h3 class="box-title"
					style="font-size: 16px; font-weight: bold; text-align: center; margin-top: 20px;">THỐNG
					KÊ DOANH THU THEO NGÀY</h3>
				<form:form
					action="${pageContext.request.contextPath}/admin/order/count.htm"
					method="post">
					<b>Từ Ngày</b>
					<input style="width: 175px" type="date" name="date1" id="datefield"
						max="">
					<b>Đến Ngày</b>
					<input type="date" name="date2" id="datefield1" max="">
					<br>
					<button style="left: 9%;" id="submit" type="submit"
						class="btn btn-outline-dark">Thống kê</button>

				</form:form>
				
			</div>
		</div>
		
		<div class="col-md-6">
			<div class="box" style="padding:10px 25px 50px;">
				<h3 class="box-title"
					style="font-size: 16px; font-weight: bold; text-align: center; margin-top: 20px;">THỐNG
					KÊ DOANH THU THEO NĂM</h3>
				<form:form
					action="${pageContext.request.contextPath}/admin/order/countAll.htm"
					method="post">
					<input style="width: 175px;padding: 6px; border-radius: 3px;" 
						type="number" placeholder="Nhập năm thống kê"
						name="year" required="required">
					<button id="submitAll" type="submit"
						class="btn btn-outline-dark">Thống kê</button>
				</form:form>
			</div>
		</div>

	</div>
	<div class="row" style="padding-top: 20px;">
		<div class="col-md-6">
			<div class="box" style="padding: 20px;">
				<h3 class="box-title"
					style="font-size: 16px; font-weight: bold; text-align: center;">THỐNG
					KÊ DOANH THU THEO MÃ SẢN PHẨM</h3>
				<form:form
					action="${pageContext.request.contextPath}/admin/order/countById.htm"
					method="post">
					<input autocomplete="off" type="text" name="search"
						required="required" placeholder="Nhập mã sản phẩm">
					<br>
					<b>Từ Ngày</b>
					<input style="width: 175px" type="date" name="date1"
						id="datefield_byid" max="">
					<b>Đến Ngày</b>
					<input type="date" name="date2" max="" id="datefield1_byid">
					<br>
					<button id="submit" type="submit" style="left: 9%;"
						class="btn btn-outline-dark">Thống kê</button>
				</form:form>

			</div>
		</div>
		<div class="col-md-6">
			<div class="box" style="padding: 20px; padding-bottom: 41px;">
				<h3 class="box-title"
					style="font-size: 16px; font-weight: bold; text-align: center;">THỐNG
					KÊ DOANH THU TẤT CẢ SẢN PHẨM</h3>
				<form:form
					action="${pageContext.request.contextPath}/admin/order/countAllProduct.htm"
					method="post">
					<br>
					<b>Từ Ngày</b>
					<input style="width: 175px" type="date" name="date1"
						id="datefield_all" max="">
					<b>Đến Ngày</b>
					<input type="date" name="date2" max="" id="datefield1_all">
					<br>
					<button id="submit" type="submit" style="left: 23%;"
						class="btn btn-outline-dark">Thống kê doanh thu tất cả sp</button>
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
		function
		addPeriod(nStr) {
			nStr +='' ;
			x=nStr.split( '.');
			x1=x[0];
		x2=x.length>
		1 ? '.' + x[1] : ''; var rgx = /(\d+)(\d{3})/; while (rgx.test(x1)) {
		x1 = x1.replace(rgx, '$1' + '.' + '$2'); } return x1 + x2; }
		</script>