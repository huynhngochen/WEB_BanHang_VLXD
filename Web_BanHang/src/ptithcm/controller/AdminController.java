package ptithcm.controller;

import java.io.File;
import java.io.Serializable;
import java.security.MessageDigest;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ptithcm.entity.DatHang;
import ptithcm.entity.HangHoa;
import ptithcm.entity.LoaiHang;
import ptithcm.entity.Phuong;
import ptithcm.entity.Quan;
import ptithcm.entity.QuanPhuong;
import ptithcm.entity.Role;
import ptithcm.entity.Slider;
import ptithcm.entity.ThongTinUser;
import ptithcm.entity.UserID;

@Transactional
@Controller
@RequestMapping("/admin/")
public class AdminController {
	private static final Map<String, UserID> mapUsers = new HashMap<String, UserID>();
	@Autowired
	SessionFactory factory;
	@Autowired
	ServletContext context;

	// @RequestMapping(value = "test")
	// public String test(ModelMap model, HttpServletRequest request,
	// HttpServletResponse response) {
	//
	//
	// return "admin/test";
	// }
	// Đăng nhập
	@RequestMapping(value = "index")
	public String showLogin(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		return "admin/index";
	}

	@RequestMapping(value = "index", method = RequestMethod.POST)
	public String login(HttpServletRequest request, ModelMap model) {
		String username = request.getParameter("u");
		String password = md5(request.getParameter("p"));

		if (username.trim().length() == 0) {
			model.addAttribute("message_u", "Vui lòng nhập username!!");
		}
		if (password.trim().length() == 0) {
			model.addAttribute("message_p", "Vui lòng nhập Password!!");
		}
		Session session = factory.getCurrentSession();
		String hql = "FROM UserID";
		Query query = session.createQuery(hql);
		List<UserID> list = query.list();
		// lấy ra id của NV
		String hql1 = "SELECT id FROM Role WHERE  id != 1";
		Query query2 = session.createQuery(hql1);
		List listRole = query2.list();

		// System.out.println(listRole.get(i));
		for (UserID hen : list) {
			if (username.equals(hen.getUsername()) == true) {
				if (password.equals(hen.getPassword()) == false) {
					model.addAttribute("message", "Sai Mật Khẩu");
					return "admin/index";
				} else {
					if (hen.getRole().getId() != 1) {
						HttpSession admin_session = request.getSession();
						admin_session.setAttribute("user", hen);
						if (hen.getRole().getId() == 7) {
							return "redirect:/admin/shipper.htm";
						}
						return "redirect:/admin/admin-home.htm";
					} else {
						model.addAttribute("message", "Tài Khoản không tồn tại");
						return "admin/index";
					}
				}
			} else {
				if (password.equals(hen.getPassword()) == true) {
					model.addAttribute("message", "Tên đăng nhập không đúng!");
				}
			}
		}

		// Integer id = (Integer) query2.uniqueResult();

		return "admin/index";
	}

	// đăng xuất
	@RequestMapping("logout")
	public String logout(HttpServletRequest req) {
		HttpSession logout_session = req.getSession();
		logout_session.removeAttribute("user");
		return "redirect:/admin/index.htm";
	}

	@RequestMapping("shipper")
	public String viewOrderofShipper(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() == 1) {
			return "redirect:/admin/index.htm";
		}
		System.out.println(user.getUsername());
		model.put("order", getListNavofShipper(0, 10, user.getUsername()));
		model.put("totalitem", totalItemofShipper(user.getUsername()) / 10);

