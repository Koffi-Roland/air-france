package com.airfrance.repind.entity.firme;

/*PROTECTED REGION ID(_UJDegGQgEeSRQ7C-gEfj8g i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : SelfBookingTool.java</p>
 * BO SelfBookingTool
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="PM_SBT")
public class SelfBookingTool implements Serializable {

/*PROTECTED REGION ID(serialUID _UJDegGQgEeSRQ7C-gEfj8g) ENABLED START*/
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
     * gin
     */
    @Id
    @GeneratedValue(generator = "foreignGenerator")
    @GenericGenerator(name = "foreignGenerator", strategy = "foreign", parameters = { @Parameter(name = "property", value = "personneMorale") })
    @Column(name="PS_SGIN", length=12, nullable=false)
    private String gin;
        
            
    /**
     * portalGdsCode
     */
    @Column(name="RPT_SCODE", length=2)
    private String portalGdsCode;
        
            
    /**
     * sbtCode
     */
    @Column(name="RSBT_SCODE", length=2)
    private String sbtCode;
        
            
    /**
     * personneMorale
     */
    // 1 <-> 1
    @OneToOne(mappedBy="selfBookingTool")
    private PersonneMorale personneMorale;
        
    /*PROTECTED REGION ID(_UJDegGQgEeSRQ7C-gEfj8g u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public SelfBookingTool() {
    }
        
    /** 
     * full constructor
     * @param pGin gin
     * @param pPortalGdsCode portalGdsCode
     * @param pSbtCode sbtCode
     */
    public SelfBookingTool(String pGin, String pPortalGdsCode, String pSbtCode) {
        this.gin = pGin;
        this.portalGdsCode = pPortalGdsCode;
        this.sbtCode = pSbtCode;
    }

    /**
     *
     * @return gin
     */
    public String getGin() {
        return this.gin;
    }

    /**
     *
     * @param pGin gin value
     */
    public void setGin(String pGin) {
        this.gin = pGin;
    }

    /**
     *
     * @return personneMorale
     */
    public PersonneMorale getPersonneMorale() {
        return this.personneMorale;
    }

    /**
     *
     * @param pPersonneMorale personneMorale value
     */
    public void setPersonneMorale(PersonneMorale pPersonneMorale) {
        this.personneMorale = pPersonneMorale;
    }

    /**
     *
     * @return portalGdsCode
     */
    public String getPortalGdsCode() {
        return this.portalGdsCode;
    }

    /**
     *
     * @param pPortalGdsCode portalGdsCode value
     */
    public void setPortalGdsCode(String pPortalGdsCode) {
        this.portalGdsCode = pPortalGdsCode;
    }

    /**
     *
     * @return sbtCode
     */
    public String getSbtCode() {
        return this.sbtCode;
    }

    /**
     *
     * @param pSbtCode sbtCode value
     */
    public void setSbtCode(String pSbtCode) {
        this.sbtCode = pSbtCode;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_UJDegGQgEeSRQ7C-gEfj8g) ENABLED START*/
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
        buffer.append("gin=").append(getGin());
        buffer.append(",");
        buffer.append("portalGdsCode=").append(getPortalGdsCode());
        buffer.append(",");
        buffer.append("sbtCode=").append(getSbtCode());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _UJDegGQgEeSRQ7C-gEfj8g) ENABLED START*/
    
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

    /*PROTECTED REGION ID(_UJDegGQgEeSRQ7C-gEfj8g u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
