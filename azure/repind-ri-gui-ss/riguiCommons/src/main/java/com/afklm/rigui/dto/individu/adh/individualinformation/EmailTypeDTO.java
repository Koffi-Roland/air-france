package com.afklm.rigui.dto.individu.adh.individualinformation;

/*PROTECTED REGION ID(_b10ExyMUEeCWJOBY8f-ONQ i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : EmailTypeDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class EmailTypeDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 2668575096318195167L;


	/**
     * cleEmail
     */
    private String cleEmail;
        
        
    /**
     * version
     */
    private String version;
        
        
    /**
     * statusMedium
     */
    private String statusMedium;
        
        
    /**
     * codeMedium
     */
    private String codeMedium;
        
        
    /**
     * email
     */
    private String email;
        
        
    /**
     * autoriseMailing
     */
    private String autoriseMailing;
        
        
    /**
     * infosCompl
     */
    private String infosCompl;
        
        
    /**
     * dateCreation
     */
    private Date dateCreation;
        
        
    /**
     * dateModification
     */
    private Date dateModification;
        

    /*PROTECTED REGION ID(_b10ExyMUEeCWJOBY8f-ONQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public EmailTypeDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCleEmail cleEmail
     * @param pVersion version
     * @param pStatusMedium statusMedium
     * @param pCodeMedium codeMedium
     * @param pEmail email
     * @param pAutoriseMailing autoriseMailing
     * @param pInfosCompl infosCompl
     * @param pDateCreation dateCreation
     * @param pDateModification dateModification
     */
    public EmailTypeDTO(String pCleEmail, String pVersion, String pStatusMedium, String pCodeMedium, String pEmail, String pAutoriseMailing, String pInfosCompl, Date pDateCreation, Date pDateModification) {
        this.cleEmail = pCleEmail;
        this.version = pVersion;
        this.statusMedium = pStatusMedium;
        this.codeMedium = pCodeMedium;
        this.email = pEmail;
        this.autoriseMailing = pAutoriseMailing;
        this.infosCompl = pInfosCompl;
        this.dateCreation = pDateCreation;
        this.dateModification = pDateModification;
    }

    /**
     *
     * @return autoriseMailing
     */
    public String getAutoriseMailing() {
        return this.autoriseMailing;
    }

    /**
     *
     * @param pAutoriseMailing autoriseMailing value
     */
    public void setAutoriseMailing(String pAutoriseMailing) {
        this.autoriseMailing = pAutoriseMailing;
    }

    /**
     *
     * @return cleEmail
     */
    public String getCleEmail() {
        return this.cleEmail;
    }

    /**
     *
     * @param pCleEmail cleEmail value
     */
    public void setCleEmail(String pCleEmail) {
        this.cleEmail = pCleEmail;
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
     * @return infosCompl
     */
    public String getInfosCompl() {
        return this.infosCompl;
    }

    /**
     *
     * @param pInfosCompl infosCompl value
     */
    public void setInfosCompl(String pInfosCompl) {
        this.infosCompl = pInfosCompl;
    }

    /**
     *
     * @return statusMedium
     */
    public String getStatusMedium() {
        return this.statusMedium;
    }

    /**
     *
     * @param pStatusMedium statusMedium value
     */
    public void setStatusMedium(String pStatusMedium) {
        this.statusMedium = pStatusMedium;
    }

    /**
     *
     * @return version
     */
    public String getVersion() {
        return this.version;
    }

    /**
     *
     * @param pVersion version value
     */
    public void setVersion(String pVersion) {
        this.version = pVersion;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_b10ExyMUEeCWJOBY8f-ONQ) ENABLED START*/
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
        buffer.append("cleEmail=").append(getCleEmail());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append(",");
        buffer.append("statusMedium=").append(getStatusMedium());
        buffer.append(",");
        buffer.append("codeMedium=").append(getCodeMedium());
        buffer.append(",");
        buffer.append("email=").append(getEmail());
        buffer.append(",");
        buffer.append("autoriseMailing=").append(getAutoriseMailing());
        buffer.append(",");
        buffer.append("infosCompl=").append(getInfosCompl());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_b10ExyMUEeCWJOBY8f-ONQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
