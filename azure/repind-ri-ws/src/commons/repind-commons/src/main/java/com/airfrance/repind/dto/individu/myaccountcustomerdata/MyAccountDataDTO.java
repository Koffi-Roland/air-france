package com.airfrance.repind.dto.individu.myaccountcustomerdata;

/*PROTECTED REGION ID(_fMKqID8vEeCFh9Ea_LFCog i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : MyAccountDataDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class MyAccountDataDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -7428631118787140776L;


	/**
     * accountIdentifier
     */
    private String accountIdentifier;
        
        
    /**
     * dateModification
     */
    private Date dateModification;
        
        
    /**
     * emailIdentifier
     */
    private String emailIdentifier;
        
        
    /**
     * customerType
     */
    private String customerType;
        
        
    /**
     * fbIdentifier
     */
    private String fbIdentifier;
        
        
    /**
     * percentageFullProfil
     */
    private Integer percentageFullProfil;
        
        
    /**
     * personnalizedIdentifier
     */
    private String personnalizedIdentifier;
        
        
    /**
     * secretQuestion
     */
    private String secretQuestion;
        
        
    /**
     * Pos
     */
    private String Pos;
        
        
    /**
     * status
     */
    private String status;
        
        
    /**
     * website_carrier
     */
    private String website_carrier;
        
        
    /**
     * creationSignature
     */
    private String creationSignature;
        
        
    /**
     * modificationSignature
     */
    private String modificationSignature;
        
        
    /**
     * creationSignatureSite
     */
    private String creationSignatureSite;
        
        
    /**
     * modificationSignatureSite
     */
    private String modificationSignatureSite;
        
        
    /**
     * dateCreation
     */
    private Date dateCreation;
        

    /*PROTECTED REGION ID(_fMKqID8vEeCFh9Ea_LFCog u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public MyAccountDataDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pAccountIdentifier accountIdentifier
     * @param pDateModification dateModification
     * @param pEmailIdentifier emailIdentifier
     * @param pCustomerType customerType
     * @param pFbIdentifier fbIdentifier
     * @param pPercentageFullProfil percentageFullProfil
     * @param pPersonnalizedIdentifier personnalizedIdentifier
     * @param pSecretQuestion secretQuestion
     * @param pPos Pos
     * @param pStatus status
     * @param pWebsite_carrier website_carrier
     * @param pCreationSignature creationSignature
     * @param pModificationSignature modificationSignature
     * @param pCreationSignatureSite creationSignatureSite
     * @param pModificationSignatureSite modificationSignatureSite
     * @param pDateCreation dateCreation
     */
    public MyAccountDataDTO(String pAccountIdentifier, Date pDateModification, String pEmailIdentifier, String pCustomerType, String pFbIdentifier, Integer pPercentageFullProfil, String pPersonnalizedIdentifier, String pSecretQuestion, String pPos, String pStatus, String pWebsite_carrier, String pCreationSignature, String pModificationSignature, String pCreationSignatureSite, String pModificationSignatureSite, Date pDateCreation) {
        this.accountIdentifier = pAccountIdentifier;
        this.dateModification = pDateModification;
        this.emailIdentifier = pEmailIdentifier;
        this.customerType = pCustomerType;
        this.fbIdentifier = pFbIdentifier;
        this.percentageFullProfil = pPercentageFullProfil;
        this.personnalizedIdentifier = pPersonnalizedIdentifier;
        this.secretQuestion = pSecretQuestion;
        this.Pos = pPos;
        this.status = pStatus;
        this.website_carrier = pWebsite_carrier;
        this.creationSignature = pCreationSignature;
        this.modificationSignature = pModificationSignature;
        this.creationSignatureSite = pCreationSignatureSite;
        this.modificationSignatureSite = pModificationSignatureSite;
        this.dateCreation = pDateCreation;
    }

    /**
     *
     * @return Pos
     */
    public String getPos() {
        return this.Pos;
    }

    /**
     *
     * @param pPos Pos value
     */
    public void setPos(String pPos) {
        this.Pos = pPos;
    }

    /**
     *
     * @return accountIdentifier
     */
    public String getAccountIdentifier() {
        return this.accountIdentifier;
    }

    /**
     *
     * @param pAccountIdentifier accountIdentifier value
     */
    public void setAccountIdentifier(String pAccountIdentifier) {
        this.accountIdentifier = pAccountIdentifier;
    }

    /**
     *
     * @return creationSignature
     */
    public String getCreationSignature() {
        return this.creationSignature;
    }

    /**
     *
     * @param pCreationSignature creationSignature value
     */
    public void setCreationSignature(String pCreationSignature) {
        this.creationSignature = pCreationSignature;
    }

    /**
     *
     * @return creationSignatureSite
     */
    public String getCreationSignatureSite() {
        return this.creationSignatureSite;
    }

    /**
     *
     * @param pCreationSignatureSite creationSignatureSite value
     */
    public void setCreationSignatureSite(String pCreationSignatureSite) {
        this.creationSignatureSite = pCreationSignatureSite;
    }

    /**
     *
     * @return customerType
     */
    public String getCustomerType() {
        return this.customerType;
    }

    /**
     *
     * @param pCustomerType customerType value
     */
    public void setCustomerType(String pCustomerType) {
        this.customerType = pCustomerType;
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
     * @return emailIdentifier
     */
    public String getEmailIdentifier() {
        return this.emailIdentifier;
    }

    /**
     *
     * @param pEmailIdentifier emailIdentifier value
     */
    public void setEmailIdentifier(String pEmailIdentifier) {
        this.emailIdentifier = pEmailIdentifier;
    }

    /**
     *
     * @return fbIdentifier
     */
    public String getFbIdentifier() {
        return this.fbIdentifier;
    }

    /**
     *
     * @param pFbIdentifier fbIdentifier value
     */
    public void setFbIdentifier(String pFbIdentifier) {
        this.fbIdentifier = pFbIdentifier;
    }

    /**
     *
     * @return modificationSignature
     */
    public String getModificationSignature() {
        return this.modificationSignature;
    }

    /**
     *
     * @param pModificationSignature modificationSignature value
     */
    public void setModificationSignature(String pModificationSignature) {
        this.modificationSignature = pModificationSignature;
    }

    /**
     *
     * @return modificationSignatureSite
     */
    public String getModificationSignatureSite() {
        return this.modificationSignatureSite;
    }

    /**
     *
     * @param pModificationSignatureSite modificationSignatureSite value
     */
    public void setModificationSignatureSite(String pModificationSignatureSite) {
        this.modificationSignatureSite = pModificationSignatureSite;
    }

    /**
     *
     * @return percentageFullProfil
     */
    public Integer getPercentageFullProfil() {
        return this.percentageFullProfil;
    }

    /**
     *
     * @param pPercentageFullProfil percentageFullProfil value
     */
    public void setPercentageFullProfil(Integer pPercentageFullProfil) {
        this.percentageFullProfil = pPercentageFullProfil;
    }

    /**
     *
     * @return personnalizedIdentifier
     */
    public String getPersonnalizedIdentifier() {
        return this.personnalizedIdentifier;
    }

    /**
     *
     * @param pPersonnalizedIdentifier personnalizedIdentifier value
     */
    public void setPersonnalizedIdentifier(String pPersonnalizedIdentifier) {
        this.personnalizedIdentifier = pPersonnalizedIdentifier;
    }

    /**
     *
     * @return secretQuestion
     */
    public String getSecretQuestion() {
        return this.secretQuestion;
    }

    /**
     *
     * @param pSecretQuestion secretQuestion value
     */
    public void setSecretQuestion(String pSecretQuestion) {
        this.secretQuestion = pSecretQuestion;
    }

    /**
     *
     * @return status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     *
     * @param pStatus status value
     */
    public void setStatus(String pStatus) {
        this.status = pStatus;
    }

    /**
     *
     * @return website_carrier
     */
    public String getWebsite_carrier() {
        return this.website_carrier;
    }

    /**
     *
     * @param pWebsite_carrier website_carrier value
     */
    public void setWebsite_carrier(String pWebsite_carrier) {
        this.website_carrier = pWebsite_carrier;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_fMKqID8vEeCFh9Ea_LFCog) ENABLED START*/
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
        buffer.append("accountIdentifier=").append(getAccountIdentifier());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("emailIdentifier=").append(getEmailIdentifier());
        buffer.append(",");
        buffer.append("customerType=").append(getCustomerType());
        buffer.append(",");
        buffer.append("fbIdentifier=").append(getFbIdentifier());
        buffer.append(",");
        buffer.append("percentageFullProfil=").append(getPercentageFullProfil());
        buffer.append(",");
        buffer.append("personnalizedIdentifier=").append(getPersonnalizedIdentifier());
        buffer.append(",");
        buffer.append("secretQuestion=").append(getSecretQuestion());
        buffer.append(",");
        buffer.append("Pos=").append(getPos());
        buffer.append(",");
        buffer.append("status=").append(getStatus());
        buffer.append(",");
        buffer.append("website_carrier=").append(getWebsite_carrier());
        buffer.append(",");
        buffer.append("creationSignature=").append(getCreationSignature());
        buffer.append(",");
        buffer.append("modificationSignature=").append(getModificationSignature());
        buffer.append(",");
        buffer.append("creationSignatureSite=").append(getCreationSignatureSite());
        buffer.append(",");
        buffer.append("modificationSignatureSite=").append(getModificationSignatureSite());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_fMKqID8vEeCFh9Ea_LFCog u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
