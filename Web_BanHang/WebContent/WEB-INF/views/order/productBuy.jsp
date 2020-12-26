<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<!DOCTYPE html>

	<base href="${pageContext.servletContext.contextPath}/">
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/fonts/fontawesome/css/all.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/adminhome.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/js/Jquery-3.5.1.js">
    <link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@300;400;500;515;600;700&display=swap"
        rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/asset/js/Jquery-3.5.1.js"></script>
    <script src="${pageContext.request.contextPath}/asset/js/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/asset/bootstrap/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js"></script>
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
        	
        		<a href="${pageContext.request.contextPath}/admin/order/indexPrice.htm"
		class="btn btn-outline-dark mb-4">Back</a>
        	
        		
        		<div class="row" style="padding-top: 10px;">
					<div class="col-md-12">
						<h3 class="text-center mt-4">Số Liệu Thống Kê Theo Biểu Đồ</h3>
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
	var json = ${jsonText};
	var newPrice = new Array();
	var newDate = new Array();
	console.log(json);
	for(var i =0; i<json.length; i++){
		newDate.push(String(json[i].date));
		newPrice.push(json[i].price);
	}
	console.log(newDate);
	console.log(newPrice);
	
	new Chart(document.getElementById("line-chart"), {
		  type: 'line',
		  data: {
		    labels: newDate,
		    datasets: [{ 
		        data: newPrice,
		        label: "Giá",
		        borderColor: "#3e95cd",
		        fill: false
		      }]
		  },
		  options: {
		    title: {
		      display: true,
		      text: 'Thống kê giá bán của sản phẩm'
		    }
		  }
		});
		
	if ('${message}') {
		alert('${message}');
	}
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