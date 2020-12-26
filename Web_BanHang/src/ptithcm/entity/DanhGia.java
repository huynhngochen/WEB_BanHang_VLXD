package ptithcm.entity;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="DanhGia")
public class DanhGia {

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="mark")
	private int mark;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="YYYY-MM-dd HH:mm:ss")
	@Column(name="date")
//	private Timestamp date;
	private java.util.Date utilTimestamp;
	
	@ManyToOne
	@JoinColumn(name = "hanghoa_id")
	private HangHoa hanghoas;
	
	@ManyToOne
	@JoinColumn(name="khachhang_id")
	private UserID userid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

//	public Timestamp getDate() {
//		return date;
//	}
//
//	public void setDate(Timestamp date) {
//		this.date = date;
//	}

	public java.util.Date getUtilTimestamp() {
		return utilTimestamp;
	}

	public void setUtilTimestamp(java.util.Date utilTimestamp) {
		this.utilTimestamp = utilTimestamp;
	}

	public HangHoa getHanghoas() {
		return hanghoas;
	}

	public void setHanghoas(HangHoa hanghoas) {
		this.hanghoas = hanghoas;
	}

	public UserID getUserid() {
		return userid;
	}

	public void setUserid(UserID userid) {
		this.userid = userid;
	}
	
	
}
