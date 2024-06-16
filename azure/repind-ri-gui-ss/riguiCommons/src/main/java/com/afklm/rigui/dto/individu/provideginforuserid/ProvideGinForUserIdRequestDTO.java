package com.afklm.rigui.dto.individu.provideginforuserid;

/*PROTECTED REGION ID(_iT-SgDXBEeCEFJUFQSNX7g i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : ProvideGinForUserIdRequestDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ProvideGinForUserIdRequestDTO  {
        
    /**
     * IdentifierType
     */
    private String IdentifierType;
        
        
    /**
     * Identifier
     */
    private String Identifier;
        

    /*PROTECTED REGION ID(_iT-SgDXBEeCEFJUFQSNX7g u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ProvideGinForUserIdRequestDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pIdentifierType IdentifierType
     * @param pIdentifier Identifier
     */
    public ProvideGinForUserIdRequestDTO(String pIdentifierType, String pIdentifier) {
        this.IdentifierType = pIdentifierType;
        this.Identifier = pIdentifier;
    }

    /**
     *
     * @return Identifier
     */
    public String getIdentifier() {
        return this.Identifier;
    }

    /**
     *
     * @param pIdentifier Identifier value
     */
    public void setIdentifier(String pIdentifier) {
        this.Identifier = pIdentifier;
    }

    /**
     *
     * @return IdentifierType
     */
    public String getIdentifierType() {
        return this.IdentifierType;
    }

    /**
     *
     * @param pIdentifierType IdentifierType value
     */
    public void setIdentifierType(String pIdentifierType) {
        this.IdentifierType = pIdentifierType;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_iT-SgDXBEeCEFJUFQSNX7g) ENABLED START*/
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
            .append("IdentifierType", getIdentifierType())
            .append("Identifier", getIdentifier())
            .toString();
    }

    /*PROTECTED REGION ID(_iT-SgDXBEeCEFJUFQSNX7g u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
