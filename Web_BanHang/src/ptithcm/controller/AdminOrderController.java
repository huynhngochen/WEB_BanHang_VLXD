package ptithcm.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.sun.javafx.collections.MappingChange.Map;

import Model.Cart;
import Model.CountAllProduct;
import Model.MyItem;
import Model.ProductImport;
import Model.ProfitProduct;
import Model.Report;
import ptithcm.entity.ChiTietDatHang;
import ptithcm.entity.ChiTietPhieuNhap;
import ptithcm.entity.DatHang;
import ptithcm.entity.HangHoa;
import ptithcm.entity.HoaDon;
import ptithcm.entity.Slider;
import ptithcm.entity.UserID;

@Transactional
@Controller
@RequestMapping("/admin/order/")
public class AdminOrderController {
	@Autowired
	SessionFactory factory;

	Date date = new Date();

	// ============TÌM KIẾM THÔNG TIN ĐƠN HÀNG=======
	// tìm kiếm thông tin mã đơn hàng
	@RequestMapping("search")
	public String searchOrder(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		String keyword = req.getParameter("search").trim();

		while (keyword.indexOf(" ") != -1) {
			keyword = keyword.replaceAll(" ", "");
		}
		System.out.println(keyword);

		Session session = factory.getCurrentSession();
		String hql = "FROM DatHang WHERE dbo.ufn_removeMark(id) LIKE '%" + keyword + "%'";
		Query query = session.createQuery(hql);
		List<DatHang> list = query.list();

		if (list.isEmpty()) {
			model.addAttribute("message", "Không có kết quả !");
		} else {
			model.addAttribute("order", list);
		}

		return "order/search";
	}
	
	
	// tìm kiếm thông tin mã đơn hàng theo mã khách hàng
		@RequestMapping("searchUser")
		public String searchOrderByUser(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

			HttpSession admin_session = req.getSession();
			admin_session.getAttribute("user");

			if (admin_session.getAttribute("user") == null) {
				return "redirect:/admin/index.htm";
			}
			UserID user = (UserID) admin_session.getAttribute("user");
			if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
				return "redirect:/admin/index.htm";
			}
			String keyword = req.getParameter("searchUser").trim();

			while (keyword.indexOf(" ") != -1) {
				keyword = keyword.replaceAll(" ", "");
			}
			System.out.println(keyword);

			Session session = factory.getCurrentSession();
			String hql = "FROM DatHang WHERE id_khachhang= '" + keyword + "'";
			System.out.println(hql);
			Query query = session.createQuery(hql);
			List<DatHang> list = query.list();

			if (list.isEmpty()) {
				model.addAttribute("message", "Không có kết quả !");
			} else {
				model.addAttribute("order", list);
			}

			return "order/search";
		}

	// tìm kiếm thông tin trạng thái đơn hàng
	@RequestMapping("searchStatus")
	public String searchByStatus(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() == 1) {
			return "redirect:/admin/index.htm";
		}
		String status = req.getParameter("status");
		int status_delivery = Integer.parseInt(status);
		System.out.println(status_delivery);
		
		Session session = factory.getCurrentSession();
		String hql = "FROM DatHang WHERE status_delivery = :status";
		Query query = session.createQuery(hql);
		query.setInteger("status", status_delivery);
