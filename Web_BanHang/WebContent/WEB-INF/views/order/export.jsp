<%@ page pageEncoding="utf-8"%>

<title>Xuất Hóa Đơn</title>
<style type="text/css">
#wp-content {
	padding-bottom: 50px;
}

.box-bill {
	padding-bottom: 20px;
}

.box-bill span {
	padding-right: 200px;
}

.export {
	max-width: 80%;
	margin: 0 auto;
	border: 1px solid #333;
	padding: 50px 0px;
}

.box-customer {
	padding-bottom: 20px;
}

.box-customer span {
	padding-right: 30px;
}

.box-cashier {
	padding-bottom: 20px;
}

.box-product {
	padding-bottom: 20px;
	border-bottom: 1px dotted #333;
}

.total {
	padding: 5px 25px 0px 25px;
	font-size: 18px;
	font-weight: bold;
}

.box-btn {
	text-align: right;
}

form {
	display: inline-block;
	margin-right: 20px;
}
.container-fluid .row {
padding: 0;
}
</style>

<%@ include file="../hen/header_template.jsp"%>
<!-- end header -->
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12">
			<div class="row">
				<div class="export">
					<h1 class="text-center">HÓA ĐƠN MUA HÀNG</h1>
					<h3 class="text-center">SHOW ROOM THIẾT VỊ VẬT LIỆU XÂY DỰNG
						TATA</h3>
					<p class="text-center">47 Lã Xuân Oai, Q9</p>
					<hr>
					<div class="container box-bill">
						<span>Ngày: <b><f:formatDate pattern="dd-MM-yyyy"
									value="${export.date}" /></b></span> <span>Mã Hóa đơn: HĐ00<b>${export.id}</b></span>
					</div>
					<div class="box-customer container">
						<span> Khách Hàng: <b>${detail_order[0].dathangs.userid.thongtinuser.fullname}</b>
						</span> <span> SDT: <b>${detail_order[0].dathangs.userid.thongtinuser.sdt}
						</b>
						</span> <span> Email: <b>${detail_order[0].dathangs.userid.thongtinuser.email}
						</b>
						</span> <span> Địa Chỉ: <b>${detail_order[0].dathangs.userid.thongtinuser.address}
						</b>
						</span>
					</div>
					<div class="box-cashier container">
						<span> Nhân Viên: <b>${detail_order[0].dathangs.userid1.thongtinuser.fullname}</b>
						</span>
					</div>
					<div class="box-product container">
						<table class="table">
							<thead>
								<tr>
									<th>Tên sản phẩm</th>
									<th>Đơn giá</th>
									<th>Số lượng</th>
									<th>Thành tiền</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="d" items="${detail_order}">
									<tr>
										<td>${d.hanghoas.name}</td>
										<td>${d.price}</td>
										<td>${d.qty}</td>
										<td style="text-align: right"><f:formatNumber
												value="${d.qty * d.price}" type="number" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>

					<div class="total container-fluid">
						Tổng tiền <span class="float-right"><f:formatNumber
								minFractionDigits="0" value="${order.amount}" type="number" /></span>
					</div>
					<div class="total container">
						VAT <span class="float-right">0</span>
					</div>
					<div class="total container">
						Giảm Giá <span class="float-right">0</span>
					</div>
					<div class="total container">
						TOTAL <span class="float-right"><f:formatNumber
								minFractionDigits="0" value="${order.amount - 0}" type="number" /></span>
					</div>
				</div>

			</div>
			<div class="box-btn container">
				<form action="${pageContext.request.contextPath}/admin/order/export/${order.id}.htm" method="post">
					<button id="create_bill" type="submit"
						class="btn btn-lg btn-outline-primary">Tạo Hoá Đơn</button>
				</form>
				<button id="print" type="button" style="margin-right: 100px;"
					class="btn btn-lg btn-outline-primary">In</button>
			</div>
		</div>
	</div>
</div>
<!-- end content -->
<!-- 		footer -->
<%@ include file="../hen/footer_template.jsp"%>
<!-- 		end	footer -->


<!--  end wrapper -->
<script type="text/javascript">
	$(document).ready(function() {
		var mahd = ${export.id};
		if ('${message}') {
			alert('${message}');
		}
		if ('${error}') {
			alert('${error}');
			window.history.back();
		}
		if (mahd == 0) {
			$('#print').css('display', 'none');
		} else {
			$('#create_bill').css('display', 'none');
		}
		$('#print').click(function() {
			$('#header').css('display', 'none');
			$('.box-btn').css('display', 'none');
			window.print();
			window.history.back();
		});
	});
</script>
