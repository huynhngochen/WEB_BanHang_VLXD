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
        <!-- Menu quản lí tài khoản -->
        <div class="container-fluid">
        <a href="${pageContext.request.contextPath}/admin/info-account.htm" class="btn btn-outline-dark mb-4">Back</a>
            <div class="row">
                <div class="col-md-4 border-right p-4">
                    <p>Tài Khoản: 
                    <a href="${pageContext.request.contextPath}/admin/info-account.htm" 
                    	style="font-size: 18px !important;margin-left: 10px;font-weight: 700; color: #FF9800; text-decoration: none">
                    	${sessionScope.user.getThongtinuser().getFullname()}</a></p>
                    <div class="menu-account text-center">
                        <ul class="info">
                            <li><a href="${pageContext.request.contextPath}/admin/info-account.htm">Thông tin tài khoản</a></li>
                            <li><a href="${pageContext.request.contextPath}/admin/logout.htm">Đăng xuất</a></li>
                        </ul>
                    </div>
                </div>
                <!-- end menu quản lí tài khoản -->
                <div class="col-md-8">
                    <h4 style="text-align: center;">Thay đổi mật khẩu</h4>
					<hr>
					<p style="color: red">${message}</p>
					<form action="${pageContext.request.contextPath}/admin/changePassword.htm" method="post">
		            		<div class="form-group">
							<label class="kc">Nhập mật khẩu cũ của bạn</label>
							<br>
							<input name="oldpass" type="password" class="form-control"/>
						</div>
						<div class="form-group">
							<label class="kc">Mật khẩu mới</label>
							<br>
							<input name="newpass" type="password" class="form-control" maxlength="14"/>
						</div>
						<div class="form-group">
							<label class="kc">Xác nhận mật khẩu mới</label>
							<br>
							<input name="renewpass" type="password" class="form-control" maxlength="14"/>
						</div>
						<button class="btn btn-outline-dark float-right ml-4" type="submit">Thay đổi</button>
		            	<a href="${pageContext.request.contextPath}/admin/info-account.htm" class="btn btn-outline-dark float-right">Hủy</a>
		    		</form>
                </div>
            </div>
        </div>

    <!-- end content -->
     <%@ include file="../hen/footer_template.jsp"%>