//		System.out.println(query);
		List<DatHang> list = query.list();
		System.out.println(list);
		if (list.isEmpty()) {
			model.addAttribute("message", "Không có kết quả !");
			model.addAttribute("status_delivery", status);
		} else {
			model.addAttribute("order", list);
			model.addAttribute("status_delivery", status);
		}

		return "order/searchStatus";
	}

	// =====XEM CHI TIẾT TỪNG ĐƠN HÀNG
	@RequestMapping(value = "detailOrder/{id}")
	public String detailOrder(ModelMap model, @PathVariable("id") Integer id, HttpServletRequest req,
			HttpServletResponse response) {
		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() == 1) {
			return "redirect:/admin/index.htm";
		}
		Session session = factory.getCurrentSession();
		DatHang order = (DatHang) session.get(DatHang.class, id);
		String hql = "From ChiTietDatHang WHERE dathang_id=:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		List<ChiTietDatHang> list = query.list();
		// List<ChiTietDatHang> list = (List<ChiTietDatHang>)
		// order.getDetail_order();
		// System.out.println(order.getDetail_order());
		model.addAttribute("detail_order", list);

		return "order/detailOrder";
	}

	// ============XỬ LÝ ĐƠN HÀNG===========
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateOrder(ModelMap model, @PathVariable("id") Integer id, HttpServletRequest req,
			HttpServletResponse response, HttpSession ss) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() == 1) {
			return "redirect:/admin/index.htm";
		}
		Session session = factory.getCurrentSession();
		DatHang order = (DatHang) session.get(DatHang.class, id);
		date = order.getCreated();

		/* model.addAttribute("user", list); */
		model.addAttribute("order", order);
		return "order/update";
	}

	@RequestMapping("update")
	public String updateOrder(ModelMap model, @ModelAttribute("order") DatHang order, BindingResult errors,
			HttpServletRequest req, HttpSession ss) {

		String status = req.getParameter("status");

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() == 1) {
			return "redirect:/admin/index.htm";
		}
		if (order.getUserid2().getUsername().equals("") && Integer.parseInt(status) != 5) {
			model.addAttribute("message", "Vui lòng chọn nhân viên giao hàng!!");
		} else {
			if (Integer.parseInt(status) == 4) {
				order.setStatus_payment(true);
			}
			if (Integer.parseInt(status) == 5) {
				Session session1 = factory.getCurrentSession();
				DatHang test = (DatHang) session1.get(DatHang.class, order.getId());
				List<ChiTietDatHang> list = (List<ChiTietDatHang>) test.getDetail_order();
				for (int i = 0; i < list.size(); i++) {
					String hql1 = "UPDATE HangHoa SET count_buy = count_buy - (:countbuy), in_stock= in_stock +(:countbuy) WHERE id = :id";
					Query query1 = session1.createQuery(hql1);
					query1.setParameter("countbuy", list.get(i).getQty());
					query1.setParameter("id", list.get(i).getHanghoas().getId());
					int result = query1.executeUpdate();
					System.out.println(result);
				}
				if (user.getRole().getId() == 7) {
					order.setUserid2(user);
				}
				else {
					order.setUserid2(null);
				}
				
			}
			else {
				order.setUserid2(order.getUserid2());
			}
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			order.setStatus_delivery(Integer.parseInt(status));
			order.setCreated(date);
			order.setUserid1(user);
			
			try {
				session.update(order);
				t.commit();
				model.addAttribute("message", "Cập nhật thành công !");
				if (user.getRole().getId() == 7) {
					return "redirect:/admin/shipper.htm";
				} else {
					return "redirect:/admin/admin-home.htm";
				}
			} catch (Exception e) {
				t.rollback();
				model.addAttribute("message", "Cập nhật thất bại !");
			} finally {
				session.close();
			}
		}

		return "order/update";
	}

	// ============THỐNG KÊ DOANH THU=============
	@RequestMapping(value = "indexCount")
	public String indexCount(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}

		Session session = factory.getCurrentSession();
		Date cur = new Date();
		SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");
		String date = formatter2.format(cur);
		String hql = "SELECT SUM(amount) FROM DatHang WHERE created = :date " + "AND status_payment = 'true'";
		Query query = session.createQuery(hql);
		query.setDate("date", cur);

		model.addAttribute("date", date);
		model.addAttribute("count", query.uniqueResult());

		return "order/indexCount";
	}

	// THỐNG KÊ DOANH THU SẢN PHẨM
	@RequestMapping(value = "indexSales")
	public String indexSales(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		return "order/indexSales";
	}

	@RequestMapping(value = "count")
	public String count(ModelMap model, HttpServletRequest req, HttpServletResponse response) {
		HashMap<Integer, ProfitProduct> listProfitProduct = new HashMap<>();

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");
		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		Date date1 = new Date();
		Date date2 = new Date();

		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		String var_date1 = req.getParameter("date1");
		String var_date2 = req.getParameter("date2");
		if (var_date1.equals("")) {
			model.addAttribute("message", "Vui lòng chọn ngày thống kê!");
		}
		if (var_date2.equals("")) {
			model.addAttribute("message", "Vui lòng chọn ngày thống kê!");
		} else {
			try {
				date1 = formatter2.parse(var_date1);
				date2 = formatter2.parse(var_date2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (date1.after(date2)) {
				model.addAttribute("message", "Ngày kết thúc phải lớn hơn ngày bắt đầu");
				return "order/indexSales";
			} else if (date1.before(date2)) {
				Session session = factory.getCurrentSession();
				String hql = "SELECT new map(SUM(amount) as sum, dh.created as created) "
						+ "FROM DatHang dh WHERE created >= :date_start "
						+ "AND created <= :date_finish AND status_payment = 'true' " + "GROUP BY created";
				Query query = session.createQuery(hql);
				query.setDate("date_start", date1);
				query.setDate("date_finish", date2);
				List listCount = query.list();

				// create a new Gson instance
				Gson gson = new Gson();
				// convert your list to json
				String jsonListCount = gson.toJson(listCount);
				// print your generated json
				System.out.println("jsonCartList: " + jsonListCount);
				model.addAttribute("report", jsonListCount);
				return "order/count";
				// List<MyItem> reportOrder = new ArrayList<>();
				// while (date1.before(date2) || date1.equals(date2)) {
				// int i = 1;
				// Date tmp = date1;
				// MyItem myItem = new MyItem();
				// myItem.setTime(formatter2.format(tmp));
				// Query query = session.createQuery("SELECT ISNULL(SUM(amount),
				// 0) FROM DatHang WHERE created =:date"
				// + " AND status_payment = 'true'");
				// query.setDate("date", tmp);
				// myItem.setValue((long) query.uniqueResult());
				// reportOrder.add(myItem);
				// date1 = addDays(date1, i);
				// // System.out.println(formatter2.format(date1));
				// }

			} else {
				model.addAttribute("message", "Ngày kết thúc phải lớn hơn ngày bắt đầu");
				return "order/indexSales";
			}
		}
		return "order/indexSales";
	}

	@RequestMapping(value = "countAll")
	public String countAll(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");
		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		
		String var_year = req.getParameter("year");
		int year = Integer.parseInt(var_year);
		if(year < 0){
			model.addAttribute("message", "Năm phải lớn hơn 0. Mời nhập lại");
			return "order/indexSales";
		}
		else {
			Session session = factory.getCurrentSession();
//			String hql = "SELECT new map(ISNULL(SUM(amount) as sum, created as created) "
//					+ "FROM DatHang WHERE status_payment = 'true' " + "GROUP BY created";
			String hql = "SELECT new map(ISNULL(SUM(amount),0) as sum, MONTH(created) as month) "
					+ "FROM DatHang WHERE status_payment = 'true' AND YEAR(created) = :year "
					+ "GROUP BY MONTH(created)";
			Query query = session.createQuery(hql);
			query.setParameter("year", year);
			List listCountAll = query.list();
			System.out.println(listCountAll);

			Gson gson = new Gson();
			String jsonListCount = gson.toJson(listCountAll);
//			 System.out.println("jsonCartList: " + jsonListCount);
			
			model.addAttribute("year", year);
			model.addAttribute("reportAll", jsonListCount);
			return "order/countAll";
		}
	
	}

	// Thống kê doanh thu theo mã sản phẩm
	@RequestMapping(value = "countById")
	public String countByIdProduct(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");
		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		Date date1 = new Date();
		Date date2 = new Date();

		String id_hanghoa = req.getParameter("search");
		int id_hh = Integer.parseInt(id_hanghoa);
//		System.out.println(id_hh);
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		String var_date1 = req.getParameter("date1");
		String var_date2 = req.getParameter("date2");
		System.out.println(var_date1 + var_date2);
		if (var_date1.equals("")) {
			model.addAttribute("message", "Vui lòng chọn ngày thống kê!");
		}
		if (var_date2.equals("")) {
			model.addAttribute("message", "Vui lòng chọn ngày thống kê!");
		} else {
			try {
				date1 = formatter2.parse(var_date1);
				date2 = formatter2.parse(var_date2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (date1.after(date2)) {
				model.addAttribute("message", "Ngày kết thúc phải lớn hơn ngày bắt đầu");
				return "order/indexCount";
			} else if (date1.before(date2)) {
				Session session = factory.getCurrentSession();
				String hql = "SELECT new map(SUM(dh.amount) as sum, dh.created as created) "
						+ "FROM DatHang dh, ChiTietDatHang ctdh WHERE dh.created >= :date_start "
						+ "AND dh.created <= :date_finish AND dh.status_payment = 'true' "
						+ "AND dh.id = ctdh.dathangs.id AND ctdh.hanghoas.id = :id_hh " + "GROUP BY dh.created";
				Query query = session.createQuery(hql);
				query.setDate("date_start", date1);
				query.setDate("date_finish", date2);
				query.setParameter("id_hh", id_hh);
				List listCount = query.list();

				// create a new Gson instance
				Gson gson = new Gson();
				// convert your list to json
				String jsonListCount = gson.toJson(listCount);
				// print your generated json
				System.out.println("jsonCartList: " + jsonListCount);
				model.addAttribute("countById", jsonListCount);
				return "order/countById";

			} else {
				model.addAttribute("message", "Ngày kết thúc phải lớn hơn ngày bắt đầu");
				return "order/indexSales";
			}
		}
		return "order/indexSales";
	}

	// Thống kê doanh thu tất cả sp
	@RequestMapping(value = "countAllProduct")
	public String countAllProduct(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");
		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		Date date1 = new Date();
		Date date2 = new Date();

		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		String var_date1 = req.getParameter("date1");
		String var_date2 = req.getParameter("date2");
		if (var_date1.equals("")) {
			model.addAttribute("message", "Vui lòng chọn ngày thống kê!");
			return "order/indexSales";
		}
		if (var_date2.equals("")) {
			model.addAttribute("message", "Vui lòng chọn ngày thống kê!");

		} else {
			try {
				date1 = formatter2.parse(var_date1);
				date2 = formatter2.parse(var_date2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (date1.after(date2)) {
				model.addAttribute("message", "Ngày kết thúc phải lớn hơn ngày bắt đầu");
				return "order/indexSales";
			} else if (date1.before(date2)) {
				List<CountAllProduct> listCountAll = new ArrayList<>();
				Session session = factory.getCurrentSession();
				String hql = "FROM HangHoa";
				Query query = session.createQuery(hql);
				List<HangHoa> listProduct = query.list();
				long totalCount = 0;

				for (int i = 0; i < listProduct.size(); i++) {
					CountAllProduct item = new CountAllProduct();
//					Session session1 = factory.getCurrentSession();
					String hql1 = "SELECT ISNULL(SUM(dh.amount),0)"
							+ "FROM DatHang dh, ChiTietDatHang ctdh WHERE dh.created >= :date_start "
							+ "AND dh.created <= :date_finish AND dh.status_payment = 'true' "
							+ "AND dh.id = ctdh.dathangs.id AND ctdh.hanghoas.id = :id_hh ";
					Query query1 = session.createQuery(hql1);
					query1.setDate("date_start", date1);
					query1.setDate("date_finish", date2);
					query1.setParameter("id_hh", listProduct.get(i).getId());
					Long sum = (Long) query1.uniqueResult();
					totalCount += sum;
					item.setProduct(listProduct.get(i));
					item.setTotal(sum);
					listCountAll.add(item);
				}

				model.addAttribute("date_start", date1);
				model.addAttribute("date_finish", date2);
				// session.setAttribute("total", totalCount);
				model.addAttribute("total", totalCount);
				model.addAttribute("countAll", listCountAll);
				return "order/countAllProduct";

			} else {
				model.addAttribute("message", "Ngày kết thúc phải lớn hơn ngày bắt đầu");
				return "order/indexSales";
			}
		}

		return "order/indexCount";
	}

	// THỐNG KÊ LỢI NHUẬN SẢN PHẨM
	@RequestMapping(value = "indexProfit")
	public String indexProfit(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		return "order/indexProfit";
	}

	@RequestMapping(value = "profits")
	public String profitsProduct(ModelMap model, HttpServletRequest req, HttpServletResponse response,
			HttpSession session) {
		HashMap<Integer, ProfitProduct> listProfitProduct = new HashMap<>();
		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		Date date1 = new Date();
		Date date2 = new Date();
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		String var_date1 = req.getParameter("date1");
		String var_date2 = req.getParameter("date2");
		// String id_hanghoa = req.getParameter("search");
		// int id_hh = Integer.parseInt(id_hanghoa);

		if (var_date1.equals("")) {
			model.addAttribute("message", "Vui lòng chọn ngày thống kê!");
		} else if (var_date2.equals("")) {
			model.addAttribute("message", "Vui lòng chọn ngày thống kê!");
		} else {
			try {
				date1 = formatter2.parse(var_date1);
				date2 = formatter2.parse(var_date2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (date1.after(date2)) {
				model.addAttribute("message", "Ngày kết thúc phải lớn hơn ngày bắt đầu");
				return "order/indexProfit";
			} else if (date1.before(date2)) {
				
				Session session1 = factory.getCurrentSession();
				String hql3 = "FROM HangHoa";
				Query query3 = session1.createQuery(hql3);
				List<HangHoa> listProduct = query3.list();
				long tong = 0;
				for (int i = 0; i < listProduct.size(); i++) {
					HangHoa product = getHH(listProduct.get(i).getId());

					// Lấy tổng doanh thu
					ProfitProduct item = new ProfitProduct();
//					Session session1 = factory.getCurrentSession();
					String hql = "SELECT  ISNULL(SUM(ctdh.qty*ctdh.price),0)" + "FROM DatHang dh, ChiTietDatHang ctdh "
							+ "WHERE dh.id = ctdh.dathangs.id AND ctdh.hanghoas.id = :id_hh "
							+ "AND created >= :date_start AND created <= :date_finish " 
							+ "AND dh.status_delivery = 4";
					Query query = session1.createQuery(hql);
					query.setDate("date_start", date1);
					query.setDate("date_finish", date2);
					query.setParameter("id_hh", listProduct.get(i).getId());
					Long sumBan = (Long) query.uniqueResult();
					item.setTotalBuy(sumBan);
					item.setProduct(product);

					// lấy tổng số lượng bán
					String hql1 = "SELECT ISNULL(SUM(ctdh.qty),0) " + "FROM DatHang dh, ChiTietDatHang ctdh "
							+ "WHERE dh.id = ctdh.dathangs.id AND ctdh.hanghoas.id = :id_hh "
							+ "AND created >= :date_start AND created <= :date_finish " + "AND dh.status_delivery = 4";

					Query query1 = session1.createQuery(hql1);
					query1.setDate("date_start", date1);
					query1.setDate("date_finish", date2);
					query1.setParameter("id_hh", listProduct.get(i).getId());
					long countBuy = (long) query1.uniqueResult();
					String tmp_count = String.valueOf(countBuy);
					int count = Integer.parseInt(tmp_count);
					item.setCountBuy(count);

					// Lấy ra danh sách chi tiết phiếu nhập
					String hql2 = "SELECT ctpn FROM ChiTietPhieuNhap ctpn, PhieuNhap pn "
							+ "WHERE pn.id = ctpn.phieunhaps.id AND ctpn.hanghoas.id = :id_hh  "
							+ "AND date >= :date_start " + "AND date <= :date_finish AND pn.status_import = 1";
					Query query2 = session1.createQuery(hql2);
					query2.setDate("date_start", date1);
					query2.setDate("date_finish", date2);
					query2.setParameter("id_hh", listProduct.get(i).getId());
					long sumnhap = 0;
					int countNhap = 0;
					List<ChiTietPhieuNhap> list = query2.list();
					if (list.isEmpty()) {
						sumnhap = 0;
						countNhap = 0;
					} else {
						for (int k = 0; k < list.size(); k++) {
							countNhap += list.get(k).getQty();
							if (list.get(k).getQty() <= count) {
								sumnhap += list.get(k).getPrice() * list.get(k).getQty();
								count = count - list.get(k).getQty();
								// System.out.println(countNhap);
								System.out.println(count + " " + sumnhap);
							} else {
								sumnhap += count * list.get(k).getPrice();
								System.out.println(count + " " + sumnhap);
								count = 0;
							}

						}
					}
					tong += sumBan - sumnhap;
					item.setTotalImport(sumnhap);
					item.setCountImport(countNhap);
					listProfitProduct.put(listProduct.get(i).getId(), item);
				}
				// System.out.println(listProfitProduct);
				model.addAttribute("date_start", date1);
				model.addAttribute("date_finish", date2);
				session.setAttribute("total", tong);
				session.setAttribute("listProfitProduct", listProfitProduct);
				return "order/profitProduct";

			} else {
				model.addAttribute("message", "Ngày kết thúc phải lớn hơn ngày bắt đầu");
				return "order/indexProfit";
			}
		}

		return "order/indexProfit";
	}

	// Thống kê lợi nhuận sp cả DB
	@RequestMapping(value = "profitAll")
	public String profitsProductAll(ModelMap model, HttpServletRequest req, HttpServletResponse response,
			HttpSession session) {
		HashMap<Integer, ProfitProduct> listProfitProduct = new HashMap<>();
		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}

		Session session3 = factory.getCurrentSession();
		String hql3 = "FROM HangHoa";
		Query query3 = session3.createQuery(hql3);
		List<HangHoa> listProduct = query3.list();
		long tong = 0;
		for (int i = 0; i < listProduct.size(); i++) {
			HangHoa product = getHH(listProduct.get(i).getId());

			// Lấy tổng doanh thu
			ProfitProduct item = new ProfitProduct();
			String hql = "SELECT  ISNULL(SUM(ctdh.qty*ctdh.price),0)" + "FROM DatHang dh, ChiTietDatHang ctdh "
					+ "WHERE dh.id = ctdh.dathangs.id AND ctdh.hanghoas.id = :id_hh " + "AND dh.status_delivery = 4";
			Query query = session3.createQuery(hql);
			query.setParameter("id_hh", listProduct.get(i).getId());
			Long sumBan = (Long) query.uniqueResult();
			item.setTotalBuy(sumBan);
			item.setProduct(product);

			// lấy tổng số lượng bán
			String hql1 = "SELECT ISNULL(SUM(ctdh.qty),0) " + "FROM DatHang dh, ChiTietDatHang ctdh "
					+ "WHERE dh.id = ctdh.dathangs.id AND ctdh.hanghoas.id = :id_hh " + "AND dh.status_delivery = 4";

			Query query1 = session3.createQuery(hql1);
			query1.setParameter("id_hh", listProduct.get(i).getId());
			long countBuy = (long) query1.uniqueResult();
			String tmp_count = String.valueOf(countBuy);
			int count = Integer.parseInt(tmp_count);
			item.setCountBuy(count);

			// Lấy ra danh sách chi tiết phiếu nhập
			String hql2 = "SELECT ctpn FROM ChiTietPhieuNhap ctpn, PhieuNhap pn "
					+ "WHERE pn.id = ctpn.phieunhaps.id AND ctpn.hanghoas.id = :id_hh  " + "AND pn.status_import = 1";
			Query query2 = session3.createQuery(hql2);
			query2.setParameter("id_hh", listProduct.get(i).getId());
			long sumnhap = 0;
			int countNhap = 0;
			List<ChiTietPhieuNhap> list = query2.list();
			if (list.isEmpty()) {
				sumnhap = 0;
				countNhap = 0;
			} else {
				for (int k = 0; k < list.size(); k++) {
					countNhap += list.get(k).getQty();
					if (list.get(k).getQty() <= count) {
						sumnhap += list.get(k).getPrice() * list.get(k).getQty();
						count = count - list.get(k).getQty();
						System.out.println(count + " " + sumnhap);
					} else {
						sumnhap += count * list.get(k).getPrice();
						count = 0;
					}
				}
			}
			tong += sumBan - sumnhap;
			item.setTotalProfit(sumBan - sumnhap);
			// System.out.println(item.getTotalProfit());
			item.setTotalImport(sumnhap);
			item.setCountImport(countNhap);
			listProfitProduct.put(item.getProduct().getId(), item);
		}
		List<ProfitProduct> peopleByAge = new ArrayList<>(listProfitProduct.values());

		Collections.sort(peopleByAge, Comparator.comparing(ProfitProduct::getTotalProfit));
		Collections.reverse(peopleByAge);

		session.setAttribute("total", tong);
		model.addAttribute("listProfitProduct", peopleByAge);

		return "order/profitProductAll";
	}

	// THỐNG KÊ GIÁ(nhập, bán) SẢN PHẨM
	@RequestMapping(value = "indexPrice")
	public String indexPrice(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		return "order/indexPrice";
	}

	// Thống kê giá nhập của sản phẩm trong 1 khoảng time
	@RequestMapping(value = "productImport")
	public String reportProductImport(ModelMap model, HttpServletRequest req, HttpServletResponse response) {
		HashMap<Integer, ProfitProduct> listProfitProduct = new HashMap<>();

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		Date date1 = new Date();
		Date date2 = new Date();

		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		String id_hanghoa = req.getParameter("search");
		int id_hh = Integer.parseInt(id_hanghoa);
		String var_date1 = req.getParameter("date1");
		String var_date2 = req.getParameter("date2");
		if (var_date1.equals("")) {
			model.addAttribute("message", "Vui lòng chọn ngày thống kê!");
		}
		if (var_date2.equals("")) {
			model.addAttribute("message", "Vui lòng chọn ngày thống kê!");
		} else {
			try {
				date1 = formatter2.parse(var_date1);
				date2 = formatter2.parse(var_date2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (date1.after(date2)) {
				model.addAttribute("message", "Ngày kết thúc phải lớn hơn ngày bắt đầu");
				return "order/indexPrice";
			} else if (date1.before(date2)) {
				Session session = factory.getCurrentSession();
				String hql = "SELECT DISTINCT new map(pn.date as date, ctpn.price as price)  FROM PhieuNhap pn, ChiTietPhieuNhap ctpn "
						+ "WHERE date >= :date_start AND date <= :date_finish "
						+ "AND ctpn.hanghoas.id = :id_hh AND pn.id = ctpn.phieunhaps.id " + "AND pn.status_import = 1";

				Query query = session.createQuery(hql);
				query.setDate("date_start", date1);
				query.setDate("date_finish", date2);
				query.setParameter("id_hh", id_hh);
				List productImport = query.list();

				// create a new Gson instance
				Gson gson = new Gson();
				// convert your list to json
				String jsonCartList = gson.toJson(productImport);
				// print your generated json
				System.out.println("jsonCartList: " + jsonCartList);

				model.addAttribute("productImport", jsonCartList);
				return "order/productImport";

			} else {
				model.addAttribute("message", "Ngày kết thúc phải lớn hơn ngày bắt đầu");
				return "order/indexPrice";
			}
		}

		return "order/indexPrice";
	}

	// Thống kê giá bán của sản phẩm trong 1 khoảng time
	@RequestMapping(value = "productBuy")
	public String reportProductBuy(ModelMap model, HttpServletRequest req, HttpServletResponse response) {
		HashMap<Integer, ProfitProduct> listProfitProduct = new HashMap<>();

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		Date date1 = new Date();
		Date date2 = new Date();

		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		String id_hanghoa = req.getParameter("search");
		int id_hh = Integer.parseInt(id_hanghoa);
		String var_date1 = req.getParameter("date1");
		String var_date2 = req.getParameter("date2");
		if (var_date1.equals("")) {
			model.addAttribute("message", "Vui lòng chọn ngày thống kê!");
		}
		if (var_date2.equals("")) {
			model.addAttribute("message", "Vui lòng chọn ngày thống kê!");
		} else {
			try {
				date1 = formatter2.parse(var_date1);
				date2 = formatter2.parse(var_date2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (date1.after(date2)) {
				model.addAttribute("message", "Ngày kết thúc phải lớn hơn ngày bắt đầu");
				return "order/indexPrice";
			} else if (date1.before(date2)) {
				Session session = factory.getCurrentSession();
				String hql = "SELECT DISTINCT new map(dh.created as date, ctdh.price as price)"
						+ "FROM DatHang dh, ChiTietDatHang ctdh "
						+ "WHERE created >= :date_start AND created <= :date_finish "
						+ "AND ctdh.hanghoas.id = :id_hh AND dh.id = ctdh.dathangs.id";

				Query query = session.createQuery(hql);
				query.setDate("date_start", date1);
				query.setDate("date_finish", date2);
				query.setParameter("id_hh", id_hh);
				List test = query.list();

				// create a new Gson instance
				Gson gson = new Gson();
				// convert your list to json
				String jsonCartList = gson.toJson(test);
				// print your generated json
				System.out.println("jsonCartList: " + jsonCartList);
				model.addAttribute("jsonText", jsonCartList);
				return "order/productBuy";

			} else {
				model.addAttribute("message", "Ngày kết thúc phải lớn hơn ngày bắt đầu");
				return "order/indexPrice";
			}
		}

		return "order/indexPrice";
	}

	// THỐNG KÊ TỒN KHO SẢN PHẨM
	@RequestMapping(value = "indexInstock")
	public String indexInstock(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		return "order/indexInstock";
	}

	// Thống kê tồn kho sản phẩm
	@RequestMapping(value = "instockProduct")
	public String instockProduct(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		Session session = factory.getCurrentSession();
		String hql = "FROM HangHoa hh";
		Query query = session.createQuery(hql);
		List<HangHoa> listProduct = query.list();
		int total = 0;
		for (int i = 0; i < listProduct.size(); i++) {
			total += listProduct.get(i).getIn_stock();
		}

		model.addAttribute("total", total);
		model.addAttribute("listProduct", listProduct);

		return "order/instockProduct";
	}

	// Thống kê tồn kho theo mã sp
	@RequestMapping(value = "instockId")
	public String instockProductId(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		String id_hanghoa = req.getParameter("search");
		int id_hh = Integer.parseInt(id_hanghoa);
		HangHoa product = getHH(id_hh);
		if (product == null) {
			model.addAttribute("message", "Mã hàng hóa không tồn tại!");
		} else {
			// Tính tống sl nhập
			Session session = factory.getCurrentSession();
			String hql = "SELECT ISNULL(SUM(ctdh.qty),0) FROM DatHang dh, ChiTietDatHang ctdh "
					+ "WHERE dh.id = ctdh.dathangs.id AND ctdh.hanghoas.id = :id_hh " + "AND dh.status_delivery = 4";
			Query query = session.createQuery(hql);
			query.setParameter("id_hh", id_hh);
			long tongBan = (long) query.uniqueResult();

			model.addAttribute("tongBan", tongBan);
			model.addAttribute("instock", product.getIn_stock());
			model.addAttribute("id_hh", product.getId());
		}

		return "order/instockId";
	}

	// Thống kê tồn kho 1 sản phẩm
	@RequestMapping("searchProduct")
	public String searchProduct(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		String keyword = req.getParameter("search").trim();

		while (keyword.indexOf(" ") != -1) {
			keyword = keyword.replaceAll(" ", "");
		}
		System.out.println(keyword);

		Session session = factory.getCurrentSession();
		String hql = "FROM HangHoa WHERE id =" + Integer.parseInt(keyword) + "";
		Query query = session.createQuery(hql);
		List<HangHoa> listProduct = query.list();

		if (listProduct.isEmpty()) {
			model.addAttribute("message", "Mã hàng hóa không tồn tại!");
		} else {
			model.addAttribute("listProduct", listProduct);
		}

		return "order/instockProduct";
	}

	// Thống kê số lượng đơn hàng, tổng trị mua của khách
	@RequestMapping(value = "countSumId")
	public String countSumIdCustomer(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		String sdt = req.getParameter("search").trim();
		System.out.println(sdt);

		Session session = factory.getCurrentSession();
		String hql = "SELECT u FROM UserID u, ThongTinUser t " + "WHERE u.thongtinuser.id = t.id "
				+ "AND u.role.id = 1 AND t.sdt = :sdt";
		Query query = session.createQuery(hql);
		query.setParameter("sdt", sdt);

		UserID id = (UserID) query.uniqueResult();
		System.out.println(id);
		if (id == null) {
			model.addAttribute("message", "SDT khách hàng không tồn tại!");
			return "order/indexCount";
		} else {
			String hql1 = "SELECT ISNULL(SUM(amount),0) " 
					+ "FROM DatHang WHERE id_khachhang = :id_kh "
					+ "AND status_delivery = 4";
			Query query1 = session.createQuery(hql1);
			query1.setParameter("id_kh", id.getUsername());
			Long total = (Long) query1.uniqueResult();

			String hql2 = "FROM DatHang WHERE id_khachhang = :id_kh" 
						+ " AND status_delivery = 4";
			Query query2 = session.createQuery(hql2);
			query2.setParameter("id_kh", id.getUsername());
			List<DatHang> listCountSum = query2.list();

			model.addAttribute("user", id);
			model.addAttribute("total", total);
			model.addAttribute("countSumId", listCountSum);
			return "order/countSumId";
		}

		// return "order/countSumId";
	}

	@RequestMapping(value = "export/{id}", method = RequestMethod.GET)
	public String viewExport(ModelMap model, HttpServletRequest req, HttpServletResponse response,
			@PathVariable("id") Integer id) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() == 1 && user.getRole().getId() == 7) {
			return "redirect:/admin/index.htm";
		}
		Session session = factory.getCurrentSession();
		DatHang order = (DatHang) session.get(DatHang.class, id);
		String hql = "From ChiTietDatHang WHERE dathang_id=:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		List<ChiTietDatHang> list = query.list();

		// List<ChiTietDatHang> list = (List<ChiTietDatHang>)
		// order.getDetail_order();
		String hql1 = "FROM HoaDon WHERE dathang_id = :id";
		Query query1 = session.createQuery(hql1);
		query1.setParameter("id", id);
		HoaDon export = (HoaDon) query1.uniqueResult();
		if (export == null) {
			model.addAttribute("export", new HoaDon());
		} else {
			model.addAttribute("export", export);
		}
		model.addAttribute("detail_order", list);
		model.addAttribute("order", order);

		return "order/export";
	}

	@RequestMapping(value = "export/{id}", method = RequestMethod.POST)
	public String printExport(ModelMap model, HttpServletRequest req, HttpServletResponse response,
			@ModelAttribute("export") HoaDon export, @PathVariable("id") Integer id) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() == 1 && user.getRole().getId() == 7) {
			return "redirect:/admin/index.htm";
		}
		Session session = factory.getCurrentSession();
		DatHang order = (DatHang) session.get(DatHang.class, id);
		Session session1 = factory.openSession();
		Transaction t = session1.beginTransaction();
		try {
			export.setDate(new Date());
			export.setDathang(order);
			session1.save(export);
			t.commit();
			model.addAttribute("export", export);
			model.addAttribute("detail_order", export.getDathang().getDetail_order());
			model.addAttribute("order", export.getDathang());
			model.addAttribute("message", "In thành công!");

		} catch (Exception e) {
			t.rollback();
			model.addAttribute("error", "Hóa đơn đã được tạo!");
		} finally {
			session1.close();
		}
		return "order/export";
	}

	// load danh sách Nhân Viên đơn hàng ra combobox
	@ModelAttribute("employee")
	public List<UserID> getEmployees() {
		Session session = factory.getCurrentSession();
		String hql = "FROM UserID WHERE role_id != 1 AND role_id != 7";
		Query query = session.createQuery(hql);
		List<UserID> list = query.list();
		return list;
	}

	@ModelAttribute("shipper")
	public List<UserID> getShippers() {
		Session session = factory.getCurrentSession();
		String hql = "FROM KVGiaoHang";
		Query query = session.createQuery(hql);
		List<UserID> list = query.list();
		return list;
	}

	@ModelAttribute("product")
	public List<HangHoa> getProducts() {
		Session session = factory.getCurrentSession();
		String hql = "FROM HangHoa";
		Query query = session.createQuery(hql);
		List<HangHoa> list = query.list();
		return list;
	}

	public HangHoa getHH(int productId) {
		Session session = factory.openSession();
		String hql = "FROM HangHoa WHERE id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", productId);
		HangHoa hh = (HangHoa) query.uniqueResult();

		return hh;
	}

	public int totalItem() {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			Query query = session.createQuery("SELECT count(*) FROM DatHang");
			Long obj = (Long) query.uniqueResult();
			t.commit();
			return obj.intValue();
		} catch (Exception ex) {
			if (t != null) {
				t.rollback();
			}
			ex.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return 0;
	}

	public List<DatHang> getListNav(int start, int limit) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("FROM DatHang ORDER BY created DESC");
			query.setFirstResult(start);
			query.setMaxResults(limit);
			List<DatHang> list = query.list();
			transaction.commit();
			return list;
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			ex.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return null;
	}

	private double sumItemByDate(Date date) {
		Session session = factory.openSession();
		Transaction t = null;
		try {
			Query query = session.createQuery("SELECT ISNULL(SUM(amount), 0) FROM DatHang WHERE created =:date");
			query.setDate("date", date);
			Long obj = (Long) query.uniqueResult();
			t.commit();
			return obj.doubleValue();
			// System.err.println(query.uniqueResult());
		} catch (Exception ex) {
			if (t != null) {
				t.rollback();
			}
			ex.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return 0;
	}

	public static Date addDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public static Date subDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, -days);
		return cal.getTime();
	}

	private String covertD2S(Date date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyy");
		return df.format(date);
	}
}
