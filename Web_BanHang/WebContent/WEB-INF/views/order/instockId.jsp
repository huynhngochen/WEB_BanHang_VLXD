<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<!DOCTYPE html>

<base href="${pageContext.servletContext.contextPath}/">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/fonts/fontawesome/css/all.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/css/reset.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/css/global.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/css/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/css/adminhome.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/js/Jquery-3.5.1.js">
<link
	href="https://fonts.googleapis.com/css2?family=Quicksand:wght@300;400;500;515;600;700&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/bootstrap/css/bootstrap.min.css">
<script
	src="${pageContext.request.contextPath}/asset/js/Jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath}/asset/js/popper.min.js"></script>
<script
	src="${pageContext.request.contextPath}/asset/bootstrap/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js"></script>
<title>Quản Trị Admin</title>
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
	left: 280px;
}
form {
text-align: right;
}
input {
    width: 40%;
    padding: 10px;
    border-radius: 5px;
}
button.btn {
    padding: 10px;
    margin-left: 30px;
    margin-top: -3px;
    border-radius: 5px;
}
</style>

<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->
<div class="container-fluid">

	<a href="${pageContext.request.contextPath}/admin/order/indexInstock.htm"
		class="btn btn-outline-dark mb-4">Back</a>


	<div class="row" style="padding-top: 10px;">
		<div class="col-md-12">
			<form:form
					action="${pageContext.request.contextPath}/admin/order/instockId.htm"
					method="post">
					<input autocomplete="off" type="text" name="search" required="required"
								placeholder="Nhập mã sản phẩm">

					<button type="submit" class="btn btn-outline-dark">Thống
						kê</button>

				</form:form>
			<h3 class="text-center mt-4">Số Liệu Thống Kê Theo Biểu Đồ</h3>
			<h4 class="text-center">MÃ SP: ${id_hh}</h4>
			<canvas id="myChart" class="charjs" width="250px" height="100px"></canvas>
		</div>
	</div>
</div>
<!-- end content -->
<!-- 		footer -->
<%@ include file="../hen/footer_template.jsp"%>

<!--  end wrapper -->

<script>
$(document).ready(function(){
	
	if('${tongBan}'){
		var totalNhap = ${tongBan};
		var instock = ${instock};
	}
var ctx = document.getElementById('myChart').getContext('2d');
	 var myPieChart = new Chart(ctx, {
	    type: 'doughnut',
	    data: {
	        datasets: [{
	            data: [totalNhap, instock],
	            backgroundColor:["rgb(255, 205, 86)","rgb(54, 162, 235)"],
	        }],
	        labels: [
	            'Tổng SL Bán',
	            'Tồn Kho',
	        ]
	    },
	    options: {}
	});  
});
</script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>