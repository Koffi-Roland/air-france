package com.afklm.rigui.entity.individu;

/*PROTECTED REGION ID(_SeDh4FfEEea9lIVE0j16qg i) ENABLED START*/

// add not generated imports here

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>
 * Title : Alert.java
 * </p>
 * BO Alert
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
/* PROTECTED REGION ID(@Entity _SeDh4FfEEea9lIVE0j16qg) ENABLED START */
@Entity

@Table(name = "ALERT")
public class Alert implements Serializable {
	/*PROTECTED REGION END*/

	/*PROTECTED REGION ID(serialUID _SeDh4FfEEea9lIVE0j16qg) ENABLED START*/
	/**
	 * Determines if a de-serialized file is compatible with this class.
	 *
	 * Maintainers must change this value if and only if the new version
	 * of this class is not compatible with old versions. See Sun docs
	 * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization
	 * /serialization/spec/class.html#4100> details. </a>
	 *
	 * Not necessary to include in first version of the class, but
	 * included here as a reminder of its importance.
	 */
	private static final long serialVersionUID = 1L;
	/*PROTECTED REGION END*/


	/**
	 * alertId
	 */
	@Id
	@Column(name = "ALERT_ID", length = 12, nullable = false, unique = true)
	@SequenceGenerator(name = "SEQ_ALERT", sequenceName = "SEQ_ALERT",
			allocationSize = 1)
	@GeneratedValue(generator = "SEQ_ALERT")
	private Integer alertId;


	/**
	 * type
	 */
	@Column(name="TYPE", length=2)
	private String type;


	/**
	 * creationDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATION_DATE")
	private Date creationDate;


	/**
	 * creationSignature
	 */
	@Column(name="CREATION_SIGNATURE")
	private String creationSignature;


	/**
	 * creationSite
	 */
	@Column(name="CREATION_SITE")
	private String creationSite;


	/**
	 * modificationDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MODIFICATION_DATE")
	private Date modificationDate;


	/**
	 * modificationSignature
	 */
	@Column(name="MODIFICATION_SIGNATURE")
	private String modificationSignature;


	/**
	 * modificationSite
	 */
	@Column(name="MODIFICATION_SITE")
	private String modificationSite;


	/**
	 * sgin
	 */
	@Column(name="SGIN", length=12)
	private String sgin;


	/**
	 * optIn
	 */
	private String optIn;


	/**
	 * alertdata
	 */
	// 1 <-> *
	@OneToMany(mappedBy="alert", cascade=CascadeType.ALL)
//    @OneToMany(mappedBy="alert")
	private Set<AlertData> alertdata;

	/* PROTECTED REGION ID(_SeDh4FfEEea9lIVE0j16qg u var) ENABLED START */
	// add your custom variables here if necessary
	/* PROTECTED REGION END */

	/**
	 * default constructor
	 */
	public Alert() {
		// empty constructor
	}

	/**
	 * full constructor
	 *
	 * @param pType                  type
	 * @param pAlertId               alertId
	 * @param pCreationDate          creationDate
	 * @param pCreationSignature     creationSignature
	 * @param pCreationSite          creationSite
	 * @param pModificationDate      modificationDate
	 * @param pModificationSignature modificationSignature
	 * @param pModificationSite      modificationSite
	 * @param pSgin                  sgin
	 * @param pOptIn                 optIn
	 */
	public Alert(String pType, Integer pAlertId, Date pCreationDate, String pCreationSignature, String pCreationSite,
				 Date pModificationDate, String pModificationSignature, String pModificationSite, String pSgin,
				 String pOptIn) {
		this.type = pType;
		this.alertId = pAlertId;
		this.creationDate = pCreationDate;
		this.creationSignature = pCreationSignature;
		this.creationSite = pCreationSite;
		this.modificationDate = pModificationDate;
		this.modificationSignature = pModificationSignature;
		this.modificationSite = pModificationSite;
		this.sgin = pSgin;
		this.optIn = pOptIn;
	}

	/**
	 *
	 * @return alertId
	 */
	public Integer getAlertId() {
		return this.alertId;
	}

	/**
	 *
	 * @param pAlertId alertId value
	 */
	public void setAlertId(Integer pAlertId) {
		this.alertId = pAlertId;
	}

	/**
	 *
	 * @return alertdata
	 */
	public Set<AlertData> getAlertdata() {
		return this.alertdata;
	}

	/**
	 *
	 * @param pAlertdata alertdata value
	 */
	public void setAlertdata(Set<AlertData> pAlertdata) {
		this.alertdata = pAlertdata;
	}

	/**
	 *
	 * @return creationDate
	 */
	public Date getCreationDate() {
		return this.creationDate;
	}

