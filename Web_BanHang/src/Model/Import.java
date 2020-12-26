package Model;

import ptithcm.entity.HangHoa;

public class Import {
	private HangHoa product;
	private int quantity;
	private long price;
	private long amount;
	public Import() {
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
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}

	
}
