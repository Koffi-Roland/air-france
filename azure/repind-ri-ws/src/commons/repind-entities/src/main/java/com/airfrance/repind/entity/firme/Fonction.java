package com.airfrance.repind.entity.firme;

/*PROTECTED REGION ID(_u4-qYKUyEeSXNpATSKyi0Q i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.adresse.Telecoms;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/*PROTECTED REGION END*/


/**
 * <p>Title : Fonction.java</p>
 * BO Fonction
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="FONCTION")
public class Fonction implements Serializable {

/*PROTECTED REGION ID(serialUID _u4-qYKUyEeSXNpATSKyi0Q) ENABLED START*/
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_FONCTION")
    @SequenceGenerator(name="ISEQ_FONCTION", sequenceName = "ISEQ_FONCTION", allocationSize = 1)
    @Column(name="ICLE", length=10)
    private Integer cle;
        
            
    /**
     * fonction
     */
    @Column(name="SFONCTION", length=3, nullable=false)
    private String fonction;
        
            
    /**
     * dateDebutValidite
     */
    @Column(name="DDATE_DEBUT_VALIDITE", nullable=false)
    private Date dateDebutValidite;
        
            
    /**
     * dateFinValidite
     */
    @Column(name="DDATE_FIN_VALIDITE")
    private Date dateFinValidite;
        
            
    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION", nullable=false)
    private Date dateCreation;
        
            
    /**
     * signatureCreation
     */
    @Column(name="SSIGNATURE_CREATION", length=16, nullable=false)
    private String signatureCreation;
        
            
    /**
     * dateModification
     */
    @Column(name="DDATE_MODIFICATION", nullable=false)
    private Date dateModification;
        
            
    /**
     * signatureModification
     */
    @Column(name="SSIGNATURE_MODIFICATION", length=16, nullable=false)
    private String signatureModification;
        
            
    /**
     * version
     */
    @Version
    @Column(name="IVERSION", length=38)
    private Integer version;
        
            
    /**
     * emails
     */
    // 1 <-> * 
    @OneToMany(mappedBy="fonction", fetch=FetchType.LAZY)
    private Set<Email> emails;
        
            
    /**
     * membre
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(name="IKEY_MEMBRE", nullable=false)
//    @ForeignKey  (name = "FONCTION_MEM_FK")
    private Membre membre;
        
            
    /**
     * postalAddresses
     */
    // 1 <-> * 
    @OneToMany(mappedBy="fonction", fetch=FetchType.LAZY)
    private Set<PostalAddress> postalAddresses;
        
            
    /**
     * telecoms
     */
    // 1 <-> * 
    @OneToMany(mappedBy="fonction", fetch=FetchType.LAZY)
    private Set<Telecoms> telecoms;
        
    /*PROTECTED REGION ID(_u4-qYKUyEeSXNpATSKyi0Q u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public Fonction() {
    }
        
    /** 
     * full constructor
     * @param pCle cle
     * @param pFonction fonction
     * @param pDateDebutValidite dateDebutValidite
     * @param pDateFinValidite dateFinValidite
     * @param pDateCreation dateCreation
     * @param pSignatureCreation signatureCreation
     * @param pDateModification dateModification
     * @param pSignatureModification signatureModification
     * @param pVersion version
     */
    public Fonction(Integer pCle, String pFonction, Date pDateDebutValidite, Date pDateFinValidite, Date pDateCreation, String pSignatureCreation, Date pDateModification, String pSignatureModification, Integer pVersion) {
        this.cle = pCle;
        this.fonction = pFonction;
        this.dateDebutValidite = pDateDebutValidite;
        this.dateFinValidite = pDateFinValidite;
        this.dateCreation = pDateCreation;
        this.signatureCreation = pSignatureCreation;
        this.dateModification = pDateModification;
        this.signatureModification = pSignatureModification;
        this.version = pVersion;
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
     * @return dateDebutValidite
     */
    public Date getDateDebutValidite() {
        return this.dateDebutValidite;
    }

    /**
     *
     * @param pDateDebutValidite dateDebutValidite value
     */
    public void setDateDebutValidite(Date pDateDebutValidite) {
        this.dateDebutValidite = pDateDebutValidite;
    }

    /**
     *
     * @return dateFinValidite
     */
    public Date getDateFinValidite() {
        return this.dateFinValidite;
    }

    /**
     *
     * @param pDateFinValidite dateFinValidite value
     */
    public void setDateFinValidite(Date pDateFinValidite) {
        this.dateFinValidite = pDateFinValidite;
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
     * @return emails
     */
    public Set<Email> getEmails() {
        return this.emails;
    }

    /**
     *
     * @param pEmails emails value
     */
    public void setEmails(Set<Email> pEmails) {
        this.emails = pEmails;
    }

    /**
     *
     * @return fonction
     */
    public String getFonction() {
        return this.fonction;
    }

    /**
     *
     * @param pFonction fonction value
     */
    public void setFonction(String pFonction) {
        this.fonction = pFonction;
    }

    /**
     *
     * @return membre
     */
    public Membre getMembre() {
        return this.membre;
    }

    /**
     *
     * @param pMembre membre value
     */
    public void setMembre(Membre pMembre) {
        this.membre = pMembre;
    }

    /**
     *
     * @return postalAddresses
     */
    public Set<PostalAddress> getPostalAddresses() {
        return this.postalAddresses;
    }

    /**
     *
     * @param pPostalAddresses postalAddresses value
     */
    public void setPostalAddresses(Set<PostalAddress> pPostalAddresses) {
        this.postalAddresses = pPostalAddresses;
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
     * @return telecoms
     */
    public Set<Telecoms> getTelecoms() {
        return this.telecoms;
    }

    /**
     *
     * @param pTelecoms telecoms value
     */
    public void setTelecoms(Set<Telecoms> pTelecoms) {
        this.telecoms = pTelecoms;
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
        /*PROTECTED REGION ID(toString_u4-qYKUyEeSXNpATSKyi0Q) ENABLED START*/
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
        buffer.append("fonction=").append(getFonction());
        buffer.append(",");
        buffer.append("dateDebutValidite=").append(getDateDebutValidite());
        buffer.append(",");
        buffer.append("dateFinValidite=").append(getDateFinValidite());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("signatureCreation=").append(getSignatureCreation());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("signatureModification=").append(getSignatureModification());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _u4-qYKUyEeSXNpATSKyi0Q) ENABLED START*/
    
    /**
     * {@inheritDoc}
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        //TODO Customize here if necessary
        return hashCodeImpl();
    }

    /**
     * {@inheritDoc}
     * @see Object#equals(Object)
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

    /*PROTECTED REGION ID(_u4-qYKUyEeSXNpATSKyi0Q u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
