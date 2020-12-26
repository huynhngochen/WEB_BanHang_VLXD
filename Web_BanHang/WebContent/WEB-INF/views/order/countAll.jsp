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
</style>

<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->
<div class="container-fluid">

	<a href="${pageContext.request.contextPath}/admin/order/indexSales.htm"
		class="btn btn-outline-dark mb-4">Back</a>

	<div class="row text-center">
		<div class="col-md-6">
			<div class="box" style="padding-bottom: 30px;">
			<h3 class="box-title"
					style="font-size: 16px; font-weight: bold; text-align: center; margin-top: 20px;">THỐNG
					KÊ DOANH THU THEO NĂM</h3>
				<form:form style="padding-top: 20px;"
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
	<div class="row" style="padding-top: 10px;">
		<div class="col-md-12">
			<h3 class="text-center mt-4">Số Liệu Thống Kê Năm ${year}</h3>
			<canvas id="line-chart" width="500" height="200"></canvas>
		</div>
	</div>
</div>
<!-- end content -->
<!-- 		footer -->
<%@ include file="../hen/footer_template.jsp"%>

<!--  end wrapper -->

<script>
$(document).ready(function(){
	
	if('${message}') {
		alert('${message}');
	}
	var json = ${reportAll};
	var newSum = new Array();
	var newMonth = new Array();
	console.log(json);
	for(var i =0; i<json.length; i++){
		newMonth.push(String(json[i].month));
		newSum.push(json[i].sum);
	}
	console.log(newMonth);
	console.log(newSum);
new Chart(document.getElementById("line-chart"), {
	  type: 'bar',
	  data: {
	    labels: newMonth,
	    datasets: [{ 
	        data: newSum,
	        label: "Tổng Thu", 
	        "backgroundColor":["rgba(255, 99, 132, 0.2)","rgba(255, 159, 64, 0.2)","rgba(255, 205, 86, 0.2)","rgba(75, 192, 192, 0.2)","rgba(54, 162, 235, 0.2)","rgba(153, 102, 255, 0.2)","rgba(201, 203, 207, 0.2)"],"borderColor":["rgb(255, 99, 132)","rgb(255, 159, 64)","rgb(255, 205, 86)","rgb(75, 192, 192)","rgb(54, 162, 235)","rgb(153, 102, 255)","rgb(201, 203, 207)"],
	        "borderWidth":1,
	        fill: false 
	      }]
	  },
	  options: {
	    title: {
	      display: true,
	      text: 'Thống kê doanh thu bán hàng theo năm'
	    }
	  }
	});
var today = new Date();
var dd = today.getDate();
var mm = today.getMonth()+1; //January is 0!
var yyyy = today.getFullYear();
 if(dd<10){
        dd='0'+dd
    } 
    if(mm<10){
        mm='0'+mm
    } 

today = yyyy+'-'+mm+'-'+dd;
document.getElementById("datefield").setAttribute("max", today);
document.getElementById("datefield1").setAttribute("max", today);
});


</script>