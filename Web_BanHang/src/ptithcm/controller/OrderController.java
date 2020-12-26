package ptithcm.controller;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import Model.Cart;
import ptithcm.entity.ChiTietDatHang;
import ptithcm.entity.DanhGia;
import ptithcm.entity.DatHang;
import ptithcm.entity.HangHoa;
import ptithcm.entity.KhuyenMai;
import ptithcm.entity.LoaiHang;
import ptithcm.entity.ThongTinUser;
import ptithcm.entity.UserID;

@Transactional
@Controller
@RequestMapping("/user/")
public class OrderController {
	@Autowired
	SessionFactory factory;

	@Autowired
	JavaMailSender mailer;

	Date date = new Date();

	// lấy danh sách sản phẩm
	@SuppressWarnings("unchecked")
	public List<HangHoa> getProducts() {
		Session session = factory.getCurrentSession();
		String hql = "FROM HangHoa";
		Query query = session.createQuery(hql);
		List<HangHoa> list = query.list();
		return list;
	}

	// load danh sách hãng xe ra combobox
	@ModelAttribute("brand")
	public List<LoaiHang> getBrands() {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiHang";
		Query query = session.createQuery(hql);
		List<LoaiHang> list = query.list();
		return list;
	}
	
	@ModelAttribute("promotion")
	public List<KhuyenMai> getPromotions() {
		Session session = factory.getCurrentSession();
		String hql = "FROM KhuyenMai WHERE date_start <= :date AND :date <= date_finish";
		Query query = session.createQuery(hql);
		query.setDate("date", date);
		List<KhuyenMai> list = query.list();
		
		return list;
	}
	
	@RequestMapping(value = "payment/{id}")
	public String payment(ModelMap model, HttpSession session, HttpServletRequest request,
			@PathVariable("id") Integer id) {
		
		HttpSession user_session = request.getSession();
		// UserID user = (UserID) user_session.getAttribute("user");
		user_session.getAttribute("user");
		if (user_session.getAttribute("user") == null) {
			return "redirect:/user/login.htm";
		}
		if (user_session.getAttribute("user") == null) {
			model.addAttribute("isLogin", false);
		}
		if (user_session.getAttribute("user") != null) {
			model.addAttribute("isLsogin", true);
		}
		Session session1 = factory.getCurrentSession();
		DatHang order = (DatHang) session1.get(DatHang.class, id);
		
		model.addAttribute("order", order);
		return "user/payment";
	}

	@RequestMapping(value = "buynow", method = RequestMethod.GET)
	public String buyNow(ModelMap model, HttpSession session, HttpServletRequest request) {
		HashMap<Integer, Cart> cartItems = (HashMap<Integer, Cart>) session.getAttribute("cart");

		HttpSession user_session = request.getSession();
		// UserID user = (UserID) user_session.getAttribute("user");
		user_session.getAttribute("user");
		if (user_session.getAttribute("user") == null) {
			return "redirect:/user/login.htm";
		}
		if (user_session.getAttribute("user") == null) {
			model.addAttribute("isLogin", false);
		}
		if (user_session.getAttribute("user") != null) {
			model.addAttribute("isLsogin", true);
		}
		if (cartItems == null) {
			cartItems = new HashMap<>();
		}
		if (cartItems.isEmpty()) {
			model.addAttribute("message", "Không có sản phẩm nào trong giỏ hàng của bạn");
		} else {
			session.setAttribute("totalprice", totalPrice(cartItems));
			session.setAttribute("cart", cartItems);

		}
		for (Entry<Integer, Cart> entry1 : cartItems.entrySet()) {
			if (entry1.getValue().getQuantity() > entry1.getValue().getProduct().getIn_stock()) {
				model.addAttribute("message", "Sản phẩm ' " + entry1.getValue().getProduct().getName() + " ' "
						+ " không đủ số lượng để đặt!");
				return "user/buynow";
			}
		}
		Session session1 = factory.openSession();
		String hql = "FROM KhuyenMai WHERE date_start <= :date AND date_finish >= :date";
		Query query = session1.createQuery(hql);
		query.setDate("date", date);
		List<KhuyenMai> list = query.list();
		
		String status_payment = request.getParameter("st");
		String mess_payment = request.getParameter("tx");
		System.out.println(status_payment);
		System.out.println(mess_payment);
		if(status_payment.equals("") == false){
			if(status_payment.equals("Completed") == true){
				System.out.println("completed TRue");
				model.addAttribute("completed_payment", "complete");
				model.addAttribute("message_payment", mess_payment);
			}
		}
		model.addAttribute("order", new DatHang());
		model.addAttribute("promotion", list);
		return "user/buynow";
	}

