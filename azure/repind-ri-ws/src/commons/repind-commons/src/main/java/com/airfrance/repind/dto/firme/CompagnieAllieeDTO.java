package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_08z57WkzEeGhB9497mGnHw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;

/*PROTECTED REGION END*/

/**
 * <p>Title : CompagnieAllieeDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class CompagnieAllieeDTO implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -6749497671421884421L;


	/**
     * cle
     */
    private Integer cle;
        
        
    /**
     * codeCie
     */
    private String codeCie;
        
        
    /**
     * personneMorale
     */
    private PersonneMoraleDTO personneMorale;
        

    /*PROTECTED REGION ID(_08z57WkzEeGhB9497mGnHw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public CompagnieAllieeDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCle cle
     * @param pCodeCie codeCie
     */
    public CompagnieAllieeDTO(Integer pCle, String pCodeCie) {
        this.cle = pCle;
        this.codeCie = pCodeCie;
    }

    /**
     *
     * @return cle
     */
    public Integer getCle() {
        return this.cle;
    }

    /**
     *
     * @param pCle cle value
     */
    public void setCle(Integer pCle) {
        this.cle = pCle;
    }

    /**
     *
     * @return codeCie
     */
    public String getCodeCie() {
        return this.codeCie;
    }

    /**
     *
     * @param pCodeCie codeCie value
     */
    public void setCodeCie(String pCodeCie) {
        this.codeCie = pCodeCie;
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
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_08z57WkzEeGhB9497mGnHw) ENABLED START*/
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
        buffer.append("cle=").append(getCle());
        buffer.append(",");
        buffer.append("codeCie=").append(getCodeCie());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_08z57WkzEeGhB9497mGnHw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
