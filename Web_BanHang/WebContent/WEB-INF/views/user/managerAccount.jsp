<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/fonts/fontawesome/css/all.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/css/reset.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/css/global.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/css/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/css/styleManagerAccount.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/js/Jquery-3.5.1.js">
    <link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@300;400;500;515;600;700&display=swap"
        rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/asset/js/Jquery-3.5.1.js"></script>
    <script src="${pageContext.request.contextPath}/sset/js/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/asset/bootstrap/js/bootstrap.min.js"></script>
    <title>Manager Account</title>
    <base href="${pageContext.servletContext.contextPath}/">
</head>
<body>
<script type="text/javascript">
	$(document).ready(function(){
		$("#btn-refresh").click(function(){ 
			location.reload(true);
			}); 
	});
</script>
<div id="wrapper">
       <%@ include file="../hen/headerUser.jsp"%>
    <hr>
    <!-- end header -->
    <div id="wp-content">
        <!-- Menu quản lí tài khoản -->
        <div class="container">
            <div class="row">
                <div class="col-md-4 border-right p-4">
                    <p>Xin Chào : 
                    <a href="user/managerAccount.htm" 
                    	style="font-size: 18px !important;margin-left: 10px;font-weight: 700; color: #FF9800; text-decoration: none">
                    	${sessionScope.user.getUsername()}</a></p>
                    <div class="menu-account text-center">
						<ul>
							<li><a href="user/managerAccount.htm"><span class="mr-3">
										<img
										src="${pageContext.request.contextPath}/images/account.png">
								</span> Thông tin tài khoản</a></li>
							<li><a href="user/mycart.htm"><span
									style="margin-right: 32px;"> <img
										src="${pageContext.request.contextPath}/images/supermarketmini.png"></span>
									Quản lý đơn hàng</a></li>
							<li><a href="user/logout.htm"
								class="float-right"><span style="margin-left: 62px;"
									class="float-left"> <img
										src="${pageContext.request.contextPath}/images/logout.png"></span>
									Đăng xuất</a></li>
						</ul>
					</div>
                </div>
                <!-- end menu quản lí tài khoản -->
                <div class="col-md-8">
                    <div class="account p-4">
                         <p>Họ và Tên: <strong>${user.thongtinuser.fullname}</strong></p>
                        <p>Email: <strong>${user.thongtinuser.email} </strong></p>
                        <p>SDT: <strong>${user.thongtinuser.sdt}</strong></p>
                        <p>Địa Chỉ: <strong>${user.thongtinuser.address}</strong></p>
                        <p>Phường: <strong>${user.thongtinuser.quanphuong.phuongs.name}</strong></p>
                         <p>Quận: <strong>${user.thongtinuser.quanphuong.quans.name}</strong></p>
                     	<a id="btn-changePassword" href="user/changePassword.htm" type="button" class="btn btn-dark text-light">Thay đổi mật khẩu</a>
                        <a id="btn-re-account" href="user/changeAccount/${user.thongtinuser.id}.htm" type="button" class="btn btn-dark text-light">Chỉnh sửa thông tin cá nhân</a>
                        <!-- <button class="brn btn-dark" id="btn-refresh">REFRESH</button> -->
                </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- end content -->

    <%@ include file="../hen/footerUser.jsp"%>

    <!-- end footer -->

</div>

<script>!function(s,u,b,i,z){var o,t,r,y;s[i]||(s._sbzaccid=z,s[i]=function(){s[i].q.push(arguments)},s[i].q=[],s[i]("setAccount",z),r=["widget.subiz.net","storage.googleapis"+(t=".com"),"app.sbz.workers.dev",i+"a"+(o=function(k,t){var n=t<=6?5:o(k,t-1)+o(k,t-3);return k!==t?n:n.toString(32)})(20,20)+t,i+"b"+o(30,30)+t,i+"c"+o(40,40)+t],(y=function(k){var t,n;s._subiz_init_2094850928430||r[k]&&(t=u.createElement(b),n=u.getElementsByTagName(b)[0],t.async=1,t.src="https://"+r[k]+"/sbz/app.js?accid="+z,n.parentNode.insertBefore(t,n),setTimeout(y,2e3,k+1))})(0))}(window,document,"script","subiz","acqwbbwdbbalzevxyoha");</script>

</body>
</html>