	@RequestMapping(value = "buynow", method = RequestMethod.POST)
	public String buyNow(ModelMap model, HttpSession session, @ModelAttribute("order") DatHang order,
			HttpServletRequest request, ChiTietDatHang detail_order) {
		HashMap<Integer, Cart> cartItems = (HashMap<Integer, Cart>) session.getAttribute("cart");
		if (cartItems == null) {
			cartItems = new HashMap<>();
		}
		String payment = request.getParameter("payment");
		String message = request.getParameter("message");
		String makm = request.getParameter("makm");
		HttpSession user_session = request.getSession();
		UserID user = (UserID) user_session.getAttribute("user");
		
		user_session.getAttribute("user");
		if (user_session.getAttribute("user") == null) {
			return "redirect:/user/login.htm";
		}
		if (user_session.getAttribute("user") == null) {
			model.addAttribute("isLogin", false);
		}
		if (user_session.getAttribute("user") != null) {
			model.addAttribute("isLsogin", true);
		}
		// Thông tin gửi mail
		String from = "n16dccn055@student.ptithcm.edu.vn";
		String subject = "ĐƠN ĐẶT HÀNG CỦA BẠN!";
		String body = "";
		String to = user.getThongtinuser().getEmail();
		MimeMessage mail = mailer.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail);

		// kiểm tra số lượng số lượng khách đặt so với số lượng tồn
		for (Entry<Integer, Cart> entry1 : cartItems.entrySet()) {
			if (entry1.getValue().getQuantity() > entry1.getValue().getProduct().getIn_stock()) {
				model.addAttribute("message", "Sản phẩm ' " + entry1.getValue().getProduct().getName() + " ' "
						+ " không đủ số lượng để đặt!");
				return "user/buynow";
			}
		}
		Session session1 = factory.openSession();
		Transaction t = session1.beginTransaction(); 
		
		order.setStatus_payment(false);
		order.setStatus_delivery(1);
		order.setCreated(new Date());
		order.setMessage(message);
		order.setUserid(user);
		if (payment.equals("offline")) {
			order.setPayment("1");
		} else {
			order.setPayment("2");
		}
		try {
			if(makm.equals("") == false && makm.equals("Chọn mã") == false) {
				Session session2 = factory.getCurrentSession();
				String hql1 = "FROM KhuyenMai WHERE makm=:makm AND "
						+ "date_start <= :date AND :date <= date_finish";
				Query query1 = session1.createQuery(hql1);
				query1.setParameter("makm", makm);
				query1.setDate("date", date);
				KhuyenMai test = (KhuyenMai) query1.uniqueResult();
				List<KhuyenMai> list = query1.list();
				if(list.isEmpty()){
					model.addAttribute("message", "Mã khuyến mãi không có hiệu lực!");
					return "user/buynow";
				}
				order.setKhuyenmai(test);
				order.setAmount(totalPrice(cartItems) - (totalPrice(cartItems)*list.get(0).getDiscount()/100));
				session1.save(order);
				for (Entry<Integer, Cart> entry : cartItems.entrySet()) {
					detail_order = new ChiTietDatHang();
					detail_order.setDathangs(order);
					detail_order.setHanghoas(entry.getValue().getProduct());
					detail_order.setQty(entry.getValue().getQuantity());
					detail_order.setPrice(entry.getValue().getPrice() - 
									(entry.getValue().getPrice()*list.get(0).getDiscount()/100));
					session1.save(detail_order);
				}
			}
			else {
				order.setAmount(totalPrice(cartItems));
				session1.save(order);
				for (Entry<Integer, Cart> entry : cartItems.entrySet()) {
					detail_order = new ChiTietDatHang();
					detail_order.setDathangs(order);
					detail_order.setHanghoas(entry.getValue().getProduct());
					detail_order.setQty(entry.getValue().getQuantity());
					detail_order.setPrice(entry.getValue().getPrice());
					session1.save(detail_order);
				}
			}
			
//			session1.save(detail_order);
			// Lưu thông tin từng sản phẩm
			for (Entry<Integer, Cart> entry : cartItems.entrySet()) {
				body += "Chi tiết đơn hàng của bạn " + "\n" + "Tên sản phẩm: " + entry.getValue().getProduct().getName()
						+ "\n" + "Số lượng: " + entry.getValue().getQuantity() + " || " + "Giá: "
						+ entry.getValue().getProduct().getPrice() * (100 - entry.getValue().getProduct().getDiscount())
								/ 100
						+ "VNĐ" + "\n";

				// update số lượng bán và trừ số lượng tồn
				Session session2 = factory.openSession();
				String hql = "UPDATE HangHoa SET count_buy = count_buy + (:countbuy), "
						+ "in_stock= in_stock-(:countbuy) WHERE id = :id";
				Query query = session2.createQuery(hql);
				query.setParameter("countbuy", entry.getValue().getQuantity());
				query.setParameter("id", entry.getValue().getProduct().getId());
				int result = query.executeUpdate();
			}

			// gửi mail
			body += "Tổng tiền: " + order.getAmount() + " VNĐ " + "\n" + "Ngày đặt: " + order.getCreated() + "\n"
					+ "Hình thức thanh toán: " + order.getPayment() + "\n " + "Tên khách hàng: "
					+ user.getThongtinuser().getFullname() + " || " + "Số điện thoại: "
					+ user.getThongtinuser().getSdt() + " || " + "Địa chỉ giao hàng: "
					+ user.getThongtinuser().getAddress() + "\n" + "Ghi chú: " + order.getMessage() + "\n"
					+ "SHOWROOM Nội Thất & Thiết Bị Vệ Sinh TATA xin cảm ơn !";
			helper.setFrom(from, from);
			helper.setTo(to);
			helper.setReplyTo(from, from);
			helper.setSubject(subject);
			helper.setText(body);
			mailer.send(mail);
			// model.addAttribute("message", "Đặt hàng thành công !");
			t.commit();
			// xóa sản phẩm khỏi giỏ hàng
			cartItems = new HashMap<>();
			session.setAttribute("cart", cartItems);
			session.setAttribute("totalprice", 0);
			if(order.getPayment().equals("2") == true){
				return "redirect:/user/payment/" + order.getId() + ".htm";
			}
			else {
				return "redirect:/user/success.htm?cm=&st=&tx=";
			}
			
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Đặt hàng thất bại !");
		} finally {
			session1.close();
		}

