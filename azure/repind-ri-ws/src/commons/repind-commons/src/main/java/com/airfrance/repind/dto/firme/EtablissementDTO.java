package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_YbWYALdcEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;

/*PROTECTED REGION END*/

/**
 * <p>Title : EtablissementDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class EtablissementDTO extends PersonneMoraleDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -90721573801309241L;


	/**
     * type
     */
    private String type;
        
        
    /**
     * ginAgence
     */
    private String ginAgence;
        
        
    /**
     * siret
     */
    private String siret;
        
        
    /**
     * siegeSocial
     */
    private String siegeSocial;
        
        
    /**
     * ce
     */
    private String ce;
        
        
    /**
     * rem
     */
    private String rem;
        

    /*PROTECTED REGION ID(_YbWYALdcEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public EtablissementDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pType type
     * @param pGinAgence ginAgence
     * @param pSiret siret
     * @param pSiegeSocial siegeSocial
     * @param pCe ce
     * @param pRem rem
     */
    public EtablissementDTO(String pType, String pGinAgence, String pSiret, String pSiegeSocial, String pCe, String pRem) {
        this.type = pType;
        this.ginAgence = pGinAgence;
        this.siret = pSiret;
        this.siegeSocial = pSiegeSocial;
        this.ce = pCe;
        this.rem = pRem;
    }

    /**
     *
     * @return ce
     */
    public String getCe() {
        return this.ce;
    }

    /**
     *
     * @param pCe ce value
     */
    public void setCe(String pCe) {
        this.ce = pCe;
    }

    /**
     *
     * @return ginAgence
     */
    public String getGinAgence() {
        return this.ginAgence;
    }

    /**
     *
     * @param pGinAgence ginAgence value
     */
    public void setGinAgence(String pGinAgence) {
        this.ginAgence = pGinAgence;
    }

    /**
     *
     * @return rem
     */
    public String getRem() {
        return this.rem;
    }

    /**
     *
     * @param pRem rem value
     */
    public void setRem(String pRem) {
        this.rem = pRem;
    }

    /**
     *
     * @return siegeSocial
     */
    public String getSiegeSocial() {
        return this.siegeSocial;
    }

    /**
     *
     * @param pSiegeSocial siegeSocial value
     */
    public void setSiegeSocial(String pSiegeSocial) {
        this.siegeSocial = pSiegeSocial;
    }

    /**
     *
     * @return siret
     */
    public String getSiret() {
        return this.siret;
    }

    /**
     *
     * @param pSiret siret value
     */
    public void setSiret(String pSiret) {
        this.siret = pSiret;
    }

    /**
     *
     * @return type
     */
    public String getType() {
        return this.type;
    }

    /**
     *
     * @param pType type value
     */
    public void setType(String pType) {
        this.type = pType;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_YbWYALdcEeCrCZp8iGNNVw) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }

    /**
     *
     * @return object as string
     */
    @Override
    public String toStringImpl() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@").append(Integer.toHexString(System.identityHashCode(this)));
        buffer.append("[");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("ginAgence=").append(getGinAgence());
        buffer.append(",");
        buffer.append("siret=").append(getSiret());
        buffer.append(",");
        buffer.append("siegeSocial=").append(getSiegeSocial());
        buffer.append(",");
        buffer.append("ce=").append(getCe());
        buffer.append(",");
        buffer.append("rem=").append(getRem());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_YbWYALdcEeCrCZp8iGNNVw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
