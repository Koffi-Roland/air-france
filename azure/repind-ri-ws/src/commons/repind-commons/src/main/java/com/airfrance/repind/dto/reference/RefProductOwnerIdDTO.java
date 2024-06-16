package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_qLl_8AKDEeeb1IzCQutBDQ i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : RefProductOwnerIdDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public  class RefProductOwnerIdDTO implements Serializable {

	/**
	 * RefProductId
	 */
	private RefProductDTO refProduct;
	
	/**
	 * RefOwnerId
	 */
	private RefOwnerDTO refOwner;

	/*PROTECTED REGION ID(_qLl_8AKDEeeb1IzCQutBDQ u var) ENABLED START*/
	// add your custom variables here if necessary

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
	 * Getter
	 * @return
	 */
	public RefProductDTO getRefProduct() {
		return refProduct;
	}

	/**
	 * setter
	 * @param refProductId
	 */
	public void setRefProduct(RefProductDTO refProductId) {
		this.refProduct = refProductId;
	}

	/**
	 * Getter
	 * @return
	 */
	public RefOwnerDTO getRefOwner() {
		return refOwner;
	}

	/**
	 * Setter
	 * @param refOwnerId
	 */
	public void setRefOwner(RefOwnerDTO refOwnerId) {
		this.refOwner = refOwnerId;
	}

	/**
	 *
	 * @return object as string
	 */
	public String toString() {
		String result = null;
		/*PROTECTED REGION ID(toString_qLl_8AKDEeeb1IzCQutBDQ) ENABLED START*/
		result = toStringImpl();
		/*PROTECTED REGION END*/
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
		buffer.append("]");
		return buffer.toString();
	}

	/*PROTECTED REGION ID(_qLl_8AKDEeeb1IzCQutBDQ u m) ENABLED START*/
	// add your custom methods here if necessary
	/*PROTECTED REGION END*/

}
