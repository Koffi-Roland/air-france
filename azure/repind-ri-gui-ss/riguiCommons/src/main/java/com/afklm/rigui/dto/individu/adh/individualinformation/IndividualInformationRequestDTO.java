package com.afklm.rigui.dto.individu.adh.individualinformation;

/*PROTECTED REGION ID(_QNWM0CMUEeCWJOBY8f-ONQ i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : IndividualInformationRequestDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class IndividualInformationRequestDTO  {
        
    /**
     * identificationNumber
     */
    private String identificationNumber;
        
        
    /**
     * option
     */
    private String option;

    
    /**
     * populationTargeted
     */
    private String populationTargeted;
    

    /*PROTECTED REGION ID(_QNWM0CMUEeCWJOBY8f-ONQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public IndividualInformationRequestDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pIdentificationNumber identificationNumber
     * @param pOption option
     */
    public IndividualInformationRequestDTO(String pIdentificationNumber, String pOption) {
        this.identificationNumber = pIdentificationNumber;
        this.option = pOption;
    }

    /** 
     * full constructor
     * @param pIdentificationNumber identificationNumber
     * @param pOption option
     * @param pPopulationtargeted populationtargeted
     */
    public IndividualInformationRequestDTO(String pIdentificationNumber, String pOption, String pPopulationTargeted) {
        this.identificationNumber = pIdentificationNumber;
        this.option = pOption;
        this.populationTargeted = pPopulationTargeted;
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
    * @return populationTargeted
    */
   public String getPopulationTargeted() {
       // On positionne une valeur par défaut qui est "I" pour les Individus Encartés compatible avec tous les vieux services
	   // if (this.populationTargeted == null || "".equals(this.populationTargeted)) {		   
	//   return "I";
	//   } else {
		   return this.populationTargeted;
	//   }
   }

   /**
    *
    * @param pPopulationTargeted value
    */
   public void setPopulationTargeted(String pPopulationTargeted) {
       this.populationTargeted = pPopulationTargeted;
   }
    
    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_QNWM0CMUEeCWJOBY8f-ONQ) ENABLED START*/
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
            .append("populationTargeted", getPopulationTargeted())
            .toString();
    }

    /*PROTECTED REGION ID(_QNWM0CMUEeCWJOBY8f-ONQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
