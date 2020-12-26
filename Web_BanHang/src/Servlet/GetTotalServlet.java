package Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getTotal")
public class GetTotalServlet extends HttpServlet {

	public GetTotalServlet() {
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String totalPrice = request.getParameter("totalPrice").trim();
		int totalprice = Integer.valueOf(totalPrice);
		String makm = request.getParameter("makm");
		int km = Integer.valueOf(makm);
		System.out.println(totalprice + " " + makm );
		long total = totalprice - (totalprice * km / 100);
		String tong = String.valueOf(total);
		
		response.setContentType("text/plain");
		response.getWriter().write(tong);
	}
}
