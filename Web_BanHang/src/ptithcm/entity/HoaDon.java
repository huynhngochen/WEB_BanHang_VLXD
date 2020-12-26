package ptithcm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="HoaDon")
public class HoaDon {

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="YYYY-MM-dd")
	@Column(name="date")
	private Date date;
	
	@OneToOne
	@JoinColumn(name="dathang_id")
	private DatHang dathang;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public DatHang getDathang() {
		return dathang;
	}

	public void setDathang(DatHang dathang) {
		this.dathang = dathang;
	}

}