		return "user/buynow";

	}

	// =======XÁC NHẬN THÔNG TIN GIAO HÀNG========
	@RequestMapping("confirmInfo/{id}")
	public String confirmInfo(ModelMap model, @PathVariable("id") Integer id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		HttpSession user_session = request.getSession();
		user_session.getAttribute("user");
		if (user_session.getAttribute("user") == null) {
			return "redirect:/user/index.htm";
		}
		if (user_session.getAttribute("user") == null) {
			model.addAttribute("isLogin", false);
		}
		if (user_session.getAttribute("user") != null) {
			model.addAttribute("isLogin", true);
		}

		Session session1 = factory.getCurrentSession();
		ThongTinUser infouser = (ThongTinUser) session1.get(ThongTinUser.class, id);

		if (infouser.getSdt().equals("") || infouser.getAddress().equals("") || infouser.getEmail().equals("")
				|| infouser.getFullname().equals("")) {

			model.addAttribute("message", "Vui lòng cập nhật đủ thông tin trước khi đặt hàng");

		}

		model.addAttribute("infouser", infouser);
		return "user/confirmInfo";
	}

	// =========QUẢN LÝ ĐƠN HÀNG============
	@RequestMapping(value = "mycart")
	public String viewMyCart(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession user_session = request.getSession();
		UserID userid = (UserID) user_session.getAttribute("user");
		user_session.getAttribute("user");
		if (user_session.getAttribute("user") == null) {
			return "redirect:/user/login.htm";
		}
		if (user_session.getAttribute("user") == null) {
			model.addAttribute("isLogin", false);
		}
		if (user_session.getAttribute("user") != null) {
			model.addAttribute("isLogin", true);
		}

		model.put("order", getListNav(0, 5, userid.getUsername()));
		model.put("totalitem", totalItem(userid.getUsername()) / 5);

		return "user/mycart";
	}

	@RequestMapping(value = "mycart/{page}", method = RequestMethod.GET)
	public String viewMyCartListByPage(ModelMap model, HttpServletRequest request, HttpServletResponse response,
			@PathVariable("page") int page) {
		HttpSession user_session = request.getSession();
		UserID userid = (UserID) user_session.getAttribute("user");
		user_session.getAttribute("user");
		if (user_session.getAttribute("user") == null) {
			return "redirect:/user/login.htm";
		}
		if (user_session.getAttribute("user") == null) {
			model.addAttribute("isLogin", false);
		}
		if (user_session.getAttribute("user") != null) {
			model.addAttribute("isLogin", true);
		}

		model.put("order", getListNav((page - 1) * 5, 5, userid.getUsername()));
		model.put("totalitem", totalItem(userid.getUsername()) / 5);

		return "user/mycart";
	}

	@RequestMapping(value = "detailMycart/{id}")
	public String detailMyCart(ModelMap model, @PathVariable("id") Integer id, 
			HttpServletRequest request) {

		HttpSession user_session = request.getSession();
		UserID userid = (UserID) user_session.getAttribute("user");
		user_session.getAttribute("user");
		if (user_session.getAttribute("user") == null) {
			return "redirect:/user/login.htm";
		}
		if (user_session.getAttribute("user") == null) {
			model.addAttribute("isLogin", false);
		}
		if (user_session.getAttribute("user") != null) {
			model.addAttribute("isLogin", true);
		}
		Session session = factory.getCurrentSession();
		DatHang order = (DatHang) session.get(DatHang.class, id);
		String hql = "From ChiTietDatHang WHERE dathang_id=:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		List<ChiTietDatHang> list = query.list();
//		List<ChiTietDatHang> list = (List<ChiTietDatHang>) order.getDetail_order();
		model.addAttribute("detail_order", list);
//		System.out.println(list);
		return "user/detailMycart";
	}

	// ======HỦY ĐƠN HÀNG======
	// Xóa sản phẩm
	@RequestMapping("delete/{id}")
	public String cancelOrder(ModelMap model, @PathVariable("id") Integer id, HttpServletRequest req,
			HttpServletResponse response) {

		HttpSession user_session = req.getSession();
		user_session.getAttribute("user");
		if (user_session.getAttribute("user") == null) {
			return "redirect:/user/login.htm";
		}
		if (user_session.getAttribute("user") == null) {
			model.addAttribute("isLogin", false);
		}
		if (user_session.getAttribute("user") != null) {
			model.addAttribute("isLogin", true);
		}

		Session session = factory.openSession();
		DatHang order = (DatHang) session.get(DatHang.class, id);
		List<ChiTietDatHang> list = (List<ChiTietDatHang>) order.getDetail_order();
		date = order.getCreated();
		Transaction t = session.beginTransaction();

		Session session1 = factory.getCurrentSession();
		for (int i = 0; i < list.size(); i++) {
			String hql1 = "UPDATE HangHoa SET count_buy = count_buy - (:countbuy), in_stock= in_stock +(:countbuy) WHERE id = :id";
			Query query1 = session1.createQuery(hql1);
			query1.setParameter("countbuy", list.get(i).getQty());
			query1.setParameter("id", list.get(i).getHanghoas().getId());
			int result = query1.executeUpdate();
		}
		// System.out.println(id);
		order.setStatus_delivery(5);
		order.setCreated(date);
		try {
			session.update(order);
			t.commit();
			// model.addAttribute("message", "Hủy đơn thành công !");
			// showAlert("Hủy đơn thành công");
			return "redirect:/user/mycart.htm";
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Hủy đơn thất bại !");
		} finally {
			session.close();
		}

		model.addAttribute("order", order);
		return "user/detailMycart";
	}

	public long totalPrice(HashMap<Integer, Cart> cartItems) {
		int count = 0;
		for (Entry<Integer, Cart> list : cartItems.entrySet()) {
			count += (list.getValue().getProduct().getPrice() * (100 - list.getValue().getProduct().getDiscount())
					/ 100) * list.getValue().getQuantity();
		}
		return count;
	}

	@RequestMapping("success")
	public String successOrder(ModelMap model, HttpServletRequest request) {
		HttpSession user_session = request.getSession();
		if (user_session.getAttribute("user") == null) {
			return "redirect:/user/index.htm";
		}
		
		if (user_session.getAttribute("user") == null) {
			model.addAttribute("isLogin", false);
		}
		if (user_session.getAttribute("user") != null) {
			model.addAttribute("isLogin", true);
		}
		UserID user = (UserID) user_session.getAttribute("user");
		String status_payment = request.getParameter("st");
		String iddh = request.getParameter("cm");
		String message_payment = request.getParameter("tx");
		if(status_payment.equals("Completed") == true){
			Session session = factory.openSession();
			String hql = "UPDATE DatHang SET status_payment = true, "
					+ "message_payment = (:message), status_delivery = 2 "
					+ "WHERE id = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", Integer.valueOf(iddh));
			query.setParameter("message", message_payment);
			int result = query.executeUpdate();
		}
		else if(status_payment.equals("DECLINED") == true){
			model.addAttribute("message", "Thanh toán chưa thể thực hiện");
		}

		return "user/success";
	}

	public int totalItem(String id) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			Query query = session.createQuery("SELECT count(*) FROM DatHang WHERE id_khachhang = :idKH");
			query.setParameter("idKH", id);
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

	public List<DatHang> getListNav(int start, int limit, String id) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("FROM DatHang WHERE id_khachhang = :idKH ORDER BY id DESC");
			query.setParameter("idKH", id);
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

	public HangHoa getHH(int productId) {
		Session session = factory.openSession();
		String hql = "FROM HangHoa WHERE id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", productId);
		HangHoa hh = (HangHoa) query.uniqueResult();

		return hh;
	}

}
