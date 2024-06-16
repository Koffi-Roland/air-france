package com.airfrance.repind.entity.reference;

/*PROTECTED REGION ID(_6tDz4KH4EeKZdMZUz-fcMQ i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : RefVillesAnnexes.java</p>
 * BO RefVillesAnnexes
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="REF_VILLES_ANNEXES")
public class RefVillesAnnexes implements Serializable {

/*PROTECTED REGION ID(serialUID _6tDz4KH4EeKZdMZUz-fcMQ) ENABLED START*/
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
     * icle
     */
    @Id
    @GenericGenerator(name="ISEQ_REF_VILLES_ANNEXES", strategy = "com.airfrance.repind.util.StringSequenceGenerator",
    parameters = { 
    		@Parameter(name = "sequence_name", value = "ISEQ_REF_VILLES_ANNEXES") 
    })
    @GeneratedValue(generator = "ISEQ_REF_VILLES_ANNEXES")
    @Column(name="ICLE")
    private String icle;
        
            
    /**
     * libelleEn
     */
    @Column(name="SLIBELLE_EN")
    private String libelleEn;
        
            
    /**
     * libelle
     */
    @Column(name="SLIBELLE")
    private String libelle;
        
            
    /**
     * codeVillePrincipal
     */
    @Column(name="SCODE_VILLE_PRINCIPALE")
    private String codeVillePrincipal;
        
            
    /**
     * codeProvEtat
     */
    @Column(name="SCODE_PROVINCE")
    private String codeProvEtat;
        
            
    /**
     * dateModif
     */
    @Column(name="DDATE_MODIF")
    private Date dateModif;
        
            
    /**
     * aeroport
     */
    @Column(name="SAEROPORT")
    private String aeroport;
        
            
    /**
     * signModif
     */
    @Column(name="SSIGNATURE_MODIF")
    private String signModif;
        
            
    /**
     * pays
     */
    // * -> 1
    @ManyToOne()
    @JoinColumn(name="SCODE_PAYS", nullable=false)
    @ForeignKey(name = "FK_REF_VILLES_ANNEXES_SCODE_PAYS")
    private Pays pays;
        
    /*PROTECTED REGION ID(_6tDz4KH4EeKZdMZUz-fcMQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    
    /** 
     * default constructor 
     */
    public RefVillesAnnexes() {
    }
    
        
    /** 
     * full constructor
     * @param pIcle icle
     * @param pLibelleEn libelleEn
     * @param pLibelle libelle
     * @param pCodeVillePrincipal codeVillePrincipal
     * @param pCodeProvEtat codeProvEtat
     * @param pDateModif dateModif
     * @param pAeroport aeroport
     * @param pSignModif signModif
     */
    public RefVillesAnnexes(String pIcle, String pLibelleEn, String pLibelle, String pCodeVillePrincipal, String pCodeProvEtat, Date pDateModif, String pAeroport, String pSignModif) {
        this.icle = pIcle;
        this.libelleEn = pLibelleEn;
        this.libelle = pLibelle;
        this.codeVillePrincipal = pCodeVillePrincipal;
        this.codeProvEtat = pCodeProvEtat;
        this.dateModif = pDateModif;
        this.aeroport = pAeroport;
        this.signModif = pSignModif;
    }

    /**
     *
     * @return aeroport
     */
    public String getAeroport() {
        return this.aeroport;
    }

    /**
     *
     * @param pAeroport aeroport value
     */
    public void setAeroport(String pAeroport) {
        this.aeroport = pAeroport;
    }

    /**
     *
     * @return codeProvEtat
     */
    public String getCodeProvEtat() {
        return this.codeProvEtat;
    }

    /**
     *
     * @param pCodeProvEtat codeProvEtat value
     */
    public void setCodeProvEtat(String pCodeProvEtat) {
        this.codeProvEtat = pCodeProvEtat;
    }

    /**
     *
     * @return codeVillePrincipal
     */
    public String getCodeVillePrincipal() {
        return this.codeVillePrincipal;
    }

    /**
     *
     * @param pCodeVillePrincipal codeVillePrincipal value
     */
    public void setCodeVillePrincipal(String pCodeVillePrincipal) {
        this.codeVillePrincipal = pCodeVillePrincipal;
    }

    /**
     *
     * @return dateModif
     */
    public Date getDateModif() {
        return this.dateModif;
    }

    /**
     *
     * @param pDateModif dateModif value
     */
    public void setDateModif(Date pDateModif) {
        this.dateModif = pDateModif;
    }

    /**
     *
     * @return icle
     */
    public String getIcle() {
        return this.icle;
    }

    /**
     *
     * @param pIcle icle value
     */
    public void setIcle(String pIcle) {
        this.icle = pIcle;
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
     * @return libelleEn
     */
    public String getLibelleEn() {
        return this.libelleEn;
    }

    /**
     *
     * @param pLibelleEn libelleEn value
     */
    public void setLibelleEn(String pLibelleEn) {
        this.libelleEn = pLibelleEn;
    }

    /**
     *
     * @return pays
     */
    public Pays getPays() {
        return this.pays;
    }

    /**
     *
     * @param pPays pays value
     */
    public void setPays(Pays pPays) {
        this.pays = pPays;
    }

    /**
     *
     * @return signModif
     */
    public String getSignModif() {
        return this.signModif;
    }

    /**
     *
     * @param pSignModif signModif value
     */
    public void setSignModif(String pSignModif) {
        this.signModif = pSignModif;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_6tDz4KH4EeKZdMZUz-fcMQ) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }
    
    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        return new ToStringBuilder(this)
            .append("icle", getIcle())
            .append("libelleEn", getLibelleEn())
            .append("libelle", getLibelle())
            .append("codeVillePrincipal", getCodeVillePrincipal())
            .append("codeProvEtat", getCodeProvEtat())
            .append("dateModif", getDateModif())
            .append("aeroport", getAeroport())
            .append("signModif", getSignModif())
            .toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _6tDz4KH4EeKZdMZUz-fcMQ) ENABLED START*/

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if(obj == null){
        	return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RefVillesAnnexes other = (RefVillesAnnexes) obj;

     // if pks are both set, compare
        if (icle != null) {
          if (other.icle != null) {
            return new EqualsBuilder().append(icle, other.icle).isEquals();
          }
        }

        return new EqualsBuilder().
        append(libelle,other.libelle).
        append(codeVillePrincipal,other.codeVillePrincipal).
        append(pays,other.pays).
        isEquals();
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = new HashCodeBuilder().
        append(libelle).
        append(codeVillePrincipal).
        append(pays).
        toHashCode();


        return result;
    }
    
    /*PROTECTED REGION END*/

    /*PROTECTED REGION ID(_6tDz4KH4EeKZdMZUz-fcMQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
