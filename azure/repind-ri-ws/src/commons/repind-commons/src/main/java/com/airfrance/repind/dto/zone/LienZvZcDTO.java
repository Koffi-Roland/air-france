package com.airfrance.repind.dto.zone;

/*PROTECTED REGION ID(_R-VBwEfkEeSjFN6DwEJjiw i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : LienZvZcDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class LienZvZcDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -3603682538021283232L;


	/**
     * cle
     */
    private Integer cle;
        
        
    /**
     * type
     */
    private String type;
        
        
    /**
     * dateOuverture
     */
    private Date dateOuverture;
        
        
    /**
     * dateFermeture
     */
    private Date dateFermeture;
        
        
    /**
     * zoneComm
     */
    private ZoneCommDTO zoneComm;
        
        
    /**
     * zoneVente
     */
    private ZoneVenteDTO zoneVente;
        

    /*PROTECTED REGION ID(_R-VBwEfkEeSjFN6DwEJjiw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public LienZvZcDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCle cle
     * @param pType type
     * @param pDateOuverture dateOuverture
     * @param pDateFermeture dateFermeture
     */
    public LienZvZcDTO(Integer pCle, String pType, Date pDateOuverture, Date pDateFermeture) {
        this.cle = pCle;
        this.type = pType;
        this.dateOuverture = pDateOuverture;
        this.dateFermeture = pDateFermeture;
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
     * @return zoneComm
     */
    public ZoneCommDTO getZoneComm() {
        return this.zoneComm;
    }

    /**
     *
     * @param pZoneComm zoneComm value
     */
    public void setZoneComm(ZoneCommDTO pZoneComm) {
        this.zoneComm = pZoneComm;
    }

    /**
     *
     * @return zoneVente
     */
    public ZoneVenteDTO getZoneVente() {
        return this.zoneVente;
    }

    /**
     *
     * @param pZoneVente zoneVente value
     */
    public void setZoneVente(ZoneVenteDTO pZoneVente) {
        this.zoneVente = pZoneVente;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_R-VBwEfkEeSjFN6DwEJjiw) ENABLED START*/
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
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("dateOuverture=").append(getDateOuverture());
        buffer.append(",");
        buffer.append("dateFermeture=").append(getDateFermeture());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_R-VBwEfkEeSjFN6DwEJjiw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
