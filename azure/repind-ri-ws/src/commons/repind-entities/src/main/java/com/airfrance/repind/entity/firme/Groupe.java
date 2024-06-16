package com.airfrance.repind.entity.firme;

/*PROTECTED REGION ID(_bWI8ILbCEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.SecondaryTable;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : Groupe.java</p>
 * BO Groupe
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@DiscriminatorValue("G")
@SecondaryTable(name="GROUPE")
public class Groupe extends PersonneMorale implements Serializable {

/*PROTECTED REGION ID(serialUID _bWI8ILbCEeCrCZp8iGNNVw) ENABLED START*/
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

            
    /*PROTECTED REGION ID(_bWI8I7bCEeCrCZp8iGNNVw p) ENABLED START*/
    /**
     * scode
     */
    @Column(table="GROUPE", name="SCODE", length=5)
    private String code;
    /*PROTECTED REGION END*/
        
    /*PROTECTED REGION ID(_bWI8ILbCEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public Groupe() {
    }
        
    /** 
     * full constructor
     * @param pCode code
     */
    public Groupe(String pCode) {
        this.code = pCode;
    }

    /**
     *
     * @return code
     */
    public String getCode() {
        return this.code;
    }

    /**
     *
     * @param pCode code value
     */
    public void setCode(String pCode) {
        this.code = pCode;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_bWI8ILbCEeCrCZp8iGNNVw) ENABLED START*/
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
        buffer.append("code=").append(getCode());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _bWI8ILbCEeCrCZp8iGNNVw) ENABLED START*/

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
        final Groupe other = (Groupe) obj;

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

    /*PROTECTED REGION ID(_bWI8ILbCEeCrCZp8iGNNVw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