	/**
	 *
	 * @param pCreationDate creationDate value
	 */
	public void setCreationDate(Date pCreationDate) {
		this.creationDate = pCreationDate;
	}

	/**
	 *
	 * @return creationSignature
	 */
	public String getCreationSignature() {
		return this.creationSignature;
	}

	/**
	 *
	 * @param pCreationSignature creationSignature value
	 */
	public void setCreationSignature(String pCreationSignature) {
		this.creationSignature = pCreationSignature;
	}

	/**
	 *
	 * @return creationSite
	 */
	public String getCreationSite() {
		return this.creationSite;
	}

	/**
	 *
	 * @param pCreationSite creationSite value
	 */
	public void setCreationSite(String pCreationSite) {
		this.creationSite = pCreationSite;
	}

	/**
	 *
	 * @return modificationDate
	 */
	public Date getModificationDate() {
		return this.modificationDate;
	}

	/**
	 *
	 * @param pModificationDate modificationDate value
	 */
	public void setModificationDate(Date pModificationDate) {
		this.modificationDate = pModificationDate;
	}

	/**
	 *
	 * @return modificationSignature
	 */
	public String getModificationSignature() {
		return this.modificationSignature;
	}

	/**
	 *
	 * @param pModificationSignature modificationSignature value
	 */
	public void setModificationSignature(String pModificationSignature) {
		this.modificationSignature = pModificationSignature;
	}

	/**
	 *
	 * @return modificationSite
	 */
	public String getModificationSite() {
		return this.modificationSite;
	}

	/**
	 *
	 * @param pModificationSite modificationSite value
	 */
	public void setModificationSite(String pModificationSite) {
		this.modificationSite = pModificationSite;
	}

	/**
	 *
	 * @return optIn
	 */
	public String getOptIn() {
		return this.optIn;
	}

	/**
	 *
	 * @param pOptIn optIn value
	 */
	public void setOptIn(String pOptIn) {
		this.optIn = pOptIn;
	}

	/**
	 *
	 * @return sgin
	 */
	public String getSgin() {
		return this.sgin;
	}

	/**
	 *
	 * @param pSgin sgin value
	 */
	public void setSgin(String pSgin) {
		this.sgin = pSgin;
	}

	/**
	 *
	 * @return type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 *
	 * @param pType type value
	 */
	public void setType(String pType) {
		this.type = pType;
	}

	/**
	 *
	 * @return object as string
	 */
	public String toString() {
		String result = null;
		/* PROTECTED REGION ID(toString_SeDh4FfEEea9lIVE0j16qg) ENABLED START */
		result = toStringImpl();
		/* PROTECTED REGION END */
		return result;
	}

	/**
	 *
	 * @return object as string
	 */
	public String toStringImpl() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getClass().getName());
		buffer.append("@").append(Integer.toHexString(System.identityHashCode(this)));
		buffer.append("[");
		buffer.append("type=").append(getType());
		buffer.append(",");
		buffer.append("alertId=").append(getAlertId());
		buffer.append(",");
		buffer.append("creationDate=").append(getCreationDate());
		buffer.append(",");
		buffer.append("creationSignature=").append(getCreationSignature());
		buffer.append(",");
		buffer.append("creationSite=").append(getCreationSite());
		buffer.append(",");
		buffer.append("modificationDate=").append(getModificationDate());
		buffer.append(",");
		buffer.append("modificationSignature=").append(getModificationSignature());
		buffer.append(",");
		buffer.append("modificationSite=").append(getModificationSite());
		buffer.append(",");
		buffer.append("sgin=").append(getSgin());
		buffer.append(",");
		buffer.append("optIn=").append(getOptIn());
		buffer.append("]");
		return buffer.toString();
	}

	/* PROTECTED REGION ID(equals hash _SeDh4FfEEea9lIVE0j16qg) ENABLED START */

	/**
	 * {@inheritDoc}
	 *
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO Customize here if necessary
		return hashCodeImpl();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO Customize here if necessary
		return equalsImpl(obj);
	}

	/* PROTECTED REGION END */

	/**
	 * Generated implementation method for hashCode You can stop calling it in the
	 * hashCode() generated in protected region if necessary
	 *
	 * @return hashcode
	 */
	private int hashCodeImpl() {
		return super.hashCode();
	}

	/**
	 * Generated implementation method for equals You can stop calling it in the
	 * equals() generated in protected region if necessary
	 *
	 * @return if param equals the current object
	 * @param obj Object to compare with current
	 */
	private boolean equalsImpl(Object obj) {
		return super.equals(obj);
	}

	/* PROTECTED REGION ID(_SeDh4FfEEea9lIVE0j16qg u m) ENABLED START */
	// add your custom methods here if necessary
	/* PROTECTED REGION END */

}
