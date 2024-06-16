package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_c5msALdcEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : SegmentationDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class SegmentationDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -1860863860027229639L;


	/**
     * cle
     */
    private Integer cle;
        
        
    /**
     * type
     */
    private String type;
        
        
    /**
     * niveau
     */
    private String niveau;
        
        
    /**
     * dateEntree
     */
    private Date dateEntree;
        
        
    /**
     * dateSortie
     */
    private Date dateSortie;
        
        
    /**
     * potentiel
     */
    private String potentiel;
        
        
    /**
     * montant
     */
    private Integer montant;
        
        
    /**
     * monnaie
     */
    private String monnaie;
        
        
    /**
     * politiqueVoyage
     */
    private String politiqueVoyage;
        
        
    /**
     * personneMorale
     */
    private PersonneMoraleDTO personneMorale;
        

    /*PROTECTED REGION ID(_c5msALdcEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public SegmentationDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCle cle
     * @param pType type
     * @param pNiveau niveau
     * @param pDateEntree dateEntree
     * @param pDateSortie dateSortie
     * @param pPotentiel potentiel
     * @param pMontant montant
     * @param pMonnaie monnaie
     * @param pPolitiqueVoyage politiqueVoyage
     */
    public SegmentationDTO(Integer pCle, String pType, String pNiveau, Date pDateEntree, Date pDateSortie, String pPotentiel, Integer pMontant, String pMonnaie, String pPolitiqueVoyage) {
        this.cle = pCle;
        this.type = pType;
        this.niveau = pNiveau;
        this.dateEntree = pDateEntree;
        this.dateSortie = pDateSortie;
        this.potentiel = pPotentiel;
        this.montant = pMontant;
        this.monnaie = pMonnaie;
        this.politiqueVoyage = pPolitiqueVoyage;
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
     * @return dateEntree
     */
    public Date getDateEntree() {
        return this.dateEntree;
    }

    /**
     *
     * @param pDateEntree dateEntree value
     */
    public void setDateEntree(Date pDateEntree) {
        this.dateEntree = pDateEntree;
    }

    /**
     *
     * @return dateSortie
     */
    public Date getDateSortie() {
        return this.dateSortie;
    }

    /**
     *
     * @param pDateSortie dateSortie value
     */
    public void setDateSortie(Date pDateSortie) {
        this.dateSortie = pDateSortie;
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
    public Integer getMontant() {
        return this.montant;
    }

    /**
     *
     * @param pMontant montant value
     */
    public void setMontant(Integer pMontant) {
        this.montant = pMontant;
    }

    /**
     *
     * @return niveau
     */
    public String getNiveau() {
        return this.niveau;
    }

    /**
     *
     * @param pNiveau niveau value
     */
    public void setNiveau(String pNiveau) {
        this.niveau = pNiveau;
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
     * @return politiqueVoyage
     */
    public String getPolitiqueVoyage() {
        return this.politiqueVoyage;
    }

    /**
     *
     * @param pPolitiqueVoyage politiqueVoyage value
     */
    public void setPolitiqueVoyage(String pPolitiqueVoyage) {
        this.politiqueVoyage = pPolitiqueVoyage;
    }

    /**
     *
     * @return potentiel
     */
    public String getPotentiel() {
        return this.potentiel;
    }

    /**
     *
     * @param pPotentiel potentiel value
     */
    public void setPotentiel(String pPotentiel) {
        this.potentiel = pPotentiel;
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
        /*PROTECTED REGION ID(toString_c5msALdcEeCrCZp8iGNNVw) ENABLED START*/
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
        buffer.append("niveau=").append(getNiveau());
        buffer.append(",");
        buffer.append("dateEntree=").append(getDateEntree());
        buffer.append(",");
        buffer.append("dateSortie=").append(getDateSortie());
        buffer.append(",");
        buffer.append("potentiel=").append(getPotentiel());
        buffer.append(",");
        buffer.append("montant=").append(getMontant());
        buffer.append(",");
        buffer.append("monnaie=").append(getMonnaie());
        buffer.append(",");
        buffer.append("politiqueVoyage=").append(getPolitiqueVoyage());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_c5msALdcEeCrCZp8iGNNVw u m) ENABLED START*/
	public Boolean isActive() throws ParseException {
		Date now = new Date();
		if (((now.after(this.getDateEntree()) || now.equals(this.getDateEntree()))
				&& (this.getDateSortie() == null
						|| this.getDateSortie().equals(new SimpleDateFormat("ddMMyyyy").parse("02011901")))
				|| ((now.after(this.getDateEntree()) || now.equals(this.getDateEntree()))
						&& (this.getDateSortie() != null
								&& (now.before(this.getDateSortie()) || now.equals(this.getDateSortie())))))) {
			return true;
		} else {
			return false;
		}
	}
    /*PROTECTED REGION END*/

}
