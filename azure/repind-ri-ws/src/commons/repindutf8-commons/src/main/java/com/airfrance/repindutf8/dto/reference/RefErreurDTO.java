package com.airfrance.repindutf8.dto.reference;

/*PROTECTED REGION ID(_Ackc8IJTEeKhdftDNws56g i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefErreurDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class RefErreurDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -7340445748801583631L;


	/**
     * refErreurId
     */
    private Long refErreurId;
        
        
    /**
     * scode
     */
    private String scode;
        
        
    /**
     * libelle
     */
    private String libelle;
        
        
    /**
     * libelleEn
     */
    private String libelleEn;
        

    /*PROTECTED REGION ID(_Ackc8IJTEeKhdftDNws56g u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public RefErreurDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pRefErreurId refErreurId
     * @param pScode scode
     * @param pLibelle libelle
     * @param pLibelleEn libelleEn
     */
    public RefErreurDTO(Long pRefErreurId, String pScode, String pLibelle, String pLibelleEn) {
        this.refErreurId = pRefErreurId;
        this.scode = pScode;
        this.libelle = pLibelle;
        this.libelleEn = pLibelleEn;
    }

    /**
     *
     * @return libelle
     */
    public String getLibelle() {
        return this.libelle;
    }

    /**
     *
     * @param pLibelle libelle value
     */
    public void setLibelle(String pLibelle) {
        this.libelle = pLibelle;
    }

    /**
     *
     * @return libelleEn
     */
    public String getLibelleEn() {
        return this.libelleEn;
    }

    /**
     *
     * @param pLibelleEn libelleEn value
     */
    public void setLibelleEn(String pLibelleEn) {
        this.libelleEn = pLibelleEn;
    }

    /**
     *
     * @return refErreurId
     */
    public Long getRefErreurId() {
        return this.refErreurId;
    }

    /**
     *
     * @param pRefErreurId refErreurId value
     */
    public void setRefErreurId(Long pRefErreurId) {
        this.refErreurId = pRefErreurId;
    }

    /**
     *
     * @return scode
     */
    public String getScode() {
        return this.scode;
    }

    /**
     *
     * @param pScode scode value
     */
    public void setScode(String pScode) {
        this.scode = pScode;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_Ackc8IJTEeKhdftDNws56g) ENABLED START*/
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
        buffer.append("refErreurId=").append(getRefErreurId());
        buffer.append(",");
        buffer.append("scode=").append(getScode());
        buffer.append(",");
        buffer.append("libelle=").append(getLibelle());
        buffer.append(",");
        buffer.append("libelleEn=").append(getLibelleEn());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_Ackc8IJTEeKhdftDNws56g u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
