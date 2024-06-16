package com.airfrance.repind.entity.reference;
/*PROTECTED REGION ID(_1LTBQPz9EeaexJbSRqCy0Q iComposite) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefProductOwnerId.java</p>
 * BO RefProductOwner
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Embeddable
public class RefProductOwnerId implements Serializable {

    /** 
     * default constructor 
     */
    public RefProductOwnerId() {
    	//empty constructor
    }

        
    /** Attribute*/
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PRODUCTID", nullable=false)
    @ForeignKey(name = "FK_REF_PRODUCT_OWNER_FK1")
    private RefProduct refProduct;
        
    /** Attribute*/
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="OWNERID", nullable=false)
    @ForeignKey(name = "FK_REF_PRODUCT_OWNER_FK2")
    private RefOwner refOwner;
    
    /*PROTECTED REGION ID(_1LTBQPz9EeaexJbSRqCy0Q u varComposite) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     *
     * @return refProduct
     */
    public RefProduct getRefProduct() {
        return this.refProduct;
    }

    /**
     *
     * @param pRefProduct refProduct value
     */
    public void setRefProduct(RefProduct pRefProduct) {
        this.refProduct = pRefProduct;
    }

    /**
     *
     * @return refOwner
     */
    public RefOwner getRefOwner() {
        return this.refOwner;
    }

    /**
     *
     * @param pRefOwner refOwner value
     */
    public void setRefOwner(RefOwner pRefOwner) {
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
        buffer.append("refProduct=").append(getRefProduct());
    buffer.append(",");
        buffer.append("refOwner=").append(getRefOwner());
        buffer.append("]");
        return buffer.toString();
    }
    
    
    
    /*PROTECTED REGION ID(equals hash compID_1LTBQPz9EeaexJbSRqCy0Q) ENABLED START*/
    
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



    /*PROTECTED REGION ID(_1LTBQPz9EeaexJbSRqCy0Q u mComposite) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
