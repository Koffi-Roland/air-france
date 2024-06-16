package com.afklm.rigui.dto.role;

/*PROTECTED REGION ID(_8xkqQPcZEd-Kx8TJdz7fGw i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : BusinessRoleDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class BusinessRoleDTO implements Serializable {


        
    /**
     * cleRole
     */
    private Integer cleRole;
        
        
    /**
     * ginInd
     */
    private String ginInd;
        
        
    /**
     * ginPm
     */
    private String ginPm;
        
        
    /**
     * numeroContrat
     */
    private String numeroContrat;
        
        
    /**
     * type
     */
    private String type;
        
        
    /**
     * dateCreation
     */
    private Date dateCreation;
        
        
    /**
     * siteCreation
     */
    private String siteCreation;
        
        
    /**
     * signatureCreation
     */
    private String signatureCreation;
        
        
    /**
     * dateModification
     */
    private Date dateModification;
        
        
    /**
     * siteModification
     */
    private String siteModification;
        
        
    /**
     * signatureModification
     */
    private String signatureModification;
        

        
        
    /**
     * roleRcs
     */
    private RoleRcsDTO roleRcs;
        
        
    /**
     * roleTravelers
     */
    private RoleTravelersDTO roleTravelers;
        
        
    /**
     * roleUCCRDTO
     */
    private RoleUCCRDTO roleUCCRDTO;
    
    /**
     * roleGPDTO
     */
    private RoleGPDTO roleGPDTO;
        

    /*PROTECTED REGION ID(_8xkqQPcZEd-Kx8TJdz7fGw u var) ENABLED START*/
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
	    public BusinessRoleDTO() {
	    //empty constructor
	    }
	    
	    
	    public BusinessRoleDTO(Integer cleRole) {
	    	super();
	    	this.setCleRole(cleRole);
		}

	/**
     *
     * @return cleRole
     */
    public Integer getCleRole() {
        return this.cleRole;
    }

    /**
     *
     * @param pCleRole cleRole value
     */
    public void setCleRole(Integer pCleRole) {
        this.cleRole = pCleRole;
    }

    /**
     *
     * @return dateCreation
     */
    public Date getDateCreation() {
        return this.dateCreation;
    }

    /**
     *
     * @param pDateCreation dateCreation value
     */
    public void setDateCreation(Date pDateCreation) {
        this.dateCreation = pDateCreation;
    }

    /**
     *
     * @return dateModification
     */
    public Date getDateModification() {
        return this.dateModification;
    }

    /**
     *
     * @param pDateModification dateModification value
     */
    public void setDateModification(Date pDateModification) {
        this.dateModification = pDateModification;
    }

    /**
     *
     * @return ginInd
     */
    public String getGinInd() {
        return this.ginInd;
    }

    /**
     *
     * @param pGinInd ginInd value
     */
    public void setGinInd(String pGinInd) {
        this.ginInd = pGinInd;
    }

    /**
     *
     * @return ginPm
     */
    public String getGinPm() {
        return this.ginPm;
    }

    /**
     *
     * @param pGinPm ginPm value
     */
    public void setGinPm(String pGinPm) {
        this.ginPm = pGinPm;
    }

    /**
     *
     * @return numeroContrat
     */
    public String getNumeroContrat() {
        return this.numeroContrat;
    }

    /**
     *
     * @param pNumeroContrat numeroContrat value
     */
    public void setNumeroContrat(String pNumeroContrat) {
        this.numeroContrat = pNumeroContrat;
    }



    /**
     *
     * @return roleRcs
     */
    public RoleRcsDTO getRoleRcs() {
        return this.roleRcs;
    }

    /**
     *
     * @param pRoleRcs roleRcs value
     */
    public void setRoleRcs(RoleRcsDTO pRoleRcs) {
        this.roleRcs = pRoleRcs;
    }

    /**
     *
     * @return roleTravelers
     */
    public RoleTravelersDTO getRoleTravelers() {
        return this.roleTravelers;
    }

    /**
     *
     * @param pRoleTravelers roleTravelers value
     */
    public void setRoleTravelers(RoleTravelersDTO pRoleTravelers) {
        this.roleTravelers = pRoleTravelers;
    }

    /**
     *
     * @return roleUCCRDTO
     */
    public RoleUCCRDTO getRoleUCCRDTO() {
        return this.roleUCCRDTO;
    }

    /**
     *
     * @param pRoleUCCRDTO roleUCCRDTO value
     */
    public void setRoleUCCRDTO(RoleUCCRDTO pRoleUCCRDTO) {
        this.roleUCCRDTO = pRoleUCCRDTO;
    }

    /**
     * 
	 * @return the roleGPDTO
	 */
	public RoleGPDTO getRoleGPDTO() {
		return roleGPDTO;
	}

	/**
	 * @param roleGPDTO the roleGPDTO to set
	 */
	public void setRoleGPDTO(RoleGPDTO roleGPDTO) {
		this.roleGPDTO = roleGPDTO;
	}

	/**
     *
     * @return signatureCreation
     */
    public String getSignatureCreation() {
        return this.signatureCreation;
    }

    /**
     *
     * @param pSignatureCreation signatureCreation value
     */
    public void setSignatureCreation(String pSignatureCreation) {
        this.signatureCreation = pSignatureCreation;
    }

    /**
     *
     * @return signatureModification
     */
    public String getSignatureModification() {
        return this.signatureModification;
    }

    /**
     *
     * @param pSignatureModification signatureModification value
     */
    public void setSignatureModification(String pSignatureModification) {
        this.signatureModification = pSignatureModification;
    }

    /**
     *
     * @return siteCreation
     */
    public String getSiteCreation() {
        return this.siteCreation;
    }

    /**
     *
     * @param pSiteCreation siteCreation value
     */
    public void setSiteCreation(String pSiteCreation) {
        this.siteCreation = pSiteCreation;
    }

    /**
     *
     * @return siteModification
     */
    public String getSiteModification() {
        return this.siteModification;
    }

    /**
     *
     * @param pSiteModification siteModification value
     */
    public void setSiteModification(String pSiteModification) {
        this.siteModification = pSiteModification;
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
        /*PROTECTED REGION ID(toString_8xkqQPcZEd-Kx8TJdz7fGw) ENABLED START*/
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
        buffer.append("cleRole=").append(getCleRole());
        buffer.append(",");
        buffer.append("ginInd=").append(getGinInd());
        buffer.append(",");
        buffer.append("ginPm=").append(getGinPm());
        buffer.append(",");
        buffer.append("numeroContrat=").append(getNumeroContrat());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("siteCreation=").append(getSiteCreation());
        buffer.append(",");
        buffer.append("signatureCreation=").append(getSignatureCreation());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("siteModification=").append(getSiteModification());
        buffer.append(",");
        buffer.append("signatureModification=").append(getSignatureModification());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_8xkqQPcZEd-Kx8TJdz7fGw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
