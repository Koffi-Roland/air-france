package com.airfrance.repind.dto.firme.providefirmdata;

/*PROTECTED REGION ID(_YkiFAONVEeCJ_971XLeDSQ i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : ProvideFirmDataRequestDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ProvideFirmDataRequestDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -7615518090373164556L;


	/**
     * identificationNumber
     */
    private String identificationNumber;
        
        
    /**
     * option
     */
    private String option;
        
        
    /**
     * company
     */
    private String company;
        
        
    /**
     * searchDate
     */
    private Date searchDate;
        

    /*PROTECTED REGION ID(_YkiFAONVEeCJ_971XLeDSQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ProvideFirmDataRequestDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pIdentificationNumber identificationNumber
     * @param pOption option
     * @param pCompany company
     * @param pSearchDate searchDate
     */
    public ProvideFirmDataRequestDTO(String pIdentificationNumber, String pOption, String pCompany, Date pSearchDate) {
        this.identificationNumber = pIdentificationNumber;
        this.option = pOption;
        this.company = pCompany;
        this.searchDate = pSearchDate;
    }

    /**
     *
     * @return company
     */
    public String getCompany() {
        return this.company;
    }

    /**
     *
     * @param pCompany company value
     */
    public void setCompany(String pCompany) {
        this.company = pCompany;
    }

    /**
     *
     * @return identificationNumber
     */
    public String getIdentificationNumber() {
        return this.identificationNumber;
    }

    /**
     *
     * @param pIdentificationNumber identificationNumber value
     */
    public void setIdentificationNumber(String pIdentificationNumber) {
        this.identificationNumber = pIdentificationNumber;
    }

    /**
     *
     * @return option
     */
    public String getOption() {
        return this.option;
    }

    /**
     *
     * @param pOption option value
     */
    public void setOption(String pOption) {
        this.option = pOption;
    }

    /**
     *
     * @return searchDate
     */
    public Date getSearchDate() {
        return this.searchDate;
    }

    /**
     *
     * @param pSearchDate searchDate value
     */
    public void setSearchDate(Date pSearchDate) {
        this.searchDate = pSearchDate;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_YkiFAONVEeCJ_971XLeDSQ) ENABLED START*/
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
        buffer.append("identificationNumber=").append(getIdentificationNumber());
        buffer.append(",");
        buffer.append("option=").append(getOption());
        buffer.append(",");
        buffer.append("company=").append(getCompany());
        buffer.append(",");
        buffer.append("searchDate=").append(getSearchDate());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_YkiFAONVEeCJ_971XLeDSQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
