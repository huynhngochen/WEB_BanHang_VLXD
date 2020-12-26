<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<!DOCTYPE html>
<html dir="ltr" lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords"
	content="wrappixel, admin dashboard, html css dashboard, web dashboard, bootstrap 4 admin, bootstrap 4, css3 dashboard, bootstrap 4 dashboard, Ample lite admin bootstrap 4 dashboard, frontend, responsive bootstrap 4 admin template, Ample admin lite dashboard bootstrap 4 dashboard template">
<meta name="description"
	content="Ample Admin Lite is powerful and clean admin dashboard template, inpired from Bootstrap Framework">
<meta name="robots" content="noindex,nofollow">
<title>Ample Admin Lite Template by WrapPixel</title>
<link rel="canonical"
	href="https://www.wrappixel.com/templates/ample-admin-lite/" />
<!-- Favicon icon -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/fonts/fontawesome/css/all.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/asset/bootstrap/css/bootstrap.min.css">
<link rel="icon" type="image/png" sizes="16x16"
	href="${pageContext.request.contextPath}/plugins/images/favicon.png">
<!-- Custom CSS -->
<link
	href="${pageContext.request.contextPath}/plugins/bower_components/chartist/dist/chartist.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/bower_components/chartist-plugin-tooltips/dist/chartist-plugin-tooltip.css">
<!-- Custom CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/reset.css">
	<link href="${pageContext.request.contextPath}/css/style.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/main.css"
	rel="stylesheet">
</head>
<style>
table td {
    padding: 0.9375rem 0.4rem;
    vertical-align: top;
    border-top: 1px solid rgba(120, 130, 140, 0.13);
}
</style>

