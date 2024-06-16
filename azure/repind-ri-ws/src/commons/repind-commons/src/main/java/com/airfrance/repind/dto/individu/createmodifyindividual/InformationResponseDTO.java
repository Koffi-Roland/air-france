package com.airfrance.repind.dto.individu.createmodifyindividual;

/*PROTECTED REGION ID(_dHvXIDRhEeCc7ZsKsK1lbQ i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;

/*PROTECTED REGION END*/

/**
 * <p>Title : CreateModifyIndividualResponseDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class InformationResponseDTO  {
        
    /**
     * information
     */
    private InformationDTO information;
        
        

    /*PROTECTED REGION ID(_dHvXIDRhEeCc7ZsKsK1lbQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    
    /** 
     * full constructor
     */
    public InformationResponseDTO() {
    }


    
    
    public InformationDTO getInformation() {
		return information;
	}




	public void setInformation(InformationDTO information) {
		this.information = information;
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
