package ptithcm.controller;

import java.awt.Checkbox;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
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

import com.mchange.util.MessageLogger;

import javafx.scene.control.Alert;
import ptithcm.entity.ChiTietDatHang;
import ptithcm.entity.DatHang;
import ptithcm.entity.HangHoa;
import ptithcm.entity.LoaiHang;
import ptithcm.entity.NhaCungCap;
import ptithcm.entity.PhieuNhap;
import ptithcm.entity.UserID;

@Transactional
@Controller
@RequestMapping("/admin/product/")
public class ProductController {
	@Autowired
	SessionFactory factory;
	@Autowired
	ServletContext context;
	
	// =============DANH SÁCH SẢN PHẨM
	@RequestMapping("index")
	public String viewProduct(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");
		
		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		model.put("product", getListNav(0, 10));
		model.put("totalitem", totalItem() / 10);
		model.addAttribute("brand", getBrands());
		return "product/index";
	}
	
	@RequestMapping(value = "index/{page}", method = RequestMethod.GET)
	public String viewProductListByPage(ModelMap model, HttpSession session, @PathVariable("page") int page,
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
		
		model.put("product", getListNav((page - 1) * 10, 10));
		model.put("totalitem", totalItem() / 10);
		return "product/index";
	}

	// xem sản phẩm theo hãng
	@RequestMapping("finding/{idloai}")
	public String productsOfBrand(ModelMap model, @PathVariable("idloai") Integer idloai, HttpServletRequest req,
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
		String hql = "FROM HangHoa p WHERE p.loaihang.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", idloai);
		List<HangHoa> list = query.list();
		model.addAttribute("product", list);

