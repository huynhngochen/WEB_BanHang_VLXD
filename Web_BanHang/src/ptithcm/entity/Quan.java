package ptithcm.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Quan")
public class Quan {

	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="id_thanhpho")
	private ThanhPho city;
	
	@OneToMany(mappedBy = "quans", fetch = FetchType.EAGER)
	private Collection<QuanPhuong> quanphuong;
	
	@OneToMany(mappedBy = "districts", fetch = FetchType.EAGER)
	private Collection<KVGiaoHang> kvshipper;

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

	public ThanhPho getCity() {
		return city;
	}

	public void setCity(ThanhPho city) {
		this.city = city;
	}

	public Collection<QuanPhuong> getQuanphuong() {
		return quanphuong;
	}

	public void setQuanphuong(Collection<QuanPhuong> quanphuong) {
		this.quanphuong = quanphuong;
	}

	public Collection<KVGiaoHang> getKvshipper() {
		return kvshipper;
	}

	public void setKvshipper(Collection<KVGiaoHang> kvshipper) {
		this.kvshipper = kvshipper;
	}
	
}
