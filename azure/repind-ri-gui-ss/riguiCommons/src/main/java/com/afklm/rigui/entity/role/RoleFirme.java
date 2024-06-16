package com.afklm.rigui.entity.role;

/*PROTECTED REGION ID(_MmFEULbGEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : RoleFirme.java</p>
 * BO RoleFirme
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="ROLE_FIRME")
public class RoleFirme implements Serializable {

    /*PROTECTED REGION ID(serialUID _MmFEULbGEeCrCZp8iGNNVw) ENABLED START*/
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
    @GenericGenerator(name="ISEQ_ROLE_FIRME", strategy = "com.afklm.rigui.util.StringSequenceGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "ISEQ_ROLE_FIRME")
            })
    @GeneratedValue(generator = "ISEQ_ROLE_FIRME")
    @Column(name="ICLE", nullable=false)
    private String cle;


    /**
     * gin
     */
    @Column(name="SGIN", length=12, updatable=false)
    private String gin;


    /**
     * version
     */
    @Version
    @Column(name="IVERSION")
    private Integer version;


    /**
     * type
     */
    @Column(name="STYPE", length=2)
    private String type;


    /**
     * sousType
     */
    @Column(name="SSOUS_TYPE", length=2)
    private String sousType;


    /**
     * mc
     */
    @Column(name="SMC", length=11)
    private String mc;


    /**
     * numero
     */
    @Column(name="SNUMERO", length=20)
    private String numero;


    /**
     * etat
     */
    @Column(name="SETAT", length=1)
    private String etat;


    /**
     * debutValidite
     */
    @Column(name="DDEBUT_VALIDITE")
    private Date debutValidite;


    /**
     * finValidite
     */
    @Column(name="DFIN_VALIDITE")
    private Date finValidite;


    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION")
    private Date dateCreation;


    /**
     * signatureCreation
     */
    @Column(name="SSIGNATURE_CREATION", length=16)
    private String signatureCreation;


    /**
     * dateModification
     */
    @Column(name="DDATE_MODIFICATION")
    private Date dateModification;


    /**
     * signatureModification
     */
    @Column(name="SSIGNATURE_MODIFICATION", length=16)
    private String signatureModification;


    /**
     * optin
     */
    @Column(name="SOPTIN", length=1)
    private String optin;


    /**
     * numEncompte
     */
    @Column(name="SNUM_ENCOMPTE", length=20)
    private String numEncompte;


    /**
     * refAssociee
     */
    @Column(name="SREF_ASSOCIEE", length=14)
    private String refAssociee;


    /**
     * refPrincipale
     */
    @Column(name="SREF_PRINCIPALE", length=14)
    private String refPrincipale;


    /**
     * motDirecteur
     */
    @Column(name="SMOT_DIRECTEUR", length=20)
    private String motDirecteur;


    /**
     * clefRole
     */
    @Column(name="ICLE_ROLE", length=10, updatable=false, insertable=false)
    private Integer clefRole;


    /**
     * saEmettre
     */
    @Column(name="SA_EMETTRE", length=1)
    private String saEmettre;


    /**
     * gestionBkof
     */
    @Column(name="SGESTION_BKOF", length=1)
    private String gestionBkof;


    /**
     * pays
     */
    @Column(name="SPAYS", length=2)
    private String pays;


    /**
     * tier
     */
    @Column(name="CTIER", length=1)
    private String tier;


    /**
     * businessRole
     */
    // 1 <-> 1
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ICLE_ROLE", nullable=false, foreignKey = @javax.persistence.ForeignKey(name = "FK_RF_BR"))
    private BusinessRole businessRole;

    /*PROTECTED REGION ID(_MmFEULbGEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    /**
     * default constructor 
     */
    public RoleFirme() {
        //empty constructor
    }

    /**
     * full constructor
     * @param pCle cle
     * @param pGin gin
     * @param pVersion version
     * @param pType type
     * @param pSousType sousType
     * @param pMc mc
     * @param pNumero numero
     * @param pEtat etat
     * @param pDebutValidite debutValidite
     * @param pFinValidite finValidite
     * @param pDateCreation dateCreation
     * @param pSignatureCreation signatureCreation
     * @param pDateModification dateModification
     * @param pSignatureModification signatureModification
     * @param pOptin optin
     * @param pNumEncompte numEncompte
     * @param pRefAssociee refAssociee
     * @param pRefPrincipale refPrincipale
     * @param pMotDirecteur motDirecteur
     * @param pClefRole clefRole
     * @param pSaEmettre saEmettre
     * @param pGestionBkof gestionBkof
     * @param pPays pays
     * @param pTier tier
     */
    public RoleFirme(String pCle, String pGin, Integer pVersion, String pType, String pSousType, String pMc, String pNumero, String pEtat, Date pDebutValidite, Date pFinValidite, Date pDateCreation, String pSignatureCreation, Date pDateModification, String pSignatureModification, String pOptin, String pNumEncompte, String pRefAssociee, String pRefPrincipale, String pMotDirecteur, Integer pClefRole, String pSaEmettre, String pGestionBkof, String pPays, String pTier) {
        this.cle = pCle;
        this.gin = pGin;
        this.version = pVersion;
        this.type = pType;
        this.sousType = pSousType;
        this.mc = pMc;
        this.numero = pNumero;
        this.etat = pEtat;
        this.debutValidite = pDebutValidite;
        this.finValidite = pFinValidite;
        this.dateCreation = pDateCreation;
        this.signatureCreation = pSignatureCreation;
        this.dateModification = pDateModification;
        this.signatureModification = pSignatureModification;
        this.optin = pOptin;
        this.numEncompte = pNumEncompte;
        this.refAssociee = pRefAssociee;
        this.refPrincipale = pRefPrincipale;
        this.motDirecteur = pMotDirecteur;
        this.clefRole = pClefRole;
        this.saEmettre = pSaEmettre;
        this.gestionBkof = pGestionBkof;
        this.pays = pPays;
        this.tier = pTier;
    }

    /**
     *
     * @return businessRole
     */
    public BusinessRole getBusinessRole() {
        return this.businessRole;
    }

    /**
     *
     * @param pBusinessRole businessRole value
     */
    public void setBusinessRole(BusinessRole pBusinessRole) {
        this.businessRole = pBusinessRole;
    }

    /**
     *
     * @return cle
     */
    public String getCle() {
        return this.cle;
    }

    /**
     *
     * @param pCle cle value
     */
    public void setCle(String pCle) {
        this.cle = pCle;
    }

    /**
     *
     * @return clefRole
     */
    public Integer getClefRole() {
        return this.clefRole;
    }

    /**
     *
     * @param pClefRole clefRole value
     */
    public void setClefRole(Integer pClefRole) {
        this.clefRole = pClefRole;
    }

    /**
     *
     * @return dateCreation
     */
    public Date getDateCreation() {
        return this.dateCreation;
    }

    /**
     *
     * @param pDateCreation dateCreation value
     */
    public void setDateCreation(Date pDateCreation) {
        this.dateCreation = pDateCreation;
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
     * @return debutValidite
     */
    public Date getDebutValidite() {
        return this.debutValidite;
    }

    /**
     *
     * @param pDebutValidite debutValidite value
     */
    public void setDebutValidite(Date pDebutValidite) {
        this.debutValidite = pDebutValidite;
    }

    /**
     *
     * @return etat
     */
    public String getEtat() {
        return this.etat;
    }

    /**
     *
     * @param pEtat etat value
     */
    public void setEtat(String pEtat) {
        this.etat = pEtat;
    }

    /**
     *
     * @return finValidite
     */
    public Date getFinValidite() {
        return this.finValidite;
    }

    /**
     *
     * @param pFinValidite finValidite value
     */
    public void setFinValidite(Date pFinValidite) {
        this.finValidite = pFinValidite;
    }

    /**
     *
     * @return gestionBkof
     */
    public String getGestionBkof() {
        return this.gestionBkof;
    }

    /**
     *
     * @param pGestionBkof gestionBkof value
     */
    public void setGestionBkof(String pGestionBkof) {
        this.gestionBkof = pGestionBkof;
    }

    /**
     *
     * @return gin
     */
    public String getGin() {
        return this.gin;
    }

    /**
     *
     * @param pGin gin value
     */
    public void setGin(String pGin) {
        this.gin = pGin;
    }

    /**
     *
     * @return mc
     */
    public String getMc() {
        return this.mc;
    }

    /**
     *
     * @param pMc mc value
     */
    public void setMc(String pMc) {
        this.mc = pMc;
    }

    /**
     *
     * @return motDirecteur
     */
    public String getMotDirecteur() {
        return this.motDirecteur;
    }

    /**
     *
     * @param pMotDirecteur motDirecteur value
     */
    public void setMotDirecteur(String pMotDirecteur) {
        this.motDirecteur = pMotDirecteur;
    }

    /**
     *
     * @return numEncompte
     */
    public String getNumEncompte() {
        return this.numEncompte;
    }

    /**
     *
     * @param pNumEncompte numEncompte value
     */
    public void setNumEncompte(String pNumEncompte) {
        this.numEncompte = pNumEncompte;
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
     * @return optin
     */
    public String getOptin() {
        return this.optin;
    }

    /**
     *
     * @param pOptin optin value
     */
    public void setOptin(String pOptin) {
        this.optin = pOptin;
    }

    /**
     *
     * @return pays
     */
    public String getPays() {
        return this.pays;
    }

    /**
     *
     * @param pPays pays value
     */
    public void setPays(String pPays) {
        this.pays = pPays;
    }

    /**
     *
     * @return refAssociee
     */
    public String getRefAssociee() {
        return this.refAssociee;
    }

    /**
     *
     * @param pRefAssociee refAssociee value
     */
    public void setRefAssociee(String pRefAssociee) {
        this.refAssociee = pRefAssociee;
    }

    /**
     *
     * @return refPrincipale
     */
    public String getRefPrincipale() {
        return this.refPrincipale;
    }

    /**
     *
     * @param pRefPrincipale refPrincipale value
     */
    public void setRefPrincipale(String pRefPrincipale) {
        this.refPrincipale = pRefPrincipale;
    }

    /**
     *
     * @return saEmettre
     */
    public String getSaEmettre() {
        return this.saEmettre;
    }

    /**
     *
     * @param pSaEmettre saEmettre value
     */
    public void setSaEmettre(String pSaEmettre) {
        this.saEmettre = pSaEmettre;
    }

    /**
     *
     * @return signatureCreation
     */
    public String getSignatureCreation() {
        return this.signatureCreation;
    }

    /**
     *
     * @param pSignatureCreation signatureCreation value
     */
    public void setSignatureCreation(String pSignatureCreation) {
        this.signatureCreation = pSignatureCreation;
    }

    /**
     *
     * @return signatureModification
     */
    public String getSignatureModification() {
        return this.signatureModification;
    }

    /**
     *
     * @param pSignatureModification signatureModification value
     */
    public void setSignatureModification(String pSignatureModification) {
        this.signatureModification = pSignatureModification;
    }

    /**
     *
     * @return sousType
     */
    public String getSousType() {
        return this.sousType;
    }

    /**
     *
     * @param pSousType sousType value
     */
    public void setSousType(String pSousType) {
        this.sousType = pSousType;
    }

    /**
     *
     * @return tier
     */
    public String getTier() {
        return this.tier;
    }

    /**
     *
     * @param pTier tier value
     */
    public void setTier(String pTier) {
        this.tier = pTier;
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
     * @return version
     */
    public Integer getVersion() {
        return this.version;
    }

    /**
     *
     * @param pVersion version value
     */
    public void setVersion(Integer pVersion) {
        this.version = pVersion;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_MmFEULbGEeCrCZp8iGNNVw) ENABLED START*/
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
        buffer.append("gin=").append(getGin());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("sousType=").append(getSousType());
        buffer.append(",");
        buffer.append("mc=").append(getMc());
        buffer.append(",");
        buffer.append("numero=").append(getNumero());
        buffer.append(",");
        buffer.append("etat=").append(getEtat());
        buffer.append(",");
        buffer.append("debutValidite=").append(getDebutValidite());
        buffer.append(",");
        buffer.append("finValidite=").append(getFinValidite());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("signatureCreation=").append(getSignatureCreation());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("signatureModification=").append(getSignatureModification());
        buffer.append(",");
        buffer.append("optin=").append(getOptin());
        buffer.append(",");
        buffer.append("numEncompte=").append(getNumEncompte());
        buffer.append(",");
        buffer.append("refAssociee=").append(getRefAssociee());
        buffer.append(",");
        buffer.append("refPrincipale=").append(getRefPrincipale());
        buffer.append(",");
        buffer.append("motDirecteur=").append(getMotDirecteur());
        buffer.append(",");
        buffer.append("clefRole=").append(getClefRole());
        buffer.append(",");
        buffer.append("saEmettre=").append(getSaEmettre());
        buffer.append(",");
        buffer.append("gestionBkof=").append(getGestionBkof());
        buffer.append(",");
        buffer.append("pays=").append(getPays());
        buffer.append(",");
        buffer.append("tier=").append(getTier());
        buffer.append("]");
        return buffer.toString();
    }



    /*PROTECTED REGION ID(equals hash _MmFEULbGEeCrCZp8iGNNVw) ENABLED START*/

    /**
     * {@inheritDoc}
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RoleFirme other = (RoleFirme) obj;

        // TODO: writes or generates equals method

        return super.equals(other);
    }

    /**
     * {@inheritDoc}
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        // TODO: writes or generates hashcode method

        return result;
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

    /*PROTECTED REGION ID(_MmFEULbGEeCrCZp8iGNNVw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
