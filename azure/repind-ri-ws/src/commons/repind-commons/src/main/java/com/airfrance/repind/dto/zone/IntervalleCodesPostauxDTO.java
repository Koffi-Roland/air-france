package com.airfrance.repind.dto.zone;

/*PROTECTED REGION ID(_IW0-MOHCEeS79pPzHY2rFw i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : IntervalleCodesPostauxDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class IntervalleCodesPostauxDTO implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -2762866182583632447L;


	/**
     * cle
     */
    private Long cle;
        
        
    /**
     * codePostalDebut
     */
    private String codePostalDebut;
        
        
    /**
     * codePostalFin
     */
    private String codePostalFin;
        
        
    /**
     * codePays
     */
    private String codePays;
        
        
    /**
     * usage
     */
    private String usage;
        
        
    /**
     * liensIntCpZd
     */
    private Set<LienIntCpZdDTO> liensIntCpZd;
        

    /*PROTECTED REGION ID(_IW0-MOHCEeS79pPzHY2rFw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public IntervalleCodesPostauxDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCle cle
     * @param pCodePostalDebut codePostalDebut
     * @param pCodePostalFin codePostalFin
     * @param pCodePays codePays
     * @param pUsage usage
     */
    public IntervalleCodesPostauxDTO(Long pCle, String pCodePostalDebut, String pCodePostalFin, String pCodePays, String pUsage) {
        this.cle = pCle;
        this.codePostalDebut = pCodePostalDebut;
        this.codePostalFin = pCodePostalFin;
        this.codePays = pCodePays;
        this.usage = pUsage;
    }

    /**
     *
     * @return cle
     */
    public Long getCle() {
        return this.cle;
    }

    /**
     *
     * @param pCle cle value
     */
    public void setCle(Long pCle) {
        this.cle = pCle;
    }

    /**
     *
     * @return codePays
     */
    public String getCodePays() {
        return this.codePays;
    }

    /**
     *
     * @param pCodePays codePays value
     */
    public void setCodePays(String pCodePays) {
        this.codePays = pCodePays;
    }

    /**
     *
     * @return codePostalDebut
     */
    public String getCodePostalDebut() {
        return this.codePostalDebut;
    }

    /**
     *
     * @param pCodePostalDebut codePostalDebut value
     */
    public void setCodePostalDebut(String pCodePostalDebut) {
        this.codePostalDebut = pCodePostalDebut;
    }

    /**
     *
     * @return codePostalFin
     */
    public String getCodePostalFin() {
        return this.codePostalFin;
    }

    /**
     *
     * @param pCodePostalFin codePostalFin value
     */
    public void setCodePostalFin(String pCodePostalFin) {
        this.codePostalFin = pCodePostalFin;
    }

    /**
     *
     * @return liensIntCpZd
     */
    public Set<LienIntCpZdDTO> getLiensIntCpZd() {
        return this.liensIntCpZd;
    }

    /**
     *
     * @param pLiensIntCpZd liensIntCpZd value
     */
    public void setLiensIntCpZd(Set<LienIntCpZdDTO> pLiensIntCpZd) {
        this.liensIntCpZd = pLiensIntCpZd;
    }

    /**
     *
     * @return usage
     */
    public String getUsage() {
        return this.usage;
    }

    /**
     *
     * @param pUsage usage value
     */
    public void setUsage(String pUsage) {
        this.usage = pUsage;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_IW0-MOHCEeS79pPzHY2rFw) ENABLED START*/
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
        buffer.append("codePostalDebut=").append(getCodePostalDebut());
        buffer.append(",");
        buffer.append("codePostalFin=").append(getCodePostalFin());
        buffer.append(",");
        buffer.append("codePays=").append(getCodePays());
        buffer.append(",");
        buffer.append("usage=").append(getUsage());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_IW0-MOHCEeS79pPzHY2rFw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
