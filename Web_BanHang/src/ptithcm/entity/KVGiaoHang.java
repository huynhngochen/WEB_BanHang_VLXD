package ptithcm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="KVGiaoHang")
public class KVGiaoHang {
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="id_quan")
	private Quan districts;
	
	@ManyToOne
	@JoinColumn(name = "username")
	private UserID userIDs;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Quan getDistricts() {
		return districts;
	}

	public void setDistricts(Quan districts) {
		this.districts = districts;
	}

	public UserID getUserIDs() {
		return userIDs;
	}

	public void setUserIDs(UserID userIDs) {
		this.userIDs = userIDs;
	}
	
	
}
