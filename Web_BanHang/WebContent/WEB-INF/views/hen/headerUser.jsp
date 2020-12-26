
<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<div id="header">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3">
				<div class="header-logo text-center">
					<a href="user/index.htm"><img
						src="${pageContext.request.contextPath}/asset/images/Capture.PNG"
						alt="logo"></a>
				</div>
			</div>
			<div class="col-md-7">
				<div class="header-menu">
					<ul id="main-menu">
						<li><a href="user/index.htm">Trang chủ</a></li>
						<li class="dropdown">
							<!-- <i class="fa fa-chevron-down"></i> -->
							<!-- data-toggle="dropdown" -->
							<a href="user/allproduct.htm" class="btn dropdown-toggle" >Sản
								Phẩm</a>
							<div class="dropdown-menu">
								<c:forEach var="b" items="${brand}">
									<a href="user/show/${b.id}.htm" class="dropdown-item">${b.name}</a>
									<div class="dropdown-divider"></div>
								</c:forEach>
							</div>
						</li>
						<li><a href="user/index.htm">Tin Tức</a></li>
						<li><a href="user/index.htm">Giới Thiệu</a></li>
						<li><a href="user/index.htm">Liên Hệ</a></li>
					</ul>
				</div>
			</div>
			<div class="col-md-2 ">
				<c:if test="${isLogin == false}">
					<div class="header-icon clearfix" style="padding: 10px 25px">
						<div class="info-account" style="position: relative;">
							<p class="mb-0">XinChao:</p>
						</div>
						<div class="group-icon">
							<div class="icon-user">
								<a href="user/login.htm"> <span><i
										class="fas fa-user"></i></span>
								</a>
							</div>
							<div class="icon-cart">
								<a href="user/cart/addCart.htm"> <span><i
										class="fas fa-shopping-cart"></i></span>
								</a>
							</div>
						</div>
						<form action="user/search.htm" id="find">
							<input autocomplete="off" type="text" name="search"
								placeholder="Bạn muốn tìm gì?">
							<button type="submit">
								<i class="fas fa-search"></i>
							</button>
						</form>
					</div>
				</c:if>
				<c:if test="${isLogin == true}">
					<div class="header-icon clearfix" style="padding: 10px 25px">
						<div class="info-account" style="position: relative;">
							<p class="mb-0">
								XinChao: <a href="user/managerAccount.htm"
									style="font-size: 18px !important; font-weight: 700; color: #666666; text-decoration: none">${sessionScope.user.getUsername()}</a>

							</p>
						</div>
						<div class="group-icon">
							<div class="icon-logout" style="float: left;">
							  <a href="user/logout.htm"><span><i
										class="fas fa-sign-out-alt"></i></span>
								</a>
							</div>
							<div class="icon-cart">
								<a href="user/cart/addCart.htm"> <span><i
										class="fas fa-shopping-cart"></i></span>
								</a>
							</div>
						</div>
						<form action="user/search.htm" id="find">
							<input autocomplete="off" type="text" name="search"
								placeholder="Bạn muốn tìm gì?">
							<button type="submit">
								<i class="fas fa-search"></i>
							</button>
						</form>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</div>
