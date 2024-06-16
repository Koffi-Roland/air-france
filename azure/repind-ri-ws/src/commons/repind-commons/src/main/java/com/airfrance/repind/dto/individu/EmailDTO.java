package com.airfrance.repind.dto.individu;

/*PROTECTED REGION ID(_jO2FT0M8EeCk2djT-5OeOA i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;

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
	private static final long serialVersionUID = 8278136708265121477L;


	/**
     * cleEmail
     */
    private String cleEmail;
        
        
    /**
     * version
     */
    private Integer version;
        
        
    /**
     * statutMedium
     */
    private String statutMedium;
        
        
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
        

    /*PROTECTED REGION ID(_jO2FT0M8EeCk2djT-5OeOA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public EmailDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCleEmail cleEmail
     * @param pVersion version
     * @param pStatutMedium statutMedium
     * @param pCodeMedium codeMedium
     * @param pEmail email
     * @param pAutoriseMailing autoriseMailing
     * @param pInfosCompl infosCompl
     */
    public EmailDTO(String pCleEmail, Integer pVersion, String pStatutMedium, String pCodeMedium, String pEmail, String pAutoriseMailing, String pInfosCompl) {
        this.cleEmail = pCleEmail;
        this.version = pVersion;
        this.statutMedium = pStatutMedium;
        this.codeMedium = pCodeMedium;
        this.email = pEmail;
        this.autoriseMailing = pAutoriseMailing;
        this.infosCompl = pInfosCompl;
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
        /*PROTECTED REGION ID(toString_jO2FT0M8EeCk2djT-5OeOA) ENABLED START*/
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
        buffer.append("statutMedium=").append(getStatutMedium());
        buffer.append(",");
        buffer.append("codeMedium=").append(getCodeMedium());
        buffer.append(",");
        buffer.append("email=").append(getEmail());
        buffer.append(",");
        buffer.append("autoriseMailing=").append(getAutoriseMailing());
        buffer.append(",");
        buffer.append("infosCompl=").append(getInfosCompl());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_jO2FT0M8EeCk2djT-5OeOA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
