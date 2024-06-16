package com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria;

/*PROTECTED REGION ID(_ZjTE0AWgEeegsNhfbw3UgQ i) ENABLED START*/

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
     * channel
     */
    private String channel;
        
        
    /**
     * matricule
     */
    private String matricule;
        
        
    /**
     * managingCompany
     */
    private String managingCompany;
        
        
    /**
     * officeId
     */
    private String officeId;
        
        
    /**
     * scope
     */
    private String scope;
        
        
    /**
     * context
     */
    private String context;
        
        
    /**
     * token
     */
    private String token;
        
        
    /**
     * ipAddress
     */
    private String ipAddress;
        
        
    /**
     * applicationCode
     */
    private String applicationCode;
        
        
    /**
     * site
     */
    private String site;
        
        
    /**
     * signature
     */
    private String signature;
    
    /**
     * consumerId
     */
    private String consumerId;
        

    /*PROTECTED REGION ID(_ZjTE0AWgEeegsNhfbw3UgQ u var) ENABLED START*/
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
     * @return scope
     */
    public String getScope() {
        return this.scope;
    }

    /**
     *
     * @param pScope scope value
     */
    public void setScope(String pScope) {
        this.scope = pScope;
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
        /*PROTECTED REGION ID(toString_ZjTE0AWgEeegsNhfbw3UgQ) ENABLED START*/
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
        buffer.append("scope=").append(getScope());
        buffer.append(",");
        buffer.append("context=").append(getContext());
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

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

    /*PROTECTED REGION ID(_ZjTE0AWgEeegsNhfbw3UgQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
