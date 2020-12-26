<%@ page pageEncoding="utf-8"%>

<!-- footer -->
			<!-- ============================================================== -->
			<footer class="footer text-center">
				2020 © Ample Admin brought to you by <a
					href="https://www.wrappixel.com/">wrappixel.com</a>
			</footer>
			<!-- ============================================================== -->
			<!-- End footer -->
			<!-- ============================================================== -->
		</div>
		<!-- ============================================================== -->
		<!-- End Page wrapper  -->
		<!-- ============================================================== -->
	</div>
	<!-- ============================================================== -->
	<!-- End Wrapper -->
	<!-- ============================================================== -->
	<!-- ============================================================== -->
	<!-- All Jquery -->
	<!-- ============================================================== -->
	<script
		src="${pageContext.request.contextPath}/plugins/bower_components/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap tether Core JavaScript -->
	<script
		src="${pageContext.request.contextPath}/plugins/bower_components/popper.js/dist/umd/popper.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/bootstrap_template/dist/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/app-style-switcher.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/bower_components/jquery-sparkline/jquery.sparkline.min.js"></script>
	<!--Wave Effects -->
	<script src="${pageContext.request.contextPath}/js/waves.js"></script>
	<!--Menu sidebar -->
	<script src="${pageContext.request.contextPath}/js/sidebarmenu.js"></script>
	<!--Custom JavaScript -->
	<script src="${pageContext.request.contextPath}/js/custom.js"></script>
	<!--This page JavaScript -->
	<!--chartis chart-->
	<script
		src="${pageContext.request.contextPath}/plugins/bower_components/chartist/dist/chartist.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/bower_components/chartist-plugin-tooltips/dist/chartist-plugin-tooltip.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/pages/dashboards/dashboard1.js"></script>
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
   				$("#promotion").css("display", "none");
   				$('#account').css("display","none");
   			}
   			else if (role == 7) {
   				$("#customer").css("display", "none");
   				$("#systems").css("display", "none");
   				$("#product").css("display", "none");
   				$("#sales").css("display", "none");
   				$("#import").css("display", "none");
   				$("#promotion").css("display", "none");
   				$("#account").css("display", "none");
   				$("#find").css("display", "none");
   				$("#order").css("display", "none");
   				$("#brand").css("display", "none");
   			}
   		});
   </script>
</body>

</html>