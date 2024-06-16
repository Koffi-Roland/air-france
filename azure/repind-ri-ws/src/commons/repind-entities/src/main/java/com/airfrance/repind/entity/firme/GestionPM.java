package com.airfrance.repind.entity.firme;

/*PROTECTED REGION ID(_H_7psGktEeGhB9497mGnHw i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : GestionPM.java</p>
 * BO GestionPM
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="GESTION_PM")
public class GestionPM implements Serializable {

/*PROTECTED REGION ID(serialUID _H_7psGktEeGhB9497mGnHw) ENABLED START*/
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_GESTION_PM")
    @SequenceGenerator(name="ISEQ_GESTION_PM", sequenceName = "ISEQ_GESTION_PM", allocationSize = 1)
    @Column(name="ICLE")
    private Integer cle;
        
            
    /**
     * dateDebLien
     */
    @Column(name="DDATE_DEB_LIEN")
    private Date dateDebLien;
        
            
    /**
     * dateFinLien
     */
    @Column(name="DDATE_FIN_LIEN")
    private Date dateFinLien;
        
            
    /**
     * dateMaj
     */
    @Column(name="DDATE_MAJ")
    private Date dateMaj;
        
            
    /**
     * version
     */
    @Version
    @Column(name="IVERSION", length=10)
    private Integer version;
        
            
    /**
     * lienZCFirme
     */
    @Column(name="LIEN_ZC_FIRME")
    private String lienZCFirme;
        
            
    /**
     * privilegie
     */
    @Column(name="SPRIVILEGIE")
    private String privilegie;
        
            
    /**
     * signatureMaj
     */
    @Column(name="SSIGNATURE_MAJ")
    private String signatureMaj;
        
            
    /**
     * siteMaj
     */
    @Column(name="SSITE_MAJ")
    private String siteMaj;
        
            
    /**
     * typeLien
     */
    @Column(name="STYPE_LIEN")
    private String typeLien;
        
            
    /**
     * personneMoraleGerante
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(name="SGIN_GERANTE", nullable=false)
    @ForeignKey(name = "FK_PM_GERANTE")
    private PersonneMorale personneMoraleGerante;
        
            
    /**
     * personneMoraleGeree
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(name="SGIN_GEREE", nullable=false)
    @ForeignKey(name = "FK_PM_GEREE")
    private PersonneMorale personneMoraleGeree;
        
    /*PROTECTED REGION ID(_H_7psGktEeGhB9497mGnHw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public GestionPM() {
    }
        
    /** 
     * full constructor
     * @param pCle cle
     * @param pDateDebLien dateDebLien
     * @param pDateFinLien dateFinLien
     * @param pDateMaj dateMaj
     * @param pVersion version
     * @param pLienZCFirme lienZCFirme
     * @param pPrivilegie privilegie
     * @param pSignatureMaj signatureMaj
     * @param pSiteMaj siteMaj
     * @param pTypeLien typeLien
     */
    public GestionPM(Integer pCle, Date pDateDebLien, Date pDateFinLien, Date pDateMaj, Integer pVersion, String pLienZCFirme, String pPrivilegie, String pSignatureMaj, String pSiteMaj, String pTypeLien) {
        this.cle = pCle;
        this.dateDebLien = pDateDebLien;
        this.dateFinLien = pDateFinLien;
        this.dateMaj = pDateMaj;
        this.version = pVersion;
        this.lienZCFirme = pLienZCFirme;
        this.privilegie = pPrivilegie;
        this.signatureMaj = pSignatureMaj;
        this.siteMaj = pSiteMaj;
        this.typeLien = pTypeLien;
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
     * @return dateDebLien
     */
    public Date getDateDebLien() {
        return this.dateDebLien;
    }

    /**
     *
     * @param pDateDebLien dateDebLien value
     */
    public void setDateDebLien(Date pDateDebLien) {
        this.dateDebLien = pDateDebLien;
    }

    /**
     *
     * @return dateFinLien
     */
    public Date getDateFinLien() {
        return this.dateFinLien;
    }

    /**
     *
     * @param pDateFinLien dateFinLien value
     */
    public void setDateFinLien(Date pDateFinLien) {
        this.dateFinLien = pDateFinLien;
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
     * @return lienZCFirme
     */
    public String getLienZCFirme() {
        return this.lienZCFirme;
    }

    /**
     *
     * @param pLienZCFirme lienZCFirme value
     */
    public void setLienZCFirme(String pLienZCFirme) {
        this.lienZCFirme = pLienZCFirme;
    }

    /**
     *
     * @return personneMoraleGerante
     */
    public PersonneMorale getPersonneMoraleGerante() {
        return this.personneMoraleGerante;
    }

    /**
     *
     * @param pPersonneMoraleGerante personneMoraleGerante value
     */
    public void setPersonneMoraleGerante(PersonneMorale pPersonneMoraleGerante) {
        this.personneMoraleGerante = pPersonneMoraleGerante;
    }

    /**
     *
     * @return personneMoraleGeree
     */
    public PersonneMorale getPersonneMoraleGeree() {
        return this.personneMoraleGeree;
    }

    /**
     *
     * @param pPersonneMoraleGeree personneMoraleGeree value
     */
    public void setPersonneMoraleGeree(PersonneMorale pPersonneMoraleGeree) {
        this.personneMoraleGeree = pPersonneMoraleGeree;
    }

    /**
     *
     * @return privilegie
     */
    public String getPrivilegie() {
        return this.privilegie;
    }

    /**
     *
     * @param pPrivilegie privilegie value
     */
    public void setPrivilegie(String pPrivilegie) {
        this.privilegie = pPrivilegie;
    }

    /**
     *
     * @return signatureMaj
     */
    public String getSignatureMaj() {
        return this.signatureMaj;
    }

    /**
     *
     * @param pSignatureMaj signatureMaj value
     */
    public void setSignatureMaj(String pSignatureMaj) {
        this.signatureMaj = pSignatureMaj;
    }

    /**
     *
     * @return siteMaj
     */
    public String getSiteMaj() {
        return this.siteMaj;
    }

    /**
     *
     * @param pSiteMaj siteMaj value
     */
    public void setSiteMaj(String pSiteMaj) {
        this.siteMaj = pSiteMaj;
    }

    /**
     *
     * @return typeLien
     */
    public String getTypeLien() {
        return this.typeLien;
    }

    /**
     *
     * @param pTypeLien typeLien value
     */
    public void setTypeLien(String pTypeLien) {
        this.typeLien = pTypeLien;
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
        /*PROTECTED REGION ID(toString_H_7psGktEeGhB9497mGnHw) ENABLED START*/
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
        buffer.append("dateDebLien=").append(getDateDebLien());
        buffer.append(",");
        buffer.append("dateFinLien=").append(getDateFinLien());
        buffer.append(",");
        buffer.append("dateMaj=").append(getDateMaj());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append(",");
        buffer.append("lienZCFirme=").append(getLienZCFirme());
        buffer.append(",");
        buffer.append("privilegie=").append(getPrivilegie());
        buffer.append(",");
        buffer.append("signatureMaj=").append(getSignatureMaj());
        buffer.append(",");
        buffer.append("siteMaj=").append(getSiteMaj());
        buffer.append(",");
        buffer.append("typeLien=").append(getTypeLien());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _H_7psGktEeGhB9497mGnHw) ENABLED START*/

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
        final GestionPM other = (GestionPM) obj;

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

    /*PROTECTED REGION ID(_H_7psGktEeGhB9497mGnHw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
