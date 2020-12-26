package Model;

import ptithcm.entity.HangHoa;

public class ProfitProduct {
	
	private HangHoa product;
	private long totalBuy;
	private long totalImport;
	private int countBuy;
	private int countImport;
	private long totalProfit;
	
	public ProfitProduct() {
		// TODO Auto-generated constructor stub
	}

	public HangHoa getProduct() {
		return product;
	}

	public void setProduct(HangHoa product) {
		this.product = product;
	}

	public long getTotalBuy() {
		return totalBuy;
	}

	public void setTotalBuy(long totalBuy) {
		this.totalBuy = totalBuy;
	}

	public long getTotalImport() {
		return totalImport;
	}

	public void setTotalImport(long totalImport) {
		this.totalImport = totalImport;
	}

	public int getCountBuy() {
		return countBuy;
	}

	public void setCountBuy(int countBuy) {
		this.countBuy = countBuy;
	}

	public int getCountImport() {
		return countImport;
	}

	public void setCountImport(int countImport) {
		this.countImport = countImport;
	}

	public long getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(long totalProfit) {
		this.totalProfit = totalProfit;
	}

}
