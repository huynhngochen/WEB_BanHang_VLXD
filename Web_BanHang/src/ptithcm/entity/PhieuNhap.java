package ptithcm.entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="PhieuNhap")
public class PhieuNhap {

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="amount")
	private long amount;
	
	@Column(name="status_import")
	private int status_import;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="YYYY-MM-dd")
	@Column(name="date")
	private Date date;
	
	
	@ManyToOne
	@JoinColumn(name="id_nhanvien")
	private UserID userid;
	
	@ManyToOne
	@JoinColumn(name="id_nhacc")
	private NhaCungCap nhacungcap;
	
	@OneToMany(mappedBy = "phieunhaps", fetch = FetchType.EAGER)
	private Collection<ChiTietPhieuNhap> detail_import;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public UserID getUserid() {
		return userid;
	}

	public void setUserid(UserID userid) {
		this.userid = userid;
	}

	public NhaCungCap getNhacungcap() {
		return nhacungcap;
	}

	public void setNhacungcap(NhaCungCap nhacungcap) {
		this.nhacungcap = nhacungcap;
	}

	public Collection<ChiTietPhieuNhap> getDetail_import() {
		return detail_import;
	}

	public void setDetail_import(Collection<ChiTietPhieuNhap> detail_import) {
		this.detail_import = detail_import;
	}

	public int getStatus_import() {
		return status_import;
	}

	public void setStatus_import(int status_import) {
		this.status_import = status_import;
	}
	
}
