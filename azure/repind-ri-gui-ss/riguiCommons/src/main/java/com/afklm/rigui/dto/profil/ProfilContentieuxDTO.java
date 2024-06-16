package com.afklm.rigui.dto.profil;

/*PROTECTED REGION ID(_Sck_kEr7EeSzt4lojjJV5Q i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : ProfilContentieuxDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ProfilContentieuxDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -7083015588364120320L;


	/**
     * cle
     */
    private Integer cle;
        
        
    /**
     * dateDebutDefaut
     */
    private Date dateDebutDefaut;
        
        
    /**
     * dateFinDefaut
     */
    private Date dateFinDefaut;
        
        
    /**
     * dateRepriseEmission
     */
    private Date dateRepriseEmission;
        
        
    /**
     * dateSuspenEmission
     */
    private Date dateSuspenEmission;
        
        
    /**
     * dateDebutRed
     */
    private Date dateDebutRed;
        
        
    /**
     * dateFinRed
     */
    private Date dateFinRed;
        
        
    /**
     * dateLiquidation
     */
    private Date dateLiquidation;
        
        
    /**
     * agrementAF
     */
    private String agrementAF;
        
        
    /**
     * defautPaiement
     */
    private String defautPaiement;
        
        
    /**
     * miseEnCash
     */
    private String miseEnCash;
        
        
    /**
     * redressement
     */
    private String redressement;
        
        
    /**
     * profil
     */
    private Profil_mereDTO profil;
        

    /*PROTECTED REGION ID(_Sck_kEr7EeSzt4lojjJV5Q u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ProfilContentieuxDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCle cle
     * @param pDateDebutDefaut dateDebutDefaut
     * @param pDateFinDefaut dateFinDefaut
     * @param pDateRepriseEmission dateRepriseEmission
     * @param pDateSuspenEmission dateSuspenEmission
     * @param pDateDebutRed dateDebutRed
     * @param pDateFinRed dateFinRed
     * @param pDateLiquidation dateLiquidation
     * @param pAgrementAF agrementAF
     * @param pDefautPaiement defautPaiement
     * @param pMiseEnCash miseEnCash
     * @param pRedressement redressement
     */
    public ProfilContentieuxDTO(Integer pCle, Date pDateDebutDefaut, Date pDateFinDefaut, Date pDateRepriseEmission, Date pDateSuspenEmission, Date pDateDebutRed, Date pDateFinRed, Date pDateLiquidation, String pAgrementAF, String pDefautPaiement, String pMiseEnCash, String pRedressement) {
        this.cle = pCle;
        this.dateDebutDefaut = pDateDebutDefaut;
        this.dateFinDefaut = pDateFinDefaut;
        this.dateRepriseEmission = pDateRepriseEmission;
        this.dateSuspenEmission = pDateSuspenEmission;
        this.dateDebutRed = pDateDebutRed;
        this.dateFinRed = pDateFinRed;
        this.dateLiquidation = pDateLiquidation;
        this.agrementAF = pAgrementAF;
        this.defautPaiement = pDefautPaiement;
        this.miseEnCash = pMiseEnCash;
        this.redressement = pRedressement;
    }

    /**
     *
     * @return agrementAF
     */
    public String getAgrementAF() {
        return this.agrementAF;
    }

    /**
     *
     * @param pAgrementAF agrementAF value
     */
    public void setAgrementAF(String pAgrementAF) {
        this.agrementAF = pAgrementAF;
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
     * @return dateDebutDefaut
     */
    public Date getDateDebutDefaut() {
        return this.dateDebutDefaut;
    }

    /**
     *
     * @param pDateDebutDefaut dateDebutDefaut value
     */
    public void setDateDebutDefaut(Date pDateDebutDefaut) {
        this.dateDebutDefaut = pDateDebutDefaut;
    }

    /**
     *
     * @return dateDebutRed
     */
    public Date getDateDebutRed() {
        return this.dateDebutRed;
    }

    /**
     *
     * @param pDateDebutRed dateDebutRed value
     */
    public void setDateDebutRed(Date pDateDebutRed) {
        this.dateDebutRed = pDateDebutRed;
    }

    /**
     *
     * @return dateFinDefaut
     */
    public Date getDateFinDefaut() {
        return this.dateFinDefaut;
    }

    /**
     *
     * @param pDateFinDefaut dateFinDefaut value
     */
    public void setDateFinDefaut(Date pDateFinDefaut) {
        this.dateFinDefaut = pDateFinDefaut;
    }

    /**
     *
     * @return dateFinRed
     */
    public Date getDateFinRed() {
        return this.dateFinRed;
    }

    /**
     *
     * @param pDateFinRed dateFinRed value
     */
    public void setDateFinRed(Date pDateFinRed) {
        this.dateFinRed = pDateFinRed;
    }

    /**
     *
     * @return dateLiquidation
     */
    public Date getDateLiquidation() {
        return this.dateLiquidation;
    }

    /**
     *
     * @param pDateLiquidation dateLiquidation value
     */
    public void setDateLiquidation(Date pDateLiquidation) {
        this.dateLiquidation = pDateLiquidation;
    }

    /**
     *
     * @return dateRepriseEmission
     */
    public Date getDateRepriseEmission() {
        return this.dateRepriseEmission;
    }

    /**
     *
     * @param pDateRepriseEmission dateRepriseEmission value
     */
    public void setDateRepriseEmission(Date pDateRepriseEmission) {
        this.dateRepriseEmission = pDateRepriseEmission;
    }

    /**
     *
     * @return dateSuspenEmission
     */
    public Date getDateSuspenEmission() {
        return this.dateSuspenEmission;
    }

    /**
     *
     * @param pDateSuspenEmission dateSuspenEmission value
     */
    public void setDateSuspenEmission(Date pDateSuspenEmission) {
        this.dateSuspenEmission = pDateSuspenEmission;
    }

    /**
     *
     * @return defautPaiement
     */
    public String getDefautPaiement() {
        return this.defautPaiement;
    }

    /**
     *
     * @param pDefautPaiement defautPaiement value
     */
    public void setDefautPaiement(String pDefautPaiement) {
        this.defautPaiement = pDefautPaiement;
    }

    /**
     *
     * @return miseEnCash
     */
    public String getMiseEnCash() {
        return this.miseEnCash;
    }

    /**
     *
     * @param pMiseEnCash miseEnCash value
     */
    public void setMiseEnCash(String pMiseEnCash) {
        this.miseEnCash = pMiseEnCash;
    }

    /**
     *
     * @return profil
     */
    public Profil_mereDTO getProfil() {
        return this.profil;
    }

    /**
     *
     * @param pProfil profil value
     */
    public void setProfil(Profil_mereDTO pProfil) {
        this.profil = pProfil;
    }

    /**
     *
     * @return redressement
     */
    public String getRedressement() {
        return this.redressement;
    }

    /**
     *
     * @param pRedressement redressement value
     */
    public void setRedressement(String pRedressement) {
        this.redressement = pRedressement;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_Sck_kEr7EeSzt4lojjJV5Q) ENABLED START*/
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
        buffer.append("dateDebutDefaut=").append(getDateDebutDefaut());
        buffer.append(",");
        buffer.append("dateFinDefaut=").append(getDateFinDefaut());
        buffer.append(",");
        buffer.append("dateRepriseEmission=").append(getDateRepriseEmission());
        buffer.append(",");
        buffer.append("dateSuspenEmission=").append(getDateSuspenEmission());
        buffer.append(",");
        buffer.append("dateDebutRed=").append(getDateDebutRed());
        buffer.append(",");
        buffer.append("dateFinRed=").append(getDateFinRed());
        buffer.append(",");
        buffer.append("dateLiquidation=").append(getDateLiquidation());
        buffer.append(",");
        buffer.append("agrementAF=").append(getAgrementAF());
        buffer.append(",");
        buffer.append("defautPaiement=").append(getDefautPaiement());
        buffer.append(",");
        buffer.append("miseEnCash=").append(getMiseEnCash());
        buffer.append(",");
        buffer.append("redressement=").append(getRedressement());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_Sck_kEr7EeSzt4lojjJV5Q u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
