package com.airfrance.repind.dto.agence;

/*PROTECTED REGION ID(_0VRuCWk1EeGhB9497mGnHw i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.dto.reference.ReseauDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : MembreReseauDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class MembreReseauDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 3510640473296457821L;


	/**
     * cle
     */
    private Integer cle;
        
        
    /**
     * dateDebut
     */
    private Date dateDebut;
        
        
    /**
     * dateFin
     */
    private Date dateFin;
        
        
    /**
     * agence
     */
    private AgenceDTO agence;
        
        
    /**
     * reseau
     */
    private ReseauDTO reseau;
        

    /*PROTECTED REGION ID(_0VRuCWk1EeGhB9497mGnHw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public MembreReseauDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pDateDebut dateDebut
     * @param pDateFin dateFin
     * @param pCle cle
     */
    public MembreReseauDTO(Date pDateDebut, Date pDateFin, Integer pCle) {
        this.dateDebut = pDateDebut;
        this.dateFin = pDateFin;
        this.cle = pCle;
    }

    /**
     *
     * @return agence
     */
    public AgenceDTO getAgence() {
        return this.agence;
    }

    /**
     *
     * @param pAgence agence value
     */
    public void setAgence(AgenceDTO pAgence) {
        this.agence = pAgence;
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
     * @return dateDebut
     */
    public Date getDateDebut() {
        return this.dateDebut;
    }

    /**
     *
     * @param pDateDebut dateDebut value
     */
    public void setDateDebut(Date pDateDebut) {
        this.dateDebut = pDateDebut;
    }

    /**
     *
     * @return dateFin
     */
    public Date getDateFin() {
        return this.dateFin;
    }

    /**
     *
     * @param pDateFin dateFin value
     */
    public void setDateFin(Date pDateFin) {
        this.dateFin = pDateFin;
    }

    /**
     *
     * @return reseau
     */
    public ReseauDTO getReseau() {
        return this.reseau;
    }

    /**
     *
     * @param pReseau reseau value
     */
    public void setReseau(ReseauDTO pReseau) {
        this.reseau = pReseau;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_0VRuCWk1EeGhB9497mGnHw) ENABLED START*/
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
        buffer.append("dateDebut=").append(getDateDebut());
        buffer.append(",");
        buffer.append("dateFin=").append(getDateFin());
        buffer.append(",");
        buffer.append("cle=").append(getCle());
        buffer.append("]");
        return buffer.toString();
    }

    
    /**
     * @return true if all fields are null or empty
     */
    public boolean isEmpty() {
    	if (cle == null
    			&& dateDebut == null
    			&& dateFin == null
    			&& agence == null
    			&& (reseau == null || reseau.isEmpty())) {
    		return true;
    	} else {
        	return false;    		
    	}
    }
    
    
    /**
     * @return true if all fields are null or empty
     */
    public static boolean areEmpty(Set<MembreReseauDTO> mrSet) {
    	if (mrSet != null) {
    		for (MembreReseauDTO mrDTO : mrSet) {
    			if (!mrDTO.isEmpty()) {
    				return false;
    			}
    		}
    		return true;
    	} else {
    		return true;
    	}
    }

    public boolean isActive() {
        Date dateNow = new Date();
        return (getDateFin()==null || ((dateNow.after(getDateDebut()) || dateNow.equals(getDateDebut())) && (dateNow.before(getDateFin()) || dateNow.equals(getDateFin()))));
    }
}