		return "product/finding";
	}

	// hiển thị thông tin sản phẩm
	@RequestMapping("show/{id}")
	public String viewProductbyId(ModelMap model, @PathVariable("id") Integer id, HttpServletRequest req,
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
		HangHoa product = (HangHoa) session.get(HangHoa.class, id);

		model.addAttribute("product", product);
		return "product/show";
	}

	// insert san pham
	@RequestMapping(value = "insert", method = RequestMethod.GET)
	public String insertProduct(ModelMap model, HttpServletRequest request, 
			HttpServletResponse response) {
		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		model.addAttribute("product", new HangHoa());
		return "product/insert";
	}

	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public String insertProduct(ModelMap model, @ModelAttribute("product") HangHoa product, HttpServletRequest request,
			@RequestParam("photo") MultipartFile photo, BindingResult errors) {


		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		

		if (product.getName().trim().length() == 0) {
			errors.rejectValue("name", "product", "Vui Lòng Nhập Tên Hàng!");
		}
		if (product.getPrice() < 0) {
			errors.rejectValue("price", "product", "Giá không được < 0!");
		}
		if (product.getDiscount() < 0) {
			errors.rejectValue("discount", "product", "Giảm giá Không Được < 0!");
		}
//		if (product.getIn_stock() < 0) {
//			errors.rejectValue("in_stock", "product", "Tồn kho Không Được < 0!");
//		}
		if (errors.hasErrors()) {

		} else {
			if (photo.isEmpty()) {
				model.addAttribute("message_image", "Vui Lòng Chọn Hình Ảnh!!");
			} else {
				Session session = factory.openSession();
				Transaction t = session.beginTransaction();
				try {
					String photoPath = context.getRealPath("./images/" + photo.getOriginalFilename());
					photo.transferTo(new File(photoPath));
					product.setImages_link(photo.getOriginalFilename());
					product.setCount_buy(0);
					product.setIn_stock(0);
					session.save(product);
					t.commit();
					model.addAttribute("message", "Thêm thành công!");
					product.setName("");
					product.setDescription("");
					product.setDiscount(0);
					product.setIn_stock(0);
					product.setImages_link(null);
					product.setPrice(0);
//					return "redirect:/admin/admin-home.htm";
				} catch (Exception e) {
					t.rollback();
					model.addAttribute("message", "Thêm thất bại!");
				} finally {
					session.close();
				}
			}
		}
		return "product/insert";
	}

	// Cập nhật sản phẩm
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
		HangHoa product = (HangHoa) session.get(HangHoa.class, id);
		
		System.out.println(product.getPrice());
		NumberFormat nf = NumberFormat.getInstance();
		String price = nf.format(product.getPrice());
		
		model.addAttribute("price", price);
		model.addAttribute("product", product);
		return "product/update";
	}

	@RequestMapping("update")
	public String updateProduct(ModelMap model, @ModelAttribute("product") HangHoa product,
			@RequestParam("photo") MultipartFile photo, BindingResult errors, HttpServletRequest req) {
//		String price = req.getParameter("price");
//		product.setPrice(Float.parseFloat(price));
		HttpSession admin_session = req.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		
		Session session1 = factory.getCurrentSession();
		String hql = "SELECT price FROM ChiTietPhieuNhap "
				+ "WHERE phieunhap_id = (SELECT ISNULL(MAX(pn.id),0)"
				+ " FROM ChiTietPhieuNhap ctpn, PhieuNhap pn "
				+ "WHERE pn.id = ctpn.phieunhaps.id AND hanghoa_id = :id_hanghoa )"
				+ "AND hanghoa_id = :id_hanghoa";
		Query query = session1.createQuery(hql);
		query.setParameter("id_hanghoa", product.getId());
		long gianhap = (long) query.uniqueResult();
		
		
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		if (photo.isEmpty()) {
			if (product.getName().trim().length() == 0) {
				errors.rejectValue("name", "product", "Vui lòng nhập tên sản phẩm !");
			}
			if (product.getName().trim().length() == 0) {
				errors.rejectValue("name", "product", "Vui Lòng Nhập Tên Hàng!");
			}
			if (product.getPrice() < 0) {
				errors.rejectValue("price", "product", "Giá không được < 0!");
			}
			if (product.getPrice() < gianhap) {
				errors.rejectValue("price", "product", "Giá không được nhỏ hơn giá nhập!");
			}
			if (product.getDiscount() < 0) {
				errors.rejectValue("discount", "product", "Giảm giá Không Được < 0!");
			}
			if (product.getIn_stock() < 0) {
				errors.rejectValue("in_stock", "product", "Tồn kho Không Được < 0!");
			}
			if (errors.hasErrors()) {
			} else {
				String oldImage = product.getImages_link();
				Session session = factory.openSession();
				Transaction t = session.beginTransaction();
				try {
					product.setImages_link(oldImage);
					session.update(product);
					t.commit();
					model.addAttribute("message", "Cập nhật thành công !");
					return "redirect:/admin/product/index.htm";
				} catch (Exception e) {
					t.rollback();
					model.addAttribute("message", "Cập nhật thất bại !");
				} finally {
					session.close();
				}
			}
			return "product/update";
		} else {
			if (product.getName().trim().length() == 0) {
				errors.rejectValue("name", "product", "Vui lòng nhập tên sản phẩm !");
			}
			if (product.getName().trim().length() == 0) {
				errors.rejectValue("name", "product", "Vui Lòng Nhập Tên Hàng!");
			}
			if (product.getPrice() < 0) {
				errors.rejectValue("price", "product", "Giá không được < 0!");
			}
			if (product.getDiscount() < 0) {
				errors.rejectValue("discount", "product", "Giảm giá Không Được < 0!");
			}
			if (product.getIn_stock() < 0) {
				errors.rejectValue("in_stock", "product", "Tồn kho Không Được < 0!");
			}
			if (errors.hasErrors()) {

			} else {
				Session session = factory.openSession();
				Transaction t = session.beginTransaction();
				try {
					String photoPath = context.getRealPath("./images/" + photo.getOriginalFilename());
					photo.transferTo(new File(photoPath));
					product.setImages_link(photo.getOriginalFilename());
					session.update(product);
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
		return "product/update";
	}

	// Xóa sản phẩm
	@RequestMapping(value="delete/{id}", method = RequestMethod.GET)
	public String deleteProduct(ModelMap model, @PathVariable("id") Integer id, 
			HttpServletRequest req, HttpServletResponse response) {

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
		Session session = factory.openSession();
		HangHoa product = (HangHoa) session.get(HangHoa.class, id);
		Transaction t = session.beginTransaction();
		try {
			session.delete(product);
			t.commit();
			model.addAttribute("message", "Xóa thành công!");
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Sản phẩm đã được đặt trước đó không thể xóa!");
		} finally {
			session.close();
		}
		return "product/index";
	}



	@RequestMapping("productHot")
	public String productHot(ModelMap model, HttpServletRequest request) {
		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}

		model.put("product", getListNav(0, 10));
		model.put("totalitem", totalItem() / 10);
		return "product/productHot";
	}
	
	@RequestMapping(value = "productHot/{page}", method = RequestMethod.GET)
	public String productHotByPage(ModelMap model, HttpServletRequest request,
		 @PathVariable("page") int page) {
		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 6 && user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}

		model.put("product", getListNav((page - 1) * 10, 10));
		model.put("totalitem", totalItem() / 10);
		return "product/productHot";
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

	@ModelAttribute("ishot")
	 private List<String> dataForProductHot() {
	       List<String> list = new ArrayList<String>();
	       list.add("True");
	       return list;
	   }
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
