package com.airfrance.repind.dto.agence;

/*PROTECTED REGION ID(_0VRuBWk1EeGhB9497mGnHw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;

/*PROTECTED REGION END*/

/**
 * <p>Title : LettreCompteDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class LettreCompteDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -81816191332525729L;


	/**
     * icle
     */
    private Integer icle;
        
        
    /**
     * sgin
     */
    private String sgin;
        
        
    /**
     * slettreComptoir
     */
    private String slettreComptoir;
        

    /*PROTECTED REGION ID(_0VRuBWk1EeGhB9497mGnHw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public LettreCompteDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pIcle icle
     * @param pSgin sgin
     * @param pSlettreComptoir slettreComptoir
     */
    public LettreCompteDTO(Integer pIcle, String pSgin, String pSlettreComptoir) {
        this.icle = pIcle;
        this.sgin = pSgin;
        this.slettreComptoir = pSlettreComptoir;
    }

    /**
     *
     * @return icle
     */
    public Integer getIcle() {
        return this.icle;
    }

    /**
     *
     * @param pIcle icle value
     */
    public void setIcle(Integer pIcle) {
        this.icle = pIcle;
    }

    /**
     *
     * @return sgin
     */
    public String getSgin() {
        return this.sgin;
    }

    /**
     *
     * @param pSgin sgin value
     */
    public void setSgin(String pSgin) {
        this.sgin = pSgin;
    }

    /**
     *
     * @return slettreComptoir
     */
    public String getSlettreComptoir() {
        return this.slettreComptoir;
    }

    /**
     *
     * @param pSlettreComptoir slettreComptoir value
     */
    public void setSlettreComptoir(String pSlettreComptoir) {
        this.slettreComptoir = pSlettreComptoir;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_0VRuBWk1EeGhB9497mGnHw) ENABLED START*/
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
        buffer.append("icle=").append(getIcle());
        buffer.append(",");
        buffer.append("sgin=").append(getSgin());
        buffer.append(",");
        buffer.append("slettreComptoir=").append(getSlettreComptoir());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_0VRuBWk1EeGhB9497mGnHw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
