package ptithcm.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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

import Model.Cart;
import Model.Import;
import ptithcm.entity.ChiTietDatHang;
import ptithcm.entity.ChiTietPhieuNhap;
import ptithcm.entity.DatHang;
import ptithcm.entity.HangHoa;
import ptithcm.entity.LoaiHang;
import ptithcm.entity.NhaCungCap;
import ptithcm.entity.PhieuNhap;
import ptithcm.entity.UserID;

@Transactional
@Controller
@RequestMapping("/import/")
public class ImportController {
	@Autowired
	SessionFactory factory;
	Date date = new Date();

	// load danh sách nhà cung cấp ra combobox
	@ModelAttribute("provider")
	public List<NhaCungCap> getProviders() {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhaCungCap";
		Query query = session.createQuery(hql);
		List<NhaCungCap> list = query.list();
		return list;
	}

	@RequestMapping("index")
	public String viewImport(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 2 && user.getRole().getId() != 6) {
			return "redirect:/admin/index.htm";
		}

		model.put("imports", getListNav(0, 10));
		model.put("totalitem", totalItem() / 10);

		return "import/index";
	}

	@RequestMapping(value = "index/{page}", method = RequestMethod.GET)
	public String viewImportListByPage(ModelMap model, HttpSession session, @PathVariable("page") int page,
			HttpServletRequest request) {
		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 2 && user.getRole().getId() != 6) {
			return "redirect:/admin/index.htm";
		}

		model.put("imports", getListNav((page - 1) * 10, 10));
		model.put("totalitem", totalItem() / 10);

		return "import/index";
	}

	// =====XEM CHI TIẾT PHIẾU NHẬP
	@RequestMapping(value = "detailImport/{id}")
	public String detailImport(ModelMap model, @PathVariable("id") Integer id, HttpServletRequest request) {
		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 2 && user.getRole().getId() != 6) {
			return "redirect:/admin/index.htm";
		}

		Session session = factory.getCurrentSession();
		// PhieuNhap a = (PhieuNhap) session.get(PhieuNhap.class, id);
		String hql = "FROM ChiTietPhieuNhap WHERE phieunhap_id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		List<ChiTietPhieuNhap> list = query.list();
		// System.out.println(list);
		// List<ChiTietPhieuNhap> list = (List<ChiTietPhieuNhap>)
		// a.getDetail_import();
		model.addAttribute("detail", list);
		return "import/detailImport";
	}

	@RequestMapping(value = "insert", method = RequestMethod.GET)
	public String insertImport(ModelMap model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		HashMap<Integer, Import> importItem = (HashMap<Integer, Import>) session.getAttribute("importList");
		if (importItem == null) {
			importItem = new HashMap<>();
		}

		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		model.addAttribute("imports", new PhieuNhap());
		return "import/insert";
	}

	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public String insertImport(ModelMap model, @ModelAttribute("imports") PhieuNhap imports, HttpServletRequest request,
			HttpSession session, ChiTietPhieuNhap detail_import) {
		HashMap<Integer, Import> importItem = (HashMap<Integer, Import>) session.getAttribute("importList");
		if (importItem == null) {
			importItem = new HashMap<>();
		}

		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");
		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}

		if (imports.getNhacungcap().getId().equals("")) {
			model.addAttribute("message", "Vui Lòng Chọn Nhà Cung Cấp");
			return "import/insert";
			// System.out.println(imports.getNhacungcap().getId() + "eong");
		} else {
			Session session1 = factory.openSession();
			Transaction t = session1.beginTransaction();
			imports.setDate(new Date());
			imports.setUserid(user);
			imports.setStatus_import(0);
			imports.setAmount(totalPrice(importItem));
			imports.setNhacungcap(imports.getNhacungcap());
			try {
				session1.save(imports);
				for (Entry<Integer, Import> entry : importItem.entrySet()) {
					detail_import = new ChiTietPhieuNhap();
					detail_import.setPhieunhaps(imports);
					detail_import.setHanghoas(entry.getValue().getProduct());
					detail_import.setQty(entry.getValue().getQuantity());
					detail_import.setPrice(entry.getValue().getPrice());
					session1.save(detail_import);

				}
				t.commit();
				// xóa tất cả sản phẩm khỏi session
				importItem = new HashMap<>();
				session.setAttribute("importList", importItem);
				return "redirect:/import/index.htm";
			} catch (Exception e) {
				t.rollback();
				model.addAttribute("message", "Thêm thất bại !");
			} finally {
				session1.close();
			}
		}

		return "import/insert";
	}

	@RequestMapping(value = "insert/{id}", method = RequestMethod.GET)
	public String insertImportId(ModelMap model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @PathVariable("id") Integer id) {
		HashMap<Integer, Import> importItem = (HashMap<Integer, Import>) session.getAttribute("importList");
		if (importItem == null) {
			importItem = new HashMap<>();
		}

		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 2) {
			return "redirect:/admin/index.htm";
		}
		session.setAttribute("importList", importItem);
		return "import/insert";
	}

	@RequestMapping(value = "insert/{id}", method = RequestMethod.POST)
	public String insertImportId(ModelMap model, HttpSession session, @PathVariable("id") Integer id,
			@ModelAttribute("imports") PhieuNhap imports, HttpServletRequest request, HttpServletResponse response) {
		HashMap<Integer, Import> importItem = (HashMap<Integer, Import>) session.getAttribute("importList");

		String quantity = request.getParameter("qty").trim();
		String str_price = request.getParameter("price").trim();
		int qty = Integer.parseInt(quantity);
		long price = Long.parseLong(str_price);
		System.out.println(qty + price);

		if (importItem == null) {
			importItem = new HashMap<>();
		}
		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		if (admin_session.getAttribute("user") == null) {
			model.addAttribute("isLogin", false);
			session.setAttribute("isLogin", false);
		}
		if (admin_session.getAttribute("user") != null) {
			model.addAttribute("isLsogin", true);
			session.setAttribute("isLogin", true);
		}

		HangHoa product = getHH(id);
		if (product != null) {
			if (importItem.containsKey(id)) {
				Import item = importItem.get(id);
				item.setProduct(product);
				item.setQuantity(item.getQuantity() + qty);
				item.setPrice(price);
				importItem.put(id, item);
			} else {
				Import item = new Import();
				item.setProduct(product);
				item.setQuantity(qty);
				item.setPrice(price);
				importItem.put(id, item);
			}
		}
		session.setAttribute("importList", importItem);

		return "import/insert";
	}

	@RequestMapping(value = "confirm/{id}", method = RequestMethod.GET)
	public String confirmImport(ModelMap model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @PathVariable("id") Integer id) {

		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 2 && user.getRole().getId() != 6) {
			return "redirect:/admin/index.htm";
		}
		Session session1 = factory.getCurrentSession();
		PhieuNhap imports = (PhieuNhap) session1.get(PhieuNhap.class, id);
		String hql = "FROM ChiTietPhieuNhap WHERE phieunhap_id = :id";
		Query query = session1.createQuery(hql);
		query.setParameter("id", id);
		List<ChiTietPhieuNhap> list = query.list();
		model.addAttribute("detail", list);
		model.addAttribute("imports", imports);

		return "import/confirm";
	}

	@RequestMapping(value = "confirm/{id}", method = RequestMethod.POST)
	public String confirmImportPost(ModelMap model, HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") Integer id) {

		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 2 && user.getRole().getId() != 6) {
			return "redirect:/admin/index.htm";
		}

		Session session1 = factory.getCurrentSession();
		PhieuNhap imports = (PhieuNhap) session1.get(PhieuNhap.class, id);
		String hql = "FROM ChiTietPhieuNhap WHERE phieunhap_id = :id";
		Query query = session1.createQuery(hql);
		query.setParameter("id", id);
		List<ChiTietPhieuNhap> list = query.list();

		for (int i = 0; i < list.size(); i++) {
			String hql1 = "UPDATE HangHoa SET in_stock = in_stock + (:in_stock) , "
					+ "price = ((:price)*2*(:in_stock) + (price*in_stock))/(in_stock + (:in_stock)) "
					+ "WHERE id = :id";
			Query query1 = session1.createQuery(hql1);
			query1.setParameter("in_stock", list.get(i).getQty());
			query1.setParameter("id", list.get(i).getHanghoas().getId());
			query1.setFloat("price", list.get(i).getPrice());
			int result = query1.executeUpdate();
		}
		imports.setStatus_import(1);
		model.addAttribute("message", "Đã xác nhận!");

		return "import/confirm";
	}

	// ====================CẬP NHẬT PHIẾU NHẬP================
	@RequestMapping(value = "update/{id}")
	public String updateImport(ModelMap model, @PathVariable("id") Integer id, HttpServletRequest request,
			HttpSession session) {
		HashMap<Integer, Import> importItem = (HashMap<Integer, Import>) session.getAttribute("importList");
		if (importItem == null) {
			importItem = new HashMap<>();
		}

		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		UserID user = (UserID) admin_session.getAttribute("user");
		if (user.getRole().getId() != 2 && user.getRole().getId() != 6) {
			return "redirect:/admin/index.htm";
		}

		Session session1 = factory.getCurrentSession();
		String hql = "FROM ChiTietPhieuNhap WHERE phieunhap_id = :id";
		Query query = session1.createQuery(hql);
		query.setParameter("id", id);
		// importItem = (HashMap<Integer, Import>) query.list();
		List<ChiTietPhieuNhap> list = query.list();
		Import item = new Import();
		for (int i = 0; i < list.size(); i++) {

			item.setProduct(list.get(i).getHanghoas());
			item.setQuantity(list.get(i).getQty());
			item.setPrice(list.get(i).getPrice());
			importItem.put(list.get(i).getHanghoas().getId(), item);

		}
		// session.setAttribute("importList", importItem);
		// model.addAttribute("product", null);
		model.addAttribute("id_phieunhap", id);
		model.addAttribute("detail", list);
		return "import/update";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.POST)
	public String updateImport(ModelMap model, @PathVariable("id") Integer id,
			HttpServletRequest request) {

		HttpSession user_session = request.getSession();
		user_session.getAttribute("user");
		if (user_session.getAttribute("user") == null) {
			return "redirect:/user/login.htm";
		}

		String number = request.getParameter("number");
		String var_price = request.getParameter("price");
		String var_idhh = request.getParameter("hanghoaid");
		int quantity = Integer.valueOf(number);
		long price = Long.valueOf(var_price);
		int hanghoaid = Integer.valueOf(var_idhh);
		long totalPrice = 0;
		System.out.println(quantity + " " + price + " " + hanghoaid);
		Session session = factory.getCurrentSession();
		PhieuNhap phieunhap = (PhieuNhap) session.get(PhieuNhap.class, id);

		Session session1 = factory.getCurrentSession();
		String hql = "FROM ChiTietPhieuNhap WHERE phieunhap_id=:id";
		Query query = session1.createQuery(hql);
		query.setParameter("id", id);
		List<ChiTietPhieuNhap> list = query.list();
//		HangHoa product = getHH(hanghoaid);
		
//		Session session2 = factory.getCurrentSession();
//		String hql1 = "UPDATE ChiTietPhieuNhap SET qty = (:qty), price = (:price) "
//				+ "WHERE hanghoa_id = (:id1) AND phieunhap_id = (:id2)";
//		Query query1 = session2.createQuery(hql1);
//		query1.setParameter("qty", quantity);
//		query1.setParameter("price", price);
//		query1.setParameter("id1", hanghoaid);
//		query1.setParameter("id2", id);
//		int result = (int) query1.executeUpdate();
		
		Session session2 = factory.openSession();
		Transaction t = session2.beginTransaction();
		
		
		try {
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getHanghoas().getId() == hanghoaid){
					list.get(i).setQty(quantity);
					list.get(i).setPrice(price);
				}
				session2.update(list.get(i));
				totalPrice += list.get(i).getQty() * list.get(i).getPrice();
			}
			phieunhap.setAmount(totalPrice);
			t.commit();
			model.addAttribute("message", "Cập nhật thành công !");
			model.addAttribute("detail", list);
			model.addAttribute("id_phieunhap", id);
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Cập nhật thất bại !");
			model.addAttribute("detail", list);
			model.addAttribute("id_phieunhap", id);
		} finally {
			session2.close();
		}
		return "import/update";
	}
