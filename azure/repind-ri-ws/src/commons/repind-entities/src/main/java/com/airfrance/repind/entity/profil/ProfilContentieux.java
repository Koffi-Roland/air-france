package com.airfrance.repind.entity.profil;

/*PROTECTED REGION ID(_bZ7agEpKEeSzt4lojjJV5Q i) ENABLED START*/

// add not generated imports here

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : ProfilContentieux.java</p>
 * BO ProfilContentieux
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="PR_CONTENTIEUX")
public class ProfilContentieux implements Serializable {

/*PROTECTED REGION ID(serialUID _bZ7agEpKEeSzt4lojjJV5Q) ENABLED START*/
   /**
   * Determines if a de-serialized file is compatible with this class.
   *
   * Maintainers must change this value if and only if the new version
   * of this class is not compatible with old versions. See Sun docs
   * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization
   * /serialization/spec/class.html#4100> details. </a>
   *
   * Not necessary to include in first version of the class, but
   * included here as a reminder of its importance.
   */
    private static final long serialVersionUID = 1L;
/*PROTECTED REGION END*/

            
    /**
     * cle
     */
    @Id
    @Column(name="ICLE_PRF")
    private Integer cle;
        
            
    /**
     * dateDebutDefaut
     */
    @Column(name="DDATE_DEB_DEFAUT")
    private Date dateDebutDefaut;
        
            
    /**
     * dateFinDefaut
     */
    @Column(name="DDATE_FIN_DEFAUT")
    private Date dateFinDefaut;
        
            
    /**
     * dateRepriseEmission
     */
    @Column(name="DDATE_REPRISE_EMISSION")
    private Date dateRepriseEmission;
        
            
    /**
     * dateSuspenEmission
     */
    @Column(name="DDATE_SUSPEN_EMISSION")
    private Date dateSuspenEmission;
        
            
    /**
     * dateDebutRed
     */
    @Column(name="DDATE_DEB_RED")
    private Date dateDebutRed;
        
            
    /**
     * dateFinRed
     */
    @Column(name="DDATE_FIN_RED")
    private Date dateFinRed;
        
            
    /**
     * dateLiquidation
     */
    @Column(name="DDATE_LIQUIDATION")
    private Date dateLiquidation;
        
            
    /**
     * agrementAF
     */
    @Column(name="SAGREMENT_AF", length=1)
    private String agrementAF;
        
            
    /**
     * defautPaiement
     */
    @Column(name="SDEFAUT_PAIEMENT", length=1)
    private String defautPaiement;
        
            
    /**
     * miseEnCash
     */
    @Column(name="SMISE_EN_CASH", length=1)
    private String miseEnCash;
        
            
    /**
     * redressement
     */
    @Column(name="SREDRESSEMENT", length=1)
    private String redressement;
        
            
    /**
     * profil
     */
    // 1 <-> 1
    @OneToOne(mappedBy="profilContentieux")
    private Profil_mere profil;
        
    /*PROTECTED REGION ID(_bZ7agEpKEeSzt4lojjJV5Q u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public ProfilContentieux() {
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
    public ProfilContentieux(Integer pCle, Date pDateDebutDefaut, Date pDateFinDefaut, Date pDateRepriseEmission, Date pDateSuspenEmission, Date pDateDebutRed, Date pDateFinRed, Date pDateLiquidation, String pAgrementAF, String pDefautPaiement, String pMiseEnCash, String pRedressement) {
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
    public Profil_mere getProfil() {
        return this.profil;
    }

    /**
     *
     * @param pProfil profil value
     */
    public void setProfil(Profil_mere pProfil) {
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
        /*PROTECTED REGION ID(toString_bZ7agEpKEeSzt4lojjJV5Q) ENABLED START*/
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

     
    
    /*PROTECTED REGION ID(equals hash _bZ7agEpKEeSzt4lojjJV5Q) ENABLED START*/
    
    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        //TODO Customize here if necessary
        return hashCodeImpl();
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        //TODO Customize here if necessary
        return equalsImpl(obj);
    }
    
    /*PROTECTED REGION END*/
    
    /**
     * Generated implementation method for hashCode
     * You can stop calling it in the hashCode() generated in protected region if necessary
     * @return hashcode
     */
    private int hashCodeImpl() {
        return super.hashCode();
    }

    /**
     * Generated implementation method for equals
     * You can stop calling it in the equals() generated in protected region if necessary
     * @return if param equals the current object
     * @param obj Object to compare with current
     */
    private boolean equalsImpl(Object obj) {
        return super.equals(obj);
    }

    /*PROTECTED REGION ID(_bZ7agEpKEeSzt4lojjJV5Q u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
