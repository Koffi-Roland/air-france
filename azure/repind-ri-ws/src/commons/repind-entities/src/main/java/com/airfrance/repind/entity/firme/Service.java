package com.airfrance.repind.entity.firme;

/*PROTECTED REGION ID(_OsyLEBM3EeSrLrsauMPbtg i) ENABLED START*/

// add not generated imports here

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : Service.java</p>
 * BO Service
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@DiscriminatorValue("S")
public class Service extends PersonneMorale implements Serializable {

/*PROTECTED REGION ID(serialUID _OsyLEBM3EeSrLrsauMPbtg) ENABLED START*/
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

    /*PROTECTED REGION ID(_OsyLEBM3EeSrLrsauMPbtg u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public Service() {
    }
    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_OsyLEBM3EeSrLrsauMPbtg) ENABLED START*/
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
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _OsyLEBM3EeSrLrsauMPbtg) ENABLED START*/
    
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

    /*PROTECTED REGION ID(_OsyLEBM3EeSrLrsauMPbtg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
