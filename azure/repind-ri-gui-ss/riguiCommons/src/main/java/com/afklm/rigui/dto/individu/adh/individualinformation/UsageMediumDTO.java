package com.afklm.rigui.dto.individu.adh.individualinformation;

/*PROTECTED REGION ID(_b1qUDyMUEeCWJOBY8f-ONQ i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : UsageMediumDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class UsageMediumDTO  {
        
    /**
     * codeAppliMetier
     */
    private String codeAppliMetier;
        
        
    /**
     * numeroUsage
     */
    private String numeroUsage;
        
        
    /**
     * roleadresse
     */
    private Set<RoleAdresseDTO> roleadresse;
        

    /*PROTECTED REGION ID(_b1qUDyMUEeCWJOBY8f-ONQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public UsageMediumDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCodeAppliMetier codeAppliMetier
     * @param pNumeroUsage numeroUsage
     */
    public UsageMediumDTO(String pCodeAppliMetier, String pNumeroUsage) {
        this.codeAppliMetier = pCodeAppliMetier;
        this.numeroUsage = pNumeroUsage;
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
     * @return numeroUsage
     */
    public String getNumeroUsage() {
        return this.numeroUsage;
    }

    /**
     *
     * @param pNumeroUsage numeroUsage value
     */
    public void setNumeroUsage(String pNumeroUsage) {
        this.numeroUsage = pNumeroUsage;
    }

    /**
     *
     * @return roleadresse
     */
    public Set<RoleAdresseDTO> getRoleadresse() {
        return this.roleadresse;
    }

    /**
     *
     * @param pRoleadresse roleadresse value
     */
    public void setRoleadresse(Set<RoleAdresseDTO> pRoleadresse) {
        this.roleadresse = pRoleadresse;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_b1qUDyMUEeCWJOBY8f-ONQ) ENABLED START*/
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
            .append("numeroUsage", getNumeroUsage())
            .toString();
    }

    /*PROTECTED REGION ID(_b1qUDyMUEeCWJOBY8f-ONQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
