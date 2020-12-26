<%@ page pageEncoding="utf-8"%>
<style>
.navbar-brand {
	padding: 0px 10px;
}
.btn-group a {
border: 1px solid rgba(120, 130, 140, 0.13);
}
.btn-group a:hover {
	background: rgba(120, 130, 140, 0.9);
	color: #fff;
	cursor: pointer;
}
</style>
         <%@ include file="../hen/header_template.jsp"%>
        <!-- end header -->
			<div class="container-fluid">
				<div class="row">
					<div class="col-md-12">
						<h3 class="text-center mb-3">CHƯƠNG TRÌNH KHUYẾN MÃI</h3>
						<a class="btn btn-outline-success float-left mb-4 mt-2" 
							href="${pageContext.request.contextPath}/promotion/insert.htm">Thêm mới</a>
						<form action="${pageContext.request.contextPath}/promotion/search.htm" id="find" style="display: block;">
							<input autocomplete="off" type="text" name="search" placeholder="Nhập mã khuyến mãi!">
							<button type="submit">
								<i class="fas fa-search"></i>
							</button>
						</form>
						<div class="clearfix"></div>
						<hr>
						<table class="table no-wrap table-hover">
							<thead class="thead-light text-center">
								<tr>
									<th>ID</th>
									<th>Tên chương trình KM</th>
									<th>Ngày BD</th>
									<th>Ngày KT</th>
									<th>% Giảm Giá</th>
									<th>Mã Áp Dụng</th>
									<th>Thao tác</th>
									<th>Thao tác</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="pro" items="${promotions}">
									<tr class="text-center">
										<td>${pro.id}</td>
										<td>${pro.name}</td>
										<td><f:formatDate pattern="dd-MM-yyyy"
												value="${pro.date_start}" /></td>
												<td><f:formatDate pattern="dd-MM-yyyy"
												value="${pro.date_finish}" /></td>
										<td>${pro.discount}</td>
										<td>${pro.makm}</td>
										<td><a href="${pageContext.request.contextPath}/promotion/update/${pro.id}.htm">Sửa</a></td>
										<td><a href="${pageContext.request.contextPath}/promotion/delete/${pro.id}.htm" onclick="return confirm('Bạn có chắc muốn xóa chương trình này?');">Xóa</a></td>
									</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		<!-- end content -->
		
		
        <%@ include file="../hen/footer_template.jsp"%>
       <!--  end footer -->
       
       
<script type="text/javascript">
   $(document).ready(function (){
	   if('${message}'){
		  alert('${message}');
		  window.history.back();
	  } 
	   if('${error}'){
			  alert('${error}');
			  window.history.back();
		  } 
   });
   </script>