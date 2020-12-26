package ptithcm.entity;


import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="HangHoa")
public class HangHoa {
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
//	@Column(name="price")
//	private float price;
	
	@Column(name="price")
	private long price;
	
	@Column(name="discount")
	private int discount;
	
	@Column(name="images_link")
	private String images_link;
	
	@Column(name="count_buy")
	private int count_buy;
	
	@Column(name="in_stock")
	private int in_stock;
	
	@Column(name="description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="idloai")
	private LoaiHang loaihang;
	
	@Column(name="product_hot")
	private boolean product_hot;
	
	@OneToMany(mappedBy = "hanghoas", fetch = FetchType.EAGER)
	private Collection<ChiTietDatHang> detail_order;
	
	@OneToMany(mappedBy = "hanghoas", fetch = FetchType.EAGER)
	private Collection<ChiTietPhieuNhap> detail_import;

	@OneToMany(mappedBy = "hanghoas", fetch = FetchType.EAGER)
	private Collection<DanhGia> danhgia;
	
	public HangHoa(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public float getPrice() {
//		return price;
//	}
//
//	public void setPrice(float price) {
//		this.price = price;
//	}
	

	public int getDiscount() {
		return discount;
	}


	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public String getImages_link() {
		return images_link;
	}

	public void setImages_link(String images_link) {
		this.images_link = images_link;
	}

	public int getCount_buy() {
		return count_buy;
	}

	public void setCount_buy(int count_buy) {
		this.count_buy = count_buy;
	}

	public int getIn_stock() {
		return in_stock;
	}

	public void setIn_stock(int in_stock) {
		this.in_stock = in_stock;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LoaiHang getLoaihang() {
		return loaihang;
	}

	public void setLoaihang(LoaiHang loaihang) {
		this.loaihang = loaihang;
	}
	
	public Collection<ChiTietDatHang> getDetail_order() {
		return detail_order;
	}

	public void setDetail_order(Collection<ChiTietDatHang> detail_order) {
		this.detail_order = detail_order;
	}

	public boolean isProduct_hot() {
		return product_hot;
	}

	public void setProduct_hot(boolean product_hot) {
		this.product_hot = product_hot;
	}

	public Collection<ChiTietPhieuNhap> getDetail_import() {
		return detail_import;
	}

	public void setDetail_import(Collection<ChiTietPhieuNhap> detail_import) {
		this.detail_import = detail_import;
	}

	public Collection<DanhGia> getDanhgia() {
		return danhgia;
	}

	public void setDanhgia(Collection<DanhGia> danhgia) {
		this.danhgia = danhgia;
	}
	
}
