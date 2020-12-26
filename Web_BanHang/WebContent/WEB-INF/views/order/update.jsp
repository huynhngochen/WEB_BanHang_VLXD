<%@ page pageEncoding="utf-8"%>
<style>
.container-fluid .row {
	background: #fff;
	padding: 50px 0px;
}
</style>
<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->
<div class="container-fluid">
	<a id="back" class="btn btn-outline-dark mb-4">Quay lại</a>
	<div class="row">
		<div class="col-lg-12">
			<h3 class=" text-center">Chỉnh sửa đơn hàng</h3>
			<hr>
			<%-- <p class="errors">${message}</p> --%>
			<form:form
				action="${pageContext.request.contextPath}/admin/order/update.htm"
				method="post" modelAttribute="order" enctype="multipart/form-data">
				<div class="form-group">
					<label>ID Đơn hàng</label> <br>
					<form:input readonly="true" path="id" type="text"
						class="form-control" style="width: 500px;" />
				</div>
				<c:if test="${order.status_delivery == 1}">
					<c:choose>
						<c:when test="${sessionScope.user.getRole().getId() == 6}">
							<div class="form-group">
								<label>Trạng thái đơn hàng</label> <select name="status"
									class="form-control">
									<option value="1">Đặt thành công</option>
									<option value="2">Đang xử lý</option>
									<option disabled="disabled" value="3">Đang giao hàng</option>
									<option disabled="disabled" value="4">Giao thành công</option>
									<option class="cancel" value="5">Đã hủy</option>
								</select>
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<label>Trạng thái đơn hàng</label> <select name="status"
									class="form-control">
									<option value="1">Đặt thành công</option>
									<option value="2">Đang xử lý</option>
									<option value="3">Đang giao hàng</option>
									<option value="4">Giao thành công</option>
									<option class="cancel" value="5">Đã hủy</option>
								</select>
							</div>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${order.status_delivery == 2}">
					<c:choose>
						<c:when test="${sessionScope.user.getRole().getId() == 6}">
							<div class="form-group">
								<label>Trạng thái đơn hàng</label> <select name="status"
									class="form-control">
									<option disabled="disabled" value="1">Đặt thành công</option>
									<option value="2">Đang xử lý</option>
									<option disabled="disabled" disabled="disabled" value="3">Đang
										giao hàng</option>
									<option disabled="disabled" disabled="disabled" value="4">Giao
										thành công</option>
									<option class="cancel" value="5">Đã hủy</option>
								</select>
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<label>Trạng thái đơn hàng</label> <select name="status"
									class="form-control">
									<option disabled="disabled" value="1">Đặt thành công</option>
									<option value="2">Đang xử lý</option>
									<option value="3">Đang giao hàng</option>
									<option value="4">Giao thành công</option>
									<option class="cancel" value="5">Đã hủy</option>
								</select> 
							</div>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${order.status_delivery == 3}">
					<c:choose>
						<c:when test="${sessionScope.user.getRole().getId() == 6}">
							<div class="form-group">
								<label>Trạng thái đơn hàng</label> <select name="status"
									class="form-control">
									<option disabled="disabled" value="1">Đặt thành công</option>
									<option disabled="disabled" value="2">Đang xử lý</option>
									<option disabled="disabled" value="3">Đang giao hàng</option>
									<option disabled="disabled" value="4">Giao thành công</option>
									<option class="cancel" disabled="disabled" value="5">Đã hủy</option>
								</select>
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<label>Trạng thái đơn hàng</label> <select name="status"
									class="form-control">
									<option disabled="disabled" value="1">Đặt thành công</option>
									<option disabled="disabled" value="2">Đang xử lý</option>
									<option value="3">Đang giao hàng</option>
									<option value="4">Giao thành công</option>
									<option class="cancel" value="5">Đã hủy</option>
								</select>
							</div>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${order.status_delivery == 4}">
					<c:choose>
						<c:when test="${sessionScope.user.getRole().getId() == 6}">
							<div class="form-group">
								<label>Trạng thái đơn hàng</label> <select name="status"
									class="form-control">
									<option disabled="disabled" value="1">Đặt thành công</option>
									<option disabled="disabled" value="2">Đang xử lý</option>
									<option disabled="disabled" value="3">Đang giao hàng</option>
									<option disabled="disabled" value="4">Giao thành công</option>
									<option class="cancel" disabled="disabled" value="5">Đã hủy</option>
								</select>
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<label>Trạng thái đơn hàng</label> <select name="status"
									class="form-control">
									<option disabled="disabled" value="1">Đặt thành công</option>
									<option disabled="disabled" value="2">Đang xử lý</option>
									<option disabled="disabled" value="3">Đang giao hàng</option>
									<option value="4">Giao thành công</option>
									<option class="cancel" disabled="disabled" value="5">Đã hủy</option>
								</select>
							</div>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${order.status_delivery == 5}">
					<div class="form-group">
						<label>Trạng thái đơn hàng</label> <select name="status"
							class="form-control">
							<option disabled="disabled" value="1">Đặt thành công</option>
							<option disabled="disabled" value="2">Đang xử lý</option>
							<option disabled="disabled" value="3">Đang giao hàng</option>
							<option disabled="disabled" value="4">Giao thành công</option>
							<option class="cancel" value="5">Đã hủy</option>
						</select>
					</div>
				</c:if>
				<c:choose>
					<c:when test="${order.payment == 1}">
					</c:when>
					<c:otherwise>
						<c:if test="${order.status_payment == false}">
							<div class="form-group">
								<label>Trạng thái thanh toán</label> <select
									name="status_payment" class="form-control">
									<option value="false">Chưa thanh toán</option>
									<option value="true">Đã thanh toán</option>
								</select>
							</div>
						</c:if>
						<c:if test="${order.status_payment == true}">
							<div class="form-group">
								<label>Trạng thái thanh toán</label> <select
									name="status_payment" class="form-control">
									<option disabled="disabled" value="false">Chưa thanh
										toán</option>
									<option value="true">Đã thanh toán</option>
								</select>
							</div>
						</c:if>
					</c:otherwise>
				</c:choose>
				<div class="form-group">
					<label>Hình thức thanh toán</label>
					<c:if test="${order.payment == 1}">
						<input value="Thanh toan khi nhận hàng" type="text"
							class="form-control" readonly="readonly">
						<form:input path="payment" type="text" class="form-control d-none" />
					</c:if>
					<c:if test="${order.payment == 2}">
						<input value="Chuyển khoản" type="text" class="form-control" readonly="readonly">
						<form:input path="payment" type="text" class="form-control d-none" />
					</c:if>
				</div>
				<div class="form-group">
					<label>Tổng tiền (vnd)</label>
					<form:input path="amount" type="number" min="0"
						class="form-control" />
					<form:errors class="errors" path="amount" />
				</div>
				<div class="form-group d-none">
					<label>Id Khách hàng</label>
					<form:input path="userid.username" type="text" class="form-control" />
					<form:errors class="errors" path="userid.username" />
				</div>
				<div class="form-group">
					<label>Địa chỉ giao hàng</label>
					<form:input readonly="true" path="userid.thongtinuser.address"
						type="text" class="form-control" />
				</div>
				<div class="form-group">
					<label>Phường</label> <input readonly="true" type="text"
						value="${order.userid.thongtinuser.quanphuong.phuongs.name }"
						class="form-control" />
				</div>
				<div class="form-group">
					<label>Quận</label>
					<form:input readonly="true"
						path="userid.thongtinuser.quanphuong.quans.name" type="text"
						class="form-control" />
				</div>
				<div class="form-group">
					<label>Thành Phố</label>
					<form:input readonly="true"
						path="userid.thongtinuser.quanphuong.quans.city.name" type="text"
						class="form-control" />
				</div>
				<div class="form-group d-none">
					<label>Ngày đặt hàng</label>
					<form:input path="created" class="form-control" />
					<form:errors class="errors" path="created" />
				</div>
				<div class="form-group" >
					<label class="shipper">Nhân viên giao hàng</label> <br> <select id="shipper"
						class="form-control">
						<option value="">-------SELECT----------</option>
						<c:forEach var="u" items="${shipper}">
							<c:if
								test="${u.districts.id == order.userid.thongtinuser.quanphuong.quans.id}">
								<option value="${u.userIDs.username}">${u.userIDs.thongtinuser.fullname}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
				<form:input path="userid2.username" value="" type="hidden"
					class="form-control" />
				<div class="form-group">
					<label>Ghi chú thêm</label>
					<form:input path="message" type="text" class="form-control" />
					<form:errors class="errors" path="message" />
				</div>
				<button id="update" class="btn btn-outline-success">Cập nhật</button>

			</form:form>
			<hr>
		</div>
	</div>
</div>


<%@ include file="../hen/footer_template.jsp"%>
<!--  end footer -->

<script type="text/javascript">
		$(document).ready(function() {
			if ('${message}') {
				alert('${message}');
				window.history.back();
			}
			var status_payment = ${order.status_payment}
			var status = ${order.status_delivery}
			if( status == 5){
				$('#update').css("display", "none");
			}
			if(status_payment == true){
				$('option.cancel').attr('disabled','disabled');
			}
			$('#back').click(function(){
				/* alert('ok'); */
				window.history.back();
			})
			var role = ${sessionScope.user.getRole().getId()};
   			if(role == 7) {
   				$("#employee").css("display", "none");
   				$("#shipper").css("display", "none");
   				$('.shipper').css("display", "none");
   					/* $('.logo').css('display','none'); */
   			}
   			$('select#shipper').change(function(){
   		 		/* alert($('select#shipper').val());  */
   		 		$('input[id="userid2.username"]').val($('select#shipper').val());
   		 	});
		});
	</script>
