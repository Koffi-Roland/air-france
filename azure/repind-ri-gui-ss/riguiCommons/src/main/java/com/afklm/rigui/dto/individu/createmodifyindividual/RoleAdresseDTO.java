package com.afklm.rigui.dto.individu.createmodifyindividual;

/*PROTECTED REGION ID(_nV_MrTRkEeCc7ZsKsK1lbQ i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : RoleAdresseDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class RoleAdresseDTO  {
        
    /**
     * codeRoleAdresse
     */
    private String codeRoleAdresse;
        

    /*PROTECTED REGION ID(_nV_MrTRkEeCc7ZsKsK1lbQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public RoleAdresseDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCodeRoleAdresse codeRoleAdresse
     */
    public RoleAdresseDTO(String pCodeRoleAdresse) {
        this.codeRoleAdresse = pCodeRoleAdresse;
    }

    /**
     *
     * @return codeRoleAdresse
     */
    public String getCodeRoleAdresse() {
        return this.codeRoleAdresse;
    }

    /**
     *
     * @param pCodeRoleAdresse codeRoleAdresse value
     */
    public void setCodeRoleAdresse(String pCodeRoleAdresse) {
        this.codeRoleAdresse = pCodeRoleAdresse;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_nV_MrTRkEeCc7ZsKsK1lbQ) ENABLED START*/
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
            .append("codeRoleAdresse", getCodeRoleAdresse())
            .toString();
    }

    /*PROTECTED REGION ID(_nV_MrTRkEeCc7ZsKsK1lbQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
