package com.afklm.rigui.dto.adresse;

/*PROTECTED REGION ID(_n1JlcOA1Ed-ucu7RY88Scg i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;


/*PROTECTED REGION END*/

/**
 * <p>Title : EmailDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class EmailDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 8805282918913815294L;


	/**
     * sain
     */
    private String sain;
        
        
    /**
     * sgin
     */
    private String sgin;
        
        
    /**
     * version
     */
    private Integer version;
        
        
    /**
     * codeMedium
     */
    private String codeMedium;
        
        
    /**
     * statutMedium
     */
    private String statutMedium;
        
        
    /**
     * email
     */
    private String email;
        
        
    /**
     * descriptifComplementaire
     */
    private String descriptifComplementaire;
        
        
    /**
     * autorisationMailing
     */
    private String autorisationMailing;
        
        
    /**
     * signatureModification
     */
    private String signatureModification;
        
        
    /**
     * siteModification
     */
    private String siteModification;
        
        
    /**
     * dateModification
     */
    private Date dateModification;
        
        
    /**
     * signatureCreation
     */
    private String signatureCreation;
        
        
    /**
     * siteCreation
     */
    private String siteCreation;
        
        
    /**
     * dateCreation
     */
    private Date dateCreation;
        
        
    /**
     * cleRole
     */
    private Integer cleRole;
        
        
    /**
     * cleTemp
     */
    private Integer cleTemp;
        
        

        

        

    /*PROTECTED REGION ID(_n1JlcOA1Ed-ucu7RY88Scg u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public EmailDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pSain sain
     * @param pSgin sgin
     * @param pVersion version
     * @param pCodeMedium codeMedium
     * @param pStatutMedium statutMedium
     * @param pEmail email
     * @param pDescriptifComplementaire descriptifComplementaire
     * @param pAutorisationMailing autorisationMailing
     * @param pSignatureModification signatureModification
     * @param pSiteModification siteModification
     * @param pDateModification dateModification
     * @param pSignatureCreation signatureCreation
     * @param pSiteCreation siteCreation
     * @param pDateCreation dateCreation
     * @param pCleRole cleRole
     * @param pCleTemp cleTemp
     */
    public EmailDTO(String pSain, String pSgin, Integer pVersion, String pCodeMedium, String pStatutMedium, String pEmail, String pDescriptifComplementaire, String pAutorisationMailing, String pSignatureModification, String pSiteModification, Date pDateModification, String pSignatureCreation, String pSiteCreation, Date pDateCreation, Integer pCleRole, Integer pCleTemp) {
        this.sain = pSain;
        this.sgin = pSgin;
        this.version = pVersion;
        this.codeMedium = pCodeMedium;
        this.statutMedium = pStatutMedium;
        this.email = pEmail;
        this.descriptifComplementaire = pDescriptifComplementaire;
        this.autorisationMailing = pAutorisationMailing;
        this.signatureModification = pSignatureModification;
        this.siteModification = pSiteModification;
        this.dateModification = pDateModification;
        this.signatureCreation = pSignatureCreation;
        this.siteCreation = pSiteCreation;
        this.dateCreation = pDateCreation;
        this.cleRole = pCleRole;
        this.cleTemp = pCleTemp;
    }

    /**
     *
     * @return autorisationMailing
     */
    public String getAutorisationMailing() {
        return this.autorisationMailing;
    }

    /**
     *
     * @param pAutorisationMailing autorisationMailing value
     */
    public void setAutorisationMailing(String pAutorisationMailing) {
        this.autorisationMailing = pAutorisationMailing;
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
     * @return cleTemp
     */
    public Integer getCleTemp() {
        return this.cleTemp;
    }

    /**
     *
     * @param pCleTemp cleTemp value
     */
    public void setCleTemp(Integer pCleTemp) {
        this.cleTemp = pCleTemp;
    }

    /**
     *
     * @return codeMedium
     */
    public String getCodeMedium() {
        return this.codeMedium;
    }

    /**
     *
     * @param pCodeMedium codeMedium value
     */
    public void setCodeMedium(String pCodeMedium) {
        this.codeMedium = pCodeMedium;
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
     * @return descriptifComplementaire
     */
    public String getDescriptifComplementaire() {
        return this.descriptifComplementaire;
    }

    /**
     *
     * @param pDescriptifComplementaire descriptifComplementaire value
     */
    public void setDescriptifComplementaire(String pDescriptifComplementaire) {
        this.descriptifComplementaire = pDescriptifComplementaire;
    }

    /**
     *
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     *
     * @param pEmail email value
     */
    public void setEmail(String pEmail) {
        this.email = pEmail;
    }







    /**
     *
     * @return sain
     */
    public String getSain() {
        return this.sain;
    }

    /**
     *
     * @param pSain sain value
     */
    public void setSain(String pSain) {
        this.sain = pSain;
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
     * @return statutMedium
     */
    public String getStatutMedium() {
        return this.statutMedium;
    }

    /**
     *
     * @param pStatutMedium statutMedium value
     */
    public void setStatutMedium(String pStatutMedium) {
        this.statutMedium = pStatutMedium;
    }

    /**
     *
     * @return version
     */
    public Integer getVersion() {
        return this.version;
    }

    /**
     *
     * @param pVersion version value
     */
    public void setVersion(Integer pVersion) {
        this.version = pVersion;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_n1JlcOA1Ed-ucu7RY88Scg) ENABLED START*/
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
        buffer.append("sain=").append(getSain());
        buffer.append(",");
        buffer.append("sgin=").append(getSgin());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append(",");
        buffer.append("codeMedium=").append(getCodeMedium());
        buffer.append(",");
        buffer.append("statutMedium=").append(getStatutMedium());
        buffer.append(",");
        buffer.append("email=").append(getEmail());
        buffer.append(",");
        buffer.append("descriptifComplementaire=").append(getDescriptifComplementaire());
        buffer.append(",");
        buffer.append("autorisationMailing=").append(getAutorisationMailing());
        buffer.append(",");
        buffer.append("signatureModification=").append(getSignatureModification());
        buffer.append(",");
        buffer.append("siteModification=").append(getSiteModification());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("signatureCreation=").append(getSignatureCreation());
        buffer.append(",");
        buffer.append("siteCreation=").append(getSiteCreation());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("cleRole=").append(getCleRole());
        buffer.append(",");
        buffer.append("cleTemp=").append(getCleTemp());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_n1JlcOA1Ed-ucu7RY88Scg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
