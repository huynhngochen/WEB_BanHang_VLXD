<%@ page pageEncoding="utf-8"%>
<style>
.container-fluid .row {
background: #fff;
padding: 50px 0px;
}
</style>
<%@ include file="../hen/header_template.jsp"%>
        <!-- ==============END HEADER============== -->
			<div class="container-fluid">
			<a id="back"
				class="btn btn-outline-dark mb-4">Quay lại</a>
				<div class="row">
					<div class="col-lg-12 col-md-12">
						<h3 class="text-center">Thông tin khách hàng</h3>
						<div class="box-customer container">
								<p>Họ Tên KH: <b>${detail_order[0].dathangs.userid.thongtinuser.fullname}</b></p>
								<p>SDT:  <b>${detail_order[0].dathangs.userid.thongtinuser.sdt} </b></p>
								<p>Email:  <b>${detail_order[0].dathangs.userid.thongtinuser.email} </b></p>
								<p>Địa Chỉ:  <b>${detail_order[0].dathangs.userid.thongtinuser.address} </b></p>
								<p>Phường:  <b>${detail_order[0].dathangs.userid.thongtinuser.quanphuong.phuongs.name} </b></p>
								<p>Quận:  <b>${detail_order[0].dathangs.userid.thongtinuser.quanphuong.quans.name} </b></p>
						</div>
						<h3 class="text-center mt-3">Thông tin đơn hàng</h3>
						<div class="box-product container">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>Tên sản phẩm</th>
										<th>Số lượng</th>
										<th>Thành tiền</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="d" items="${detail_order}">
										<tr>
											<td>${d.hanghoas.name}</td>
											<td>${d.qty}</td>
											<td><f:formatNumber value="${d.qty * d.price}" type="number" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						
						</div>
					</div>
				</div>
			</div>
		
		<!-- ===============END CONTENT============== -->
<!-- 		footer -->
		<%@ include file="../hen/footer_template.jsp"%>
<!--==============END FOOTER ============== -->

   <!--  end wrapper -->
   <script type="text/javascript">
   		$(document).ready(function(){
   			$('#back').click(function(){
				/* alert('ok'); */
				window.history.back();
			})
   			var role = ${sessionScope.user.getRole().getId()};
   			if(role == 7) {
   				/* $('.logo').css('display','none'); */
   			}
   		});
   </script>
