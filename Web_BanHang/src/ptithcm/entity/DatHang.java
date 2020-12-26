package ptithcm.entity;


import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="DatHang")
public class DatHang {
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="amount")
	private long amount;
	
	@Column(name="payment")
	private String payment;
	//1: thanh toán khi nhận hàng
	// 2: chuyển khoản
	
	@Column(name="message")
	private String message;
	
	@Column(name="message_payment")
	private String message_payment;
	
	@Column(name="status_delivery")
	private int status_delivery;
	//1: đặt hàng thành công
	//2: Đang Xử Lý
	//3: Đang giao hàng
	//4: Giao thành công
	//5. Đã hủy đơn

	@Column(name="status_payment")
	private boolean status_payment; 
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="YYYY-MM-dd")
	@Column(name="created")
	private Date created;
	
	@ManyToOne
	@JoinColumn(name="id_khachhang")
	private UserID userid;
	
	@ManyToOne
	@JoinColumn(name="id_nhanvien")
	private UserID userid1;
	
	@ManyToOne
	@JoinColumn(name="id_shipper")
	private UserID userid2;
	
	@ManyToOne
	@JoinColumn(name="id_khuyenmai")
	private KhuyenMai khuyenmai;
	
	
	@OneToMany(mappedBy = "dathangs", fetch = FetchType.EAGER)
	private Collection<ChiTietDatHang> detail_order;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus_delivery() {
		return status_delivery;
	}

	public void setStatus_delivery(int status_delivery) {
		this.status_delivery = status_delivery;
	}

	public boolean isStatus_payment() {
		return status_payment;
	}
	
	public void setStatus_payment(boolean status_payment) {
		this.status_payment = status_payment;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public UserID getUserid() {
		return userid;
	}

	public void setUserid(UserID userid) {
		this.userid = userid;
	}

	public Collection<ChiTietDatHang> getDetail_order() {
		return detail_order;
	}

	public void setDetail_order(Collection<ChiTietDatHang> detail_order) {
		this.detail_order = detail_order;
	}

	public UserID getUserid1() {
		return userid1;
	}

	public void setUserid1(UserID userid1) {
		this.userid1 = userid1;
	}

	public KhuyenMai getKhuyenmai() {
		return khuyenmai;
	}

	public void setKhuyenmai(KhuyenMai khuyenmai) {
		this.khuyenmai = khuyenmai;
	}

	public UserID getUserid2() {
		return userid2;
	}

	public void setUserid2(UserID userid2) {
		this.userid2 = userid2;
	}

	public String getMessage_payment() {
		return message_payment;
	}

	public void setMessage_payment(String message_payment) {
		this.message_payment = message_payment;
	}

	

}
