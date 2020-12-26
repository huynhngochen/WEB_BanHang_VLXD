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
@Table(name="LoaiHang")
public class LoaiHang {
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="images")
	private String images;
	
	@OneToMany(mappedBy="loaihang", fetch=FetchType.EAGER)
	private Collection<HangHoa> hanghoa;


	
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

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}


	public Collection<HangHoa> getHanghoa() {
		return hanghoa;
	}

	public void setHanghoa(Collection<HangHoa> hanghoa) {
		this.hanghoa = hanghoa;
	}
	
	
}
