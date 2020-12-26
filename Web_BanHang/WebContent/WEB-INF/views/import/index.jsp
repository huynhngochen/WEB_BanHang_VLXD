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
					<div class="col-lg-12">
						<a id="insert" class="btn btn-outline-success float-left mb-4 mt-2" 
							href="${pageContext.request.contextPath}/import/insert.htm">Thêm mới</a>
						<form action="${pageContext.request.contextPath}/import/search.htm" id="find" style="display: block;">
							<input autocomplete="off" type="text" name="search" placeholder="Nhập mã phiếu nhập!">
							<button type="submit">
								<i class="fas fa-search"></i>
							</button>
						</form>
						<div class="clearfix"></div>
						<hr>
						<div class="d-md-flex mb-3">
								<h3 class="box-title mb-0">List Import</h3>
							</div>
						<table class="table no-wrap table-hover">
							<thead class="thead-light text-center">
								<tr>
									<th>ID Phiếu Nhập</th>
									<th>Ngày Nhập Hàng</th>
									<th>Tổng tiền</th>
									<th>Nhà Cung Cấp</th>
									<th>Nhân Viên</th>
									<th>Thao tác</th>
									<th>Thao tác</th>
									<th class="edit">Thao tác</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="ip" items="${imports}">
									<tr class="text-center">
										<td>PN ${ip.id}</td>
										<td><f:formatDate pattern="dd-MM-yyyy"
												value="${ip.date}" /></td>
										<td><f:formatNumber value="${ip.amount}" type="number" /></td>
										<td>${ip.nhacungcap.name }</td>
										<td>${ip.userid.thongtinuser.fullname }</td>
										<td><a href="${pageContext.request.contextPath}/import/detailImport/${ip.id}.htm">Xem</a></td>
										<td><a href="${pageContext.request.contextPath}/import/confirm/${ip.id}.htm">Nhập hàng</a></td>
										<c:choose>
											<c:when test="${ip.status_import == 0}">
												<td class="edit"><a href="${pageContext.request.contextPath}/import/update/${ip.id}.htm">Sửa</a></td>
											</c:when>
											<c:otherwise>
												<td class="edit"></td>
											</c:otherwise>
										</c:choose>
									</tr>
							</c:forEach>
							</tbody>
						</table>
						<div class="btn-group">
							<a class="btn"><i class="fa fa-angle-left"></i></a>
							<c:forEach var="i" begin="0" end="${totalitem}">
							<a class="btn"
								href="${pageContext.request.contextPath}/import/index/${i+1}.htm"><c:out
									value="${i+1}" /></a>
						</c:forEach>
							<a class="btn"><i class="fa fa-angle-right"></i></a>
						</div>
					</div>
				</div>
			</div>
		<!-- end content -->
		
		
        <%@ include file="../hen/footer_template.jsp"%>
       <!--  end footer -->
       
       
       <script type="text/javascript">
   		$(document).ready(function(){
   			var role = ${sessionScope.user.getRole().getId()};
   			if(role == 6) {
   				$("#insert").css("display", "none");
   				$(".edit").css("display", "none");
   			}
   		});
   </script>
   