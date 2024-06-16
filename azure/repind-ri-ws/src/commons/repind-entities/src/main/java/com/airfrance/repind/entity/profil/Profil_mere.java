package com.airfrance.repind.entity.profil;

/*PROTECTED REGION ID(_tG3cUDUcEeCq6pHdxM8RnQ i) ENABLED START*/

// add not generated imports here

import javax.persistence.*;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : Profil_mere.java</p>
 * BO Profil_mere
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="PROFIL_MERE")
public class Profil_mere implements Serializable {

/*PROTECTED REGION ID(serialUID _tG3cUDUcEeCq6pHdxM8RnQ) ENABLED START*/
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
     * icle_prf
     */
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_PROFIL_MERE")
    @SequenceGenerator(name="ISEQ_PROFIL_MERE", sequenceName = "ISEQ_PROFIL_MERE",
			allocationSize = 1)
    @Column(name="ICLE_PRF", length=10)
    private Integer icle_prf;
        
            
    /**
     * sgin_pm
     */
    @Column(name="SGIN_PM", length=12)
    private String sgin_pm;
        
            
    /**
     * icle_role
     */
    @Column(name="ICLE_ROLE", length=10)
    private Integer icle_role;
        
            
    /**
     * icle_banq
     */
    @Column(name="ICLE_BANQ", length=10)
    private Integer icle_banq;
        
            
    /**
     * icle_fact
     */
    @Column(name="ICLE_FACT", length=10)
    private Integer icle_fact;
        
            
    /**
     * icle_financ
     */
    @Column(name="ICLE_FINANC", length=10)
    private Integer icle_financ;
        
            
    /**
     * sgin_ind
     */
    @Column(name="SGIN_IND", length=12)
    private String sgin_ind;
        
            
    /**
     * stype
     */
    @Column(name="STYPE", length=2)
    private String stype;
        
            
    /**
     * profilContentieux
     */
    // 1 <-> 1
    @OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ProfilContentieux profilContentieux;
        
            
    /**
     * profilDemarchage
     */
    // 1 <-> 1
    @OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ProfilDemarchage profilDemarchage;
        
            
    /**
     * profilQualitatif
     */
    // 1 <-> 1
    @OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ProfilQualitatif profilQualitatif;
        
            
    /**
     * profil_af
     */
    // 1 -> 1
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Profil_af profil_af;
        
    /*PROTECTED REGION ID(_tG3cUDUcEeCq6pHdxM8RnQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public Profil_mere() {
    }
        
    /** 
     * full constructor
     * @param pIcle_prf icle_prf
     * @param pSgin_pm sgin_pm
     * @param pIcle_role icle_role
     * @param pIcle_banq icle_banq
     * @param pIcle_fact icle_fact
     * @param pIcle_financ icle_financ
     * @param pSgin_ind sgin_ind
     * @param pStype stype
     */
    public Profil_mere(Integer pIcle_prf, String pSgin_pm, Integer pIcle_role, Integer pIcle_banq, Integer pIcle_fact, Integer pIcle_financ, String pSgin_ind, String pStype) {
        this.icle_prf = pIcle_prf;
        this.sgin_pm = pSgin_pm;
        this.icle_role = pIcle_role;
        this.icle_banq = pIcle_banq;
        this.icle_fact = pIcle_fact;
        this.icle_financ = pIcle_financ;
        this.sgin_ind = pSgin_ind;
        this.stype = pStype;
    }

    /**
     *
     * @return icle_banq
     */
    public Integer getIcle_banq() {
        return this.icle_banq;
    }

    /**
     *
     * @param pIcle_banq icle_banq value
     */
    public void setIcle_banq(Integer pIcle_banq) {
        this.icle_banq = pIcle_banq;
    }

    /**
     *
     * @return icle_fact
     */
    public Integer getIcle_fact() {
        return this.icle_fact;
    }

    /**
     *
     * @param pIcle_fact icle_fact value
     */
    public void setIcle_fact(Integer pIcle_fact) {
        this.icle_fact = pIcle_fact;
    }

    /**
     *
     * @return icle_financ
     */
    public Integer getIcle_financ() {
        return this.icle_financ;
    }

