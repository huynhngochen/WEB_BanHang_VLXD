package ptithcm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ChiTietDatHang")
public class ChiTietDatHang {
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="qty")
	private int qty;
	
	@Column(name="price")
	private long price;


	@ManyToOne
	@JoinColumn(name="dathang_id")
	private DatHang dathangs;
	
	@ManyToOne
	@JoinColumn(name = "hanghoa_id")
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
	public DatHang getDathangs() {
		return dathangs;
	}

	public void setDathangs(DatHang dathangs) {
		this.dathangs = dathangs;
	}

	public HangHoa getHanghoas() {
		return hanghoas;
	}

	public void setHanghoas(HangHoa hanghoas) {
		this.hanghoas = hanghoas;
	}
	
	
}
