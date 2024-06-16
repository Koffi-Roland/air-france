package com.afklm.rigui.dto.individu.adh.individualinformation;

/*PROTECTED REGION ID(_b1qUFyMUEeCWJOBY8f-ONQ i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : BlocTelecomS09424DTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class BlocTelecomS09424DTO  {
        
    /**
     * telecomPrincipal
     */
    private String telecomPrincipal;
        
        
    /**
     * signaturesic
     */
    private Set<SignatureSICDTO> signaturesic;
        
        
    /**
     * telecomsic
     */
    private TelecomSICDTO telecomsic;
        

    /*PROTECTED REGION ID(_b1qUFyMUEeCWJOBY8f-ONQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public BlocTelecomS09424DTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pTelecomPrincipal telecomPrincipal
     */
    public BlocTelecomS09424DTO(String pTelecomPrincipal) {
        this.telecomPrincipal = pTelecomPrincipal;
    }

    /**
     *
     * @return signaturesic
     */
    public Set<SignatureSICDTO> getSignaturesic() {
        return this.signaturesic;
    }

    /**
     *
     * @param pSignaturesic signaturesic value
     */
    public void setSignaturesic(Set<SignatureSICDTO> pSignaturesic) {
        this.signaturesic = pSignaturesic;
    }

    /**
     *
     * @return telecomPrincipal
     */
    public String getTelecomPrincipal() {
        return this.telecomPrincipal;
    }

    /**
     *
     * @param pTelecomPrincipal telecomPrincipal value
     */
    public void setTelecomPrincipal(String pTelecomPrincipal) {
        this.telecomPrincipal = pTelecomPrincipal;
    }

    /**
     *
     * @return telecomsic
     */
    public TelecomSICDTO getTelecomsic() {
        return this.telecomsic;
    }

    /**
     *
     * @param pTelecomsic telecomsic value
     */
    public void setTelecomsic(TelecomSICDTO pTelecomsic) {
        this.telecomsic = pTelecomsic;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_b1qUFyMUEeCWJOBY8f-ONQ) ENABLED START*/
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
            .append("telecomPrincipal", getTelecomPrincipal())
            .toString();
    }

    /*PROTECTED REGION ID(_b1qUFyMUEeCWJOBY8f-ONQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
