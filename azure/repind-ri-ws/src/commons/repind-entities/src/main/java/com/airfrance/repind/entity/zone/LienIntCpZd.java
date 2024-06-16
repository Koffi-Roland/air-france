package com.airfrance.repind.entity.zone;

/*PROTECTED REGION ID(_iR0skDM1EeKT_JQCdHEO1w i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : LienIntCpZd.java</p>
 * BO LienIntCpZd
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="LIEN_INT_CP_ZD")
public class LienIntCpZd implements Serializable {

/*PROTECTED REGION ID(serialUID _iR0skDM1EeKT_JQCdHEO1w) ENABLED START*/
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_LIEN_INT_CP_ZD")
    @SequenceGenerator(name="ISEQ_LIEN_INT_CP_ZD", sequenceName = "ISEQ_LIEN_INT_CP_ZD", allocationSize = 1)
    @Column(name="ICLE")
    private Long cle;
        
            
    /**
     * signatureMaj
     */
    @Column(name="SSIGNATURE_MAJ", length=16, nullable=false)
    private String signatureMaj;
        
            
    /**
     * dateMaj
     */
    @Column(name="DDATE_MAJ", nullable=false)
    private Date dateMaj;
        
            
    /**
     * dateFinLien
     */
    @Column(name="DDATE_FIN_LIEN")
    private Date dateFinLien;
        
            
    /**
     * dateDebutLien
     */
    @Column(name="DDATE_DEB_LIEN")
    private Date dateDebutLien;
        
            
    /**
     * usage
     */
    @Column(name="SUSAGE", length=2)
    private String usage;
        
            
    /**
     * codeVille
     */
    @Column(name="SCODE_VILLE", length=5)
    private String codeVille;
        
            
    /**
     * codeProvince
     */
    @Column(name="SCODE_PROV", length=2)
    private String codeProvince;
        
            
    /**
     * codePays
     */
    @Column(name="SCODE_PAYS", length=2)
    private String codePays;
        
            
    /**
     * intervalleCodesPostaux
     */
    // * <-> 1
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ICLE_INT_CP", referencedColumnName = "ICLE", nullable=true)
    @ForeignKey(name = "FK_LIEN_INT_CP_ZD_ICLE_INT_CP_ICLE")
    private IntervalleCodesPostaux intervalleCodesPostaux;
        
            
    /**
     * zoneDecoup
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(name="IGIN_ZONE", referencedColumnName = "IGIN", nullable=false, updatable=true, insertable=true)
    @ForeignKey(name = "FK_LIEN_INT_CP_ZD_IGIN_ZONE_IGIN")
    private ZoneDecoup zoneDecoup;
        
    /*PROTECTED REGION ID(_iR0skDM1EeKT_JQCdHEO1w u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public LienIntCpZd() {
    }
        
    /** 
     * full constructor
     * @param pCle cle
     * @param pSignatureMaj signatureMaj
     * @param pDateMaj dateMaj
     * @param pDateFinLien dateFinLien
     * @param pDateDebutLien dateDebutLien
     * @param pUsage usage
     * @param pCodeVille codeVille
     * @param pCodeProvince codeProvince
     * @param pCodePays codePays
     */
    public LienIntCpZd(Long pCle, String pSignatureMaj, Date pDateMaj, Date pDateFinLien, Date pDateDebutLien, String pUsage, String pCodeVille, String pCodeProvince, String pCodePays) {
        this.cle = pCle;
        this.signatureMaj = pSignatureMaj;
        this.dateMaj = pDateMaj;
        this.dateFinLien = pDateFinLien;
        this.dateDebutLien = pDateDebutLien;
        this.usage = pUsage;
        this.codeVille = pCodeVille;
        this.codeProvince = pCodeProvince;
        this.codePays = pCodePays;
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
     * @return codePays
     */
    public String getCodePays() {
        return this.codePays;
    }

    /**
     *
     * @param pCodePays codePays value
     */
    public void setCodePays(String pCodePays) {
        this.codePays = pCodePays;
    }

    /**
     *
     * @return codeProvince
     */
    public String getCodeProvince() {
        return this.codeProvince;
    }

    /**
     *
     * @param pCodeProvince codeProvince value
     */
    public void setCodeProvince(String pCodeProvince) {
        this.codeProvince = pCodeProvince;
    }

    /**
     *
     * @return codeVille
     */
    public String getCodeVille() {
        return this.codeVille;
    }

    /**
     *
     * @param pCodeVille codeVille value
     */
    public void setCodeVille(String pCodeVille) {
        this.codeVille = pCodeVille;
    }

    /**
     *
     * @return dateDebutLien
     */
    public Date getDateDebutLien() {
        return this.dateDebutLien;
    }

    /**
     *
     * @param pDateDebutLien dateDebutLien value
     */
    public void setDateDebutLien(Date pDateDebutLien) {
        this.dateDebutLien = pDateDebutLien;
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
     * @return intervalleCodesPostaux
     */
    public IntervalleCodesPostaux getIntervalleCodesPostaux() {
        return this.intervalleCodesPostaux;
    }

    /**
     *
     * @param pIntervalleCodesPostaux intervalleCodesPostaux value
     */
    public void setIntervalleCodesPostaux(IntervalleCodesPostaux pIntervalleCodesPostaux) {
        this.intervalleCodesPostaux = pIntervalleCodesPostaux;
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
     * @return usage
     */
    public String getUsage() {
        return this.usage;
    }

    /**
     *
     * @param pUsage usage value
     */
    public void setUsage(String pUsage) {
        this.usage = pUsage;
    }

    /**
     *
     * @return zoneDecoup
     */
    public ZoneDecoup getZoneDecoup() {
        return this.zoneDecoup;
    }

    /**
     *
     * @param pZoneDecoup zoneDecoup value
     */
    public void setZoneDecoup(ZoneDecoup pZoneDecoup) {
        this.zoneDecoup = pZoneDecoup;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_iR0skDM1EeKT_JQCdHEO1w) ENABLED START*/
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
        buffer.append("signatureMaj=").append(getSignatureMaj());
        buffer.append(",");
        buffer.append("dateMaj=").append(getDateMaj());
        buffer.append(",");
        buffer.append("dateFinLien=").append(getDateFinLien());
        buffer.append(",");
        buffer.append("dateDebutLien=").append(getDateDebutLien());
        buffer.append(",");
        buffer.append("usage=").append(getUsage());
        buffer.append(",");
        buffer.append("codeVille=").append(getCodeVille());
        buffer.append(",");
        buffer.append("codeProvince=").append(getCodeProvince());
        buffer.append(",");
        buffer.append("codePays=").append(getCodePays());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _iR0skDM1EeKT_JQCdHEO1w) ENABLED START*/

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
        final LienIntCpZd other = (LienIntCpZd) obj;

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

    /*PROTECTED REGION ID(_iR0skDM1EeKT_JQCdHEO1w u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
