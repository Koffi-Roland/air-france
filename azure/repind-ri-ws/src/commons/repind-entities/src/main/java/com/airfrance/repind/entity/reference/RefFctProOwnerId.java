package com.airfrance.repind.entity.reference;
/*PROTECTED REGION ID(_puOBgDyZEeeO_ZXPvPFEyw iComposite) ENABLED START*/

// add not generated imports here

import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : RefFctProOwnerId.java</p>
 * BO RefFctProOwner
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@javax.persistence.Embeddable
public class RefFctProOwnerId implements Serializable {

    /** 
     * default constructor 
     */
    public RefFctProOwnerId() {
    	//empty constructor
    }

        

        
    /** Attribute*/
    @javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
    @javax.persistence.JoinColumn(name="OWNERID", nullable=false)
    @org.hibernate.annotations.ForeignKey(name = "FK_REF_FCTPRO_OWNER_OWNERID")
    private RefOwner refOwner;
    
    /*PROTECTED REGION ID(_puOBgDyZEeeO_ZXPvPFEyw u varComposite) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    


    /**
     *
     * @return refOwner
     */
    public com.airfrance.repind.entity.reference.RefOwner getRefOwner() {
        return this.refOwner;
    }

    /**
     *
     * @param pRefOwner refOwner value
     */
    public void setRefOwner(com.airfrance.repind.entity.reference.RefOwner pRefOwner) {
        this.refOwner = pRefOwner;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@").append(Integer.toHexString(System.identityHashCode(this)));
        buffer.append("[");
    buffer.append(",");
        buffer.append("refOwner=").append(getRefOwner());
        buffer.append("]");
        return buffer.toString();
    }
    
    
    
    /*PROTECTED REGION ID(equals hash compID_puOBgDyZEeeO_ZXPvPFEyw) ENABLED START*/
    
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




}
