package com.airfrance.repind.entity.profil;

/*PROTECTED REGION ID(_VOKb0EpKEeSzt4lojjJV5Q i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : ProfilQualitatif.java</p>
 * BO ProfilQualitatif
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="PR_QUALITATIF")
public class ProfilQualitatif implements Serializable {

/*PROTECTED REGION ID(serialUID _VOKb0EpKEeSzt4lojjJV5Q) ENABLED START*/
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
    @GeneratedValue(generator = "foreignGenerator")
    @GenericGenerator(name = "foreignGenerator", strategy = "foreign", parameters = { @Parameter(name = "property", value = "profil") })
    @Column(name="ICLE_PRF", length=10)
    private Integer cle;
        
            
    /**
     * debutPeriodeResultat
     */
    @Column(name="DDEB_PERIODE_RESULTAT")
    private Date debutPeriodeResultat;
        
            
    /**
     * finPeriodeResultat
     */
    @Column(name="DFIN_PERIODE_RESULTAT")
    private Date finPeriodeResultat;
        
            
    /**
     * inbBilManuscrits
     */
    @Column(name="INB_BIL_MANUSCRITS", length=10)
    private Integer inbBilManuscrits;
        
            
    /**
     * inbGoShowNavette
     */
    @Column(name="INB_GOSHOW_NAVETTE", length=10)
    private Integer inbGoShowNavette;
        
            
    /**
     * inbGoShowNonNav
     */
    @Column(name="INB_GOSHOW_NON_NAV", length=10)
    private Integer inbGoShowNonNav;
        
            
    /**
     * inbNoShowNavette
     */
    @Column(name="INB_NOSHOW_NAVETTE", length=10)
    private Integer inbNoShowNavette;
        
            
    /**
     * inbNoShowNonNav
     */
    @Column(name="INB_NOSHOW_NON_NAV", length=10)
    private Integer inbNoShowNonNav;
        
            
    /**
     * inbReserveNavette
     */
    @Column(name="INB_RESERVE_NAVETTE", length=10)
    private Integer inbReserveNavette;
        
            
    /**
     * inbReserveNonNav
     */
    @Column(name="INB_RESERVE_NON_NAV", length=10)
    private Integer inbReserveNonNav;
        
            
    /**
     * inbTotBilEmis
     */
    @Column(name="INB_TOT_BIL_EMIS", length=10)
    private Integer inbTotBilEmis;
        
            
    /**
     * profil
     */
    // 1 <-> 1
    @OneToOne(mappedBy="profilQualitatif")
    private Profil_mere profil;
        
    /*PROTECTED REGION ID(_VOKb0EpKEeSzt4lojjJV5Q u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public ProfilQualitatif() {
    }
        
    /** 
     * full constructor
     * @param pCle cle
     * @param pDebutPeriodeResultat debutPeriodeResultat
     * @param pFinPeriodeResultat finPeriodeResultat
     * @param pInbBilManuscrits inbBilManuscrits
     * @param pInbGoShowNavette inbGoShowNavette
     * @param pInbGoShowNonNav inbGoShowNonNav
     * @param pInbNoShowNavette inbNoShowNavette
     * @param pInbNoShowNonNav inbNoShowNonNav
     * @param pInbReserveNavette inbReserveNavette
     * @param pInbReserveNonNav inbReserveNonNav
     * @param pInbTotBilEmis inbTotBilEmis
     */
    public ProfilQualitatif(Integer pCle, Date pDebutPeriodeResultat, Date pFinPeriodeResultat, Integer pInbBilManuscrits, Integer pInbGoShowNavette, Integer pInbGoShowNonNav, Integer pInbNoShowNavette, Integer pInbNoShowNonNav, Integer pInbReserveNavette, Integer pInbReserveNonNav, Integer pInbTotBilEmis) {
        this.cle = pCle;
        this.debutPeriodeResultat = pDebutPeriodeResultat;
        this.finPeriodeResultat = pFinPeriodeResultat;
        this.inbBilManuscrits = pInbBilManuscrits;
        this.inbGoShowNavette = pInbGoShowNavette;
        this.inbGoShowNonNav = pInbGoShowNonNav;
        this.inbNoShowNavette = pInbNoShowNavette;
        this.inbNoShowNonNav = pInbNoShowNonNav;
        this.inbReserveNavette = pInbReserveNavette;
        this.inbReserveNonNav = pInbReserveNonNav;
        this.inbTotBilEmis = pInbTotBilEmis;
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
     * @return debutPeriodeResultat
     */
    public Date getDebutPeriodeResultat() {
        return this.debutPeriodeResultat;
    }

    /**
     *
     * @param pDebutPeriodeResultat debutPeriodeResultat value
     */
    public void setDebutPeriodeResultat(Date pDebutPeriodeResultat) {
        this.debutPeriodeResultat = pDebutPeriodeResultat;
    }

    /**
     *
     * @return finPeriodeResultat
     */
    public Date getFinPeriodeResultat() {
        return this.finPeriodeResultat;
    }

    /**
     *
     * @param pFinPeriodeResultat finPeriodeResultat value
     */
    public void setFinPeriodeResultat(Date pFinPeriodeResultat) {
        this.finPeriodeResultat = pFinPeriodeResultat;
    }

    /**
     *
     * @return inbBilManuscrits
     */
    public Integer getInbBilManuscrits() {
        return this.inbBilManuscrits;
    }

    /**
     *
     * @param pInbBilManuscrits inbBilManuscrits value
     */
    public void setInbBilManuscrits(Integer pInbBilManuscrits) {
        this.inbBilManuscrits = pInbBilManuscrits;
    }

    /**
     *
     * @return inbGoShowNavette
     */
    public Integer getInbGoShowNavette() {
        return this.inbGoShowNavette;
    }

    /**
     *
     * @param pInbGoShowNavette inbGoShowNavette value
     */
    public void setInbGoShowNavette(Integer pInbGoShowNavette) {
        this.inbGoShowNavette = pInbGoShowNavette;
    }

    /**
     *
     * @return inbGoShowNonNav
     */
    public Integer getInbGoShowNonNav() {
        return this.inbGoShowNonNav;
    }

    /**
     *
     * @param pInbGoShowNonNav inbGoShowNonNav value
     */
    public void setInbGoShowNonNav(Integer pInbGoShowNonNav) {
        this.inbGoShowNonNav = pInbGoShowNonNav;
    }

    /**
     *
     * @return inbNoShowNavette
     */
    public Integer getInbNoShowNavette() {
        return this.inbNoShowNavette;
    }

    /**
     *
     * @param pInbNoShowNavette inbNoShowNavette value
     */
    public void setInbNoShowNavette(Integer pInbNoShowNavette) {
        this.inbNoShowNavette = pInbNoShowNavette;
    }

    /**
     *
     * @return inbNoShowNonNav
     */
    public Integer getInbNoShowNonNav() {
        return this.inbNoShowNonNav;
    }

    /**
     *
     * @param pInbNoShowNonNav inbNoShowNonNav value
     */
    public void setInbNoShowNonNav(Integer pInbNoShowNonNav) {
        this.inbNoShowNonNav = pInbNoShowNonNav;
    }

    /**
     *
     * @return inbReserveNavette
     */
    public Integer getInbReserveNavette() {
        return this.inbReserveNavette;
    }

    /**
     *
     * @param pInbReserveNavette inbReserveNavette value
     */
    public void setInbReserveNavette(Integer pInbReserveNavette) {
        this.inbReserveNavette = pInbReserveNavette;
    }

    /**
     *
     * @return inbReserveNonNav
     */
    public Integer getInbReserveNonNav() {
        return this.inbReserveNonNav;
    }

    /**
     *
     * @param pInbReserveNonNav inbReserveNonNav value
     */
    public void setInbReserveNonNav(Integer pInbReserveNonNav) {
        this.inbReserveNonNav = pInbReserveNonNav;
    }

    /**
     *
     * @return inbTotBilEmis
     */
    public Integer getInbTotBilEmis() {
        return this.inbTotBilEmis;
    }

    /**
     *
     * @param pInbTotBilEmis inbTotBilEmis value
     */
    public void setInbTotBilEmis(Integer pInbTotBilEmis) {
        this.inbTotBilEmis = pInbTotBilEmis;
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
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_VOKb0EpKEeSzt4lojjJV5Q) ENABLED START*/
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
        buffer.append("debutPeriodeResultat=").append(getDebutPeriodeResultat());
        buffer.append(",");
        buffer.append("finPeriodeResultat=").append(getFinPeriodeResultat());
        buffer.append(",");
        buffer.append("inbBilManuscrits=").append(getInbBilManuscrits());
        buffer.append(",");
        buffer.append("inbGoShowNavette=").append(getInbGoShowNavette());
        buffer.append(",");
        buffer.append("inbGoShowNonNav=").append(getInbGoShowNonNav());
        buffer.append(",");
        buffer.append("inbNoShowNavette=").append(getInbNoShowNavette());
        buffer.append(",");
        buffer.append("inbNoShowNonNav=").append(getInbNoShowNonNav());
        buffer.append(",");
        buffer.append("inbReserveNavette=").append(getInbReserveNavette());
        buffer.append(",");
        buffer.append("inbReserveNonNav=").append(getInbReserveNonNav());
        buffer.append(",");
        buffer.append("inbTotBilEmis=").append(getInbTotBilEmis());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _VOKb0EpKEeSzt4lojjJV5Q) ENABLED START*/
    
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

    /*PROTECTED REGION ID(_VOKb0EpKEeSzt4lojjJV5Q u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
