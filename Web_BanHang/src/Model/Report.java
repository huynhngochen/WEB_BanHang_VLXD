package Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
public class Report {
	@Column(name="price")
	private long price;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="YYYY-MM-dd")
	@Column(name="created")
	private Date created;
	public Report() {
		// TODO Auto-generated constructor stub
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}

}
