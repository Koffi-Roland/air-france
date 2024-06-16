package com.airfrance.repind.dto.individu.myaccountcustomerdata;

/*PROTECTED REGION ID(_PftckD5kEeChwshMtbvhCA i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : MyAccountCustomerDataRequestDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class MyAccountCustomerDataRequestDTO  {
        
    /**
     * identificationNumber
     */
    private String identificationNumber;
        
        
    /**
     * option
     */
    private String option;
        

    /*PROTECTED REGION ID(_PftckD5kEeChwshMtbvhCA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public MyAccountCustomerDataRequestDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pIdentificationNumber identificationNumber
     * @param pOption option
     */
    public MyAccountCustomerDataRequestDTO(String pIdentificationNumber, String pOption) {
        this.identificationNumber = pIdentificationNumber;
        this.option = pOption;
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
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_PftckD5kEeChwshMtbvhCA) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }

    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        return new ToStringBuilder(this)
            .append("identificationNumber", getIdentificationNumber())
            .append("option", getOption())
            .toString();
    }

    /*PROTECTED REGION ID(_PftckD5kEeChwshMtbvhCA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
