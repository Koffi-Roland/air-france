package com.airfrance.repind.entity.agence;

/*PROTECTED REGION ID(_QeyLtmk1EeGhB9497mGnHw i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : OfficeID.java</p>
 * BO OfficeID
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="OFFICE_ID")
public class OfficeID implements Serializable {

/*PROTECTED REGION ID(serialUID _QeyLtmk1EeGhB9497mGnHw) ENABLED START*/
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_OFFICE_ID")
    @SequenceGenerator(name="ISEQ_OFFICE_ID", sequenceName = "ISEQ_OFFICE_ID", allocationSize = 1)
    @Column(name="ICLE")
    private Long cle;
        
            
    /**
     * dateLastResa
     */
    @Column(name="DDATE_LAST_RESA")
    private Date dateLastResa;
        
            
    /**
     * dateMaj
     */
    @Column(name="DDATE_MAJ", nullable=false)
    private Date dateMaj;
        
            
    /**
     * codeGDS
     */
    @Column(name="SCODE_GDS", nullable=false)
    private String codeGDS;
        
            
    /**
     * lettreComptoir
     */
    @Column(name="SLETTRE_COMPTOIR")
    private String lettreComptoir;
        
            
    /**
     * majManuelle
     */
    @Column(name="SMAJ_MANUELLE", nullable=false)
    private String majManuelle;
        
            
    /**
     * officeID
     */
    @Column(name="SOFFICE_ID")
    private String officeID;
        
            
    /**
     * signatureMaj
     */
    @Column(name="SSIGNATURE_MAJ", nullable=false)
    private String signatureMaj;
        
            
    /**
     * siteMaj
     */
    @Column(name="SSITE_MAJ", nullable=false)
    private String siteMaj;
        
            
    /**
     * agence
     */
    // * <-> 1
    @ManyToOne
    @JoinColumn(name="SGIN", nullable=false)
    @ForeignKey(name = "FK_OFF_ID_AGENCE")
    private Agence agence;
        
    /*PROTECTED REGION ID(_QeyLtmk1EeGhB9497mGnHw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public OfficeID() {
    }
        
    /** 
     * full constructor
     * @param pCle cle
     * @param pDateLastResa dateLastResa
     * @param pDateMaj dateMaj
     * @param pCodeGDS codeGDS
     * @param pLettreComptoir lettreComptoir
     * @param pMajManuelle majManuelle
     * @param pOfficeID officeID
     * @param pSignatureMaj signatureMaj
     * @param pSiteMaj siteMaj
     */
    public OfficeID(Long pCle, Date pDateLastResa, Date pDateMaj, String pCodeGDS, String pLettreComptoir, String pMajManuelle, String pOfficeID, String pSignatureMaj, String pSiteMaj) {
        this.cle = pCle;
        this.dateLastResa = pDateLastResa;
        this.dateMaj = pDateMaj;
        this.codeGDS = pCodeGDS;
        this.lettreComptoir = pLettreComptoir;
        this.majManuelle = pMajManuelle;
        this.officeID = pOfficeID;
        this.signatureMaj = pSignatureMaj;
        this.siteMaj = pSiteMaj;
    }

    /**
     *
     * @return agence
     */
    public Agence getAgence() {
        return this.agence;
    }

    /**
     *
     * @param pAgence agence value
     */
    public void setAgence(Agence pAgence) {
        this.agence = pAgence;
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
     * @return codeGDS
     */
    public String getCodeGDS() {
        return this.codeGDS;
    }

    /**
     *
     * @param pCodeGDS codeGDS value
     */
    public void setCodeGDS(String pCodeGDS) {
        this.codeGDS = pCodeGDS;
    }

    /**
     *
     * @return dateLastResa
     */
    public Date getDateLastResa() {
        return this.dateLastResa;
    }

    /**
     *
     * @param pDateLastResa dateLastResa value
     */
    public void setDateLastResa(Date pDateLastResa) {
        this.dateLastResa = pDateLastResa;
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
     * @return lettreComptoir
     */
    public String getLettreComptoir() {
        return this.lettreComptoir;
    }

    /**
     *
     * @param pLettreComptoir lettreComptoir value
     */
    public void setLettreComptoir(String pLettreComptoir) {
        this.lettreComptoir = pLettreComptoir;
    }

    /**
     *
     * @return majManuelle
     */
    public String getMajManuelle() {
        return this.majManuelle;
    }

    /**
     *
     * @param pMajManuelle majManuelle value
     */
    public void setMajManuelle(String pMajManuelle) {
        this.majManuelle = pMajManuelle;
    }

    /**
     *
     * @return officeID
     */
    public String getOfficeID() {
        return this.officeID;
    }

    /**
     *
     * @param pOfficeID officeID value
     */
    public void setOfficeID(String pOfficeID) {
        this.officeID = pOfficeID;
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
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_QeyLtmk1EeGhB9497mGnHw) ENABLED START*/
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
        buffer.append("dateLastResa=").append(getDateLastResa());
        buffer.append(",");
        buffer.append("dateMaj=").append(getDateMaj());
        buffer.append(",");
        buffer.append("codeGDS=").append(getCodeGDS());
        buffer.append(",");
        buffer.append("lettreComptoir=").append(getLettreComptoir());
        buffer.append(",");
        buffer.append("majManuelle=").append(getMajManuelle());
        buffer.append(",");
        buffer.append("officeID=").append(getOfficeID());
        buffer.append(",");
        buffer.append("signatureMaj=").append(getSignatureMaj());
        buffer.append(",");
        buffer.append("siteMaj=").append(getSiteMaj());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _QeyLtmk1EeGhB9497mGnHw) ENABLED START*/

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
        final OfficeID other = (OfficeID) obj;

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

    /*PROTECTED REGION ID(_QeyLtmk1EeGhB9497mGnHw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
