package Model;

import ptithcm.entity.HangHoa;

public class Cart {

	private HangHoa product;
	private int quantity;
	private long price;
	private long totalPrice;
//	private double totalPrice;
	
	public Cart() {
		this.quantity = 0;
		// TODO Auto-generated constructor stub
	}

	public HangHoa getProduct() {
		return product;
	}

	public void setProduct(HangHoa product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}


	
}
