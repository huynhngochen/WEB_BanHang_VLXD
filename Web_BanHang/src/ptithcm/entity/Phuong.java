package ptithcm.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Phuong")
public class Phuong {


	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy = "phuongs", fetch = FetchType.EAGER)
	private Collection<QuanPhuong> quanphuong;

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

	public Collection<QuanPhuong> getQuanphuong() {
		return quanphuong;
	}

	public void setQuanphuong(Collection<QuanPhuong> quanphuong) {
		this.quanphuong = quanphuong;
	}
	
	
}
