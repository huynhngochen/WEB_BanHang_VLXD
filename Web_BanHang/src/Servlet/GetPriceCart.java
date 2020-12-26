package Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/test")
public class GetPriceCart extends HttpServlet{

	public GetPriceCart() {
		// TODO Auto-generated constructor stub
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String id_product = request.getParameter("makm").trim();
		int test = Integer.parseInt(id_product);
		long tong = 0;
		
		response.setContentType("text/plain");
		response.getWriter().write((int) tong);
	}

}
