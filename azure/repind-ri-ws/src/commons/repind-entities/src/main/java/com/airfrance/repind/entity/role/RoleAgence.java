package com.airfrance.repind.entity.role;

/*PROTECTED REGION ID(_sAz1UDnEEeS2wtWjh0gEaw i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : RoleAgence.java</p>
 * BO RoleAgence
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="ROLE_AGENCE")
public class RoleAgence implements Serializable {

/*PROTECTED REGION ID(serialUID _sAz1UDnEEeS2wtWjh0gEaw) ENABLED START*/
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
     * cleRole
     */
    @Id
    @GeneratedValue(generator = "foreignGeneratorAgency")
    @GenericGenerator(name = "foreignGeneratorAgency", strategy = "foreign", parameters = { @Parameter(name = "property", value = "businessRole") })
    @Column(name="ICLE_ROLE", length=10)
    private Integer cleRole;
        
            
    /**
     * typeIncentive
     */
    @Column(name="STYPE_INCENTIVE", length=1)
    private String typeIncentive;
        
            
    /**
     * siteCreation
     */
    @Column(name="SSITE_CREATION", length=10, nullable=false)
    private String siteCreation;
        
            
    /**
     * type
     */
    @Column(name="STYPE", length=2, nullable=false)
    private String type;
        
            
    /**
     * version
     */
    @Version
    @Column(name="IVERSION", length=38, nullable=false)
    private Integer version;
        
            
    /**
     * numero
     */
    @Column(name="SNUMERO", length=20, nullable=false)
    private String numero;
        
            
    /**
     * etat
     */
    @Column(name="SETAT", length=1)
    private String etat;
        
            
    /**
     * gin
     */
    @Column(name="SGIN", length=12)
    private String gin;
        
            
    /**
     * statusCrtSeq
     */
    @Column(name="SSTATUS_CRT_SEQ", length=2)
    private String statusCrtSeq;
        
            
    /**
     * sousType
     */
    @Column(name="SSOUS_TYPE", length=2)
    private String sousType;
        
            
    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION", nullable=false)
    private Date dateCreation;
        
            
    /**
     * siteModification
     */
    @Column(name="SSITE_MODIFICATION", length=10)
    private String siteModification;
        
            
    /**
     * debutValidite
     */
    @Column(name="DDEBUT_VALIDITE", nullable=false)
    private Date debutValidite;
        
            
    /**
     * signatureCreation
     */
    @Column(name="SSIGNATURE_CREATION", length=16, nullable=false)
    private String signatureCreation;
        
            
    /**
     * finValidite
     */
    @Column(name="DFIN_VALIDITE")
    private Date finValidite;
        
            
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
     * contracting
     */
    @Column(name="SCONTRACTING", length=1)
    private String contracting;
        
            
    /**
     * exclusion
     */
    @Column(name="SEXCLUSION", length=1)
    private String exclusion;
        
            
    /**
     * libelle
     */
    @Column(name="SLIBELLE", length=30)
    private String libelle;
        
            
    /**
     * nomContrat
     */
    @Column(name="SNOM_CONTRAT", length=30)
    private String nomContrat;
        
            
    /**
     * businessRole
     */
    // 1 <-> 1
    @OneToOne(fetch=FetchType.LAZY, mappedBy="roleAgence")
    private BusinessRole businessRole;
        
            
    /**
     * contratTcSeq
     */
    // * <-> 1
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SNUMERO_TC", nullable=true)
    @ForeignKey(name = "SYS_C005026")
    private ContratTcSeq contratTcSeq;
        
    /*PROTECTED REGION ID(_sAz1UDnEEeS2wtWjh0gEaw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public RoleAgence() {
    }
        
    /** 
     * full constructor
     * @param pCleRole cleRole
     * @param pTypeIncentive typeIncentive
     * @param pSiteCreation siteCreation
     * @param pType type
     * @param pVersion version
     * @param pNumero numero
     * @param pEtat etat
     * @param pGin gin
     * @param pStatusCrtSeq statusCrtSeq
     * @param pSousType sousType
     * @param pDateCreation dateCreation
     * @param pSiteModification siteModification
     * @param pDebutValidite debutValidite
     * @param pSignatureCreation signatureCreation
     * @param pFinValidite finValidite
     * @param pDateModification dateModification
     * @param pSignatureModification signatureModification
     * @param pContracting contracting
     * @param pExclusion exclusion
     * @param pLibelle libelle
     * @param pNomContrat nomContrat
     */
    public RoleAgence(Integer pCleRole, String pTypeIncentive, String pSiteCreation, String pType, Integer pVersion, String pNumero, String pEtat, String pGin, String pStatusCrtSeq, String pSousType, Date pDateCreation, String pSiteModification, Date pDebutValidite, String pSignatureCreation, Date pFinValidite, Date pDateModification, String pSignatureModification, String pContracting, String pExclusion, String pLibelle, String pNomContrat) {
        this.cleRole = pCleRole;
        this.typeIncentive = pTypeIncentive;
        this.siteCreation = pSiteCreation;
        this.type = pType;
        this.version = pVersion;
        this.numero = pNumero;
        this.etat = pEtat;
        this.gin = pGin;
        this.statusCrtSeq = pStatusCrtSeq;
        this.sousType = pSousType;
        this.dateCreation = pDateCreation;
        this.siteModification = pSiteModification;
        this.debutValidite = pDebutValidite;
        this.signatureCreation = pSignatureCreation;
        this.finValidite = pFinValidite;
        this.dateModification = pDateModification;
        this.signatureModification = pSignatureModification;
        this.contracting = pContracting;
        this.exclusion = pExclusion;
        this.libelle = pLibelle;
        this.nomContrat = pNomContrat;
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
     * @return cleRole
     */
    public Integer getCleRole() {
        return this.cleRole;
    }

    /**
     *
     * @param pCleRole cleRole value
     */
    public void setCleRole(Integer pCleRole) {
        this.cleRole = pCleRole;
    }

    /**
     *
     * @return contracting
     */
    public String getContracting() {
        return this.contracting;
    }

    /**
     *
     * @param pContracting contracting value
     */
    public void setContracting(String pContracting) {
        this.contracting = pContracting;
    }

    /**
     *
     * @return contratTcSeq
     */
    public ContratTcSeq getContratTcSeq() {
        return this.contratTcSeq;
    }

    /**
     *
     * @param pContratTcSeq contratTcSeq value
     */
    public void setContratTcSeq(ContratTcSeq pContratTcSeq) {
        this.contratTcSeq = pContratTcSeq;
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
     * @return exclusion
     */
    public String getExclusion() {
        return this.exclusion;
    }

    /**
     *
     * @param pExclusion exclusion value
     */
    public void setExclusion(String pExclusion) {
        this.exclusion = pExclusion;
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
     * @return nomContrat
     */
    public String getNomContrat() {
        return this.nomContrat;
    }

    /**
     *
     * @param pNomContrat nomContrat value
     */
    public void setNomContrat(String pNomContrat) {
        this.nomContrat = pNomContrat;
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
     * @return siteCreation
     */
    public String getSiteCreation() {
        return this.siteCreation;
    }

    /**
     *
     * @param pSiteCreation siteCreation value
     */
    public void setSiteCreation(String pSiteCreation) {
        this.siteCreation = pSiteCreation;
    }

    /**
     *
     * @return siteModification
     */
    public String getSiteModification() {
        return this.siteModification;
    }

    /**
     *
     * @param pSiteModification siteModification value
     */
    public void setSiteModification(String pSiteModification) {
        this.siteModification = pSiteModification;
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
     * @return statusCrtSeq
     */
    public String getStatusCrtSeq() {
        return this.statusCrtSeq;
    }

    /**
     *
     * @param pStatusCrtSeq statusCrtSeq value
     */
    public void setStatusCrtSeq(String pStatusCrtSeq) {
        this.statusCrtSeq = pStatusCrtSeq;
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
     * @return typeIncentive
     */
    public String getTypeIncentive() {
        return this.typeIncentive;
    }

    /**
     *
     * @param pTypeIncentive typeIncentive value
     */
    public void setTypeIncentive(String pTypeIncentive) {
        this.typeIncentive = pTypeIncentive;
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
        /*PROTECTED REGION ID(toString_sAz1UDnEEeS2wtWjh0gEaw) ENABLED START*/
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
        buffer.append("cleRole=").append(getCleRole());
        buffer.append(",");
        buffer.append("typeIncentive=").append(getTypeIncentive());
        buffer.append(",");
        buffer.append("siteCreation=").append(getSiteCreation());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append(",");
        buffer.append("numero=").append(getNumero());
        buffer.append(",");
        buffer.append("etat=").append(getEtat());
        buffer.append(",");
        buffer.append("gin=").append(getGin());
        buffer.append(",");
        buffer.append("statusCrtSeq=").append(getStatusCrtSeq());
        buffer.append(",");
        buffer.append("sousType=").append(getSousType());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("siteModification=").append(getSiteModification());
        buffer.append(",");
        buffer.append("debutValidite=").append(getDebutValidite());
        buffer.append(",");
        buffer.append("signatureCreation=").append(getSignatureCreation());
        buffer.append(",");
        buffer.append("finValidite=").append(getFinValidite());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("signatureModification=").append(getSignatureModification());
        buffer.append(",");
        buffer.append("contracting=").append(getContracting());
        buffer.append(",");
        buffer.append("exclusion=").append(getExclusion());
        buffer.append(",");
        buffer.append("libelle=").append(getLibelle());
        buffer.append(",");
        buffer.append("nomContrat=").append(getNomContrat());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _sAz1UDnEEeS2wtWjh0gEaw) ENABLED START*/
    
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

    /*PROTECTED REGION ID(_sAz1UDnEEeS2wtWjh0gEaw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
