package com.afklm.rigui.dto.ws;

/*PROTECTED REGION ID(_K9_PANUqEeef5oRB6XPNlw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : RequestorDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class RequestorDTO implements Serializable {


        
    /**
     * <p>Values :</p><p>B2C, </p><p>DS (Direct Sales), </p><p>FBC (Flying Blue Call Center),</p><p>MOB (Mobile),</p><p>APPS (Mobile apps)</p>
     */
    private String channel;
        
        
    /**
     * <p>Id of the caller if  known (FBC)</p>
     */
    private String matricule;
        
        
    /**
     * <p>Values:</p><p>AF (Air France)</p><p>KL (KLM)</p>
     */
    private String managingCompany;
        
        
    /**
     * <p>OfficeId associated to the caller if known.</p>
     */
    private String officeId;
        
        
    /**
     * <p><b>Request context</b></p><p>This parameter is aimed to ask for a specific processing that will be useful in the future.</p><br/>
     * <p><b> Mandatory </b> for a prospect usage </p><br/>
     * <p> B2C_HOME_PAGE or B2C_BOOKING_PROCESS or B2C_HOME_PAGE_RECONCILIATION </p>
     * 
     */
    private String context;
        
        
    /**
     * <p> [0-9]{1,12} GIN of authenticated customer </p>
     */
    private String loggedGin;
        
        
    /**
     * <p>[0-9]{1,12} FB number </p>
     * 
     */
    private String reconciliationDataCIN;
        
        
    /**
     * <p>Token provided after a call to AuthenticateAnIndividual</p><p>Used only when an active session is required (B2C, mobile applications)</p>
     */
    private String token;
        
        
    /**
     * <p>IPAdress if known</p>
     */
    private String ipAddress;
        
        
    /**
     * <p>ISI, OSC, etc.</p>
     */
    private String applicationCode;
        
        
    /**
     * <p>Values:</p><p>QVI (Valbonne)</p><p>TLS (Toulouse)</p><p>AMS (Amsterdam)</p>
     */
    private String site;
        
        
    /**
     * <p>Free text</p><p>Name of the process used</p>
     */
    private String signature;
        

    /*PROTECTED REGION ID(_K9_PANUqEeef5oRB6XPNlw u var) ENABLED START*/
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
	    public RequestorDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return applicationCode
     */
    public String getApplicationCode() {
        return this.applicationCode;
    }

    /**
     *
     * @param pApplicationCode applicationCode value
     */
    public void setApplicationCode(String pApplicationCode) {
        this.applicationCode = pApplicationCode;
    }

    /**
     *
     * @return channel
     */
    public String getChannel() {
        return this.channel;
    }

    /**
     *
     * @param pChannel channel value
     */
    public void setChannel(String pChannel) {
        this.channel = pChannel;
    }

    /**
     *
     * @return context
     */
    public String getContext() {
        return this.context;
    }

    /**
     *
     * @param pContext context value
     */
    public void setContext(String pContext) {
        this.context = pContext;
    }

    /**
     *
     * @return ipAddress
     */
    public String getIpAddress() {
        return this.ipAddress;
    }

    /**
     *
     * @param pIpAddress ipAddress value
     */
    public void setIpAddress(String pIpAddress) {
        this.ipAddress = pIpAddress;
    }

    /**
     *
     * @return loggedGin
     */
    public String getLoggedGin() {
        return this.loggedGin;
    }

    /**
     *
     * @param pLoggedGin loggedGin value
     */
    public void setLoggedGin(String pLoggedGin) {
        this.loggedGin = pLoggedGin;
    }

    /**
     *
     * @return managingCompany
     */
    public String getManagingCompany() {
        return this.managingCompany;
    }

    /**
     *
     * @param pManagingCompany managingCompany value
     */
    public void setManagingCompany(String pManagingCompany) {
        this.managingCompany = pManagingCompany;
    }

    /**
     *
     * @return matricule
     */
    public String getMatricule() {
        return this.matricule;
    }

    /**
     *
     * @param pMatricule matricule value
     */
    public void setMatricule(String pMatricule) {
        this.matricule = pMatricule;
    }

    /**
     *
     * @return officeId
     */
    public String getOfficeId() {
        return this.officeId;
    }

    /**
     *
     * @param pOfficeId officeId value
     */
    public void setOfficeId(String pOfficeId) {
        this.officeId = pOfficeId;
    }

    /**
     *
     * @return reconciliationDataCIN
     */
    public String getReconciliationDataCIN() {
        return this.reconciliationDataCIN;
    }

    /**
     *
     * @param pReconciliationDataCIN reconciliationDataCIN value
     */
    public void setReconciliationDataCIN(String pReconciliationDataCIN) {
        this.reconciliationDataCIN = pReconciliationDataCIN;
    }

    /**
     *
     * @return signature
     */
    public String getSignature() {
        return this.signature;
    }

    /**
     *
     * @param pSignature signature value
     */
    public void setSignature(String pSignature) {
        this.signature = pSignature;
    }

    /**
     *
     * @return site
     */
    public String getSite() {
        return this.site;
    }

    /**
     *
     * @param pSite site value
     */
    public void setSite(String pSite) {
        this.site = pSite;
    }

    /**
     *
     * @return token
     */
    public String getToken() {
        return this.token;
    }

    /**
     *
     * @param pToken token value
     */
    public void setToken(String pToken) {
        this.token = pToken;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_K9_PANUqEeef5oRB6XPNlw) ENABLED START*/
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
        buffer.append("channel=").append(getChannel());
        buffer.append(",");
        buffer.append("matricule=").append(getMatricule());
        buffer.append(",");
        buffer.append("managingCompany=").append(getManagingCompany());
        buffer.append(",");
        buffer.append("officeId=").append(getOfficeId());
        buffer.append(",");
        buffer.append("context=").append(getContext());
        buffer.append(",");
        buffer.append("loggedGin=").append(getLoggedGin());
        buffer.append(",");
        buffer.append("reconciliationDataCIN=").append(getReconciliationDataCIN());
        buffer.append(",");
        buffer.append("token=").append(getToken());
        buffer.append(",");
        buffer.append("ipAddress=").append(getIpAddress());
        buffer.append(",");
        buffer.append("applicationCode=").append(getApplicationCode());
        buffer.append(",");
        buffer.append("site=").append(getSite());
        buffer.append(",");
        buffer.append("signature=").append(getSignature());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_K9_PANUqEeef5oRB6XPNlw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
