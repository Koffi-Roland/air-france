package com.afklm.rigui.dto.individu.createmodifyindividual;

/*PROTECTED REGION ID(_dHvXIDRhEeCc7ZsKsK1lbQ i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;

/*PROTECTED REGION END*/

/**
 * <p>Title : InformationDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2017</p>
 * <p>Company : AIRFRANCE</p>
 */
public class InformationDTO  {
        
    /**
     * informationCode
     */
    private String informationCode;
        
    /**
     * informationDetails
     */
    private String informationDetails;


    /*PROTECTED REGION ID(_dHvXIDRhEeCc7ZsKsK1lbQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    
    /** 
     * full constructor
     */
    public InformationDTO() {
    }



    public String getInformationCode() {
		return informationCode;
	}



	public void setInformationCode(String informationCode) {
		this.informationCode = informationCode;
	}



	public String getInformationDetails() {
		return informationDetails;
	}



	public void setInformationDetails(String informationDetails) {
		this.informationDetails = informationDetails;
	}



	/**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_dHvXIDRhEeCc7ZsKsK1lbQ) ENABLED START*/
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
            .toString();
    }

    /*PROTECTED REGION ID(_dHvXIDRhEeCc7ZsKsK1lbQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