    /**
     *
     * @param pIcle_financ icle_financ value
     */
    public void setIcle_financ(Integer pIcle_financ) {
        this.icle_financ = pIcle_financ;
    }

    /**
     *
     * @return icle_prf
     */
    public Integer getIcle_prf() {
        return this.icle_prf;
    }

    /**
     *
     * @param pIcle_prf icle_prf value
     */
    public void setIcle_prf(Integer pIcle_prf) {
        this.icle_prf = pIcle_prf;
    }

    /**
     *
     * @return icle_role
     */
    public Integer getIcle_role() {
        return this.icle_role;
    }

    /**
     *
     * @param pIcle_role icle_role value
     */
    public void setIcle_role(Integer pIcle_role) {
        this.icle_role = pIcle_role;
    }

    /**
     *
     * @return profilContentieux
     */
    public ProfilContentieux getProfilContentieux() {
        return this.profilContentieux;
    }

    /**
     *
     * @param pProfilContentieux profilContentieux value
     */
    public void setProfilContentieux(ProfilContentieux pProfilContentieux) {
        this.profilContentieux = pProfilContentieux;
    }

    /**
     *
     * @return profilDemarchage
     */
    public ProfilDemarchage getProfilDemarchage() {
        return this.profilDemarchage;
    }

    /**
     *
     * @param pProfilDemarchage profilDemarchage value
     */
    public void setProfilDemarchage(ProfilDemarchage pProfilDemarchage) {
        this.profilDemarchage = pProfilDemarchage;
    }

    /**
     *
     * @return profilQualitatif
     */
    public ProfilQualitatif getProfilQualitatif() {
        return this.profilQualitatif;
    }

    /**
     *
     * @param pProfilQualitatif profilQualitatif value
     */
    public void setProfilQualitatif(ProfilQualitatif pProfilQualitatif) {
        this.profilQualitatif = pProfilQualitatif;
    }

    /**
     *
     * @return profil_af
     */
    public Profil_af getProfil_af() {
        return this.profil_af;
    }

    /**
     *
     * @param pProfil_af profil_af value
     */
    public void setProfil_af(Profil_af pProfil_af) {
        this.profil_af = pProfil_af;
    }

    /**
     *
     * @return sgin_ind
     */
    public String getSgin_ind() {
        return this.sgin_ind;
    }

    /**
     *
     * @param pSgin_ind sgin_ind value
     */
    public void setSgin_ind(String pSgin_ind) {
        this.sgin_ind = pSgin_ind;
    }

    /**
     *
     * @return sgin_pm
     */
    public String getSgin_pm() {
        return this.sgin_pm;
    }

    /**
     *
     * @param pSgin_pm sgin_pm value
     */
    public void setSgin_pm(String pSgin_pm) {
        this.sgin_pm = pSgin_pm;
    }

    /**
     *
     * @return stype
     */
    public String getStype() {
        return this.stype;
    }

    /**
     *
     * @param pStype stype value
     */
    public void setStype(String pStype) {
        this.stype = pStype;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_tG3cUDUcEeCq6pHdxM8RnQ) ENABLED START*/
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
        buffer.append("icle_prf=").append(getIcle_prf());
        buffer.append(",");
        buffer.append("sgin_pm=").append(getSgin_pm());
        buffer.append(",");
        buffer.append("icle_role=").append(getIcle_role());
        buffer.append(",");
        buffer.append("icle_banq=").append(getIcle_banq());
        buffer.append(",");
        buffer.append("icle_fact=").append(getIcle_fact());
        buffer.append(",");
        buffer.append("icle_financ=").append(getIcle_financ());
        buffer.append(",");
        buffer.append("sgin_ind=").append(getSgin_ind());
        buffer.append(",");
        buffer.append("stype=").append(getStype());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _tG3cUDUcEeCq6pHdxM8RnQ) ENABLED START*/

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
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
        final Profil_mere other = (Profil_mere) obj;

        // TODO: writes or generates equals method
        
        return super.equals(other);
    }
  
    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
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

    /*PROTECTED REGION ID(_tG3cUDUcEeCq6pHdxM8RnQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
