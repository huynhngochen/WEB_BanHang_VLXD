package ptithcm.controller;

import java.io.IOException;
import java.util.HashMap;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import Model.Cart;
import ptithcm.entity.HangHoa;
import ptithcm.entity.LoaiHang;
import ptithcm.entity.UserID;

@Transactional
@Controller
@RequestMapping("user/cart/")
public class CartController {

	@Autowired
	SessionFactory factory;

	// load danh sách hãng xe ra combobox
	@ModelAttribute("brand")
	public List<LoaiHang> getBrands() {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiHang";
		Query query = session.createQuery(hql);
		List<LoaiHang> list = query.list();
		return list;
	}

	@RequestMapping(value = "addCart")
	public String viewCart(ModelMap model, HttpSession session, 
			HttpServletRequest request, HttpServletResponse response) {
		HashMap<Integer, Cart> cartItems = (HashMap<Integer, Cart>) session.getAttribute("cart");

		HttpSession user_session = request.getSession();
		user_session.getAttribute("user");
		UserID userID = (UserID) user_session.getAttribute("user");
//		System.out.println(userID.getThongtinuser().getId());
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
		if(cartItems.isEmpty()){
			model.addAttribute("message", "Không có sản phẩm nào trong giỏ hàng của bạn");
		}
		else {
			session.setAttribute("totalprice", totalPrice(cartItems));
			session.setAttribute("cart", cartItems);
		}
		
		model.addAttribute("user", userID);

		return "cart/addCart";
	}
	
	@RequestMapping("addCart/{id}")
	public String viewCartGet(ModelMap model, HttpSession session, @PathVariable("id") Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		HashMap<Integer, Cart> cartItems = (HashMap<Integer, Cart>) session.getAttribute("cart");
		
		if (cartItems == null) {
			cartItems = new HashMap<>();
		}
		HttpSession user_session = request.getSession();
		user_session.getAttribute("user");
		
		UserID userID = (UserID) user_session.getAttribute("user");
		
		if (user_session.getAttribute("user") == null) {
			return "redirect:/user/login.htm";
		}
		if (user_session.getAttribute("user") == null) {
			model.addAttribute("isLogin", false);
			session.setAttribute("isLogin", false);
		}
		if (user_session.getAttribute("user") != null) {
			model.addAttribute("isLsogin", true);
			session.setAttribute("isLogin", true);
		}
		HangHoa product = getHH(id);
		if (product != null) {
			if (cartItems.containsKey(id)) {
				Cart item = cartItems.get(id);
				item.setProduct(product);
				item.setQuantity(item.getQuantity() + 1);
				item.setTotalPrice((product.getPrice() * (100-product.getDiscount())/100) * item.getQuantity());
				item.setPrice(product.getPrice()* (100 - product.getDiscount())/100);
				cartItems.put(id, item);
			} else {
				Cart item = new Cart();
				item.setProduct(product);
				item.setQuantity(1);
				item.setPrice(product.getPrice()* (100 - product.getDiscount())/100);
				item.setTotalPrice((product.getPrice() * (100-product.getDiscount())/100) * item.getQuantity());
				cartItems.put(id, item);
				
			}
		}
		model.addAttribute("user", userID);
		session.setAttribute("cart", cartItems);
		session.setAttribute("totalprice", totalPrice(cartItems));

		return "cart/addCart";
	}

