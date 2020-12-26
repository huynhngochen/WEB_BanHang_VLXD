<style>
i {
font-size: 30px;
    margin-right: 10px;
    }
</style>
<%@ page pageEncoding="utf-8"%>
<div id="header">
	<div class="container-fluid clearfix">
		<div class="row mb-2">
			<div class="col-md-8">
			<div id="icon-menu">
				<img src="${pageContext.request.contextPath}/asset/images/menu.png"
					    width="5%" height="auto" alt="">
			</div>
				<%-- <div class="logo">
					<a href=""> <img
						src="${pageContext.request.contextPath}/asset/images/logo.png"
						height="100px" alt="">
					</a>
				</div> --%>
			</div>
			<div class="col-md-4">
				<%-- <c:if test="${isLogin == true}"> --%>
				<div class="info-user">
					<div class="info-user-icon">
						<i class="fas fa-user-cog"></i>
					</div>
					<div class="info-account">
						<span>XinChao: <a href="admin/info-account.htm"
							style="font-size: 18px !important; font-weight: 700; color: #666666; text-decoration: none">
								${sessionScope.user.getUsername()}</a>
						</span>

					</div>
					<div class="info-user-more">
						<a href="admin/managerAccount.htm" id="account">Tài Khoản</a> <span
							style="padding: 0, 5px;">|</span> <a href="admin/logout.htm"
							onclick="return confirm('Bạn có chắc muốn đăng xuất?');">Đăng
							Xuất</a>
					</div>
				</div>
				<%-- </c:if> --%>
			</div>
		</div>
<!-- 		<div class="row">
			<div class="col-md-12">
				<div class="header-menu">
					<a href="admin/admin-home.htm" class="btn btn-outline-dark"
						id="order"><span><img
							src="asset/images/icon-qly-don-hang.png" width="60px"
							height="50px" alt=""></span>Quản lý đơn hàng</a> 
					<a href="admin/shipper.htm" class="btn btn-outline-dark"
						id="order-shipper"><span>
						<img src="asset/images/icon-qly-don-hang.png" width="60px"
							height="50px" alt=""></span>Quản lý đơn hàng</a> <a
						href="admin/product/index.htm" class="btn btn-outline-dark"
						id="product"><span><img
							src="asset/images/icon-qly-hang-hoa.png" width="60px"
							height="50px" alt=""></span>Quản lý sản phẩm</a> <a
						href="admin/admin-user.htm" class="btn btn-outline-dark"
						id="customer"> <span><img src="asset/images/kh.png"
							width="60px" height="50px" alt=""> </span>Quản lý khách hàng
					</a> <a href="admin/system.htm" class="btn btn-outline-dark"
						id="system"> <span><img
							src="asset/images/icon-he-thong.png" width="60px" height="50px"
							alt=""></span>Quản lý hệ thống
					</a> <a href="admin/order/indexCount.htm" class="btn btn-outline-dark"
						id="sales"><span><img
							src="asset/images/icon-thong-ke.jpg" width="60px" height="50px"
							alt=""></span>Thống kê Doanh Thu</a> </a> 
					<a href="import/index.htm" class="btn btn-outline-dark" id="import"> <span><img
							src="asset/images/import.png" width="60px" height="50px" alt=""></span>Lập
						Phiếu Nhập
					</a> <a href="promotion/index.htm" class="btn btn-outline-dark"
						id="export"><span><img src="asset/images/coupon.png"
							width="60px" height="50px" alt=""></span>Khuyến Mãi</a>
				</div>
			</div>
		</div> -->
	</div>
</div>

<script type="text/javascript">
   		$(document).ready(function(){
   			var role = ${sessionScope.user.getRole().getId()};
   			if(role != 7) {
   				$("#order-shipper").css("display", "none");
   			}
   			if(role == 6) {
   				$("#system").css("display", "none");
   				$("#product").css("display", "none");
   				$("#sales").css("display", "none");
   				
   				$("#export").css("display", "none");
   			}
   			else if (role == 7) {
   				$("#customer").css("display", "none");
   				$("#system").css("display", "none");
   				$("#product").css("display", "none");
   				$("#sales").css("display", "none");
   				$("#import").css("display", "none");
   				$("#export").css("display", "none");
   				$("#account").css("display", "none");
   				$("#find").css("display", "none");
   				$("#order").css("display", "none");
   			}
   		});
   </script>