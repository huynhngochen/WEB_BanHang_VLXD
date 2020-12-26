package Model;

import java.sql.Date;

public class ProductImport {
	private String time;
//	private Date time;
	private float value = 0;
	public ProductImport() {
		// TODO Auto-generated constructor stub
	}
	
//	public Date getTime() {
//		return time;
//	}
//	public void setTime(Date time) {
//		this.time = time;
//	}
	
	public float getValue() {
		return value;
	}
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setValue(float value) {
		this.value = value;
	}
	
}
