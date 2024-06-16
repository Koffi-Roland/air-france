package com.airfrance.repind.dto.individu.createmodifyindividual;

/*PROTECTED REGION ID(_jQQNgDRhEeCc7ZsKsK1lbQ i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : RequeteDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class RequeteDTO  {
        
    /**
     * codeAppliMetier
     */
    private String codeAppliMetier;
        
        
    /**
     * clefVisa
     */
    private String clefVisa;
        
        
    /**
     * context
     */
    private String context;
        

    /*PROTECTED REGION ID(_jQQNgDRhEeCc7ZsKsK1lbQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public RequeteDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCodeAppliMetier codeAppliMetier
     * @param pClefVisa clefVisa
     * @param pContext context
     */
    public RequeteDTO(String pCodeAppliMetier, String pClefVisa, String pContext) {
        this.codeAppliMetier = pCodeAppliMetier;
        this.clefVisa = pClefVisa;
        this.context = pContext;
    }

    /**
     *
     * @return clefVisa
     */
    public String getClefVisa() {
        return this.clefVisa;
    }

    /**
     *
     * @param pClefVisa clefVisa value
     */
    public void setClefVisa(String pClefVisa) {
        this.clefVisa = pClefVisa;
    }

    /**
     *
     * @return codeAppliMetier
     */
    public String getCodeAppliMetier() {
        return this.codeAppliMetier;
    }

    /**
     *
     * @param pCodeAppliMetier codeAppliMetier value
     */
    public void setCodeAppliMetier(String pCodeAppliMetier) {
        this.codeAppliMetier = pCodeAppliMetier;
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
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_jQQNgDRhEeCc7ZsKsK1lbQ) ENABLED START*/
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
            .append("codeAppliMetier", getCodeAppliMetier())
            .append("clefVisa", getClefVisa())
            .append("context", getContext())
            .toString();
    }

    /*PROTECTED REGION ID(_jQQNgDRhEeCc7ZsKsK1lbQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
