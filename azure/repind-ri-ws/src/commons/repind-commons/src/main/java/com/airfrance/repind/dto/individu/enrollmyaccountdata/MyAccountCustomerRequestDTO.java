package com.airfrance.repind.dto.individu.enrollmyaccountdata;

/*PROTECTED REGION ID(_X1D-8EM7EeCk2djT-5OeOA i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.dto.individu.EmailDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.individu.TelecomDTO;

import java.io.Serializable;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : MyAccountCustomerRequestDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class MyAccountCustomerRequestDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -7111002814234617247L;


	/**
     * civility
     */
    private String civility;
        
        
    /**
     * gin
     */
    private String gin;
        
        
    /**
     * firstName
     */
    private String firstName;
        
        
    /**
     * lastName
     */
    private String lastName;
        
        
    /**
     * emailIdentifier
     */
    private String emailIdentifier;
        
        
    /**
     * password
     */
    private String password;
        
        
    /**
     * language
     */
    private String language;
        
        
    /**
     * Should be Air France or KLM
     */
    private String website;
        
        
    /**
     * Market from which the customer requested newsletter subscription for the first time
     */
    private String pointOfSell;
        
        
    /**
     * Local Newsletter subscription authorization
     */
    private Boolean optIn;
        
        
    /**
     * directFBEnroll
     */
    private Boolean directFBEnroll;
        
        
    /**
     * email
     */
    private String email;
        
        
    /**
     * comGroupType
     */
    private String comGroupType;
        
        
    /**
     * comType
     */
    private String comType;
        
        
    /**
     * media
     */
    private Set<String> media;
        
        
    /**
     * emailException
     */
    private EmailDTO emailException;
        
        
    /**
     * signature
     */
    private SignatureDTO signature;
    
    
    /**
     * channel
     */
    private String channel;
        
    
	/**
     * telecom
     */
    private TelecomDTO telecom;
        

    /*PROTECTED REGION ID(_X1D-8EM7EeCk2djT-5OeOA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public MyAccountCustomerRequestDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCivility civility
     * @param pGin gin
     * @param pFirstName firstName
     * @param pLastName lastName
     * @param pEmailIdentifier emailIdentifier
     * @param pPassword password
     * @param pLanguage language
     * @param pWebsite website
     * @param pPointOfSell pointOfSell
     * @param pOptIn optIn
     * @param pDirectFBEnroll directFBEnroll
     * @param pEmail email
     * @param pComGroupType comGroupType
     * @param pComType comType
     * @param pMedia media
     */
    public MyAccountCustomerRequestDTO(String pCivility, String pGin, String pFirstName, String pLastName, String pEmailIdentifier, String pPassword, String pLanguage, String pWebsite, String pPointOfSell, Boolean pOptIn, Boolean pDirectFBEnroll, String pEmail, String pComGroupType, String pComType, Set<String> pMedia, String pChannel) {
        this.civility = pCivility;
        this.gin = pGin;
        this.firstName = pFirstName;
        this.lastName = pLastName;
        this.emailIdentifier = pEmailIdentifier;
        this.password = pPassword;
        this.language = pLanguage;
        this.website = pWebsite;
        this.pointOfSell = pPointOfSell;
        this.optIn = pOptIn;
        this.directFBEnroll = pDirectFBEnroll;
        this.email = pEmail;
        this.comGroupType = pComGroupType;
        this.comType = pComType;
        this.media = pMedia;
        this.channel = pChannel;
    }

    /**
     *
     * @return civility
     */
    public String getCivility() {
        return this.civility;
    }

    /**
     *
     * @param pCivility civility value
     */
    public void setCivility(String pCivility) {
        this.civility = pCivility;
    }

    /**
     *
     * @return comGroupType
     */
    public String getComGroupType() {
        return this.comGroupType;
    }

    /**
     *
     * @param pComGroupType comGroupType value
     */
    public void setComGroupType(String pComGroupType) {
        this.comGroupType = pComGroupType;
    }

    /**
     *
     * @return comType
     */
    public String getComType() {
        return this.comType;
    }

    /**
     *
     * @param pComType comType value
     */
    public void setComType(String pComType) {
        this.comType = pComType;
    }

    /**
     *
     * @return directFBEnroll
     */
    public Boolean getDirectFBEnroll() {
        return this.directFBEnroll;
    }

    /**
     *
     * @param pDirectFBEnroll directFBEnroll value
     */
    public void setDirectFBEnroll(Boolean pDirectFBEnroll) {
        this.directFBEnroll = pDirectFBEnroll;
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
     * @return emailException
     */
    public EmailDTO getEmailException() {
        return this.emailException;
    }

    /**
     *
     * @param pEmailException emailException value
     */
    public void setEmailException(EmailDTO pEmailException) {
        this.emailException = pEmailException;
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
     * @return firstName
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     *
     * @param pFirstName firstName value
     */
    public void setFirstName(String pFirstName) {
        this.firstName = pFirstName;
    }

    /**
     *
     * @return gin
     */
    public String getGin() {
        return this.gin;
    }

    /**
     *
     * @param pGin gin value
     */
    public void setGin(String pGin) {
        this.gin = pGin;
    }

    /**
     *
     * @return language
     */
    public String getLanguage() {
        return this.language;
    }

    /**
     *
     * @param pLanguage language value
     */
    public void setLanguage(String pLanguage) {
        this.language = pLanguage;
    }

    /**
     *
     * @return lastName
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     *
     * @param pLastName lastName value
     */
    public void setLastName(String pLastName) {
        this.lastName = pLastName;
    }

    /**
     *
     * @return media
     */
    public Set<String> getMedia() {
        return this.media;
    }

    /**
     *
     * @param pMedia media value
     */
    public void setMedia(Set<String> pMedia) {
        this.media = pMedia;
    }

    /**
     *
     * @return optIn
     */
    public Boolean getOptIn() {
        return this.optIn;
    }

    /**
     *
     * @param pOptIn optIn value
     */
    public void setOptIn(Boolean pOptIn) {
        this.optIn = pOptIn;
    }

    /**
     *
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     *
     * @param pPassword password value
     */
    public void setPassword(String pPassword) {
        this.password = pPassword;
    }

    /**
     *
     * @return pointOfSell
     */
    public String getPointOfSell() {
        return this.pointOfSell;
    }

    /**
     *
     * @param pPointOfSell pointOfSell value
     */
    public void setPointOfSell(String pPointOfSell) {
        this.pointOfSell = pPointOfSell;
    }

    /**
     *
     * @return signature
     */
    public SignatureDTO getSignature() {
        return this.signature;
    }

    /**
     *
     * @param pSignature signature value
     */
    public void setSignature(SignatureDTO pSignature) {
        this.signature = pSignature;
    }

    /**
     *
     * @return telecom
     */
    public TelecomDTO getTelecom() {
        return this.telecom;
    }

    /**
     *
     * @param pTelecom telecom value
     */
    public void setTelecom(TelecomDTO pTelecom) {
        this.telecom = pTelecom;
    }

    /**
     *
     * @return website
     */
    public String getWebsite() {
        return this.website;
    }

    /**
     *
     * @param pWebsite website value
     */
    public void setWebsite(String pWebsite) {
        this.website = pWebsite;
    }
    
    /**
     * 
     * @return
     */
    public String getChannel() {
		return channel;
	}

    /**
     * 
     * @param channel
     */
	public void setChannel(String channel) {
		this.channel = channel;
	}

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_X1D-8EM7EeCk2djT-5OeOA) ENABLED START*/
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
        buffer.append("civility=").append(getCivility());
        buffer.append(",");
        buffer.append("gin=").append(getGin());
        buffer.append(",");
        buffer.append("firstName=").append(getFirstName());
        buffer.append(",");
        buffer.append("lastName=").append(getLastName());
        buffer.append(",");
        buffer.append("emailIdentifier=").append(getEmailIdentifier());
        buffer.append(",");
        buffer.append("password=").append(getPassword());
        buffer.append(",");
        buffer.append("language=").append(getLanguage());
        buffer.append(",");
        buffer.append("website=").append(getWebsite());
        buffer.append(",");
        buffer.append("pointOfSell=").append(getPointOfSell());
        buffer.append(",");
        buffer.append("optIn=").append(getOptIn());
        buffer.append(",");
        buffer.append("directFBEnroll=").append(getDirectFBEnroll());
        buffer.append(",");
        buffer.append("email=").append(getEmail());
        buffer.append(",");
        buffer.append("comGroupType=").append(getComGroupType());
        buffer.append(",");
        buffer.append("comType=").append(getComType());
        buffer.append(",");
        buffer.append("media=").append(getMedia());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_X1D-8EM7EeCk2djT-5OeOA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
