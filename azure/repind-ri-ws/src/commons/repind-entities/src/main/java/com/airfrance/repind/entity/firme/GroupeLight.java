package com.airfrance.repind.entity.firme;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.SecondaryTable;
import java.io.Serializable;

/**
 * <p>
 * Title : Groupe.java
 * </p>
 * BO Groupe
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Entity

@DiscriminatorValue("G")
@SecondaryTable(name = "GROUPE")
public class GroupeLight extends PersonneMoraleLight implements Serializable {

	/**
	 * Determines if a de-serialized file is compatible with this class.
	 *
	 * Maintainers must change this value if and only if the new version of this
	 * class is not compatible with old versions. See Sun docs for <a
	 * href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization
	 * /serialization/spec/class.html#4100> details. </a>
	 *
	 * Not necessary to include in first version of the class, but included here
	 * as a reminder of its importance.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * scode
	 */
	@Column(table = "GROUPE", name = "SCODE", length = 5)
	private String code;

	/**
	 * default constructor
	 */
	public GroupeLight() {
	}

	/**
	 * full constructor
	 * 
	 * @param pCode
	 *            code
	 */
	public GroupeLight(String pCode) {
		this.code = pCode;
	}

	/**
	 *
	 * @return code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 *
	 * @param pCode
	 *            code value
	 */
	public void setCode(String pCode) {
		this.code = pCode;
	}

	/**
	 *
	 * @return object as string
	 */
	public String toString() {
		String result = null;
		result = toStringImpl();
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
		buffer.append("code=").append(getCode());
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see Object#equals(Object)
	 */
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
		final GroupeLight other = (GroupeLight) obj;

		return super.equals(other);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = super.hashCode();

		return result;
	}

}
