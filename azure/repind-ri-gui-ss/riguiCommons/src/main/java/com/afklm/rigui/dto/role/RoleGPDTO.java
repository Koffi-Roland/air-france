package com.afklm.rigui.dto.role;

/*PROTECTED REGION ID(_mtKYcNWxEeef5oRB6XPNlw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;


/*PROTECTED REGION END*/

/**
 * <p>Title : TelecomDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public  class RoleGPDTO implements Serializable {


	/**
	 * roleKey
	 */
	private Integer roleKey;

	/**
	 * matricule
	 */
	private String matricule;
	
	
	/**
	 * version
	 */
	private String version;
	
	
	/**
	 * state
	 */
	private String state;

	/**
	 * rightOwner
	 */
	private String rightOwner;


	/**
	 * organism
	 */
	private String organism;


	/**
	 * managingCompany
	 */
	private String managingCompany;


	/**
	 * countryCode
	 */
	private String countryCode;


	/**
	 * typology
	 */
	private String typology;


	/**
	 * entryCompanyDate
	 */
	private Date entryCompanyDate;


	/**
	 * expiryCardDate
	 */
	private Date expiryCardDate;
	
	/**
	 * type
	 */
	private String type;
	
	/**
	 * identifierOrder
	 */
	private String identifierOrder;
	
	/**
     * businessRole
     */
    private BusinessRoleDTO businessRole;


	/*PROTECTED REGION ID(_mtKYcNWxEeef5oRB6XPNlw u var) ENABLED START*/
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
	 * default constructor 
	 */
	public RoleGPDTO() {
		//empty constructor
	}
	

	/**
	 * @param roleKey
	 * @param matricule
	 * @param version
	 * @param state
	 * @param rightOwner
	 * @param organism
	 * @param managingCompany
	 * @param countryCode
	 * @param typology
	 * @param entryCompanyDate
	 * @param expiryCardDate
	 * @param type
	 * @param identifierOrder
	 */
	public RoleGPDTO(Integer roleKey, String matricule, String version, String state,
			String rightOwner, String organism, String managingCompany,
			String countryCode, String typology, Date entryCompanyDate,
			Date expiryCardDate, String type, String identifierOrder) {
		this.roleKey = roleKey;
		this.matricule = matricule;
		this.version = version;
		this.state = state;
		this.rightOwner = rightOwner;
		this.organism = organism;
		this.managingCompany = managingCompany;
		this.countryCode = countryCode;
		this.typology = typology;
		this.entryCompanyDate = entryCompanyDate;
		this.expiryCardDate = expiryCardDate;
		this.type = type;
		this.identifierOrder = identifierOrder;
	}



	/**
	 * @return the roleKey
	 */
	public Integer getRoleKey() {
		return roleKey;
	}



	/**
	 * @param roleKey the roleKey to set
	 */
	public void setRoleKey(Integer roleKey) {
		this.roleKey = roleKey;
	}




	/**
	 * @return the matricule
	 */
	public String getMatricule() {
		return matricule;
	}



	/**
	 * @param matricule the matricule to set
	 */
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}


	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}


	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}


	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}


	/**
	 * @return the rightOwner
	 */
	public String getRightOwner() {
		return rightOwner;
	}



	/**
	 * @param rightOwner the rightOwner to set
	 */
	public void setRightOwner(String rightOwner) {
		this.rightOwner = rightOwner;
	}



	/**
	 * @return the organism
	 */
	public String getOrganism() {
		return organism;
	}



	/**
	 * @param organism the organism to set
	 */
	public void setOrganism(String organism) {
		this.organism = organism;
	}



	/**
	 * @return the managingCompany
	 */
	public String getManagingCompany() {
		return managingCompany;
	}



	/**
	 * @param managingCompany the managingCompany to set
	 */
	public void setManagingCompany(String managingCompany) {
		this.managingCompany = managingCompany;
	}



	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}



	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}



	/**
	 * @return the typology
	 */
	public String getTypology() {
		return typology;
	}



	/**
	 * @param typology the typology to set
	 */
	public void setTypology(String typology) {
		this.typology = typology;
	}



	/**
	 * @return the entryCompanyDate
	 */
	public Date getEntryCompanyDate() {
		return entryCompanyDate;
	}



	/**
	 * @param entryCompanyDate the entryCompanyDate to set
	 */
	public void setEntryCompanyDate(Date entryCompanyDate) {
		this.entryCompanyDate = entryCompanyDate;
	}



	/**
	 * @return the expiryCardDate
	 */
	public Date getExpiryCardDate() {
		return expiryCardDate;
	}

	

	/**
	 * @param expiryCardDate the expiryCardDate to set
	 */
	public void setExpiryCardDate(Date expiryCardDate) {
		this.expiryCardDate = expiryCardDate;
	}


	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * @return the identifierOrder
	 */
	public String getIdentifierOrder() {
		return identifierOrder;
	}

	/**
	 * @param identifierOrder the identifierOrder to set
	 */
	public void setIdentifierOrder(String identifierOrder) {
		this.identifierOrder = identifierOrder;
	}
	
	/**
	 * @return the businessRole
	 */
	public BusinessRoleDTO getBusinessRole() {
		return businessRole;
	}


	/**
	 * @param businessRole the businessRole to set
	 */
	public void setBusinessRole(BusinessRoleDTO businessRole) {
		this.businessRole = businessRole;
	}


	/**
	 *
	 * @return object as string
	 */
	public String toString() {
		String result = null;
		/*PROTECTED REGION ID(toString_mtKYcNWxEeef5oRB6XPNlw) ENABLED START*/
		result = toStringImpl();
		/*PROTECTED REGION END*/
		return result;
	}

	/**
	 *
	 * @return object as string
	 */
	public String toStringImpl() {
		StringBuilder builder = new StringBuilder();
		builder.append("RoleGPDTO [roleKey=");
		builder.append(roleKey);
		builder.append(", matricule=");
		builder.append(matricule);
		builder.append(", version=");
		builder.append(version);
		builder.append(", state=");
		builder.append(state);
		builder.append(", rightOwner=");
		builder.append(rightOwner);
		builder.append(", organism=");
		builder.append(organism);
		builder.append(", managingCompany=");
		builder.append(managingCompany);
		builder.append(", countryCode=");
		builder.append(countryCode);
		builder.append(", typology=");
		builder.append(typology);
		builder.append(", entryCompanyDate=");
		builder.append(entryCompanyDate);
		builder.append(", expiryCardDate=");
		builder.append(expiryCardDate);
		builder.append(", type=");
		builder.append(type);
		builder.append(", identifierOrder=");
		builder.append(identifierOrder);
		builder.append("]");
		return builder.toString();
	}

	/**
	 *
	 * @return object as string
	 */


	/*PROTECTED REGION ID(_mtKYcNWxEeef5oRB6XPNlw u m) ENABLED START*/
	// add your custom methods here if necessary
	/*PROTECTED REGION END*/

}
