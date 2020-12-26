package ptithcm.controller;

import java.io.Serializable;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import ptithcm.entity.DanhGia;
import ptithcm.entity.DatHang;
import ptithcm.entity.HangHoa;
import ptithcm.entity.KhuyenMai;
import ptithcm.entity.LoaiHang;
import ptithcm.entity.Phuong;
import ptithcm.entity.Quan;
import ptithcm.entity.QuanPhuong;
import ptithcm.entity.Role;
import ptithcm.entity.Slider;
import ptithcm.entity.ThanhPho;
import ptithcm.entity.ThongTinUser;
import ptithcm.entity.UserID;

@Transactional
@Controller
@RequestMapping("/user/")
public class UserController {
	private static final Map<String, UserID> mapUsers = new HashMap<String, UserID>();
	@Autowired
	SessionFactory factory;
	
	@Autowired
	JavaMailSender mailer;

	// lấy danh sách slider
	@SuppressWarnings("unchecked")
	public List<Slider> getSliders() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Slider";
		Query query = session.createQuery(hql);
		List<Slider> list = query.list();
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
	
	@ModelAttribute("district")
	public List<Quan> getDistricts() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Quan";
		Query query = session.createQuery(hql);
		List<Quan> list = query.list();
		return list;
	}
	
	@ModelAttribute("ward")
	public List<Phuong> getWards() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Phuong ORDER BY name ASC";
		Query query = session.createQuery(hql);
		List<Phuong> list = query.list();
		return list;
	}
	
	@ModelAttribute("city")
	public List<ThanhPho> getcitys() {
		Session session = factory.getCurrentSession();
		String hql = "FROM ThanhPho";
		Query query = session.createQuery(hql);
		List<ThanhPho> list = query.list();
		return list;
	}
	
	@ModelAttribute("test")
	public List<DanhGia> getDanhgias() {
		Session session = factory.getCurrentSession();
		String hql = "FROM DanhGia";
		Query query = session.createQuery(hql);
		List<DanhGia> list = query.list();
		return list;
	}
	
	// lấy ra danh sách sản phẩm
		@SuppressWarnings("unchecked")
		public List<HangHoa> getProducts() {
			Session session = factory.getCurrentSession();
			String hql = "FROM HangHoa ORDER BY id DESC";
			Query query = session.createQuery(hql);
			List<HangHoa> list = query.list();
			return list;
		}

	// lấy ra danh sách người dùng
	@SuppressWarnings("unchecked")
	public List<UserID> getUsers() {
		Session session = factory.getCurrentSession();
		String hql = "FROM UserID";
		Query query = session.createQuery(hql);
		List<UserID> list = query.list();
		return list;
	}

	// lấy danh sách thông tin user
	@SuppressWarnings("unchecked")
	public List<ThongTinUser> getInfoUsers() {
		Session session = factory.getCurrentSession();
		String hql = "FROM ThongTinUser";
		Query query = session.createQuery(hql);
		List<ThongTinUser> list = query.list();
		return list;
	}

	@RequestMapping(value = "index")
	public String viewIndex(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		HttpSession user_session = request.getSession();
		user_session.getAttribute("user");
		if (user_session.getAttribute("user") == null) {
			model.addAttribute("isLogin", false);
		}
		if (user_session.getAttribute("user") != null) {
			model.addAttribute("isLogin", true);
		}
		
		model.put("product", getProducts());
		model.addAttribute("slider", getSliders());
		return "user/index";
	}
	

	@RequestMapping(value = "search")
	public String searchProduct(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession user_session = request.getSession();
		user_session.getAttribute("user");
		if (user_session.getAttribute("user") == null) {
			model.addAttribute("isLogin", false);
		}
		if (user_session.getAttribute("user") != null) {
			model.addAttribute("isLogin", true);
		}
		String keyword = request.getParameter("search");
		keyword = covertToString(keyword).trim();
		while (keyword.indexOf("  ") != -1) {
			keyword = keyword.replaceAll("  ", " ");
		}

		System.out.println(keyword);
		
		Session session = factory.getCurrentSession();
		String hql = "FROM HangHoa WHERE dbo.ufn_removeMark(name) LIKE '%" + keyword + "%'";
		Query query = session.createQuery(hql);
		List<HangHoa> list = query.list();
		if(list.isEmpty()){
			model.addAttribute("message", "Không có sản phẩm nào!!");
		}
		else {
			model.addAttribute("product", list);
		}
		return "user/search";
	}

	// đăng xuất
	@RequestMapping("logout")
	public String logout(HttpServletRequest req) {
		HttpSession logout_session = req.getSession();
		logout_session.removeAttribute("user");
		return "redirect:/user/index.htm";
	}

	// đăng nhập
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String showLogin() {
		return "user/login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, ModelMap model) {
		String username = request.getParameter("your_name");
		String password = md5(request.getParameter("your_pass"));
		if (username.trim().length() == 0) {
			model.addAttribute("message1", "Vui lòng nhập username!!");
		}
		if (password.trim().length() == 0) {		
			model.addAttribute("message2", "Vui lòng nhập Password!!");
		}
		

//		System.out.println(username + password);
		Session session = factory.getCurrentSession();
		String hql = "FROM UserID";
		Query query = session.createQuery(hql);
		List<UserID> list = query.list();
		// lấy ra id của role khách hàng
		String hql1 = "SELECT id FROM Role WHERE  id = 1 ";
		Query query2 = session.createQuery(hql1);
		Integer id = (Integer) query2.uniqueResult();
		for (UserID hen : list) {
			if (username.equals(hen.getUsername()) == true) {
				if (password.equals(hen.getPassword()) == false) {
					model.addAttribute("message", "Tên đăng nhập hoặc mật khẩu không đúng!");
					return "user/login";
				} else {
					if (hen.getRole().getId() == id) {
						HttpSession user_session = request.getSession();
						user_session.setAttribute("user", hen);
//						model.addAttribute("message", "true");
						return "redirect:/user/index.htm";
					} else {
						model.addAttribute("message", "Tài khoản không tồn tại");
						return "user/login";
					}
				}
			} else {
				if (password.equals(hen.getPassword()) == true) {
					model.addAttribute("message", "Tên đăng nhập hoặc mật khẩu không đúng!");
				}
			}
		}
		return "user/login";
	}

	// Đăng ký
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String showRegister(ModelMap model) {
		
		model.addAttribute("user", new UserID());
		return "user/register";
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String register(ModelMap model, HttpServletRequest req, @ModelAttribute("user") UserID user,
			BindingResult errors, ThongTinUser infouser) {
		String pattern = ".{6,}";
		String username = req.getParameter("username").trim();
		String password = req.getParameter("password").trim();
		String repass = req.getParameter("repassword").trim();
		Role role = new Role();
//		QuanPhuong qp = new QuanPhuong();
		role.setId(1);
		user.setRole(role);
		infouser.setFullname("");
		infouser.setSdt("");
		infouser.setEmail("");
		infouser.setAddress("");
		infouser.setQuanphuong(null);
		
		if (username.trim().length() == 0) {
			errors.rejectValue("username", "user", "Vui Lòng Nhập Tên Đăng Nhập!");
		}
		if (password.trim().length() == 0) {
			errors.rejectValue("password", "user", "Vui Lòng Nhập Mật Khẩu!");
		}
		if (repass.trim().length() == 0) {
			model.addAttribute("message_pass", "Vui lòng xác nhận mật khẩu !");
			return "user/register";
		}
		if (errors.hasErrors()) {

		}else {
			for (UserID hen : getUsers()) {
				if (hen.getUsername().equals(username) == true) {
					errors.rejectValue("username", "user", "Tên đăng nhập không được trùng!");
					return "user/register";
				}
			}
			if(!password.matches(pattern)) {
				errors.rejectValue("password", "user", "Mật khẩu phải có ít nhất 6 ký tự!");
				return "user/register";
			}
			if (repass.trim().equals(user.getPassword().trim()) == false) {
				model.addAttribute("message_pass", "Xác nhận mật khẩu không trùng khớp, vui lòng nhập lại !");
				return "user/register";
			}
			user.setUsername(username);
			user.setPassword(md5(password));
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			try {
				session.save(infouser);
				user.setThongtinuser(infouser);
				session.save(user);
				t.commit();
				model.addAttribute("message", "Thêm thành công!");
				return "redirect:/user/login.htm";
			} catch (Exception e) {
				t.rollback();
				model.addAttribute("message", "Thêm thất bại!");
			} finally {
				session.close();
			}
		}
		return "user/register";
	}

	// Quản lí thông tin
	@RequestMapping(value = "managerAccount", method = RequestMethod.GET)
	public String managerAccount(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		HttpSession user_session = request.getSession();
		UserID user = (UserID) user_session.getAttribute("user");

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

		model.addAttribute("user", user);
		return "user/managerAccount";
	}

	// Chỉnh sửa thông tin
	@RequestMapping(value = "changeAccount", method = RequestMethod.GET)
	public String changeAccount(ModelMap model, HttpServletRequest request, 
			HttpServletResponse response) {
		// model.addAttribute("infouser", new ThongTinUser());

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
		return "user/changeAccount";
	}

	@RequestMapping("changeAccount/{id}")
	public String changeAccount(ModelMap model, @PathVariable("id") Integer id, 
			HttpServletRequest req, HttpServletResponse response) {

		HttpSession user_session = req.getSession();
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
		
		Session session = factory.getCurrentSession();
		String hql = "FROM ThongTinUser WHERE id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		ThongTinUser infouser = (ThongTinUser) query.uniqueResult();
//		ThongTinUser infouser = (ThongTinUser) session.get(ThongTinUser.class, id);
		
//		System.out.println(infouser.getQuanphuong().getQuans().getId());
		model.addAttribute("infouser", infouser);

		return "user/changeAccount";
	}

	@RequestMapping(value = "changeAccount", method = RequestMethod.POST)
	public String changeAccount(ModelMap model, @ModelAttribute("infouser") ThongTinUser infouser,
			HttpServletRequest req, HttpServletResponse response, BindingResult errors) {

		HttpSession user_session = req.getSession();
		UserID user = (UserID) user_session.getAttribute("user");
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
		String id_p = req.getParameter("ward");
		String id_q = req.getParameter("district");
		System.out.println( id_p + id_q);
//		String id_p = req.getParameter("id_phuong");
//		String id_q = req.getParameter("id_quan");
		int id_phuong = Integer.valueOf(id_p);
		if( id_p.equals("") == true ){
			model.addAttribute("message1", "Vui lòng chọn địa chỉ hợp lệ!");
			return "user/changeAccount";
		}
		else if(id_q.equals("") == true) {
			model.addAttribute("message1", "Vui lòng chọn địa chỉ hợp lệ!");
			return "user/changeAccount";
		}
		else {
			Session session = factory.getCurrentSession();
			String hql = "SELECT isnull(id, 0) FROM QuanPhuong WHERE id_phuong = :id_phuong AND id_quan = :id_quan";
			Query query = session.createQuery(hql);
			query.setParameter("id_quan", id_q);
			query.setInteger("id_phuong", id_phuong);
			List tmp = query.list();
			if(tmp.isEmpty()){
				model.addAttribute("message1", "Địa chỉ không hợp lệ! Vui lòng kiểm tra lại");
//				return "user/changeAccount";
			}
			else {
				QuanPhuong qp = (QuanPhuong) session.get(QuanPhuong.class, (Serializable) tmp.get(0));
				infouser.setQuanphuong(qp);
				if (infouser.getFullname().length() == 0) {
					errors.rejectValue("fullname", "infouser", "Vui Lòng Nhập Họ Tên!");
				} else if (infouser.getEmail().length() == 0) {
					errors.rejectValue("email", "infouser", "Vui Lòng Nhập Email!");
				} else if (checkEmail(infouser.getEmail()) == false) {
					errors.rejectValue("email", "infouser", "Email không hợp lệ!");
				} else if (infouser.getSdt().length() == 0) {
					errors.rejectValue("sdt", "infouser", "Vui Lòng Nhập sdt!");
				} else if (checkPhone(infouser.getSdt()) == false) {
					errors.rejectValue("sdt", "infouser", "SDT không hợp lệ!");
				} else if (infouser.getAddress().length() == 0) {
					errors.rejectValue("address", "infouser", "Vui Lòng Nhập Địa chỉ!");
				} else if (errors.hasErrors()) {
					// model.addAttribute("message", "Sửa Các Lỗi Sau!");
				} else {
					Session session1 = factory.openSession();
					Transaction t = session1.beginTransaction();
					try {
						user.setThongtinuser(infouser);
						session1.update(user);
						session1.update(infouser);
						user_session.setAttribute("user", user);
						t.commit();
						model.addAttribute("message", "Cập nhật thành công");
//						 return "redirect:/user/buynow.htm";
					} catch (Exception ex) {
						t.rollback();
						model.addAttribute("message1","Cập nhật thất bại!");
					} finally {
						session1.close();
					}
				}
			}
		}
		
		
		return "user/changeAccount";
	}

	// Thay đổi mật khẩu
	@RequestMapping(value = "changePassword", method = RequestMethod.GET)
	public String changePass(ModelMap model, HttpServletRequest req, HttpServletResponse response) {
		HttpSession user_session = req.getSession();
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

		return "user/changePassword";
	}

	@RequestMapping(value = "changePassword", method = RequestMethod.POST)
	public String changepass(ModelMap model, HttpServletRequest req, HttpServletResponse response) {
		String pattern = ".{6,}";
		HttpSession user_session = req.getSession();
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
		UserID user = (UserID) user_session.getAttribute("user");
		String oldpass = md5(req.getParameter("oldpass"));
		String newpass = req.getParameter("newpass");
		String renewpass = req.getParameter("renewpass");

		if (oldpass.length() == 0) {
			model.addAttribute("message", "Vui lòng điền đủ thông tin !");
			return "user/changePassword";
		}
		if (newpass.length() == 0) {
			model.addAttribute("message", "Vui lòng điền đủ thông tin !");
			return "user/changePassword";
		}
		if (renewpass.length() == 0) {
			model.addAttribute("message", "Vui lòng điền đủ thông tin !");
			return "user/changePassword";
		} else {
			if (oldpass.equals(user.getPassword()) == false) {
				model.addAttribute("message", "Mật khẩu cũ không đúng !");
				return "user/changePassword";
			}
			if(!newpass.matches(pattern)) {
				model.addAttribute("message", "Mật khẩu phải có ít nhất 6 ký tự!");
				return "user/changePassword";
			}
			if (newpass.equals(renewpass) == false) {
				model.addAttribute("message", "Xác nhận mật khẩu mới không chính xác !");
				return "user/changePassword";
			} else {
				Session session = factory.openSession();
				Transaction t = session.beginTransaction();
				try {
					user.setPassword(md5(newpass));
					session.update(user);
					t.commit();
					model.addAttribute("message", "Đổi mật khẩu thành công !");
				} catch (Exception ex) {
					t.rollback();
					model.addAttribute("message", "Đổi mật khẩu thất bại !");
				} finally {
					session.close();
				}
			}
		}
		return "user/changePassword";
	}
	
	//		QUÊN MẬT KHẨU
	@RequestMapping(value="forgotPassword", method=RequestMethod.GET)
	public String forgotPassword(ModelMap model,HttpServletRequest req){
		HttpSession user_session = req.getSession();
		
		if(user_session.getAttribute("user") != null) {
			return "redirect:/user/index.htm";
		}
		else {
			return "user/forgotPassword";
		}
	}
	
	@RequestMapping(value="forgotPassword", method=RequestMethod.POST)
	public String forgotPassword1(ModelMap model, HttpServletRequest req){
		String from = "n16dccn055@student.ptithcm.edu.vn";
		String subject = "THAY ĐỔI MẬT KHẨU MỚI!";
		String body = "";
		MimeMessage mail = mailer.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail);
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		username.trim();
		email.trim();
		if(username.length() == 0) {
			model.addAttribute("message", "Vui lòng điền đủ thông tin !");
			return "user/forgotPassword";
		}
		if(email.length() == 0) {
			model.addAttribute("message", "Vui lòng điền đủ thông tin !");
			return "user/forgotPassword";
		}
		else {
			if(checkEmail(email) == false) {
				model.addAttribute("message", "Email bạn nhập chưa đúng định dạng !");
				return "user/forgotPassword";
			}
			List<UserID> check = getUsers();
			for(UserID hen : check) {
				if(username.trim().equals(hen.getUsername())== true && email.trim().equals(hen.getThongtinuser().getEmail()) == true) {
					Session session = factory.openSession();
					Transaction t = session.beginTransaction();
					try {
						String newpass = randomNumber();
						body += "THÔNG BÁO TỪ SHOWROOM THIẾT BỊ VÀ VẬT LIỆU XÂY DỰNG TATA" + "\n" + 
								"Mật khẩu của bạn đã được đặt về mặc định là: " + newpass 
								+ " . Vui lòng đăng nhập lại để thay đổi mật khẩu mới. "
								+ "Showroom TATA xin cảm ơn !";
						helper.setFrom(from, from);
						helper.setTo(email);
						helper.setReplyTo(from, from);
						helper.setSubject(subject);
						helper.setText(body);
						mailer.send(mail);
						model.addAttribute("message", "Mật khẩu của bạn đã được thay đổi. Vui lòng kiểm tra Email của bạn !");
						
						hen.setPassword(md5(newpass));
						session.update(hen);
						t.commit();
//						return "user/login";
					}
					catch(Exception ex) {
						t.rollback();
						return "user/forgotPassword";
					}
					finally {
						session.close();
					}
				}
			}
			model.addAttribute("message", "Tên đăng nhập hoặc Email chưa đúng. Vui lòng kiểm tra lại !");
		}
		return "user/forgotPassword";
	}

	// ============MODULE DANH MỤC SẢN PHẨM================
	// Show tất cả sản phẩm
	@RequestMapping("allproduct")
	public String showAllProduct(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

		HttpSession user_session = req.getSession();
		user_session.getAttribute("user");

		if (user_session.getAttribute("user") == null) {
			model.addAttribute("isLogin", false);
		}
		if (user_session.getAttribute("user") != null) {
			model.addAttribute("isLogin", true);
		}

		model.put("product", getListNav(0, 8));
		model.put("totalitem", totalItem() / 8);
		model.addAttribute("slider", getSliders());
		return "user/allproduct";
	}

	// show sản phẩm phân trang
	@RequestMapping(value = "allproduct/{page}", method = RequestMethod.GET)
	public String viewProductListByPage(ModelMap model, HttpSession session, @PathVariable("page") int page,
			HttpServletRequest request) {
		HttpSession user_session = request.getSession();
		user_session.getAttribute("user");
		if (user_session.getAttribute("user") == null) {
			model.addAttribute("isLogin", false);
		}
		if (user_session.getAttribute("user") != null) {
			model.addAttribute("isLogin", true);
		}

		model.put("product", getListNav((page - 1) * 8, 8));
		model.put("totalitem", totalItem() / 8);
		return "user/allproduct";
	}

	// Show sản phẩm theo loại
	@RequestMapping("show/{idloai}")
	public String showProductofId(ModelMap model, @PathVariable("idloai") Integer idloai, HttpServletRequest req,
			HttpServletResponse response) {

		HttpSession user_session = req.getSession();
		user_session.getAttribute("user");

		if (user_session.getAttribute("user") == null) {
			model.addAttribute("isLogin", false);
		}
		if (user_session.getAttribute("user") != null) {
			model.addAttribute("isLogin", true);
		}
		Session session = factory.getCurrentSession();
		String hql = "FROM HangHoa p WHERE p.loaihang.id = :id ORDER BY id DESC";
		Query query = session.createQuery(hql);
		query.setParameter("id", idloai);
		List<HangHoa> list = query.list();
		model.addAttribute("product", list);
		model.addAttribute("slider", getSliders());
		return "user/show";
	}

	// XEM CHI TIẾT SẢN PHẨM
	@RequestMapping("detail-product/{id}")
	public String showDetailProduct(ModelMap model, @PathVariable("id") Integer id, HttpServletRequest req,
			HttpServletResponse response, HttpSession session) {

		HttpSession user_session = req.getSession();
		user_session.getAttribute("user");

//		if (user_session.getAttribute("user") == null) {
//			return "redirect:/user/login.htm";
//		}
		
		if (user_session.getAttribute("user") == null) {
			model.addAttribute("isLogin", false);
		}
		if (user_session.getAttribute("user") != null) {
			model.addAttribute("isLogin", true);
		}
		UserID user = (UserID) user_session.getAttribute("user");
		Session session1 = factory.getCurrentSession();
		HangHoa product = (HangHoa) session1.get(HangHoa.class, id);
		
		//Kiểm tra khách hàng đó đã đặt sp này hay chưa
//		Session session2 = factory.getCurrentSession();
//		String hql = "FROM DatHang dh, ChiTietDatHang ctdh WHERE id_khachhang = :id_khachhang"
//				+ " AND dh.id=ctdh.dathangs.id AND ctdh.hanghoas.id = :id_hanghoa";
//		Query query = session2.createQuery(hql);
//		query.setParameter("id_khachhang", user.getUsername());
//		query.setParameter("id_hanghoa", id);
//		List kq = query.list();
		
		//Lấy danh sách đánh giá của sp
		Session session3 = factory.getCurrentSession();
		String hql1 = "FROM DanhGia WHERE hanghoa_id = :id_hanghoa";
		Query query1 = session3.createQuery(hql1);
		query1.setParameter("id_hanghoa", id);
		List<DanhGia> list = query1.list();
		model.addAttribute("comments", list);
//		System.out.println(list);
		if (list.isEmpty()) {
//			model.addAttribute("nonactive", "nonactive");
//			model.addAttribute("comments", new DanhGia());
			model.addAttribute("product", product);
			model.addAttribute("slider", getSliders());
		}
		else {
			
			model.addAttribute("comments", list);
			model.addAttribute("product", product);
			model.addAttribute("slider", getSliders());
//			//Lấy đánh giá của sp 
//			Session session4 = factory.getCurrentSession();
//			String hql2 = "SELECT isnull(id, 0) FROM DanhGia WHERE khachhang_id= :id_khachhang AND hanghoa_id= :id_hanghoa";
//			Query query2 = session4.createQuery(hql2);
//			query2.setParameter("id_khachhang", user.getUsername());
//			query2.setParameter("id_hanghoa", id);
//			List id_comment = query2.list();
//			if(id_comment.isEmpty()){
//				model.addAttribute("comment", new DanhGia());
////				model.addAttribute("comments", list);
//				model.addAttribute("product", product);
//				model.addAttribute("slider", getSliders());
//			}
//			else {
//				DanhGia tmp = (DanhGia) session1.get(DanhGia.class, (Serializable) id_comment.get(0));
//				model.addAttribute("mark", tmp);
////				model.addAttribute("comments", list);
//				model.addAttribute("product", product);
//				model.addAttribute("slider", getSliders());
//			}
		}
//		model.addAttribute("product", product);
//		model.addAttribute("slider", getSliders());
		return "user/detail-product";
	}
	
	@RequestMapping("comment/{id}")
	public String commentProduct(ModelMap model, @PathVariable("id") Integer id, 
			HttpServletRequest req, @ModelAttribute("comment") DanhGia comment,
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
		UserID user = (UserID) user_session.getAttribute("user");
		Session session = factory.getCurrentSession();
		HangHoa product = (HangHoa) session.get(HangHoa.class, id);
		
		String rating = req.getParameter("rating");
		System.out.println(comment);
		int mark = Integer.valueOf(rating);
		
		//Kiểm tra khách hàng đó đã đặt sp này hay chưa
		Session session2 = factory.getCurrentSession();
		String hql = "FROM DatHang dh, ChiTietDatHang ctdh "
				+ "WHERE id_khachhang = :id_khachhang"
				+ " AND dh.id=ctdh.dathangs.id AND ctdh.hanghoas.id = :id_hanghoa "
				+ "AND dh.status_delivery = 4";
		Query query = session2.createQuery(hql);
		query.setParameter("id_khachhang", user.getUsername());
		query.setParameter("id_hanghoa", id);
		List kq = query.list();
		if(kq.isEmpty()){
			model.addAttribute("message", "Bạn không thể đánh giá vì chưa đặt sản phẩm!");
			
		}
		else {
			Session session3 = factory.getCurrentSession();
			String hql2 = "SELECT isnull(id, 0) FROM DanhGia WHERE khachhang_id= :id_khachhang "
					+ "AND hanghoa_id= :id_hanghoa";
			Query query2 = session3.createQuery(hql2);
			query2.setParameter("id_khachhang", user.getUsername());
			query2.setParameter("id_hanghoa", id);
			List id_comment = query2.list();
			System.out.println(id_comment);
			if(id_comment.isEmpty()){
				comment = new DanhGia();
				Session session1 = factory.openSession();
				Transaction t = session1.beginTransaction();
				
				comment.setUtilTimestamp(new Timestamp(System.currentTimeMillis()));
				comment.setMark(mark);
				comment.setUserid(user);
				comment.setHanghoas(product);
				try {
					session1.save(comment);
					t.commit();
					model.addAttribute("message1", "Đánh giá thành công !");
				}catch (Exception e) {
					t.rollback();
					model.addAttribute("message1", "Đánh giá thất bại !");
				} finally {
					session1.close();
				}
			}
			else {
				Session session4 = factory.getCurrentSession();
				comment = (DanhGia) session4.get(DanhGia.class, (Serializable) id_comment.get(0));
				Session session1 = factory.openSession();
				Transaction t = session1.beginTransaction();
				
				try {
					comment.setUtilTimestamp(new Timestamp(System.currentTimeMillis()));
//					System.out.println(new Timestamp(System.currentTimeMillis()));
					comment.setMark(mark);
					comment.setUserid(user);
					comment.setHanghoas(product);
					session1.update(comment);
					t.commit();
					model.addAttribute("message1", "Đã cập nhật lại đánh giá!");
				}catch (Exception e) {
					t.rollback();
					model.addAttribute("message1", "Đánh giá thất bại !");
				} finally {
					session1.close();
				}
			}
		}
		
		model.addAttribute("product", product);
		model.addAttribute("slider", getSliders());
		return "user/detail-product";
	}

	// mã hóa mật khẩu md5
	public static String md5(String msg) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(msg.getBytes());
			byte byteData[] = md.digest();
			// convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (Exception ex) {
			return "";
		}
	}
	
	//sinh số ngẫu nhiên
		public String randomNumber() {
			String randNumber = "";
			Random rd = new Random();
			for(int i = 0; i<8; i++) {
				Integer number = rd.nextInt(9);
				randNumber += number.toString();
			}
			return randNumber;
		}

	// hàm kiểm tra email hợp lệ
	public Boolean checkEmail(String email) {
		String emailPattern = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern regex = Pattern.compile(emailPattern);
		Matcher matcher = regex.matcher(email);
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}

	// hàm kiểm tra sđt
	public Boolean checkPhone(String phone) {
		String phonePattern = "[0]{1}[35789]{1}[0-9]{8}";
		Pattern regex = Pattern.compile(phonePattern);
		Matcher matcher = regex.matcher(phone);
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}

	// ===========PHÂN TRANG SẢN PHẨM===========
	public int totalItem() {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			Query query = session.createQuery("SELECT count(*) FROM HangHoa");
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
	
	//chuyển tiếng việt có dấu sang không dấu
	public static String covertToString(String value) {
	      try {
	            String temp = Normalizer.normalize(value, Normalizer.Form.NFD);
	            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	            return pattern.matcher(temp).replaceAll("");
	     } catch (Exception ex) {
	            ex.printStackTrace();
	      }
	      return null;
	}
	
	public List<HangHoa> getListNav(int start, int limit) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("FROM HangHoa ORDER BY id DESC");
			query.setFirstResult(start);
			query.setMaxResults(limit);
			List<HangHoa> list = query.list();
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