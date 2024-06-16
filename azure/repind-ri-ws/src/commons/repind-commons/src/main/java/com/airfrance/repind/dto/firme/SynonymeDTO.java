package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_eCK2kLdcEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : SynonymeDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class SynonymeDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 189018036831298726L;


	/**
     * cle
     */
    private Long cle;
        
        
    /**
     * statut
     */
    private String statut;
        
        
    /**
     * nom
     */
    private String nom;
        
        
    /**
     * type
     */
    private String type;
        
        
    /**
     * dateModificationSnom
     */
    private Date dateModificationSnom;
        
        
    /**
     * personneMorale
     */
    private PersonneMoraleDTO personneMorale;
        

    /*PROTECTED REGION ID(_eCK2kLdcEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public SynonymeDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCle cle
     * @param pStatut statut
     * @param pNom nom
     * @param pType type
     * @param pDateModificationSnom dateModificationSnom
     */
    public SynonymeDTO(Long pCle, String pStatut, String pNom, String pType, Date pDateModificationSnom) {
        this.cle = pCle;
        this.statut = pStatut;
        this.nom = pNom;
        this.type = pType;
        this.dateModificationSnom = pDateModificationSnom;
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
     * @return dateModificationSnom
     */
    public Date getDateModificationSnom() {
        return this.dateModificationSnom;
    }

    /**
     *
     * @param pDateModificationSnom dateModificationSnom value
     */
    public void setDateModificationSnom(Date pDateModificationSnom) {
        this.dateModificationSnom = pDateModificationSnom;
    }

    /**
     *
     * @return nom
     */
    public String getNom() {
        return this.nom;
    }

    /**
     *
     * @param pNom nom value
     */
    public void setNom(String pNom) {
        this.nom = pNom;
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
        /*PROTECTED REGION ID(toString_eCK2kLdcEeCrCZp8iGNNVw) ENABLED START*/
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
        buffer.append("statut=").append(getStatut());
        buffer.append(",");
        buffer.append("nom=").append(getNom());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("dateModificationSnom=").append(getDateModificationSnom());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_eCK2kLdcEeCrCZp8iGNNVw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