		return "admin/shipper";
	}

	@RequestMapping(value = "shipper/{page}", method = RequestMethod.GET)
	public String viewOrderofShipperListByPage(ModelMap model, HttpSession session, @PathVariable("page") int page,
			HttpServletRequest request) {
		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() == 1) {
			return "redirect:/admin/index.htm";
		}
		model.put("order", getListNavofShipper((page - 1) * 10, 10, user.getUsername()));
		model.put("totalitem", totalItemofShipper(user.getUsername()) / 10);

		return "admin/shipper";
	}

	// =============DANH SÁCH ĐƠN HÀNG
	@RequestMapping("admin-home")
	public String viewOrder(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		model.put("order", getListNav(0, 10));
		model.put("totalitem", totalItem() / 10);

		return "admin/admin-home";
	}

	@RequestMapping(value = "admin-home/{page}", method = RequestMethod.GET)
	public String viewOrderListByPage(ModelMap model, HttpSession session, @PathVariable("page") int page,
			HttpServletRequest request) {
		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}

		if (user.getRole().getId() == 7) {
			model.put("order", getListNav((page - 1) * 10, 10));
			model.put("totalitem", totalItem() / 10);
		} else {
			model.put("order", getListNav((page - 1) * 10, 10));
			model.put("totalitem", totalItem() / 10);
		}

		return "admin/admin-home";
	}

	@RequestMapping("admin-user")
	public String viewUser(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}

		Session session = factory.getCurrentSession();
		String hql = "FROM UserID";
		Query query = session.createQuery(hql);
		List<UserID> list = query.list();
		model.addAttribute("users", list);
		return "admin/admin-user";
	}

	// quản lí tài khoản admin
	@RequestMapping("managerAccount")
	public String managerAccount(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}

		Session session = factory.getCurrentSession();
		String hql = "FROM UserID";
		Query query = session.createQuery(hql);
		List<UserID> list = query.list();
		model.addAttribute("users", list);
		return "admin/managerAccount";
	}

	// Thêm tài khoản admin
	@RequestMapping(value = "managerAccount-insert", method = RequestMethod.GET)
	public String managerAccountInsert(ModelMap model, HttpServletRequest request) {
		model.addAttribute("user", new UserID());

		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}

		return "admin/managerAccount-insert";
	}

	@RequestMapping(value = "managerAccount-insert", method = RequestMethod.POST)
	public String managerAccountInsert(ModelMap model, @ModelAttribute("user") UserID user, HttpServletRequest req,
			BindingResult errors, ThongTinUser infouser) {
		infouser.setFullname("");
		infouser.setSdt("");
		infouser.setEmail("");
		infouser.setAddress("");
		String pattern = ".{6,}";
		HttpSession admin_session = req.getSession();

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}

		if (user.getUsername().trim().length() == 0) {
			errors.rejectValue("username", "user", "Vui Lòng Nhập Username!");
		}
		if (user.getPassword().trim().length() == 0) {
			errors.rejectValue("password", "user", "Vui Lòng Nhập Password!");
		}
		if (errors.hasErrors()) {

		} else {
			for (UserID hen : getUsers()) {
				if (hen.getUsername().equals(user.getUsername()) == true) {
					errors.rejectValue("username", "user", "Username không được trùng!");
					return "admin/managerAccount-insert";
				}
			}
			if (!user.getPassword().matches(pattern)) {
				errors.rejectValue("password", "user", "Mật khẩu phải có ít nhất 6 ký tự!");
				return "admin/managerAccount-insert";
			}
			user.setPassword(md5(user.getPassword()));
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			try {
				session.save(infouser);
				user.setThongtinuser(infouser);
				session.save(user);
				t.commit();
				model.addAttribute("message", "Thêm thành công!");
				user.setUsername("");
				user.setPassword("");
				// return "admin/managerAccount-insert";
			} catch (Exception e) {
				t.rollback();
				model.addAttribute("message", "Thêm thất bại!");
			} finally {
				session.close();
			}
		}
		return "admin/managerAccount-insert";
	}

	// Update tài khoản admin
	@RequestMapping(value = "info-account", method = RequestMethod.GET)
	public String infoAccount(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() == 1) {
			return "redirect:/admin/index.htm";
		}

		return "admin/info-account";
	}

	@RequestMapping(value = "managerAccount-update", method = RequestMethod.GET)
	public String managerAccountUpdate(ModelMap model, HttpServletRequest request) {
		model.addAttribute("infouser", new ThongTinUser());

		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() == 1) {
			return "redirect:/admin/index.htm";
		}

		return "admin/managerAccount-update";
	}

	@RequestMapping("managerAccount-update/{id}")
	public String managerAccountUpdate(ModelMap model, @PathVariable("id") Integer id, HttpServletRequest req,
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

		// System.out.println(id);
		Session session = factory.getCurrentSession();
		ThongTinUser infouser = (ThongTinUser) session.get(ThongTinUser.class, id);

		model.addAttribute("infouser", infouser);
		return "admin/managerAccount-update";
	}

	@RequestMapping(value = "managerAccount-update", method = RequestMethod.POST)
	public String managerAccountUpdate(ModelMap model, @ModelAttribute("infouser") ThongTinUser infouser,
			HttpServletRequest req, HttpServletResponse response, BindingResult errors) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() == 1) {
			return "redirect:/admin/index.htm";
		}
		String id_p = req.getParameter("ward");
		String id_q = req.getParameter("district");
		int id_phuong = Integer.valueOf(id_p);
		if (id_p.equals("") == true) {
			model.addAttribute("message1", "Vui lòng chọn địa chỉ hợp lệ!");
			return "admin/managerAccount-update";
		} else if (id_q.equals("") == true) {
			model.addAttribute("message1", "Vui lòng chọn địa chỉ hợp lệ!");
			return "admin/managerAccount-update";
		} else {
			Session session = factory.getCurrentSession();
			String hql = "SELECT isnull(id, 0) FROM QuanPhuong WHERE id_phuong = :id_phuong AND id_quan = :id_quan";
			Query query = session.createQuery(hql);
			query.setParameter("id_quan", id_q);
			query.setInteger("id_phuong", id_phuong);
			List tmp = query.list();
			if (tmp.isEmpty()) {
				model.addAttribute("message1", "Địa chỉ không đúng!");
				// return "user/changeAccount";
			} else {
				QuanPhuong qp = (QuanPhuong) session.get(QuanPhuong.class, (Serializable) tmp.get(0));
				infouser.setQuanphuong(qp);
				if (infouser.getFullname().length() == 0) {
					errors.rejectValue("fullname", "infouser", "Vui Lòng Nhập Họ Tên!");
				}
				if (infouser.getEmail().length() == 0) {
					errors.rejectValue("email", "infouser", "Vui Lòng Nhập Email!");
				}
				if (infouser.getSdt().length() == 0) {
					errors.rejectValue("sdt", "infouser", "Vui Lòng Nhập sdt!");
				}
				if (infouser.getAddress().length() == 0) {
					errors.rejectValue("address", "infouser", "Vui Lòng Nhập địa chỉ!");
				}
				if (errors.hasErrors()) {

				} else {
					Session session1 = factory.openSession();
					Transaction t = session1.beginTransaction();
					try {
						session1.update(infouser);
						t.commit();
						model.addAttribute("message", "Cập nhật thành công !");
						// return "redirect:/admin/admin.htm";
					} catch (Exception ex) {
						t.rollback();
						model.addAttribute("message", "Cập nhật thất bại !");
					} finally {
						session1.close();
					}
				}
			}
		}
		return "admin/managerAccount-update";
	}

	// Xóa tài khoản
	@RequestMapping(value = "managerAccount-delete/{id}", method = RequestMethod.GET)
	public String managerAccountDelete(ModelMap model, @PathVariable("id") Integer id, HttpServletRequest req,
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

		Session session = factory.openSession();
		ThongTinUser infouser = (ThongTinUser) session.get(ThongTinUser.class, id);
		Transaction t = session.beginTransaction();

		Session session1 = factory.getCurrentSession();
		String hql = "FROM UserID WHERE infouser_id = :id";
		Query query = session1.createQuery(hql);
		query.setParameter("id", infouser.getId());
		UserID usesID = (UserID) query.uniqueResult();
		// System.out.println(usesID.getUsername());

		if (getOrderByUser(usesID.getUsername()).isEmpty()) {
			String hql1 = "DELETE FROM UserID WHERE infouser_id = :id";
			Query query1 = session.createQuery(hql1);
			query1.setParameter("id", infouser.getId());
			int result = query1.executeUpdate();
			System.out.println(result);

			try {
				session.delete(infouser);
				t.commit();
				model.addAttribute("message", "Xóa thành công!");
				// return "redirect:/admin/admin-user.htm";
			} catch (Exception e) {
				t.rollback();
				model.addAttribute("message", "Lỗi xóa tài khoản!");
			} finally {
				session.close();
			}
		} else {
			model.addAttribute("message", "Không thể xóa tài khoản vì tài khoản đã được sử dụng");
		}

		// model.addAttribute("infouser", infouser);
		return "admin/admin-user";
	}

	// Thay đổi mật khẩu
	@RequestMapping(value = "changePassword", method = RequestMethod.GET)
	public String changePass(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() == 1) {
			return "redirect:/admin/index.htm";
		}

		return "admin/changePassword";
	}

	@RequestMapping(value = "changePassword", method = RequestMethod.POST)
	public String changepass(ModelMap model, HttpServletRequest req, HttpServletResponse response) {
		String pattern = ".{6,}";
		HttpSession admin_session = req.getSession();

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() == 1) {
			return "redirect:/admin/index.htm";
		}
		String oldpass = req.getParameter("oldpass");
		String newpass = req.getParameter("newpass");
		String renewpass = req.getParameter("renewpass");
		oldpass.trim();
		newpass.trim();
		renewpass.trim();

		if (oldpass.length() == 0) {
			model.addAttribute("message", "Vui lòng điền đủ thông tin !");
			return "admin/changePassword";
		}
		if (newpass.length() == 0) {
			model.addAttribute("message", "Vui lòng điền đủ thông tin !");
			return "admin/changePassword";
		}
		if (renewpass.length() == 0) {
			model.addAttribute("message", "Vui lòng điền đủ thông tin !");
			return "admin/changePassword";
		} else {
			if (oldpass.equals(user.getPassword()) == false) {
				model.addAttribute("message", "Mật khẩu cũ không đúng !");
				return "admin/changePassword";
			}
			if (!newpass.matches(pattern)) {
				model.addAttribute("message", "Mật khẩu phải có ít nhất 6 ký tự!");
				return "admin/changePassword";
			}
			if (newpass.equals(renewpass) == false) {
				model.addAttribute("message", "Xác nhận mật khẩu mới không chính xác !");
				return "admin/changePassword";
			} else {
				Session session = factory.openSession();
				Transaction t = session.beginTransaction();
				try {
					user.setPassword(newpass);
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
		return "admin/changePassword";
	}

	// ============TÌM KIẾM THÔNG TIN USERNAME=======
	// tìm kiếm thông tin tài khoản KH
	@RequestMapping("search")
	public String searchAccount(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}

		String keyword = req.getParameter("search");
		keyword = covertToString(keyword).trim();
		while (keyword.indexOf("  ") != -1) {
			keyword = keyword.replaceAll("  ", " ");
		}

		System.out.println(keyword);
		Session session = factory.getCurrentSession();
		String hql = "FROM UserID WHERE dbo.ufn_removeMark(username) LIKE '%" + keyword + "%' AND role_id = 1";
		Query query = session.createQuery(hql);
		List<UserID> list = query.list();

		if (list.isEmpty()) {
			model.addAttribute("message", "Không có kết quả !");
		} else {
			model.addAttribute("users", list);
		}

		return "admin/search";
	}

	// tìm kiếm thông tin tài khoản Admin
	@RequestMapping("searchAdmin")
	public String searchAccountAdmin(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}

		String keyword = req.getParameter("search");
		keyword = covertToString(keyword).trim();
		while (keyword.indexOf("  ") != -1) {
			keyword = keyword.replaceAll("  ", " ");
		}

		System.out.println(keyword);

		Session session = factory.getCurrentSession();
		String hql = "FROM UserID WHERE dbo.ufn_removeMark(username) LIKE '%" + keyword + "%' AND role_id = 2";
		Query query = session.createQuery(hql);
		List<UserID> list = query.list();

		if (list.isEmpty()) {
			model.addAttribute("message", "Không có kết quả !");
		} else {
			model.addAttribute("users", list);
		}

		return "admin/searchAdmin";
	}

	// module slider quảng cáo
	// load danh sách slider
	@RequestMapping("slider/index")
	public String viewSlider(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

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
		String hql = "FROM Slider";
		Query query = session.createQuery(hql);
		List<Slider> list = query.list();
		model.addAttribute("slider", list);
		return "slider/index";
	}

	// thêm mới slider
	@RequestMapping(value = "slider/insert", method = RequestMethod.GET)
	public String sliderInsert(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		model.addAttribute("slider", new Slider());
		return "slider/insert";
	}

	@RequestMapping(value = "slider/insert", method = RequestMethod.POST)
	public String sliderInsert(ModelMap model, @ModelAttribute("slider") Slider slider, HttpServletRequest req,
			@RequestParam("photo") MultipartFile photo) {
		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}

		if (photo.isEmpty()) {
			model.addAttribute("message_image", "Vui lòng chọn hình ảnh !");
		} else {
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			try {
				String photoPath = context.getRealPath("./images/" + photo.getOriginalFilename());
				photo.transferTo(new File(photoPath));
				slider.setImage(photo.getOriginalFilename());
				session.save(slider);
				t.commit();
				model.addAttribute("message", "Thêm mới thành công !");
				return "redirect:/admin/slider/index.htm";
			} catch (Exception e) {
				t.rollback();
				model.addAttribute("message", "Thêm mới thất bại !");
			} finally {
				session.close();
			}
		}
		return "slider/insert";
	}

	// Xóa item slider
	// delete
	@RequestMapping(value = "slider/delete/{id}", method = RequestMethod.GET)
	public String deleteSlider(ModelMap model, @PathVariable("id") Integer id, HttpServletRequest req,
			HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");
		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		// System.out.println(id);
		Session session = factory.openSession();
		Slider slider = (Slider) session.get(Slider.class, id);
		Transaction t = session.beginTransaction();

		try {
			session.delete(slider);
			t.commit();
			// model.addAttribute("message", "Xóa thành công !");
			return "redirect:/admin/slider/index.htm";
		} catch (Exception ex) {
			t.rollback();
			model.addAttribute("message", "Xóa thất bại !");
		} finally {
			session.close();
		}

		model.addAttribute("slider", slider);
		// model.addAttribute("ads", getAdvertisements());
		return "slider/index";
	}

	// module quản lý hệ thống
	@RequestMapping("system")
	public String viewSystem(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

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
		String hql = "FROM Slider";
		Query query = session.createQuery(hql);
		List<Slider> list = query.list();
		model.addAttribute("slider", list);
		return "admin/system";
	}

	// ==========MODULE QUẢN LÝ DANH MỤC HỆ THỐNG===========
	// load danh sách DANH MỤC
	@RequestMapping("brand/index")
	public String viewBrand(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

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
		String hql = "FROM LoaiHang";
		Query query = session.createQuery(hql);
		List<LoaiHang> list = query.list();
		model.addAttribute("brand", list);
		return "brand/index";
	}

	// Thêm mới danh mục
	@RequestMapping(value = "brand/insert", method = RequestMethod.GET)
	public String insertBrand(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");
		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		model.addAttribute("loaihang", new LoaiHang());
		return "brand/insert";
	}

	@RequestMapping(value = "brand/insert", method = RequestMethod.POST)
	public String insertBrand(ModelMap model, @ModelAttribute("loaihang") LoaiHang loaihang,
			@RequestParam("photo") MultipartFile photo, BindingResult errors, HttpServletRequest req) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");
		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		if (loaihang.getName().trim().length() == 0) {
			errors.rejectValue("name", "brand", "Vui lòng nhập tên danh mục!");
		}
		if (photo.isEmpty()) {
			model.addAttribute("message_image", "Vui lòng chọn hình ảnh !");
		}

		if (errors.hasErrors()) {

		} else {
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			try {
				String photoPath = context.getRealPath("./images/" + photo.getOriginalFilename());
				photo.transferTo(new File(photoPath));
				loaihang.setImages(photo.getOriginalFilename());
				session.save(loaihang);
				t.commit();
				model.addAttribute("message", "Thêm mới thành công !");
				loaihang.setName("");
				// return "redirect:/admin/brand/index.htm";
			} catch (Exception e) {
				t.rollback();
				model.addAttribute("message", "Thêm mới thất bại !");
			} finally {
				session.close();
			}
		}
		return "brand/insert";
	}

	// chỉnh sửa danh mục hàng hóa
	@RequestMapping("brand/update/{id}")
	public String updateBrand(ModelMap model, @PathVariable("id") Integer id, HttpServletRequest req,
			HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");
		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		System.out.println(id);
		Session session = factory.getCurrentSession();
		LoaiHang loaihang = (LoaiHang) session.get(LoaiHang.class, id);

		model.addAttribute("loaihang", loaihang);
		return "brand/update";
	}

	@RequestMapping("brand/update")
	public String updateBrand(ModelMap model, @ModelAttribute("loaihang") LoaiHang loaihang,
			@RequestParam("photo") MultipartFile photo, BindingResult errors, HttpServletRequest req) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");
		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}

		if (photo.isEmpty()) {
			if (loaihang.getName().trim().length() == 0) {
				errors.rejectValue("name", "loaihang", "Vui lòng nhập tên sản phẩm !");
			}
			if (errors.hasErrors()) {
			} else {
				String oldImage = loaihang.getImages();
				Session session = factory.openSession();
				Transaction t = session.beginTransaction();
				try {
					loaihang.setImages(oldImage);
					session.update(loaihang);
					t.commit();
					model.addAttribute("message", "Cập nhật thành công !");
				} catch (Exception e) {
					t.rollback();
					model.addAttribute("message", "Cập nhật thất bại !");
				} finally {
					session.close();
				}
			}
			return "brand/update";
		} else {
			if (loaihang.getName().trim().length() == 0) {
				errors.rejectValue("name", "loaihang", "Vui lòng nhập tên sản phẩm !");
			}
			if (errors.hasErrors()) {

			} else {
				Session session = factory.openSession();
				Transaction t = session.beginTransaction();
				try {
					String photoPath = context.getRealPath("./images/" + photo.getOriginalFilename());
					photo.transferTo(new File(photoPath));
					loaihang.setImages(photo.getOriginalFilename());
					session.update(loaihang);
					t.commit();
					model.addAttribute("message", "Cập nhật thành công !");
				} catch (Exception e) {
					t.rollback();
					model.addAttribute("message", "Cập nhật thất bại !");
				} finally {
					session.close();
				}
			}
		}
		return "brand/update";
	}

	// Xóa danh mục hàng hóa
	@RequestMapping(value = "brand/delete/{id}", method = RequestMethod.GET)
	public String deleteBrand(ModelMap model, @PathVariable("id") Integer id, HttpServletRequest req,
			HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");
		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		// System.out.println(id);
		Session session = factory.openSession();
		LoaiHang loaihang = (LoaiHang) session.get(LoaiHang.class, id);
		Transaction t = session.beginTransaction();

		if (getProductByBrand(loaihang.getId()).isEmpty()) {
			try {
				session.delete(loaihang);
				t.commit();
				model.addAttribute("message", "Xóa thành công!");
				return "redirect:/admin/brand/index.htm";
			} catch (Exception e) {
				t.rollback();
				model.addAttribute("message", "Lỗi xóa danh mục!");
			} finally {
				session.close();
			}
		} else {
			model.addAttribute("message", "Danh mục đã được tạo sản phẩm!");
		}

		// model.addAttribute("loaihang", loaihang);
		return "brand/index";
	}

	// ==============TÌM KIẾM SAN PHẨM BẰNG THANH TÌM KIẾM=====
	// tìm kiếm sản phẩm
	@RequestMapping("product/search")
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

		String keyword = req.getParameter("search");
		keyword = covertToString(keyword).trim();
		while (keyword.indexOf("  ") != -1) {
			keyword = keyword.replaceAll("  ", " ");
		}

		System.out.println(keyword);

		Session session = factory.getCurrentSession();
		String hql = "FROM HangHoa WHERE dbo.ufn_removeMark(name) LIKE '%" + keyword + "%'";
		Query query = session.createQuery(hql);
		List<HangHoa> list = query.list();

		if (list.isEmpty()) {
			model.addAttribute("message", "Không có sản phẩm nào!!");
		} else {
			model.addAttribute("product", list);
		}

		return "product/search";
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

	@ModelAttribute("order")
	public List<DatHang> getOrders() {
		Session session = factory.getCurrentSession();
		String hql = "FROM DatHang";
		Query query = session.createQuery(hql);
		List<DatHang> list = query.list();
		return list;
	}

	// lấy danh sách slider
	@SuppressWarnings("unchecked")
	public List<Slider> getSliders() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Slider";
		Query query = session.createQuery(hql);
		List<Slider> list = query.list();
		return list;
	}

	// lấy danh sách userID
	@SuppressWarnings("unchecked")
	public List<UserID> getUsers() {
		Session session = factory.getCurrentSession();
		String hql = "FROM UserID";
		Query query = session.createQuery(hql);
		List<UserID> list = query.list();
		return list;
	}

	// lấy danh sách ThongtinUser
	@SuppressWarnings("unchecked")
	public List<ThongTinUser> getInfoUsers() {
		Session session = factory.getCurrentSession();
		String hql = "FROM ThongTinUser";
		Query query = session.createQuery(hql);
		List<ThongTinUser> list = query.list();
		return list;
	}

	// load danh sách quyền truy cập ra combobox
	@ModelAttribute("role")
	public List<Role> getRoles() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Role";
		Query query = session.createQuery(hql);
		List<Role> list = query.list();
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

	// Lưu trữ thông tin người dùng vào Session.
	public static void storeLoginUser(HttpSession session, UserID userLogin) {
		// Trên JSP có thể truy cập thông qua ${loginedUser}
		session.setAttribute("userLogin", userLogin);
	}

	// Lấy thông tin người dùng lưu trữ trong Session.
	public static UserID getLoginUser(HttpSession session) {
		UserID userLogin = (UserID) session.getAttribute("userLogin");
		return userLogin;
	}

	// Tìm kiếm người dùng theo userName và password.
	public static UserID findUser(String userName, String password) {
		UserID u = mapUsers.get(userName);
		if (u != null && u.getPassword().equals(password)) {
			return u;
		}
		return null;
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

	// chuyển tiếng việt có dấu sang không dấu
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

	public int totalItemofShipper(String role) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			Query query = session.createQuery("SELECT count(*) FROM DatHang WHERE id_shipper = :role");
			query.setParameter("role", role);
			System.out.println(query);
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

	// =======PHÂN TRANG ĐƠN HÀNG
	public List<DatHang> getListNav(int start, int limit) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("FROM DatHang ORDER BY id DESC");
			query.setFirstResult(start);
			query.setMaxResults(limit);
			// System.out.println(query);
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

	public List<DatHang> getListNavofShipper(int start, int limit, String role) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("FROM DatHang WHERE id_shipper = :role ORDER BY id DESC");
			query.setFirstResult(start);
			query.setMaxResults(limit);
			query.setParameter("role", role);
			System.out.println(query);
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

	// Kiểm tra xem user đã đặt hàng hay chưa
	public List<DatHang> getOrderByUser(String username) {
		List<DatHang> list = null;
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session
					.createQuery("FROM DatHang WHERE id_khachhang = :username  OR id_nhanvien = :username");
			query.setParameter("username", username);
			list = query.list();
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
		return list;
	}

	// kiểm tra danh mục hàng hoas đã được thêm sản phẩm chưa
	public List<DatHang> getProductByBrand(int id) {
		List<DatHang> list = null;
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("FROM HangHoa WHERE idloai = :id");
			query.setParameter("id", id);
			list = query.list();
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
		return list;
	}
}
