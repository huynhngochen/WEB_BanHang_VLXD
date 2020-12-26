package ptithcm.entity;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="NhaCungCap")
public class NhaCungCap {
	@Id	
	@Column(name="id")
	private String id;
	
	@Column(name="name")
	private String name;

	@Column(name="phone")
	private String phone;
	
	@Column(name="address")
	private String address;

	
	@OneToMany(mappedBy="nhacungcap", fetch=FetchType.EAGER)
	private Collection<PhieuNhap> phieunhap;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Collection<PhieuNhap> getPhieunhap() {
		return phieunhap;
	}


	public void setPhieunhap(Collection<PhieuNhap> phieunhap) {
		this.phieunhap = phieunhap;
	}
	
	
}