	@RequestMapping(value = "addCart/{id}", method = RequestMethod.POST)
	public String viewCart(ModelMap model, HttpSession session, @PathVariable("id") Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		HashMap<Integer, Cart> cartItems = (HashMap<Integer, Cart>) session.getAttribute("cart");

		String number = request.getParameter("number");
		int number_product = Integer.parseInt(number);
		System.out.println(number_product);
		
		if (cartItems == null) {
			cartItems = new HashMap<>();
		}
		HttpSession user_session = request.getSession();
		user_session.getAttribute("user");
		
		UserID userID = (UserID) user_session.getAttribute("user");
//		System.out.println(userID.getThongtinuser().getId());
		if (user_session.getAttribute("user") == null) {
			return "redirect:/user/login.htm";
		}
		if (user_session.getAttribute("user") == null) {
			model.addAttribute("isLogin", false);
			session.setAttribute("isLogin", false);
		}
		if (user_session.getAttribute("user") != null) {
			model.addAttribute("isLsogin", true);
			session.setAttribute("isLogin", true);
		}

		HangHoa product = getHH(id);
		if (product != null) {
			if (cartItems.containsKey(id)) {
				Cart item = cartItems.get(id);
				item.setProduct(product);
				item.setQuantity(item.getQuantity() + number_product);
				item.setPrice(product.getPrice()* (100 - product.getDiscount())/100);
				item.setTotalPrice((product.getPrice() * (100-product.getDiscount())/100) * item.getQuantity());
				cartItems.put(id, item);
			} else {
				Cart item = new Cart();
				item.setProduct(product);
				item.setQuantity(number_product);
				item.setPrice(product.getPrice()* (100 - product.getDiscount())/100);
				item.setTotalPrice((product.getPrice() * (100-product.getDiscount())/100) * item.getQuantity());
				cartItems.put(id, item);
			}
		}
		model.addAttribute("user", userID);
		session.setAttribute("cart", cartItems);
		session.setAttribute("totalprice", totalPrice(cartItems));

		return "cart/addCart";
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public String deleteCart(ModelMap model, HttpServletRequest request, HttpSession session,
			@PathVariable("id") Integer id) {
		HttpSession user_session = request.getSession();
		user_session.getAttribute("user");
		if (user_session.getAttribute("user") == null) {
			return "redirect:/user/login.htm";
		}
		HashMap<Integer, Cart> cartItems = (HashMap<Integer, Cart>) session.getAttribute("cart");
		if (cartItems == null) {
			cartItems = new HashMap<>();
		}
		
		if (cartItems.containsKey(id)) {
			cartItems.remove(id);
			session.setAttribute("cart", cartItems);
			session.setAttribute("totalprice", totalPrice(cartItems));
		}
		if(cartItems.isEmpty()){
			model.addAttribute("message", "Không có sản phẩm nào trong giỏ hàng của bạn");
		}

		return "cart/addCart";
	}
	
//	@RequestMapping(value = "update", method = RequestMethod.GET)
//	public String updateCart(ModelMap model, HttpSession session,
//			HttpServletRequest request) {
//		HashMap<Integer, Cart> cartItems = (HashMap<Integer, Cart>) session.getAttribute("cart");
//
//		HttpSession user_session = request.getSession();
//		user_session.getAttribute("user");
//		if (user_session.getAttribute("user") == null) {
//			return "redirect:/user/login.htm";
//		}
////		System.out.println(id);
//		for(Entry<Integer, Cart> entry : cartItems.entrySet()){
//			
//			System.out.println();
//		}
////		for(Entry<Integer, Cart> entry : cartItems.entrySet()){
////			String number = request.getParameter("number");
////			int quantity = Integer.valueOf(number);
////			System.out.println(number + " - " + id);
////		}
////		
//		
//
//		
//		session.setAttribute("totalprice", totalPrice(cartItems));
//		session.setAttribute("cart", cartItems);
//		return "cart/addCart";
//	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateCart(ModelMap model, HttpSession session, @PathVariable("id") Integer id,
			HttpServletRequest request) {	

		HttpSession user_session = request.getSession();
		user_session.getAttribute("user");
		if (user_session.getAttribute("user") == null) {
			return "redirect:/user/login.htm";
		}
	System.out.println(id);
		String number = request.getParameter("number");
		int quantity = Integer.valueOf(number);
//		System.out.println(number);
		HashMap<Integer, Cart> cartItems = (HashMap<Integer, Cart>) session.getAttribute("cart");
		if (cartItems == null) {
			cartItems = new HashMap<>();
		}
		HangHoa product = getHH(id);
		if (cartItems.containsKey(id)) {
			Cart item = cartItems.get(id);
			item.setProduct(product);
			item.setQuantity(quantity);
			item.setPrice(product.getPrice()* (100 - product.getDiscount())/100);
			item.setTotalPrice((product.getPrice() * (100 - product.getDiscount()) / 100) * quantity);
			cartItems.put(id, item);
		}
		
		session.setAttribute("totalprice", totalPrice(cartItems));
		session.setAttribute("cart", cartItems);
		return "cart/addCart";
	}
	
