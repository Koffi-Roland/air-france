package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_V7UEMLdcEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : ChiffreDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ChiffreDTO implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -5135885566155681856L;


	/**
     * key
     */
    private Integer key;
        
        
    /**
     * type
     */
    private String type;
        
        
    /**
     * statut
     */
    private String statut;
        
        
    /**
     * montant
     */
    private BigInteger montant;
        
        
    /**
     * monnaie
     */
    private String monnaie;
        
        
    /**
     * libelle
     */
    private String libelle;
        
        
    /**
     * dateFin
     */
    private Date dateFin;
        
        
    /**
     * dateDebut
     */
    private Date dateDebut;
        
        
    /**
     * dateMaj
     */
    private Date dateMaj;
        
        
    /**
     * personneMorale
     */
    private PersonneMoraleDTO personneMorale;
        

    /*PROTECTED REGION ID(_V7UEMLdcEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ChiffreDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pKey key
     * @param pType type
     * @param pStatut statut
     * @param pMontant montant
     * @param pMonnaie monnaie
     * @param pLibelle libelle
     * @param pDateFin dateFin
     * @param pDateDebut dateDebut
     * @param pDateMaj dateMaj
     */
    public ChiffreDTO(Integer pKey, String pType, String pStatut, BigInteger pMontant, String pMonnaie, String pLibelle, Date pDateFin, Date pDateDebut, Date pDateMaj) {
        this.key = pKey;
        this.type = pType;
        this.statut = pStatut;
        this.montant = pMontant;
        this.monnaie = pMonnaie;
        this.libelle = pLibelle;
        this.dateFin = pDateFin;
        this.dateDebut = pDateDebut;
        this.dateMaj = pDateMaj;
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
     * @return dateMaj
     */
    public Date getDateMaj() {
        return this.dateMaj;
    }

    /**
     *
     * @param pDateMaj dateMaj value
     */
    public void setDateMaj(Date pDateMaj) {
        this.dateMaj = pDateMaj;
    }

    /**
     *
     * @return key
     */
    public Integer getKey() {
        return this.key;
    }

    /**
     *
     * @param pKey key value
     */
    public void setKey(Integer pKey) {
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
     * @return monnaie
     */
    public String getMonnaie() {
        return this.monnaie;
    }

    /**
     *
     * @param pMonnaie monnaie value
     */
    public void setMonnaie(String pMonnaie) {
        this.monnaie = pMonnaie;
    }

    /**
     *
     * @return montant
     */
    public BigInteger getMontant() {
        return this.montant;
    }

    /**
     *
     * @param pMontant montant value
     */
    public void setMontant(BigInteger pMontant) {
        this.montant = pMontant;
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
        /*PROTECTED REGION ID(toString_V7UEMLdcEeCrCZp8iGNNVw) ENABLED START*/
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
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("statut=").append(getStatut());
        buffer.append(",");
        buffer.append("montant=").append(getMontant());
        buffer.append(",");
        buffer.append("monnaie=").append(getMonnaie());
        buffer.append(",");
        buffer.append("libelle=").append(getLibelle());
        buffer.append(",");
        buffer.append("dateFin=").append(getDateFin());
        buffer.append(",");
        buffer.append("dateDebut=").append(getDateDebut());
        buffer.append(",");
        buffer.append("dateMaj=").append(getDateMaj());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_V7UEMLdcEeCrCZp8iGNNVw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
