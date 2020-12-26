<%@ page pageEncoding="utf-8"%>
<style>
.container-fluid .row {
	background: #fff;
	padding: 50px 0px;
}

ul.info {
	list-style: none;
}

ul.info li a {
	color: #f33155;
	font-size: 18px;
}

ul.info li:hover a {
	color: #000;
}
</style>
<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->
<div class="container-fluid">

	<a id="back" class="btn btn-outline-dark mb-4">Quay lại</a>

	<div class="row">
		<div class="col-lg-12">
			<h4 style="text-align: center;">Chỉnh sửa thông tin cá nhân</h4>
			<hr>
			<p style="color: red">${message}</p>
			<form:form
				action="${pageContext.request.contextPath}/admin/managerAccount-update.htm"
				modelAttribute="infouser" method="post"
				enctype="multipart/form-data">
				<div class="form-group">
					<label>Tên đăng nhập</label>
					<form:input readonly="true" path="id" class="form-control"
						autocomplete="off" style="width: 100%;" required="required"/>
					<form:errors path="id" element="div" style="color: red" />
				</div>
				<div class="form-group">
					<label>Họ và Tên</label>
					<form:input path="fullname" class="form-control" autocomplete="off"
						style="width: 100%;" required="required"/>
					<form:errors path="fullname" element="div" style="color: red" />
				</div>
				<div class="form-group">
					<label>Email</label>
					<form:input path="email" class="form-control" autocomplete="off"
						style="width: 100%;" required="required"/>
					<form:errors path="email" element="div" style="color: red" />
				</div>
				<div class="form-group">
					<label>SDT</label>
					<form:input path="sdt" class="form-control" autocomplete="off"
						style="width: 100%;" required="required"/>
					<form:errors path="sdt" element="div" style="color: red" />
				</div>

				<div class="form-group">
					<label>Địa Chỉ</label>
					<form:input path="address" class="form-control" required="required"
						autocomplete="off" style="width: 100%;" />
					<form:errors path="address" element="div" style="color: red" />
				</div>
				<div class="form-group">
					<label>Phường</label> <select id="phuong" class="form-control"
						name="ward" required="required">
						<option value="">-------SELECT----------</option>
						<c:forEach var="w" items="${ward}">
							<option value="${w.id}">${w.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label>Quận</label> <select id="select" class="form-control"
						name="district" required="required">
						<option value="">-------SELECT----------</option>
						<c:forEach var="d" items="${district}">
							<option value="${d.id}">${d.name}</option>
						</c:forEach>
					</select>
				</div>
				<%-- <div class="form-group">
					<label>Quận</label> <br> <input type="hidden" name="id_quan"
						value="${infouser.quanphuong.quans.id}">
					<form:select id="select" class="form-control"
						path="quanphuong.quans.id" items="${district}" itemValue="id"
						itemLabel="name" />
				</div>
				<div class="form-group">
					<label>Phường</label> <br> <input type="hidden"
						name="id_phuong" value="${infouser.quanphuong.phuongs.id }">
					<select id="phuong" class="form-control">
						<option value="">-------SELECT----------</option>
						<c:forEach var="w" items="${ward}">
							<option value="${w.id}">${w.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label>Địa chỉ</label>
					<form:input path="address" class="form-control" autocomplete="off"
						style="width: 100%;" />
					<form:errors path="address" element="div" style="color: red" />
				</div> --%>

				<button id="btnchangeAccount" type="submit"
					class="btn btn-outline-success float-right">Lưu</button>

			</form:form>
		</div>
	</div>
</div>

<!--         end content -->
<%@ include file="../hen/footer_template.jsp"%>
<script type="text/javascript">
		$(document).ready(function() {
			if('${infouser.quanphuong.quans.id}'){
				  $('#select').val('${infouser.quanphuong.quans.id}');
			}
			if('${infouser.quanphuong.phuongs.id}'){
				 $('#phuong').val('${infouser.quanphuong.phuongs.id}');
			} 
			 if('${message1}'){
				   alert('${message1}');
					  
			   }
			 if('${message}'){
				   alert('${message}');
				   /* window.history.back(); */
			   }
			/* var role = ${user.role.id}; */
			
			$('#back').click(function(){
				window.history.back();
			});
		});
	</script>