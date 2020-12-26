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
        <!-- Menu quản lí tài khoản -->
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-4 border-right p-4">
                    <p>Tài Khoản: 
                    <a href="${pageContext.request.contextPath}/admin/info-account.htm" 
                    	style="font-size: 18px !important;margin-left: 10px;font-weight: 700; color: #f33155; text-decoration: none">
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
                    <div class="account p-4">
                        <%-- <p>Tên Đăng Nhập: <strong>${sessionScope.user.getUsername()}</strong></p>
                        <p>Mật Khẩu: <strong>${sessionScope.user.getPassword()}</strong></p> --%>
                        <p>Họ và Tên: <strong>${sessionScope.user.getThongtinuser().getFullname()}</strong></p>
                        <p>Email: <strong>${sessionScope.user.getThongtinuser().getEmail()} </strong></p>
                        <p>SDT: <strong>${sessionScope.user.getThongtinuser().getSdt()}</strong></p>
                        <p>Địa Chỉ: <strong>${sessionScope.user.getThongtinuser().getAddress()}</strong></p>
                        <p>Phường:  <b>${sessionScope.user.getThongtinuser().getQuanphuong().getPhuongs().getName()} </b></p>
								<p>Quận:  <b>${sessionScope.user.getThongtinuser().getQuanphuong().getQuans().getName()} </b></p>
                     	<a id="btn-changePassword" href="${pageContext.request.contextPath}/admin/changePassword.htm" type="button" class="btn btn-dark text-light">Thay đổi mật khẩu</a>
                        <a id="btn-re-account" href="${pageContext.request.contextPath}/admin/managerAccount-update/${sessionScope.user.getThongtinuser().getId()}.htm" type="button" class="btn btn-dark text-light">Chỉnh sửa thông tin cá nhân</a>
                </div>
                </div>
            </div>
        </div>

    
    <!-- end content -->
    <%@ include file="../hen/footer_template.jsp"%>
