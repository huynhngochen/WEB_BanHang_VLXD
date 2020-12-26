<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<base href="${pageContext.servletContext.contextPath}/">
  	<title>QLBH</title>
  	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./asset/fonts/fontawesome/css/all.css">
    <link rel="stylesheet" href="./asset/css/reset.css">
    <link rel="stylesheet" href="./asset/css/global.css">
    <link rel="stylesheet" href="./asset/css/adminhome.css">

    <link rel="stylesheet" href="./asset/js/Jquery-3.5.1.js">
    <link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@300;400;500;515;600;700&display=swap"
        rel="stylesheet">
    <link rel="stylesheet" href="./asset/bootstrap/css/bootstrap.min.css">
    <script src="./asset/js/Jquery-3.5.1.js"></script>
    <script src="./asset/js/popper.min.js"></script>
    <script src="./asset/bootstrap/js/bootstrap.min.js"></script>	
</head>
<body>
        <div id="wp-content" class="mt-4">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<h3 class="text-center">Danh mục sản phẩm</h3>
						<table class="table table-bordered table-striped">
							<thead class="thead-dark text-center">
								<tr>
									<th>Username</th>
									<th>Password</th>
									<th>Fullname</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="u" items="${users}">
									<tr>
										<td>${u.username}</td>
										<td>${u.password}</td> 
										<td>${u.fullname}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		
		<script type="text/javascript">
		$(document).ready(function(){
			var role = ${sessionScope.user.getRole().getId()};
		});
	</script>
</body>
</html>

