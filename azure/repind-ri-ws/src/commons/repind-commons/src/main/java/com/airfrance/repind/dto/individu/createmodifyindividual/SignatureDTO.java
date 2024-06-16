package com.airfrance.repind.dto.individu.createmodifyindividual;

/*PROTECTED REGION ID(_nV_MnTRkEeCc7ZsKsK1lbQ i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : SignatureDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class SignatureDTO  {
        
    /**
     * typeSignature
     */
    private String typeSignature;
        
        
    /**
     * site
     */
    private String site;
        
        
    /**
     * signature
     */
    private String signature;
        
        
    /**
     * date
     */
    private String date;
        
        
    /**
     * heure
     */
    private String heure;
        

    /*PROTECTED REGION ID(_nV_MnTRkEeCc7ZsKsK1lbQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public SignatureDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pTypeSignature typeSignature
     * @param pSite site
     * @param pSignature signature
     * @param pDate date
     * @param pHeure heure
     */
    public SignatureDTO(String pTypeSignature, String pSite, String pSignature, String pDate, String pHeure) {
        this.typeSignature = pTypeSignature;
        this.site = pSite;
        this.signature = pSignature;
        this.date = pDate;
        this.heure = pHeure;
    }

    /**
     *
     * @return date
     */
    public String getDate() {
        return this.date;
    }

    /**
     *
     * @param pDate date value
     */
    public void setDate(String pDate) {
        this.date = pDate;
    }

    /**
     *
     * @return heure
     */
    public String getHeure() {
        return this.heure;
    }

    /**
     *
     * @param pHeure heure value
     */
    public void setHeure(String pHeure) {
        this.heure = pHeure;
    }

    /**
     *
     * @return signature
     */
    public String getSignature() {
        return this.signature;
    }

    /**
     *
     * @param pSignature signature value
     */
    public void setSignature(String pSignature) {
        this.signature = pSignature;
    }

    /**
     *
     * @return site
     */
    public String getSite() {
        return this.site;
    }

    /**
     *
     * @param pSite site value
     */
    public void setSite(String pSite) {
        this.site = pSite;
    }

    /**
     *
     * @return typeSignature
     */
    public String getTypeSignature() {
        return this.typeSignature;
    }

    /**
     *
     * @param pTypeSignature typeSignature value
     */
    public void setTypeSignature(String pTypeSignature) {
        this.typeSignature = pTypeSignature;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_nV_MnTRkEeCc7ZsKsK1lbQ) ENABLED START*/
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
            .append("typeSignature", getTypeSignature())
            .append("site", getSite())
            .append("signature", getSignature())
            .append("date", getDate())
            .append("heure", getHeure())
            .toString();
    }

    /*PROTECTED REGION ID(_nV_MnTRkEeCc7ZsKsK1lbQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
