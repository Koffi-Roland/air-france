package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_3DfAEGQrEeSRQ7C-gEfj8g i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : SelfBookingToolDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class SelfBookingToolDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -7643484890406744110L;


	/**
     * gin
     */
    private String gin;
        
        
    /**
     * portalGdsCode
     */
    private String portalGdsCode;
        
        
    /**
     * sbtCode
     */
    private String sbtCode;
        
        
    /**
     * personneMorale
     */
    private PersonneMoraleDTO personneMorale;
        

    /*PROTECTED REGION ID(_3DfAEGQrEeSRQ7C-gEfj8g u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public SelfBookingToolDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pGin gin
     * @param pPortalGdsCode portalGdsCode
     * @param pSbtCode sbtCode
     */
    public SelfBookingToolDTO(String pGin, String pPortalGdsCode, String pSbtCode) {
        this.gin = pGin;
        this.portalGdsCode = pPortalGdsCode;
        this.sbtCode = pSbtCode;
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
     * @return personneMorale
     */
    public PersonneMoraleDTO getPersonneMorale() {
        return this.personneMorale;
    }

    /**
     *
     * @param pPersonneMorale personneMorale value
     */
    public void setPersonneMorale(PersonneMoraleDTO pPersonneMorale) {
        this.personneMorale = pPersonneMorale;
    }

    /**
     *
     * @return portalGdsCode
     */
    public String getPortalGdsCode() {
        return this.portalGdsCode;
    }

    /**
     *
     * @param pPortalGdsCode portalGdsCode value
     */
    public void setPortalGdsCode(String pPortalGdsCode) {
        this.portalGdsCode = pPortalGdsCode;
    }

    /**
     *
     * @return sbtCode
     */
    public String getSbtCode() {
        return this.sbtCode;
    }

    /**
     *
     * @param pSbtCode sbtCode value
     */
    public void setSbtCode(String pSbtCode) {
        this.sbtCode = pSbtCode;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_3DfAEGQrEeSRQ7C-gEfj8g) ENABLED START*/
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
        buffer.append("portalGdsCode=").append(getPortalGdsCode());
        buffer.append(",");
        buffer.append("sbtCode=").append(getSbtCode());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_3DfAEGQrEeSRQ7C-gEfj8g u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
