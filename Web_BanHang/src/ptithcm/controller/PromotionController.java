package ptithcm.controller;

import java.io.File;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

import Model.Import;
import ptithcm.entity.HangHoa;
import ptithcm.entity.KhuyenMai;
import ptithcm.entity.PhieuNhap;
import ptithcm.entity.UserID;

@Transactional
@Controller
@RequestMapping("/promotion/")
public class PromotionController {
	@Autowired
	SessionFactory factory;
	Date date = new Date();

	@RequestMapping("index")
	public String viewPromotion(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
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
		String hql = "FROM KhuyenMai";
		Query query = session.createQuery(hql);
		List<KhuyenMai> list = query.list();
		model.addAttribute("promotions", list);
		return "promotion/index";
	}

	@RequestMapping(value = "insert", method = RequestMethod.GET)
	public String insertPromotion(ModelMap model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		model.addAttribute("promotions", new KhuyenMai());

		return "promotion/insert";
	}

	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public String insertPromotion(ModelMap model, @ModelAttribute("promotions") KhuyenMai promotions,
			HttpServletRequest request, BindingResult errors) {

		HttpSession admin_session = request.getSession();
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
		String var_date1 = request.getParameter("date_start");
		String var_date2 = request.getParameter("date_finish");
		System.out.println(var_date1);
		System.out.println(var_date2);
		if (promotions.getName().trim().length() == 0) {
			errors.rejectValue("name", "promotions", "Tên khuyến mãi không bỏ trống!");
		}
		if (promotions.getDiscount() < 0) {
			errors.rejectValue("discount", "promotions", "Phần trăm giam giá không được < 0!");
		}
		if (promotions.getMakm().trim().length() == 0) {
			errors.rejectValue("makm", "promotions", "Mã khuyến mãi không bỏ trống!");
		}
		else {
			Session session1 = factory.getCurrentSession();
			String hql = "FROM KhuyenMai WHERE date_start <= :date AND "
					+ ":date <= date_finish AND makm = :makm";
			Query query = session1.createQuery(hql);
			query.setDate("date", date);
			query.setParameter("makm", promotions.getMakm().trim());
			List list = query.list();
			if(list.isEmpty() == false){
				errors.rejectValue("makm", "promotions", "Mã khuyến mãi nay dang co chuong trinh khac!");
			}
			else {
				if (errors.hasErrors()) {

				} else {
					if (var_date1.equals("")) {
						model.addAttribute("message", "Vui lòng chọn ngày BD!");
					} else if (var_date2.equals("")) {
						model.addAttribute("message", "Vui lòng chọn ngày KT!");
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
							return "promotion/insert";
						} else {
							Session session = factory.openSession();
							Transaction t = session.beginTransaction();
							try {
								promotions.setDate_start(date1);
								promotions.setDate_finish(date2);
								session.save(promotions);
								t.commit();
								model.addAttribute("message", "Thêm thành công!");
								promotions.setMakm("");
								promotions.setDiscount(0);
								promotions.setName("");
								promotions.setDate_start(null);
								promotions.setDate_finish(null);

							} catch (Exception e) {
								t.rollback();
								model.addAttribute("message", "Mã KM đã tồn tại!");
							} finally {
								session.close();
							}
						}
					}

				}
			}
		}
		
		return "promotion/insert";
	}

	@RequestMapping("update/{id}")
	public String updateProduct(ModelMap model, @PathVariable("id") Integer id, HttpServletRequest req,
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
		Session session = factory.getCurrentSession();
		KhuyenMai promotion = (KhuyenMai) session.get(KhuyenMai.class, id);

		if (promotion.getDate_start().before(date)) {
			model.addAttribute("error", "Chương trình khuyến mãi đã được áp dụng, không thể chỉnh sửa!");
			return "promotion/index";
		} else {
			model.addAttribute("promotions", promotion);
		}

		return "promotion/update";
	}

	@RequestMapping("update")
	public String updateProduct(ModelMap model, @ModelAttribute("promotions") KhuyenMai promotions, BindingResult errors,
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

		Date date1 = new Date();
		Date date2 = new Date();
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		String var_date1 = request.getParameter("date1");
		String var_date2 = request.getParameter("date2");
		if (promotions.getName().trim().length() == 0) {
			errors.rejectValue("name", "promotion", "Tên chương trình km không bỏ trống!");
		}
		if (promotions.getMakm().trim().length() == 0) {
			errors.rejectValue("makm", "promotion", "Mã km không bỏ trống!");
		}
		if (promotions.getDiscount() < 0) {
			errors.rejectValue("discount", "promotion", "Phần trăm giảm giá không Được < 0!");
		}

		if (errors.hasErrors()) {

		} else {
			if (var_date1.equals("")) {
				model.addAttribute("message", "Vui lòng chọn ngày BD!");
			} else if (var_date2.equals("")) {
				model.addAttribute("message", "Vui lòng chọn ngày KT!");
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
					return "promotion/update";
				} else {
					Session session = factory.openSession();
					Transaction t = session.beginTransaction();
					try {
						promotions.setDate_start(date1);
						promotions.setDate_finish(date2);
						session.update(promotions);
						t.commit();
						model.addAttribute("message", "Cập nhật thành công !");
						return "redirect:/promotion/index.htm";
					} catch (Exception e) {
						t.rollback();
						model.addAttribute("message", "Cập nhật thất bại !");
					} finally {
						session.close();
					}
				}
			}
		}
		return "promotion/update";
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public String deletePromotion(ModelMap model, @PathVariable("id") Integer id, HttpServletRequest req,
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
		Session session = factory.openSession();
		KhuyenMai promotion = (KhuyenMai) session.get(KhuyenMai.class, id);
		Transaction t = session.beginTransaction();
		try {
			session.delete(promotion);
			t.commit();
			model.addAttribute("message", "Xóa thành công!");
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Mã khuyến mãi đã được áp dụng không thể xóa!");
		} finally {
			session.close();
		}
		return "promotion/index";
	}
	
	@RequestMapping("search")
	public String searchPromotion(ModelMap model, HttpServletRequest req, 
			HttpServletResponse response) {

		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 2 && user.getRole().getId() != 6) {
			return "redirect:/admin/index.htm";
		}
		String keyword = req.getParameter("search").trim();

		while (keyword.indexOf(" ") != -1) {
			keyword = keyword.replaceAll(" ", "");
		}
		System.out.println(keyword);

		Session session = factory.getCurrentSession();
		String hql = "FROM KhuyenMai WHERE dbo.ufn_removeMark(makm) LIKE '%" + keyword + "%'";
		Query query = session.createQuery(hql);
		List<KhuyenMai> list = query.list();

		if (list.isEmpty()) {
			model.addAttribute("message", "Không có kết quả !");
		} else {
			model.addAttribute("promotions", list);
		}

		return "promotion/index";
	}
}
