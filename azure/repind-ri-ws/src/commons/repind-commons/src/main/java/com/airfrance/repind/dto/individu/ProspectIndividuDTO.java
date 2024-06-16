package com.airfrance.repind.dto.individu;

/*PROTECTED REGION ID(_5KX30I_eEeKSvNsslAib8A i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;

/*PROTECTED REGION END*/

/**
 * <p>Title : ProspectIndividuDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ProspectIndividuDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 1548261781147298140L;


	/**
     * gin
     */
    private String gin;
        
        
    /**
     * cin
     */
    private String cin;
        
        
    /**
     * email
     */
    private String email;
        

    /*PROTECTED REGION ID(_5KX30I_eEeKSvNsslAib8A u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ProspectIndividuDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pGin gin
     * @param pCin cin
     * @param pEmail email
     */
    public ProspectIndividuDTO(String pGin, String pCin, String pEmail) {
        this.gin = pGin;
        this.cin = pCin;
        this.email = pEmail;
    }

    /**
     *
     * @return cin
     */
    public String getCin() {
        return this.cin;
    }

    /**
     *
     * @param pCin cin value
     */
    public void setCin(String pCin) {
        this.cin = pCin;
    }

    /**
     *
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     *
     * @param pEmail email value
     */
    public void setEmail(String pEmail) {
        this.email = pEmail;
    }

    /**
     *
     * @return gin
     */
    public String getGin() {
        return this.gin;
    }

    /**
     *
     * @param pGin gin value
     */
    public void setGin(String pGin) {
        this.gin = pGin;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_5KX30I_eEeKSvNsslAib8A) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }

    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@").append(Integer.toHexString(System.identityHashCode(this)));
        buffer.append("[");
        buffer.append("gin=").append(getGin());
        buffer.append(",");
        buffer.append("cin=").append(getCin());
        buffer.append(",");
        buffer.append("email=").append(getEmail());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_5KX30I_eEeKSvNsslAib8A u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
