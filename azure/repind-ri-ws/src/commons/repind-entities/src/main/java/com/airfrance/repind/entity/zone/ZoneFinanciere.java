package com.airfrance.repind.entity.zone;

/*PROTECTED REGION ID(_oSx9sEABEeS2wtWjh0gEaw i) ENABLED START*/

// add not generated imports here

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.SecondaryTable;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : ZoneFinanciere.java</p>
 * BO ZoneFinanciere
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@DiscriminatorValue("ZF")
@SecondaryTable(name="ZONE_FINANC")
public class ZoneFinanciere extends ZoneDecoup implements Serializable {

/*PROTECTED REGION ID(serialUID _oSx9sEABEeS2wtWjh0gEaw) ENABLED START*/
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

            
    /*PROTECTED REGION ID(_9658sEABEeS2wtWjh0gEaw p) ENABLED START*/
    /**
     * codePays
     */
    @Column(table="ZONE_FINANC", name="SPAYS", length=2)
    private String codePays;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_-9QxkEABEeS2wtWjh0gEaw p) ENABLED START*/
    /**
     * codeUF
     */
    @Column(table="ZONE_FINANC", name="SCODE_UF", length=4)
    private String codeUF;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_LQk9UEACEeS2wtWjh0gEaw p) ENABLED START*/
    /**
     * zoneGeo
     */
    @Column(table="ZONE_FINANC", name="SZONE_GEO")
    private String zoneGeo;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_Fh_p4EACEeS2wtWjh0gEaw p) ENABLED START*/
    /**
     * codeVille
     */
    @Column(table="ZONE_FINANC", name="SCODE_VILLE", length=5)
    private String codeVille;
    /*PROTECTED REGION END*/
        
    /*PROTECTED REGION ID(_oSx9sEABEeS2wtWjh0gEaw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public ZoneFinanciere() {
    }
        
    /** 
     * full constructor
     * @param pCodePays codePays
     * @param pCodeUF codeUF
     * @param pZoneGeo zoneGeo
     * @param pCodeVille codeVille
     */
    public ZoneFinanciere(String pCodePays, String pCodeUF, String pZoneGeo, String pCodeVille) {
        this.codePays = pCodePays;
        this.codeUF = pCodeUF;
        this.zoneGeo = pZoneGeo;
        this.codeVille = pCodeVille;
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
     * @return codeUF
     */
    public String getCodeUF() {
        return this.codeUF;
    }

    /**
     *
     * @param pCodeUF codeUF value
     */
    public void setCodeUF(String pCodeUF) {
        this.codeUF = pCodeUF;
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
     * @return zoneGeo
     */
    public String getZoneGeo() {
        return this.zoneGeo;
    }

    /**
     *
     * @param pZoneGeo zoneGeo value
     */
    public void setZoneGeo(String pZoneGeo) {
        this.zoneGeo = pZoneGeo;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_oSx9sEABEeS2wtWjh0gEaw) ENABLED START*/
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
        buffer.append("codePays=").append(getCodePays());
        buffer.append(",");
        buffer.append("codeUF=").append(getCodeUF());
        buffer.append(",");
        buffer.append("zoneGeo=").append(getZoneGeo());
        buffer.append(",");
        buffer.append("codeVille=").append(getCodeVille());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _oSx9sEABEeS2wtWjh0gEaw) ENABLED START*/
    
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

    /*PROTECTED REGION ID(_oSx9sEABEeS2wtWjh0gEaw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
