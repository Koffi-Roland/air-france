package com.airfrance.repind.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SONDE_NEWTEST", schema = "SIC2")
public class SondeNewtest implements java.io.Serializable {

	private static final long serialVersionUID = -8088075707962474982L;
	private String sgin;

	public SondeNewtest() {
	}

	public SondeNewtest(String sgin) {
		this.sgin = sgin;
	}

	@Id
	@Column(name = "SGIN", unique = true, nullable = false, length = 12)
	public String getSgin() {
		return this.sgin;
	}

	public void setSgin(String sgin) {
		this.sgin = sgin;
	}

}
