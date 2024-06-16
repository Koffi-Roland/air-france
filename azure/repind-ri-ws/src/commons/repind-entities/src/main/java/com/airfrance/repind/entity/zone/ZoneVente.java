package com.airfrance.repind.entity.zone;

/*PROTECTED REGION ID(_CsWFcLbOEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/*PROTECTED REGION END*/


/**
 * <p>Title : ZoneVente.java</p>
 * BO ZoneVente
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@DiscriminatorValue("ZV")
@SecondaryTable(name="ZONE_VENTE")
public class ZoneVente extends ZoneDecoup implements Serializable {

/*PROTECTED REGION ID(serialUID _CsWFcLbOEeCrCZp8iGNNVw) ENABLED START*/
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

            
    /*PROTECTED REGION ID(_CsfPYLbOEeCrCZp8iGNNVw p) ENABLED START*/
    /**
     * zv0
     */
    @Column(table="ZONE_VENTE", name="ZV0", length=2)
    private Integer zv0;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_CsfPYbbOEeCrCZp8iGNNVw p) ENABLED START*/
    /**
     * zv1
     */
    @Column(table="ZONE_VENTE", name="ZV1", length=2)
    private Integer zv1;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_CsfPYrbOEeCrCZp8iGNNVw p) ENABLED START*/
    /**
     * zv2
     */
    @Column(table="ZONE_VENTE", name="ZV2", length=2)
    private Integer zv2;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_CsfPZLbOEeCrCZp8iGNNVw p) ENABLED START*/
    /**
     * zv3
     */
    @Column(table="ZONE_VENTE", name="ZV3", length=2)
    private Integer zv3;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_CsfPY7bOEeCrCZp8iGNNVw p) ENABLED START*/
    /**
     * zvAlpha
     */
    @Column(table="ZONE_VENTE", name="ZVALPHA", length=8)
    private String zvAlpha;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_CsfPZbbOEeCrCZp8iGNNVw p) ENABLED START*/
    /**
     * libZvAlpha
     */
    @Column(table="ZONE_VENTE", name="SLIB_ZVALPHA", length=80)
    private String libZvAlpha;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_CsfPZrbOEeCrCZp8iGNNVw p) ENABLED START*/
    /**
     * codeMonnaie
     */
    @Column(table="ZONE_VENTE", name="SCODE_MONNAIE", length=3)
    private String codeMonnaie;
    /*PROTECTED REGION END*/
        
            
    /**
     * liensZc
     */
    // 1 <-> * 
    @OneToMany(mappedBy="zoneVente")
    private Set<LienZvZc> liensZc;
        
    /*PROTECTED REGION ID(_CsWFcLbOEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public ZoneVente() {
    }
        
    /** 
     * full constructor
     * @param pZv0 zv0
     * @param pZv1 zv1
     * @param pZv2 zv2
     * @param pZv3 zv3
     * @param pZvAlpha zvAlpha
     * @param pLibZvAlpha libZvAlpha
     * @param pCodeMonnaie codeMonnaie
     */
    public ZoneVente(Integer pZv0, Integer pZv1, Integer pZv2, Integer pZv3, String pZvAlpha, String pLibZvAlpha, String pCodeMonnaie) {
        this.zv0 = pZv0;
        this.zv1 = pZv1;
        this.zv2 = pZv2;
        this.zv3 = pZv3;
        this.zvAlpha = pZvAlpha;
        this.libZvAlpha = pLibZvAlpha;
        this.codeMonnaie = pCodeMonnaie;
    }

    /**
     *
     * @return codeMonnaie
     */
    public String getCodeMonnaie() {
        return this.codeMonnaie;
    }

    /**
     *
     * @param pCodeMonnaie codeMonnaie value
     */
    public void setCodeMonnaie(String pCodeMonnaie) {
        this.codeMonnaie = pCodeMonnaie;
    }

    /**
     *
     * @return libZvAlpha
     */
    public String getLibZvAlpha() {
        return this.libZvAlpha;
    }

    /**
     *
     * @param pLibZvAlpha libZvAlpha value
     */
    public void setLibZvAlpha(String pLibZvAlpha) {
        this.libZvAlpha = pLibZvAlpha;
    }

    /**
     *
     * @return liensZc
     */
    public Set<LienZvZc> getLiensZc() {
        return this.liensZc;
    }

    /**
     *
     * @param pLiensZc liensZc value
     */
    public void setLiensZc(Set<LienZvZc> pLiensZc) {
        this.liensZc = pLiensZc;
    }

    /**
     *
     * @return zv0
     */
    public Integer getZv0() {
        return this.zv0;
    }

    /**
     *
     * @param pZv0 zv0 value
     */
    public void setZv0(Integer pZv0) {
        this.zv0 = pZv0;
    }

    /**
     *
     * @return zv1
     */
    public Integer getZv1() {
        return this.zv1;
    }

    /**
     *
     * @param pZv1 zv1 value
     */
    public void setZv1(Integer pZv1) {
        this.zv1 = pZv1;
    }

    /**
     *
     * @return zv2
     */
    public Integer getZv2() {
        return this.zv2;
    }

    /**
     *
     * @param pZv2 zv2 value
     */
    public void setZv2(Integer pZv2) {
        this.zv2 = pZv2;
    }

    /**
     *
     * @return zv3
     */
    public Integer getZv3() {
        return this.zv3;
    }

    /**
     *
     * @param pZv3 zv3 value
     */
    public void setZv3(Integer pZv3) {
        this.zv3 = pZv3;
    }

    /**
     *
     * @return zvAlpha
     */
    public String getZvAlpha() {
        return this.zvAlpha;
    }

    /**
     *
     * @param pZvAlpha zvAlpha value
     */
    public void setZvAlpha(String pZvAlpha) {
        this.zvAlpha = pZvAlpha;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_CsWFcLbOEeCrCZp8iGNNVw) ENABLED START*/
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
        buffer.append("zv0=").append(getZv0());
        buffer.append(",");
        buffer.append("zv1=").append(getZv1());
        buffer.append(",");
        buffer.append("zv2=").append(getZv2());
        buffer.append(",");
        buffer.append("zv3=").append(getZv3());
        buffer.append(",");
        buffer.append("zvAlpha=").append(getZvAlpha());
        buffer.append(",");
        buffer.append("libZvAlpha=").append(getLibZvAlpha());
        buffer.append(",");
        buffer.append("codeMonnaie=").append(getCodeMonnaie());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _CsWFcLbOEeCrCZp8iGNNVw) ENABLED START*/

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
        final ZoneVente other = (ZoneVente) obj;

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

    /*PROTECTED REGION ID(_CsWFcLbOEeCrCZp8iGNNVw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
