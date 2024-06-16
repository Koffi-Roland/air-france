package com.airfrance.repind.dto.individu.adh.connexiondata;

/*PROTECTED REGION ID(_nNew8C7jEeC7hbdMKof7lA i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : ConnexionIdDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ConnexionIdDTO  {
        
    /**
     * connexionIdentifier
     */
    private String connexionIdentifier;
        
        
    /**
     * connexionIdentifierType
     */
    private String connexionIdentifierType;
        

    /*PROTECTED REGION ID(_nNew8C7jEeC7hbdMKof7lA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ConnexionIdDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pConnexionIdentifier connexionIdentifier
     * @param pConnexionIdentifierType connexionIdentifierType
     */
    public ConnexionIdDTO(String pConnexionIdentifier, String pConnexionIdentifierType) {
        this.connexionIdentifier = pConnexionIdentifier;
        this.connexionIdentifierType = pConnexionIdentifierType;
    }

    /**
     *
     * @return connexionIdentifier
     */
    public String getConnexionIdentifier() {
        return this.connexionIdentifier;
    }

    /**
     *
     * @param pConnexionIdentifier connexionIdentifier value
     */
    public void setConnexionIdentifier(String pConnexionIdentifier) {
        this.connexionIdentifier = pConnexionIdentifier;
    }

    /**
     *
     * @return connexionIdentifierType
     */
    public String getConnexionIdentifierType() {
        return this.connexionIdentifierType;
    }

    /**
     *
     * @param pConnexionIdentifierType connexionIdentifierType value
     */
    public void setConnexionIdentifierType(String pConnexionIdentifierType) {
        this.connexionIdentifierType = pConnexionIdentifierType;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_nNew8C7jEeC7hbdMKof7lA) ENABLED START*/
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
            .append("connexionIdentifier", getConnexionIdentifier())
            .append("connexionIdentifierType", getConnexionIdentifierType())
            .toString();
    }

    /*PROTECTED REGION ID(_nNew8C7jEeC7hbdMKof7lA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
