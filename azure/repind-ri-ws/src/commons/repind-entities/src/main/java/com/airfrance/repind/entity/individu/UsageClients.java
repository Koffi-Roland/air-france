package com.airfrance.repind.entity.individu;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "USAGE_CLIENTS")
public class UsageClients implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String[] TECHNICAL_USAGE = new String[] { "SCL", "AMF", "FBD", "FIH", "FIS", "FBC", "IHM",
			"ADM", "WEB", "SML", "B2C", "CCD", "RPD" };
	@Id
	@GenericGenerator(name="ISEQ_USAGE_CLIENTS", strategy = "com.airfrance.repind.util.StringSequenceGenerator",
	parameters = { 
			@Parameter(name = "sequence_name", value = "ISEQ_USAGE_CLIENTS") 
	})
	@GeneratedValue(generator = "ISEQ_USAGE_CLIENTS")
	@Column(name = "SRIN", length = 16, nullable = false, unique = true, updatable = false)
	private String srin;

	@Column(name = "SGIN", length = 12, nullable = false)
	private String sgin;

	@Column(name = "SCODE", length = 4)
	private String scode;

	@Column(name = "SCONST", length = 1)
	private String sconst;

	@Column(name = "DDATE_MODIFICATION")
	private Date date_modification;

	public UsageClients() {
	}

	public UsageClients(String pSrin, String pSgin, String pScode, String pSconst, Date pDate_modification) {
		this.srin = pSrin;
		this.sgin = pSgin;
		this.scode = pScode;
		this.sconst = pSconst;
		this.date_modification = pDate_modification;
	}

	public Date getDate_modification() {
		return this.date_modification;
	}

	public void setDate_modification(Date pDate_modification) {
		this.date_modification = pDate_modification;
	}

	public String getScode() {
		return this.scode;
	}

	public void setScode(String pScode) {
		this.scode = pScode;
	}

	public String getSconst() {
		return this.sconst;
	}

	public void setSconst(String pSconst) {
		this.sconst = pSconst;
	}

	public String getSgin() {
		return this.sgin;
	}

	public void setSgin(String pSgin) {
		this.sgin = pSgin;
	}

	public String getSrin() {
		return this.srin;
	}

	public void setSrin(String pSrin) {
		this.srin = pSrin;
	}

	@Override
	public String toString() {
		return toStringImpl();
	}

	public String toStringImpl() {
		return new ToStringBuilder(this).append("srin", getSrin()).append("sgin", getSgin()).append("scode", getScode())
				.append("sconst", getSconst()).append("date_modification", getDate_modification()).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final UsageClients other = (UsageClients) obj;

		// TODO: writes or generates equals method

		return super.equals(other);
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();

		// TODO: writes or generates hashcode method

		return result;
	}
}
