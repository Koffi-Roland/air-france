package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_bu8PsLdcEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : NumeroIdentDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class NumeroIdentDTO implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -7652961225463937451L;


	/**
     * key
     */
    private Long key;
        
        
    /**
     * statut
     */
    private String statut;
        
        
    /**
     * numero
     */
    private String numero;
        
        
    /**
     * type
     */
    private String type;
        
        
    /**
     * libelle
     */
    private String libelle;
        
        
    /**
     * dateOuverture
     */
    private Date dateOuverture;
        
        
    /**
     * dateFermeture
     */
    private Date dateFermeture;
        
        
    /**
     * dateModification
     */
    private Date dateModification;
        
        
    /**
     * personneMorale
     */
    private PersonneMoraleDTO personneMorale;
        

    /*PROTECTED REGION ID(_bu8PsLdcEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public NumeroIdentDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pKey key
     * @param pStatut statut
     * @param pNumero numero
     * @param pType type
     * @param pLibelle libelle
     * @param pDateOuverture dateOuverture
     * @param pDateFermeture dateFermeture
     * @param pDateModification dateModification
     */
    public NumeroIdentDTO(Long pKey, String pStatut, String pNumero, String pType, String pLibelle, Date pDateOuverture, Date pDateFermeture, Date pDateModification) {
        this.key = pKey;
        this.statut = pStatut;
        this.numero = pNumero;
        this.type = pType;
        this.libelle = pLibelle;
        this.dateOuverture = pDateOuverture;
        this.dateFermeture = pDateFermeture;
        this.dateModification = pDateModification;
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
     * @return dateModification
     */
    public Date getDateModification() {
        return this.dateModification;
    }

    /**
     *
     * @param pDateModification dateModification value
     */
    public void setDateModification(Date pDateModification) {
        this.dateModification = pDateModification;
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
     * @return key
     */
    public Long getKey() {
        return this.key;
    }

    /**
     *
     * @param pKey key value
     */
    public void setKey(Long pKey) {
        this.key = pKey;
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
     * @return numero
     */
    public String getNumero() {
        return this.numero;
    }

    /**
     *
     * @param pNumero numero value
     */
    public void setNumero(String pNumero) {
        this.numero = pNumero;
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
     * @return statut
     */
    public String getStatut() {
        return this.statut;
    }

    /**
     *
     * @param pStatut statut value
     */
    public void setStatut(String pStatut) {
        this.statut = pStatut;
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
        /*PROTECTED REGION ID(toString_bu8PsLdcEeCrCZp8iGNNVw) ENABLED START*/
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
        buffer.append("key=").append(getKey());
        buffer.append(",");
        buffer.append("statut=").append(getStatut());
        buffer.append(",");
        buffer.append("numero=").append(getNumero());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("libelle=").append(getLibelle());
        buffer.append(",");
        buffer.append("dateOuverture=").append(getDateOuverture());
        buffer.append(",");
        buffer.append("dateFermeture=").append(getDateFermeture());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append("]");
        return buffer.toString();
    }
    
    /*PROTECTED REGION ID(_bu8PsLdcEeCrCZp8iGNNVw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
