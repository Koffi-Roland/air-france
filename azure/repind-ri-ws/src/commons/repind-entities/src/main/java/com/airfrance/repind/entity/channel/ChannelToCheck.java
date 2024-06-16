package com.airfrance.repind.entity.channel;

/*PROTECTED REGION ID(_z3atQN8FEeWpIP6zY_fX5w i) ENABLED START*/

// add not generated imports here

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : ChannelToCheck.java</p>
 * BO ChannelToCheck
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="CHANNEL_TO_CHECK")
public class ChannelToCheck implements Serializable {

/*PROTECTED REGION ID(serialUID _z3atQN8FEeWpIP6zY_fX5w) ENABLED START*/
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
     * id
     */
    @Id
    @Column(name="ID")
    private Integer id;
        
            
    /**
     * channel
     */
    @Column(name="CHANNEL")
    private String channel;
        
    /*PROTECTED REGION ID(_z3atQN8FEeWpIP6zY_fX5w u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public ChannelToCheck() {
    }
        
    /** 
     * full constructor
     * @param pId id
     * @param pChannel channel
     */
    public ChannelToCheck(Integer pId, String pChannel) {
        this.id = pId;
        this.channel = pChannel;
    }

    /**
     *
     * @return channel
     */
    public String getChannel() {
        return this.channel;
    }

    /**
     *
     * @param pChannel channel value
     */
    public void setChannel(String pChannel) {
        this.channel = pChannel;
    }

    /**
     *
     * @return id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     *
     * @param pId id value
     */
    public void setId(Integer pId) {
        this.id = pId;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_z3atQN8FEeWpIP6zY_fX5w) ENABLED START*/
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
        buffer.append("id=").append(getId());
        buffer.append(",");
        buffer.append("channel=").append(getChannel());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _z3atQN8FEeWpIP6zY_fX5w) ENABLED START*/
    
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

    /*PROTECTED REGION ID(_z3atQN8FEeWpIP6zY_fX5w u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