//		long total = quantity * price;

//		System.out.println(result);
//		String hql1 = "UPDATE PhieuNhap SET amount = (:amount)  WHERE  id = (:id2)";
//		Query query1 = session1.createQuery(hql1);
//		query1.setParameter("amount", total);
//		query1.setParameter("id2", id);
//		int result1 = (int) query1.executeUpdate();
//		System.out.println(result1);
//		String hql2 = "FROM ChiTietPhieuNhap WHERE phieunhap_id = :id";
//		Query query2 = session1.createQuery(hql2);
//		query2.setParameter("id", id);
//		List<ChiTietPhieuNhap> list = query2.list();
//		model.addAttribute("detail", list);



	@RequestMapping("search")
	public String searchImport(ModelMap model, HttpServletRequest req, HttpServletResponse response,
			@ModelAttribute("imports") PhieuNhap imports) {

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
		String hql = "FROM PhieuNhap WHERE dbo.ufn_removeMark(id) LIKE '%" + keyword + "%'";
		Query query = session.createQuery(hql);
		List<PhieuNhap> list = query.list();

		if (list.isEmpty()) {
			model.addAttribute("message", "Không có kết quả !");
		} else {
			model.addAttribute("imports", list);
		}

		return "import/index";
	}

	@RequestMapping("searchProduct")
	public String searchProduct(ModelMap model, HttpServletRequest req, HttpServletResponse response,
			@ModelAttribute("imports") PhieuNhap imports) {

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
		String hql = "FROM HangHoa WHERE dbo.ufn_removeMark(id) LIKE '%" + keyword + "%'";
		Query query = session.createQuery(hql);
		List<HangHoa> list = query.list();

		if (list.isEmpty()) {
			model.addAttribute("message", "Không có kết quả !");
		} else {
			model.addAttribute("product", list);
		}

		return "import/insert";
	}

	@RequestMapping("test")
	public String search(ModelMap model, HttpServletRequest req, HttpServletResponse response) {

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
		String id = req.getParameter("id_phieunhap");
		System.out.println(id);
		int id_pn = Integer.valueOf(id);

		while (keyword.indexOf(" ") != -1) {
			keyword = keyword.replaceAll(" ", "");
		}
		System.out.println(keyword);

		Session session = factory.getCurrentSession();
		String hql = "FROM HangHoa WHERE dbo.ufn_removeMark(id) LIKE '%" + keyword + "%'";
		Query query = session.createQuery(hql);
		List<HangHoa> list = query.list();

		Session session1 = factory.getCurrentSession();
		String hql1 = "FROM ChiTietPhieuNhap WHERE phieunhap_id = :id";
		Query query1 = session1.createQuery(hql1);
		query1.setParameter("id", id_pn);
		// importItem = (HashMap<Integer, Import>) query.list();
		List<ChiTietPhieuNhap> list1 = query1.list();
		if (list.isEmpty()) {
			model.addAttribute("message", "Không có kết quả !");
			model.addAttribute("detail", list1);
		} else {
			model.addAttribute("active", "active");
			model.addAttribute("id_phieunhap", id_pn);
			model.addAttribute("product", list);
			model.addAttribute("detail", list1);
		}

		return "import/update";
	}

	@RequestMapping(value = "add/{id}", method = RequestMethod.POST)
	public String addImportId(ModelMap model, HttpSession session, @PathVariable("id") Integer id,
			@ModelAttribute("imports") PhieuNhap imports, HttpServletRequest request, HttpServletResponse response,
			ChiTietPhieuNhap detail_import) {

		String id_phieunhap = request.getParameter("id_phieunhap");
		String quantity = request.getParameter("qty").trim();
		String str_price = request.getParameter("price").trim();
		int qty = Integer.parseInt(quantity);
		long price = Long.parseLong(str_price);
		int id_pn = Integer.parseInt(id_phieunhap);
		HttpSession admin_session = request.getSession();
		admin_session.getAttribute("user");

		if (admin_session.getAttribute("user") == null) {
			return "redirect:/admin/index.htm";
		}
		if (admin_session.getAttribute("user") == null) {
			model.addAttribute("isLogin", false);
			session.setAttribute("isLogin", false);
		}
		if (admin_session.getAttribute("user") != null) {
			model.addAttribute("isLsogin", true);
			session.setAttribute("isLogin", true);
		}
		
		Session session2 = factory.getCurrentSession();
		PhieuNhap phieunhap = (PhieuNhap) session2.get(PhieuNhap.class, id_pn);
		
		String hql = "From ChiTietPhieuNhap WHERE phieunhap_id=:id";
		Query query = session2.createQuery(hql);
		query.setParameter("id", id_pn);
		List<ChiTietPhieuNhap> list = query.list();
		HangHoa product = getHH(id);
		boolean isProduct = false;long totalPrice =0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getHanghoas().getId() == id) {
				isProduct = true;
			}
		}
		if (isProduct == true) {
			Session session1 = factory.openSession();
			Transaction t = session1.beginTransaction();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getHanghoas().getId() == id) {
					list.get(i).setQty(list.get(i).getQty() + qty);
					list.get(i).setPrice(price);
					session1.update(list.get(i));
				}
				totalPrice += list.get(i).getQty() * list.get(i).getPrice();
			}
			phieunhap.setAmount(totalPrice);
			try {
				t.commit();
				model.addAttribute("message", "Đã cập nhật!");
				model.addAttribute("detail", list);
				model.addAttribute("id_phieunhap", id_pn);
				return "import/update";
			} catch (Exception e) {
				t.rollback();
				model.addAttribute("message", "Cập nhật thất bại !");
				model.addAttribute("detail", list);
				model.addAttribute("id_phieunhap", id_pn);
			} finally {
				session1.close();
			}
		}
		else {
			detail_import.setHanghoas(product);
			detail_import.setQty(qty);
			detail_import.setPrice(price);
			detail_import.setPhieunhaps(phieunhap);
			list.add(detail_import);
			for (int i = 0; i < list.size(); i++) {
				totalPrice += list.get(i).getQty() * list.get(i).getPrice();
			}
			Session session1 = factory.openSession();
			Transaction t = session1.beginTransaction();
			phieunhap.setAmount(totalPrice);
			try {
				session1.save(detail_import);
				t.commit();
				model.addAttribute("message", "Đã cập nhật!");
				model.addAttribute("detail", list);
				model.addAttribute("id_phieunhap", id_pn);
				return "import/update";
			} catch (Exception e) {
				t.rollback();
				model.addAttribute("message", "Cập nhật thất bại!");
				model.addAttribute("detail", list);
				model.addAttribute("id_phieunhap", id_pn);
			} finally {
				session1.close();
			}
		}
		return "import/update";
	}

	public int totalItem() {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			Query query = session.createQuery("SELECT count(*) FROM PhieuNhap");
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
	public List<PhieuNhap> getListNav(int start, int limit) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("FROM PhieuNhap ORDER BY id DESC");
			query.setFirstResult(start);
			query.setMaxResults(limit);
			List<PhieuNhap> list = query.list();
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

	public long totalPrice(HashMap<Integer, Import> importItem) {
		int count = 0;
		for (Entry<Integer, Import> list : importItem.entrySet()) {
			count += (list.getValue().getPrice() * list.getValue().getQuantity());
		}
		return count;
	}
}
