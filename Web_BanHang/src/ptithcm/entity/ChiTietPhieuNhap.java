package ptithcm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ChiTietPhieuNhap")
public class ChiTietPhieuNhap {

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="qty")
	private int qty;
	
	@Column(name="price")
	private long price;
	
	@ManyToOne
	@JoinColumn(name="phieunhap_id")
	private PhieuNhap phieunhaps;
	
	@ManyToOne
	@JoinColumn(name="hanghoa_id")
	private HangHoa hanghoas;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public PhieuNhap getPhieunhaps() {
		return phieunhaps;
	}

	public void setPhieunhaps(PhieuNhap phieunhaps) {
		this.phieunhaps = phieunhaps;
	}

	public HangHoa getHanghoas() {
		return hanghoas;
	}

	public void setHanghoas(HangHoa hanghoas) {
		this.hanghoas = hanghoas;
	}

	
	
}
