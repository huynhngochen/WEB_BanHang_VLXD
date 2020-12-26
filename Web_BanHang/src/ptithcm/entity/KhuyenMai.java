package ptithcm.entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="KhuyenMai")
public class KhuyenMai {

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	
	@Column(name="makm")
	private String makm;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="YYYY-MM-dd")
	@Column(name="date_start")
	private Date date_start;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="YYYY-MM-dd")
	@Column(name="date_finish")
	private Date date_finish;
	
	@Column(name="name")
	private String name;
	
	@Column(name="discount")
	private int discount;
	
	@OneToMany(mappedBy="khuyenmai", fetch=FetchType.EAGER)
	private Collection<DatHang> dathang;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMakm() {
		return makm;
	}

	public void setMakm(String makm) {
		this.makm = makm;
	}

	public Date getDate_start() {
		return date_start;
	}

	public void setDate_start(Date date_start) {
		this.date_start = date_start;
	}

	public Date getDate_finish() {
		return date_finish;
	}

	public void setDate_finish(Date date_finish) {
		this.date_finish = date_finish;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public Collection<DatHang> getDathang() {
		return dathang;
	}

	public void setDathang(Collection<DatHang> dathang) {
		this.dathang = dathang;
	}
	
	
}
