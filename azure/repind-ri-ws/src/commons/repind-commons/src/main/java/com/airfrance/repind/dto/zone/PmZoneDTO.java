package com.airfrance.repind.dto.zone;

/*PROTECTED REGION ID(_NoNowLdgEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.dto.firme.PersonneMoraleDTO;

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : PmZoneDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class PmZoneDTO implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 2287497611383887962L;


	/**
     * cle
     */
    private Long cle;
        
        
    /**
     * lienPrivilegie
     */
    private String lienPrivilegie;
        
        
    /**
     * dateOuverture
     */
    private Date dateOuverture;
        
        
    /**
     * dateFermeture
     */
    private Date dateFermeture;
        
        
    /**
     * origine
     */
    private String origine;
        
        
    /**
     * dateModif
     */
    private Date dateModif;
        
        
    /**
     * signature
     */
    private String signature;
        
        
    /**
     * usage
     */
    private String usage;
        
        
    /**
     * personneMorale
     */
    private PersonneMoraleDTO personneMorale;
        
        
    /**
     * zoneDecoup
     */
    private ZoneDecoupDTO zoneDecoup;
        

    /*PROTECTED REGION ID(_NoNowLdgEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public PmZoneDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCle cle
     * @param pLienPrivilegie lienPrivilegie
     * @param pDateOuverture dateOuverture
     * @param pDateFermeture dateFermeture
     * @param pOrigine origine
     * @param pDateModif dateModif
     * @param pSignature signature
     * @param pUsage usage
     */
    public PmZoneDTO(Long pCle, String pLienPrivilegie, Date pDateOuverture, Date pDateFermeture, String pOrigine, Date pDateModif, String pSignature, String pUsage) {
        this.cle = pCle;
        this.lienPrivilegie = pLienPrivilegie;
        this.dateOuverture = pDateOuverture;
        this.dateFermeture = pDateFermeture;
        this.origine = pOrigine;
        this.dateModif = pDateModif;
        this.signature = pSignature;
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
     * @return dateFermeture
     */
    public Date getDateFermeture() {
        return this.dateFermeture;
    }

    /**
     *
     * @param pDateFermeture dateFermeture value
     */
    public void setDateFermeture(Date pDateFermeture) {
        this.dateFermeture = pDateFermeture;
    }

    /**
     *
     * @return dateModif
     */
    public Date getDateModif() {
        return this.dateModif;
    }

    /**
     *
     * @param pDateModif dateModif value
     */
    public void setDateModif(Date pDateModif) {
        this.dateModif = pDateModif;
    }

    /**
     *
     * @return dateOuverture
     */
    public Date getDateOuverture() {
        return this.dateOuverture;
    }

    /**
     *
     * @param pDateOuverture dateOuverture value
     */
    public void setDateOuverture(Date pDateOuverture) {
        this.dateOuverture = pDateOuverture;
    }

    /**
     *
     * @return lienPrivilegie
     */
    public String getLienPrivilegie() {
        return this.lienPrivilegie;
    }

    /**
     *
     * @param pLienPrivilegie lienPrivilegie value
     */
    public void setLienPrivilegie(String pLienPrivilegie) {
        this.lienPrivilegie = pLienPrivilegie;
    }

    /**
     *
     * @return origine
     */
    public String getOrigine() {
        return this.origine;
    }

    /**
     *
     * @param pOrigine origine value
     */
    public void setOrigine(String pOrigine) {
        this.origine = pOrigine;
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
     * @return zoneDecoup
     */
    public ZoneDecoupDTO getZoneDecoup() {
        return this.zoneDecoup;
    }

    /**
     *
     * @param pZoneDecoup zoneDecoup value
     */
    public void setZoneDecoup(ZoneDecoupDTO pZoneDecoup) {
        this.zoneDecoup = pZoneDecoup;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_NoNowLdgEeCrCZp8iGNNVw) ENABLED START*/
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
        buffer.append("lienPrivilegie=").append(getLienPrivilegie());
        buffer.append(",");
        buffer.append("dateOuverture=").append(getDateOuverture());
        buffer.append(",");
        buffer.append("dateFermeture=").append(getDateFermeture());
        buffer.append(",");
        buffer.append("origine=").append(getOrigine());
        buffer.append(",");
        buffer.append("dateModif=").append(getDateModif());
        buffer.append(",");
        buffer.append("signature=").append(getSignature());
        buffer.append(",");
        buffer.append("usage=").append(getUsage());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_NoNowLdgEeCrCZp8iGNNVw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
