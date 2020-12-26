
<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<!-- slider -->
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12 p-0">
			<div id="home-slide" class="carousel slide" data-ride="carousel">
				<!-- Indicators -->
				<ul class="carousel-indicators">
					<c:forEach var="s" items="${slider}" begin="0" varStatus="status">
						<c:choose>
							<c:when test="${status.index == 0}">
								<li data-target="#home-slider" data-slide-to="${status.index}"
									class="active"></li>
							</c:when>
							<c:when test="${status.index >0}">
								<li data-target="#home-slider" data-slide-to="${status.index}"></li>
							</c:when>
						</c:choose>
					</c:forEach>
				</ul>
				<div class="carousel-inner">
					<c:forEach var="s" items="${slider}" begin="0" varStatus="status">
						<c:choose>
							<c:when test="${status.index == 0}">
								<div class="carousel-item active">
									<img src="${pageContext.request.contextPath}/images/${s.image}"
										class="d-block w-100">
								</div>
							</c:when>
							<c:when test="${status.index >0}">
								<div class="carousel-item">
									<img src="${pageContext.request.contextPath}/images/${s.image}"
										class="d-block w-100">
								</div>
							</c:when>
						</c:choose>
					</c:forEach>
				</div>
				<!-- <div class="carousel-inner">
                                <div class="carousel-item active">
                                    <img src="asset/images/slider1.jpg" alt="" class="d-block w-100">
                                </div>
                                <div class="carousel-item">
                                    <img src="asset/images/slider.jpg" alt="" class="d-block w-100">
                                </div>
                            </div> -->
			</div>

			<!-- end carousel-inner -->
			<a class="carousel-control-prev" href="#home-slide" data-slide="prev">
				<span class="carousel-control-prev-icon"></span>
			</a> <a class="carousel-control-next" href="#home-slide"
				data-slide="next"> <span class="carousel-control-next-icon"></span>
			</a>
		</div>
	</div>
</div>
<!--   end slider -->