<body>
	<!-- ============================================================== -->
	<!-- Preloader - style you can find in spinners.css -->
	<!-- ============================================================== -->
	<div class="preloader">
		<div class="lds-ripple">
			<div class="lds-pos"></div>
			<div class="lds-pos"></div>
		</div>
	</div>
	<!-- ============================================================== -->
	<!-- Main wrapper - style you can find in pages.scss -->
	<!-- ============================================================== -->
	<div id="main-wrapper" data-layout="vertical" data-navbarbg="skin5"
		data-sidebartype="full" data-sidebar-position="absolute"
		data-header-position="absolute" data-boxed-layout="full">
		<!-- ============================================================== -->
		<!-- Topbar header - style you can find in pages.scss -->
		<!-- ============================================================== -->
		<header class="topbar" data-navbarbg="skin5">
			<nav class="navbar top-navbar navbar-expand-md navbar-dark">
				<div class="navbar-header" data-logobg="skin6">
					<!-- ============================================================== -->
					<!-- Logo -->
					<!-- ============================================================== -->
					<a class="navbar-brand" href="" style="padding: 15px 10px;"> <!-- Logo icon --> <b
						class="logo-icon"> <!-- Dark Logo icon --> <img
							src="${pageContext.request.contextPath}/plugins/images/logo-icon.png"
							alt="homepage" />
					</b> <!--End Logo icon --> <!-- Logo text --> <span class="logo-text">
							<!-- dark Logo text --> <img
							src="${pageContext.request.contextPath}/plugins/images/logo-text.png"
							alt="homepage" />
					</span>
					</a>
					<!-- ============================================================== -->
					<!-- End Logo -->
					<!-- ============================================================== -->
					<!-- ============================================================== -->
					<!-- toggle and nav items -->
					<!-- ============================================================== -->
					<a
						class="nav-toggler waves-effect waves-light text-dark d-block d-md-none"
						href="javascript:void(0)"><i class="ti-menu ti-close"></i></a>
				</div>
				<!-- ============================================================== -->
				<!-- End Logo -->
				<!-- ============================================================== -->
				<div class="navbar-collapse collapse" id="navbarSupportedContent"
					data-navbarbg="skin5">
					<ul class="navbar-nav d-none d-md-block d-lg-none">
						<li class="nav-item"><a
							class="nav-toggler nav-link waves-effect waves-light text-white"
							href="javascript:void(0)"><i class="ti-menu ti-close"></i></a></li>
					</ul>
					<!-- ============================================================== -->
					<!-- Right side toggle and nav items -->
					<!-- ============================================================== -->
					<ul class="navbar-nav ml-auto d-flex align-items-center">

						<!-- ============================================================== -->
						<!-- Search -->
						<!-- ============================================================== -->
						<!--  <li class=" in">
                            <form role="search" class="app-search d-none d-md-block mr-3">
                                <input type="text" placeholder="Search..." class="form-control mt-0">
                                <a href="" class="active">
                                    <i class="fa fa-search"></i>
                                </a>
                            </form>
                        </li> -->
						<!-- ============================================================== -->
						<!-- User profile and search -->
						<!-- ============================================================== -->
						<li><a class="profile-pic" href="${pageContext.request.contextPath}/admin/info-account.htm"> <img
								src="${pageContext.request.contextPath}/plugins/images/users/varun.jpg"
								alt="user-img" width="36" class="img-circle"><span
								class="text-white font-medium">${sessionScope.user.getUsername()}</span></a></li>
						
						<!-- ============================================================== -->
						<!-- User profile and search -->
						<!-- ============================================================== -->
					</ul>
					
				</div>
				<div class="info-user-more">
						<a href="${pageContext.request.contextPath}/admin/managerAccount.htm" id="account">Tài Khoản</a> <span
							style="padding: 0, 5px;">|</span> <a href="${pageContext.request.contextPath}/admin/logout.htm"
							onclick="return confirm('Bạn có chắc muốn đăng xuất?');">Đăng
							Xuất</a>
						</div>
			</nav>
		</header>
		<!-- ============================================================== -->
		<!-- End Topbar header -->
		<!-- ============================================================== -->
		<!-- ============================================================== -->
		<!-- Left Sidebar - style you can find in sidebar.scss  -->
		<!-- ============================================================== -->
		<aside class="left-sidebar" data-sidebarbg="skin6">
			<!-- Sidebar scroll-->
			<div class="scroll-sidebar">
				<!-- Sidebar navigation-->
				<nav class="sidebar-nav">
					<ul id="sidebarnav">
						<!-- User Profile-->
						<li class="sidebar-item pt-2">
						<a id="order" class="sidebar-link waves-effect waves-dark sidebar-link"
							href="${pageContext.request.contextPath}/admin/admin-home.htm" aria-expanded="false"> <i
								class="far fa-clock" aria-hidden="true"></i> <span
								class="hide-menu">Đơn Đặt Hàng</span>
						</a>
						<a id="order-shipper" class="sidebar-link waves-effect waves-dark sidebar-link"
							href="${pageContext.request.contextPath}/admin/shipper.htm" aria-expanded="false"> <i
								class="far fa-clock" aria-hidden="true"></i> <span
								class="hide-menu">Đơn Đặt Hàng</span>
						</a>
						</li>
						<li id="customer" class="sidebar-item"><a
							class="sidebar-link waves-effect waves-dark sidebar-link"
							href="${pageContext.request.contextPath}/admin/admin-user.htm" aria-expanded="false"> <i
								class="fa fa-user" aria-hidden="true"></i> <span
								class="hide-menu">Khách Hàng</span>
						</a></li>
						<li id="product" class="sidebar-item"><a
							class="sidebar-link waves-effect waves-dark sidebar-link"
							href="${pageContext.request.contextPath}/admin/product/index.htm" aria-expanded="false">
							<i class="fas fa-building"></i><span
								class="hide-menu">Sản Phẩm</span>
						</a></li>
						<li id="import" class="sidebar-item"><a
							class="sidebar-link waves-effect waves-dark sidebar-link"
							href="${pageContext.request.contextPath}/import/index.htm" aria-expanded="false">
							<i class="fas fa-file-import"></i><span
								class="hide-menu">Phiếu Nhập</span>
						</a></li>
						<li  id="sales" class="sidebar-item"><a
							class="sidebar-link waves-effect waves-dark sidebar-link"
							href="${pageContext.request.contextPath}/admin/order/indexCount.htm" aria-expanded="false">
							<i class="fas fa-chart-area"></i><span
								class="hide-menu">Thống Kê</span>
						</a></li>
						<li id="promotion" class="sidebar-item"><a
							class="sidebar-link waves-effect waves-dark sidebar-link"
							href="${pageContext.request.contextPath}/promotion/index.htm" aria-expanded="false">
							<i class="fas fa-comment-dollar"></i><span
								class="hide-menu">Khuyến Mãi</span>
						</a></li>
						<li id="systems" class="sidebar-item"><a
							class="sidebar-link waves-effect waves-dark sidebar-link"
							href="${pageContext.request.contextPath}/admin/slider/index.htm" aria-expanded="false"> 
							<i class="fas fa-users-cog"></i><span
								class="hide-menu">Slider</span>
						</a></li>
						<li id="brand" class="sidebar-item"><a
							class="sidebar-link waves-effect waves-dark sidebar-link"
							href="${pageContext.request.contextPath}/admin/brand/index.htm" aria-expanded="false"> 
							<i class="fas fa-folder-plus"></i><span
								class="hide-menu">Danh Mục Hàng Hóa</span>
						</a></li>
						
						

					</ul>

				</nav>
				<!-- End Sidebar navigation -->
			</div>
			<!-- End Sidebar scroll-->
		</aside>
		<!-- ============================================================== -->
		<!-- End Left Sidebar - style you can find in sidebar.scss  -->
		<!-- ============================================================== -->
		<!-- ============================================================== -->
		<!-- Page wrapper  -->
		<!-- ============================================================== -->
		<div class="page-wrapper">
			<!-- ============================================================== -->
			<!-- Bread crumb and right sidebar toggle -->
			<!-- ============================================================== -->
			<div class="page-breadcrumb bg-white">
				<div class="row align-items-center">
					<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
						<h4 class="page-title text-uppercase font-medium font-14">Dashboard</h4>
					</div>
					<div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
						<div class="d-md-flex">
							<ol class="breadcrumb ml-auto">
								<li><a href="#">Dashboard</a></li>
							</ol>

						</div>
					</div>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- ============================================================== -->
			<!-- End Bread crumb and right sidebar toggle -->
			<!-- ============================================================== -->
			<!-- ============================================================== -->
