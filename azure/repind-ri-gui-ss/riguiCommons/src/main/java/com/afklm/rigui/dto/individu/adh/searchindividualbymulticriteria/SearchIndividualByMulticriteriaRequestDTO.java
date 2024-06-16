package com.afklm.rigui.dto.individu.adh.searchindividualbymulticriteria;

/*PROTECTED REGION ID(_V1pgoAWbEeegsNhfbw3UgQ i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : SearchIndividualByMulticriteriaRequestDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class SearchIndividualByMulticriteriaRequestDTO implements Serializable {


        
    /**
     * populationTargeted
     */
    private String populationTargeted;
        
        
    /**
     * processType
     */
    private String processType;
        
        
    /**
     * searchDriving
     */
    private String searchDriving;
        
        
    /**
     * contact
     */
    private ContactDTO contact;
        
        
    /**
     * identification
     */
    private IdentificationDTO identification;
        
        
    /**
     * identity
     */
    private IdentityDTO identity;
        
        
    /**
     * requestor
     */
    private RequestorDTO requestor;
        

    /*PROTECTED REGION ID(_V1pgoAWbEeegsNhfbw3UgQ u var) ENABLED START*/
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
	    public SearchIndividualByMulticriteriaRequestDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return contact
     */
    public ContactDTO getContact() {
        return this.contact;
    }

    /**
     *
     * @param pContact contact value
     */
    public void setContact(ContactDTO pContact) {
        this.contact = pContact;
    }

    /**
     *
     * @return identification
     */
    public IdentificationDTO getIdentification() {
        return this.identification;
    }

    /**
     *
     * @param pIdentification identification value
     */
    public void setIdentification(IdentificationDTO pIdentification) {
        this.identification = pIdentification;
    }

    /**
     *
     * @return identity
     */
    public IdentityDTO getIdentity() {
        return this.identity;
    }

    /**
     *
     * @param pIdentity identity value
     */
    public void setIdentity(IdentityDTO pIdentity) {
        this.identity = pIdentity;
    }

    /**
     *
     * @return populationTargeted
     */
    public String getPopulationTargeted() {
        return this.populationTargeted;
    }

    /**
     *
     * @param pPopulationTargeted populationTargeted value
     */
    public void setPopulationTargeted(String pPopulationTargeted) {
        this.populationTargeted = pPopulationTargeted;
    }

    /**
     *
     * @return processType
     */
    public String getProcessType() {
        return this.processType;
    }

    /**
     *
     * @param pProcessType processType value
     */
    public void setProcessType(String pProcessType) {
        this.processType = pProcessType;
    }

    /**
     *
     * @return requestor
     */
    public RequestorDTO getRequestor() {
        return this.requestor;
    }

    /**
     *
     * @param pRequestor requestor value
     */
    public void setRequestor(RequestorDTO pRequestor) {
        this.requestor = pRequestor;
    }

    /**
     *
     * @return searchDriving
     */
    public String getSearchDriving() {
        return this.searchDriving;
    }

    /**
     *
     * @param pSearchDriving searchDriving value
     */
    public void setSearchDriving(String pSearchDriving) {
        this.searchDriving = pSearchDriving;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_V1pgoAWbEeegsNhfbw3UgQ) ENABLED START*/
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
        buffer.append("populationTargeted=").append(getPopulationTargeted());
        buffer.append(",");
        buffer.append("processType=").append(getProcessType());
        buffer.append(",");
        buffer.append("searchDriving=").append(getSearchDriving());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_V1pgoAWbEeegsNhfbw3UgQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
