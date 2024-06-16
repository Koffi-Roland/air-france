package com.airfrance.repind.entity.zone;

/*PROTECTED REGION ID(_BgpHULbOEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/*PROTECTED REGION END*/


/**
 * <p>Title : ZoneComm.java</p>
 * BO ZoneComm
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@DiscriminatorValue("ZC")
@SecondaryTable(name="ZONE_COMM")
public class ZoneComm extends ZoneDecoup implements Serializable {

/*PROTECTED REGION ID(serialUID _BgpHULbOEeCrCZp8iGNNVw) ENABLED START*/
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

            
    /*PROTECTED REGION ID(_BgpHU7bOEeCrCZp8iGNNVw p) ENABLED START*/
    /**
     * zc1
     */
    @Column(table="ZONE_COMM", name="SZC1", length=3)
    private String zc1;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_BgpHVLbOEeCrCZp8iGNNVw p) ENABLED START*/
    /**
     * zc2
     */
    @Column(table="ZONE_COMM", name="SZC2", length=3)
    private String zc2;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_BgpHVbbOEeCrCZp8iGNNVw p) ENABLED START*/
    /**
     * zc3
     */
    @Column(table="ZONE_COMM", name="SZC3", length=5)
    private String zc3;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_BgpHVrbOEeCrCZp8iGNNVw p) ENABLED START*/
    /**
     * zc4
     */
    @Column(table="ZONE_COMM", name="SZC4", length=5)
    private String zc4;
    /*PROTECTED REGION END*/
        
            
    /*PROTECTED REGION ID(_BgpHV7bOEeCrCZp8iGNNVw p) ENABLED START*/
    /**
     * zc5
     */
    @Column(table="ZONE_COMM", name="SZC5", length=2)
    private String zc5;
    /*PROTECTED REGION END*/
        
            
    /**
     * liensZv
     */
    // 1 <-> * 
    @OneToMany(mappedBy="zoneComm")
    private Set<LienZvZc> liensZv;
        
    /*PROTECTED REGION ID(_BgpHULbOEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public ZoneComm() {
    }
        
    /** 
     * full constructor
     * @param pZc1 zc1
     * @param pZc2 zc2
     * @param pZc3 zc3
     * @param pZc4 zc4
     * @param pZc5 zc5
     */
    public ZoneComm(String pZc1, String pZc2, String pZc3, String pZc4, String pZc5) {
        this.zc1 = pZc1;
        this.zc2 = pZc2;
        this.zc3 = pZc3;
        this.zc4 = pZc4;
        this.zc5 = pZc5;
    }

    /**
     *
     * @return liensZv
     */
    public Set<LienZvZc> getLiensZv() {
        return this.liensZv;
    }

    /**
     *
     * @param pLiensZv liensZv value
     */
    public void setLiensZv(Set<LienZvZc> pLiensZv) {
        this.liensZv = pLiensZv;
    }

    /**
     *
     * @return zc1
     */
    public String getZc1() {
        return this.zc1;
    }

    /**
     *
     * @param pZc1 zc1 value
     */
    public void setZc1(String pZc1) {
        this.zc1 = pZc1;
    }

    /**
     *
     * @return zc2
     */
    public String getZc2() {
        return this.zc2;
    }

    /**
     *
     * @param pZc2 zc2 value
     */
    public void setZc2(String pZc2) {
        this.zc2 = pZc2;
    }

    /**
     *
     * @return zc3
     */
    public String getZc3() {
        return this.zc3;
    }

    /**
     *
     * @param pZc3 zc3 value
     */
    public void setZc3(String pZc3) {
        this.zc3 = pZc3;
    }

    /**
     *
     * @return zc4
     */
    public String getZc4() {
        return this.zc4;
    }

    /**
     *
     * @param pZc4 zc4 value
     */
    public void setZc4(String pZc4) {
        this.zc4 = pZc4;
    }

    /**
     *
     * @return zc5
     */
    public String getZc5() {
        return this.zc5;
    }

    /**
     *
     * @param pZc5 zc5 value
     */
    public void setZc5(String pZc5) {
        this.zc5 = pZc5;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_BgpHULbOEeCrCZp8iGNNVw) ENABLED START*/
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
        buffer.append("zc1=").append(getZc1());
        buffer.append(",");
        buffer.append("zc2=").append(getZc2());
        buffer.append(",");
        buffer.append("zc3=").append(getZc3());
        buffer.append(",");
        buffer.append("zc4=").append(getZc4());
        buffer.append(",");
        buffer.append("zc5=").append(getZc5());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _BgpHULbOEeCrCZp8iGNNVw) ENABLED START*/

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
        final ZoneComm other = (ZoneComm) obj;

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

    /*PROTECTED REGION ID(_BgpHULbOEeCrCZp8iGNNVw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
