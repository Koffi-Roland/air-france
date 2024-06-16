package com.airfrance.repind.dto.role;

/*PROTECTED REGION ID(_0IHkgDqREeS2wtWjh0gEaw i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : RoleRcsDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class RoleRcsDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 1362353460279718555L;


	/**
     * cle
     */
    private String cle;
        
        
    /**
     * type
     */
    private String type;
        
        
    /**
     * sousType
     */
    private String sousType;
        
        
    /**
     * familleTraitement
     */
    private String familleTraitement;
        
        
    /**
     * cause
     */
    private String cause;
        
        
    /**
     * numeroAffaire
     */
    private String numeroAffaire;
        
        
    /**
     * dateOuverture
     */
    private Date dateOuverture;
        
        
    /**
     * dateFermeture
     */
    private Date dateFermeture;
        
        
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
     * businessRole
     */
    private BusinessRoleDTO businessRole;
        

    /*PROTECTED REGION ID(_0IHkgDqREeS2wtWjh0gEaw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public RoleRcsDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCle cle
     * @param pType type
     * @param pSousType sousType
     * @param pFamilleTraitement familleTraitement
     * @param pCause cause
     * @param pNumeroAffaire numeroAffaire
     * @param pDateOuverture dateOuverture
     * @param pDateFermeture dateFermeture
     * @param pDateCreation dateCreation
     * @param pSiteCreation siteCreation
     * @param pSignatureCreation signatureCreation
     * @param pDateModification dateModification
     * @param pSiteModification siteModification
     * @param pSignatureModification signatureModification
     */
    public RoleRcsDTO(String pCle, String pType, String pSousType, String pFamilleTraitement, String pCause, String pNumeroAffaire, Date pDateOuverture, Date pDateFermeture, Date pDateCreation, String pSiteCreation, String pSignatureCreation, Date pDateModification, String pSiteModification, String pSignatureModification) {
        this.cle = pCle;
        this.type = pType;
        this.sousType = pSousType;
        this.familleTraitement = pFamilleTraitement;
        this.cause = pCause;
        this.numeroAffaire = pNumeroAffaire;
        this.dateOuverture = pDateOuverture;
        this.dateFermeture = pDateFermeture;
        this.dateCreation = pDateCreation;
        this.siteCreation = pSiteCreation;
        this.signatureCreation = pSignatureCreation;
        this.dateModification = pDateModification;
        this.siteModification = pSiteModification;
        this.signatureModification = pSignatureModification;
    }

    /**
     *
     * @return businessRole
     */
    public BusinessRoleDTO getBusinessRole() {
        return this.businessRole;
    }

    /**
     *
     * @param pBusinessRole businessRole value
     */
    public void setBusinessRole(BusinessRoleDTO pBusinessRole) {
        this.businessRole = pBusinessRole;
    }

    /**
     *
     * @return cause
     */
    public String getCause() {
        return this.cause;
    }

    /**
     *
     * @param pCause cause value
     */
    public void setCause(String pCause) {
        this.cause = pCause;
    }

    /**
     *
     * @return cle
     */
    public String getCle() {
        return this.cle;
    }

    /**
     *
     * @param pCle cle value
     */
    public void setCle(String pCle) {
        this.cle = pCle;
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
     * @return dateFermeture
     */
    public Date getDateFermeture() {
        return this.dateFermeture;
    }

    /**
     *
     * @param pDateFermeture dateFermeture value
     */
    public void setDateFermeture(Date pDateFermeture) {
        this.dateFermeture = pDateFermeture;
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
     * @return dateOuverture
     */
    public Date getDateOuverture() {
        return this.dateOuverture;
    }

    /**
     *
     * @param pDateOuverture dateOuverture value
     */
    public void setDateOuverture(Date pDateOuverture) {
        this.dateOuverture = pDateOuverture;
    }

    /**
     *
     * @return familleTraitement
     */
    public String getFamilleTraitement() {
        return this.familleTraitement;
    }

    /**
     *
     * @param pFamilleTraitement familleTraitement value
     */
    public void setFamilleTraitement(String pFamilleTraitement) {
        this.familleTraitement = pFamilleTraitement;
    }

    /**
     *
     * @return numeroAffaire
     */
    public String getNumeroAffaire() {
        return this.numeroAffaire;
    }

    /**
     *
     * @param pNumeroAffaire numeroAffaire value
     */
    public void setNumeroAffaire(String pNumeroAffaire) {
        this.numeroAffaire = pNumeroAffaire;
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
     * @return sousType
     */
    public String getSousType() {
        return this.sousType;
    }

    /**
     *
     * @param pSousType sousType value
     */
    public void setSousType(String pSousType) {
        this.sousType = pSousType;
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
        /*PROTECTED REGION ID(toString_0IHkgDqREeS2wtWjh0gEaw) ENABLED START*/
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
        buffer.append("cle=").append(getCle());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("sousType=").append(getSousType());
        buffer.append(",");
        buffer.append("familleTraitement=").append(getFamilleTraitement());
        buffer.append(",");
        buffer.append("cause=").append(getCause());
        buffer.append(",");
        buffer.append("numeroAffaire=").append(getNumeroAffaire());
        buffer.append(",");
        buffer.append("dateOuverture=").append(getDateOuverture());
        buffer.append(",");
        buffer.append("dateFermeture=").append(getDateFermeture());
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

    /*PROTECTED REGION ID(_0IHkgDqREeS2wtWjh0gEaw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