	@RequestMapping(value = "substract/{id}", method = RequestMethod.GET)
	public String substractCart(ModelMap model, HttpSession session, 
			@PathVariable("id") Integer id,
			HttpServletRequest request) {

		HttpSession user_session = request.getSession();
		user_session.getAttribute("user");
		if (user_session.getAttribute("user") == null) {
			return "redirect:/user/login.htm";
		}
		System.out.println(id);
		String number = request.getParameter("number");
		int quantity = Integer.valueOf(number);

		HashMap<Integer, Cart> cartItems = (HashMap<Integer, Cart>) session.getAttribute("cart");
		if (cartItems == null) {
			cartItems = new HashMap<>();
		}
		HangHoa product = getHH(id);
		if (cartItems.containsKey(id)) {
			Cart item = cartItems.get(id);
			item.setProduct(product);
			item.setQuantity(item.getQuantity()- quantity);
			if(item.getQuantity() == 0){
				cartItems.remove(id);
				session.setAttribute("cart", cartItems);
				session.setAttribute("totalprice", totalPrice(cartItems));
				return "cart/addCart";
			}
			else {
				item.setPrice(product.getPrice()* (100 - product.getDiscount())/100);
				item.setTotalPrice((product.getPrice() * (100 - product.getDiscount()) / 100) * item.getQuantity());
				cartItems.put(id, item);
			}
		}
		
		session.setAttribute("totalprice", totalPrice(cartItems));
		session.setAttribute("cart", cartItems);
		return "cart/addCart";
	}
	
//	@RequestMapping(value = "plus", method = RequestMethod.GET)
//	public String plusCart(ModelMap model, HttpSession session, 
//			HttpServletResponse response,
//			HttpServletRequest request) throws IOException {
//
//		HttpSession user_session = request.getSession();
//		user_session.getAttribute("user");
//		if (user_session.getAttribute("user") == null) {
//			return "redirect:/user/login.htm";
//		}
//	
//
////		HashMap<Integer, Cart> cartItems = (HashMap<Integer, Cart>) session.getAttribute("cart");
////		if (cartItems == null) {
////			cartItems = new HashMap<>();
////		}
////		HangHoa product = getHH(id);
////		if (cartItems.containsKey(id)) {
////			Cart item = cartItems.get(id);
////			item.setProduct(product);
////			item.setQuantity(item.getQuantity()+1);
////			item.setPrice(product.getPrice()* (100 - product.getDiscount())/100);
////			item.setTotalPrice((product.getPrice() * (100 - product.getDiscount()) / 100) * item.getQuantity());
////			cartItems.put(id, item);
////		}
////		
////		session.setAttribute("totalprice", totalPrice(cartItems));
////		session.setAttribute("cart", cartItems);
//		String userName = request.getParameter("name").trim();
//		if(userName == null || "".equals(userName)){
//			userName = "Guest";
//		}
//		
//		String greetings = "Hello " + userName;
//		
//		response.setContentType("text/plain");
//		response.getWriter().write(greetings);
//		return "cart/addCart";
//	}

	public HangHoa findById(int productId) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			String hql = "FROM HangHoa  WHERE id = :id";
			Query query = (Query) session.createQuery(hql);
			query.setParameter("id", productId);
			HangHoa obj = (HangHoa) query.uniqueResult();
			transaction.commit();
			return obj;
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

	public long totalPrice(HashMap<Integer, Cart> cartItems) {
		int count = 0;
		for (Entry<Integer, Cart> list : cartItems.entrySet()) {
			count += (list.getValue().getProduct().getPrice() *(100 - list.getValue().getProduct().getDiscount()) /100 ) * list.getValue().getQuantity();
		}
		return count;
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
