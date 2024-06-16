package com.airfrance.repind.dto.individu.adh.individualinformation;

/*PROTECTED REGION ID(_b10EzyMUEeCWJOBY8f-ONQ i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : BlocContratS09424DTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class BlocContratS09424DTO  {
        
    /**
     * rolecontratv2sic
     */
    private RoleContratV2SICDTO rolecontratv2sic;
        
        
    /**
     * signaturesic
     */
    private Set<SignatureSICDTO> signaturesic;
        

    /*PROTECTED REGION ID(_b10EzyMUEeCWJOBY8f-ONQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    
    /** 
     * full constructor
     */
    public BlocContratS09424DTO() {
    }

    /**
     *
     * @return rolecontratv2sic
     */
    public RoleContratV2SICDTO getRolecontratv2sic() {
        return this.rolecontratv2sic;
    }

    /**
     *
     * @param pRolecontratv2sic rolecontratv2sic value
     */
    public void setRolecontratv2sic(RoleContratV2SICDTO pRolecontratv2sic) {
        this.rolecontratv2sic = pRolecontratv2sic;
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
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_b10EzyMUEeCWJOBY8f-ONQ) ENABLED START*/
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

    /*PROTECTED REGION ID(_b10EzyMUEeCWJOBY8f-ONQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
