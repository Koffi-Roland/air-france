package com.airfrance.repind.dto.individu;

/*PROTECTED REGION ID(_iUQDcFvPEeKNvKoz9-ZrFQ i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : WarningDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class WarningDTO  {

	/**
	 * warningCode
	 */
	private String warningCode;


	/**
	 * warningDetails
	 */
	private String warningDetails;


	/*PROTECTED REGION ID(_iUQDcFvPEeKNvKoz9-ZrFQ u var) ENABLED START*/
	// add your custom variables here if necessary
	/*PROTECTED REGION END*/



	/** 
	 * default constructor 
	 */
	public WarningDTO() {

	}


	/** 
	 * full constructor
	 * @param pWarningCode warningCode
	 * @param pWarningDetails warningDetails
	 */
	public WarningDTO(String pWarningCode, String pWarningDetails) {
		this.warningCode = pWarningCode;
		this.warningDetails = pWarningDetails;
	}

	/**
	 *
	 * @return warningCode
	 */
	public String getWarningCode() {
		return this.warningCode;
	}

	/**
	 *
	 * @param pWarningCode warningCode value
	 */
	public void setWarningCode(String pWarningCode) {
		this.warningCode = pWarningCode;
	}

	/**
	 *
	 * @return warningDetails
	 */
	public String getWarningDetails() {
		return this.warningDetails;
	}

	/**
	 *
	 * @param pWarningDetails warningDetails value
	 */
	public void setWarningDetails(String pWarningDetails) {
		this.warningDetails = pWarningDetails;
	}

	/**
	 *
	 * @return object as string
	 */
	public String toString() {
		String result = null;
		/*PROTECTED REGION ID(toString_iUQDcFvPEeKNvKoz9-ZrFQ) ENABLED START*/
		result = toStringImpl();
		/*PROTECTED REGION END*/
		return result;
	}

	/**
	 *
	 * @return object as string
	 */
	public String toStringImpl() {
		return new ToStringBuilder(this)
				.append("warningCode", getWarningCode())
				.append("warningDetails", getWarningDetails())
				.toString();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((warningDetails == null) ? 0 : warningDetails.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WarningDTO other = (WarningDTO) obj;
		if (warningDetails == null) {
			if (other.warningDetails != null)
				return false;
		} else if (!warningDetails.equals(other.warningDetails))
			return false;
		return true;
	}


	
	

	/*PROTECTED REGION ID(_iUQDcFvPEeKNvKoz9-ZrFQ u m) ENABLED START*/
	// add your custom methods here if necessary
	/*PROTECTED REGION END*/

}
