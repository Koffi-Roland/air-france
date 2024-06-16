package com.airfrance.repind.dto.individu.adh.individualinformation;

/*PROTECTED REGION ID(_b10EwCMUEeCWJOBY8f-ONQ i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : BlocEmailS09424DTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class BlocEmailS09424DTO  {
        
    /**
     * emailtype
     */
    private EmailTypeDTO emailtype;
        
        
    /**
     * signaturesic
     */
    private Set<SignatureSICDTO> signaturesic;
        

    /*PROTECTED REGION ID(_b10EwCMUEeCWJOBY8f-ONQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    
    /** 
     * full constructor
     */
    public BlocEmailS09424DTO() {
    }

    /**
     *
     * @return emailtype
     */
    public EmailTypeDTO getEmailtype() {
        return this.emailtype;
    }

    /**
     *
     * @param pEmailtype emailtype value
     */
    public void setEmailtype(EmailTypeDTO pEmailtype) {
        this.emailtype = pEmailtype;
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
        /*PROTECTED REGION ID(toString_b10EwCMUEeCWJOBY8f-ONQ) ENABLED START*/
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

    /*PROTECTED REGION ID(_b10EwCMUEeCWJOBY8f-ONQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
