package ptithcm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ThongTinUser")
public class ThongTinUser {
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@Column(name="fullname")
	private String fullname;
	
	@Column(name="email")
	private String email;
	
	@Column(name="address")
	private String address;
	
	@Column(name="sdt")
	private String sdt;
	
	@ManyToOne
	@JoinColumn(name="id_quanphuong")
	private QuanPhuong quanphuong;
	 
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public QuanPhuong getQuanphuong() {
		return quanphuong;
	}

	public void setQuanphuong(QuanPhuong quanphuong) {
		this.quanphuong = quanphuong;
	}
}
