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
@Table(name="QuanPhuong")
public class QuanPhuong {

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;


	@ManyToOne
	@JoinColumn(name="id_quan")
	private Quan quans;
	
	@ManyToOne
	@JoinColumn(name = "id_phuong")
	private Phuong phuongs;
	
	@OneToMany(mappedBy="quanphuong", fetch=FetchType.EAGER)
	private Collection<ThongTinUser> thongtinuser;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Quan getQuans() {
		return quans;
	}

	public void setQuans(Quan quans) {
		this.quans = quans;
	}

	public Phuong getPhuongs() {
		return phuongs;
	}

	public void setPhuongs(Phuong phuongs) {
		this.phuongs = phuongs;
	}

	public Collection<ThongTinUser> getThongtinuser() {
		return thongtinuser;
	}

	public void setThongtinuser(Collection<ThongTinUser> thongtinuser) {
		this.thongtinuser = thongtinuser;
	}
	
}
