package com.afklm.rigui.entity.individu;

/*PROTECTED REGION ID(_DMrG4FfiEea5me8LTs-wgA i) ENABLED START*/

// add not generated imports here

import javax.persistence.*;
import java.io.Serializable;

/*PROTECTED REGION END*/

/**
 * <p>
 * Title : AlertData.java
 * </p>
 * BO AlertData
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
/* PROTECTED REGION ID(@Entity _DMrG4FfiEea5me8LTs-wgA) ENABLED START */
@Entity

@Table(name = "ALERT_DATA")
public class AlertData implements Serializable {
	/* PROTECTED REGION END */

	/* PROTECTED REGION ID(serialUID _DMrG4FfiEea5me8LTs-wgA) ENABLED START */
	/**
	 * Determines if a de-serialized file is compatible with this class.
	 *
	 * Maintainers must change this value if and only if the new version of this
	 * class is not compatible with old versions. See Sun docs for <a
	 * href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization
	 * /serialization/spec/class.html#4100> details. </a>
	 *
	 * Not necessary to include in first version of the class, but included here as
	 * a reminder of its importance.
	 */
	private static final long serialVersionUID = 1L;
	/* PROTECTED REGION END */

	/**
	 * alertDataId
	 */
	@Id
	@Column(name = "ALERT_DATA_ID", length = 12, nullable = false, unique = true)
	@SequenceGenerator(name = "SEQ_ALERT_DATA", sequenceName = "SEQ_ALERT_DATA",
			allocationSize = 1)
	@GeneratedValue(generator = "SEQ_ALERT_DATA")
	private Integer alertDataId;

	/**
	 * key
	 */
	@Column(name = "KEY")
	private String key;

	/**
	 * value
	 */
	@Column(name = "VALUE")
	private String value;

	/**
	 * alert
	 */
	// * <-> 1
	@ManyToOne()
	@JoinColumn(name = "ALERT_ID", nullable = true, foreignKey = @ForeignKey(name = "FK_ALERT_DATA_ALERT"))
	private Alert alert;

	/* PROTECTED REGION ID(_DMrG4FfiEea5me8LTs-wgA u var) ENABLED START */
	// add your custom variables here if necessary
	/* PROTECTED REGION END */

	/**
	 * default constructor
	 */
	public AlertData() {
		// empty constructor
	}

	/**
	 * full constructor
	 *
	 * @param pAlertDataId alertDataId
	 * @param pKey         key
	 * @param pValue       value
	 */
	public AlertData(Integer pAlertDataId, String pKey, String pValue) {
		this.alertDataId = pAlertDataId;
		this.key = pKey;
		this.value = pValue;
	}

	/**
	 *
	 * @return alert
	 */
	public Alert getAlert() {
		return this.alert;
	}

	/**
	 *
	 * @param pAlert alert value
	 */
	public void setAlert(Alert pAlert) {
		this.alert = pAlert;
	}

	/**
	 *
	 * @return alertDataId
	 */
	public Integer getAlertDataId() {
		return this.alertDataId;
	}

	/**
	 *
	 * @param pAlertDataId alertDataId value
	 */
	public void setAlertDataId(Integer pAlertDataId) {
		this.alertDataId = pAlertDataId;
	}

	/**
	 *
	 * @return key
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 *
	 * @param pKey key value
	 */
	public void setKey(String pKey) {
		this.key = pKey;
	}

	/**
	 *
	 * @return value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 *
	 * @param pValue value value
	 */
	public void setValue(String pValue) {
		this.value = pValue;
	}

	/**
	 *
	 * @return object as string
	 */
	public String toString() {
		String result = null;
		/* PROTECTED REGION ID(toString_DMrG4FfiEea5me8LTs-wgA) ENABLED START */
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
		buffer.append("alertDataId=").append(getAlertDataId());
		buffer.append(",");
		buffer.append("key=").append(getKey());
		buffer.append(",");
		buffer.append("value=").append(getValue());
		buffer.append("]");
		return buffer.toString();
	}

	/* PROTECTED REGION ID(equals hash _DMrG4FfiEea5me8LTs-wgA) ENABLED START */

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

	/* PROTECTED REGION ID(_DMrG4FfiEea5me8LTs-wgA u m) ENABLED START */
	// add your custom methods here if necessary
	/* PROTECTED REGION END */

}
