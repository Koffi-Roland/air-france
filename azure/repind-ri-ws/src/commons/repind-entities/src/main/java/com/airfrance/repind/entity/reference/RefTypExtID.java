package com.airfrance.repind.entity.reference;

/*PROTECTED REGION ID(_mr0sEI-FEeamIv5tbprkCg i) ENABLED START*/

// add not generated imports here

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : RefTypExtID.java</p>
 * BO RefTypExtID
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="REF_TYP_EXT_ID")
public class RefTypExtID implements Serializable {

/*PROTECTED REGION ID(serialUID _mr0sEI-FEeamIv5tbprkCg) ENABLED START*/
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
     * extID
     */
    @Id
    @Column(name="EXT_ID", length=15, nullable=false)
    private String extID;
        
            
    /**
     * libelle
     */
    @Column(name="SLIBELLE", length=25)
    private String libelle;
        
            
    /**
     * option
     */
    @Column(name="SOPTION", length=3)
    private String option;
        
    /*PROTECTED REGION ID(_mr0sEI-FEeamIv5tbprkCg u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public RefTypExtID() {
    	//empty constructor
    }
        
    /** 
     * full constructor
     * @param pExtID extID
     * @param pLibelle libelle
     * @param pOption option
     */
    public RefTypExtID(String pExtID, String pLibelle, String pOption) {
        this.extID = pExtID;
        this.libelle = pLibelle;
        this.option = pOption;
    }

    /**
     *
     * @return extID
     */
    public String getExtID() {
        return this.extID;
    }

    /**
     *
     * @param pExtID extID value
     */
    public void setExtID(String pExtID) {
        this.extID = pExtID;
    }

    /**
     *
     * @return libelle
     */
    public String getLibelle() {
        return this.libelle;
    }

    /**
     *
     * @param pLibelle libelle value
     */
    public void setLibelle(String pLibelle) {
        this.libelle = pLibelle;
    }

    /**
     *
     * @return option
     */
    public String getOption() {
        return this.option;
    }

    /**
     *
     * @param pOption option value
     */
    public void setOption(String pOption) {
        this.option = pOption;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_mr0sEI-FEeamIv5tbprkCg) ENABLED START*/
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
        buffer.append("extID=").append(getExtID());
        buffer.append(",");
        buffer.append("libelle=").append(getLibelle());
        buffer.append(",");
        buffer.append("option=").append(getOption());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _mr0sEI-FEeamIv5tbprkCg) ENABLED START*/
    
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

    /*PROTECTED REGION ID(_mr0sEI-FEeamIv5tbprkCg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
