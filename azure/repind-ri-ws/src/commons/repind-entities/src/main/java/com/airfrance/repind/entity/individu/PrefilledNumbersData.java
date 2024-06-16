package com.airfrance.repind.entity.individu;

/*PROTECTED REGION ID(_qlo4EJ2XEeWBdds6EPJFhg i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : PrefilledNumbersData.java</p>
 * BO PrefilledNumbersData
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="PREFILLED_NUMBERS_DATA")
public class PrefilledNumbersData implements Serializable {

/*PROTECTED REGION ID(serialUID _qlo4EJ2XEeWBdds6EPJFhg) ENABLED START*/
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
     * prefilledNumbersDataId
     */
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PREFILLED_NUMBERS_DATA")
    @SequenceGenerator(name="SEQ_PREFILLED_NUMBERS_DATA", sequenceName = "SEQ_PREFILLED_NUMBERS_DATA",
			allocationSize = 1)
    @Column(name="PREFILLED_NUMBERS_DATA_ID")
    private Integer prefilledNumbersDataId;
        
            
    /**
     * key
     */
    @Column(name="KEY")
    private String key;
        
            
    /**
     * value
     */
    @Column(name="VALUE")
    private String value;
        
            
    /**
     * prefilledNumbers
     */
    // * <-> 1
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="PREFILLED_NUMBERS_ID", nullable=true)
    @ForeignKey(name = "FK_PND_PN")
    private PrefilledNumbers prefilledNumbers;
        
    /*PROTECTED REGION ID(_qlo4EJ2XEeWBdds6EPJFhg u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public PrefilledNumbersData() {
    }
        
    /** 
     * full constructor
     * @param pPrefilledNumbersDataId prefilledNumbersDataId
     * @param pKey key
     * @param pValue value
     */
    public PrefilledNumbersData(Integer pPrefilledNumbersDataId, String pKey, String pValue) {
        this.prefilledNumbersDataId = pPrefilledNumbersDataId;
        this.key = pKey;
        this.value = pValue;
    }

    /**
     *
     * @return key
     */
    public String getKey() {
        return this.key;
    }

    /**
     *
     * @param pKey key value
     */
    public void setKey(String pKey) {
        this.key = pKey;
    }

    /**
     *
     * @return prefilledNumbers
     */
    public PrefilledNumbers getPrefilledNumbers() {
        return this.prefilledNumbers;
    }

    /**
     *
     * @param pPrefilledNumbers prefilledNumbers value
     */
    public void setPrefilledNumbers(PrefilledNumbers pPrefilledNumbers) {
        this.prefilledNumbers = pPrefilledNumbers;
    }

    /**
     *
     * @return prefilledNumbersDataId
     */
    public Integer getPrefilledNumbersDataId() {
        return this.prefilledNumbersDataId;
    }

    /**
     *
     * @param pPrefilledNumbersDataId prefilledNumbersDataId value
     */
    public void setPrefilledNumbersDataId(Integer pPrefilledNumbersDataId) {
        this.prefilledNumbersDataId = pPrefilledNumbersDataId;
    }

    /**
     *
     * @return value
     */
    public String getValue() {
        return this.value;
    }

    /**
     *
     * @param pValue value value
     */
    public void setValue(String pValue) {
        this.value = pValue;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_qlo4EJ2XEeWBdds6EPJFhg) ENABLED START*/
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
        buffer.append("prefilledNumbersDataId=").append(getPrefilledNumbersDataId());
        buffer.append(",");
        buffer.append("key=").append(getKey());
        buffer.append(",");
        buffer.append("value=").append(getValue());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _qlo4EJ2XEeWBdds6EPJFhg) ENABLED START*/
    
    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
    	HashCodeBuilder builder = new HashCodeBuilder(17, 31);
    	builder = builder.append(prefilledNumbersDataId);
    	builder = builder.append(key);
    	builder = builder.append(value);
    	
    	return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
    	if(obj==this) {
    		return true;
    	}
    	
    	if(!(obj instanceof PrefilledNumbersData)) {
    		return false;
    	}
    	
    	PrefilledNumbersData other = (PrefilledNumbersData)obj;
    	
    	EqualsBuilder builder = new EqualsBuilder();
    	builder.append(prefilledNumbersDataId,other.prefilledNumbersDataId);
    	builder.append(key,other.key);
    	builder.append(value,other.value);
    	
    	return builder.isEquals();
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

    /*PROTECTED REGION ID(_qlo4EJ2XEeWBdds6EPJFhg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
