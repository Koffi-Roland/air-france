package com.airfrance.repind.entity.individu;

/*PROTECTED REGION ID(_hagZcPKoEeKCpfUhWpPN4g i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/*PROTECTED REGION END*/


/**
 * <p>Title : PrefilledNumbers.java</p>
 * BO PrefilledNumbers
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="PREFILLED_NUMBERS")
public class PrefilledNumbers implements Serializable {

/*PROTECTED REGION ID(serialUID _hagZcPKoEeKCpfUhWpPN4g) ENABLED START*/
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
     * prefilledNumbersId
     */
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PREFILLED_NUMBERS")
    @SequenceGenerator(name="SEQ_PREFILLED_NUMBERS", sequenceName = "SEQ_PREFILLED_NUMBERS",
			allocationSize = 1)
    @Column(name="PREFILLED_NUMBERS_ID", length=12)
    private Integer prefilledNumbersId;
        
            
    /**
     * contractNumber
     */
    @Column(name="CONTRACT_NUMBER", length=20)
    private String contractNumber;
        
            
    /**
     * contractType
     */
    @Column(name="CONTRACT_TYPE", length=2)
    private String contractType;
        
            
    /**
     * sgin
     */
    @Column(name="SGIN", length=12)
    private String sgin;
        
            
    /**
     * creationDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATION_DATE")
    private Date creationDate;
        
            
    /**
     * creationSignature
     */
    @Column(name="CREATION_SIGNATURE")
    private String creationSignature;
        
            
    /**
     * creationSite
     */
    @Column(name="CREATION_SITE")
    private String creationSite;
        
            
    /**
     * modificationDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="MODIFICATION_DATE")
    private Date modificationDate;
        
            
    /**
     * modificationSignature
     */
    @Column(name="MODIFICATION_SIGNATURE")
    private String modificationSignature;
        
            
    /**
     * modificationSite
     */
    @Column(name="MODIFICATION_SITE")
    private String modificationSite;
        
            
    /**
     * prefilledNumbersData
     */
    // 1 <-> * 
    @OneToMany(mappedBy="prefilledNumbers", cascade=CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Set<PrefilledNumbersData> prefilledNumbersData;
        
    /*PROTECTED REGION ID(_hagZcPKoEeKCpfUhWpPN4g u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public PrefilledNumbers() {
    }
        
    /** 
     * full constructor
     * @param pPrefilledNumbersId prefilledNumbersId
     * @param pContractNumber contractNumber
     * @param pContractType contractType
     * @param pSgin sgin
     * @param pCreationDate creationDate
     * @param pCreationSignature creationSignature
     * @param pCreationSite creationSite
     * @param pModificationDate modificationDate
     * @param pModificationSignature modificationSignature
     * @param pModificationSite modificationSite
     */
    public PrefilledNumbers(Integer pPrefilledNumbersId, String pContractNumber, String pContractType, String pSgin, Date pCreationDate, String pCreationSignature, String pCreationSite, Date pModificationDate, String pModificationSignature, String pModificationSite) {
        this.prefilledNumbersId = pPrefilledNumbersId;
        this.contractNumber = pContractNumber;
        this.contractType = pContractType;
        this.sgin = pSgin;
        this.creationDate = pCreationDate;
        this.creationSignature = pCreationSignature;
        this.creationSite = pCreationSite;
        this.modificationDate = pModificationDate;
        this.modificationSignature = pModificationSignature;
        this.modificationSite = pModificationSite;
    }

    /**
     *
     * @return contractNumber
     */
    public String getContractNumber() {
        return this.contractNumber;
    }

    /**
     *
     * @param pContractNumber contractNumber value
     */
    public void setContractNumber(String pContractNumber) {
        this.contractNumber = pContractNumber;
    }

    /**
     *
     * @return contractType
     */
    public String getContractType() {
        return this.contractType;
    }

    /**
     *
     * @param pContractType contractType value
     */
    public void setContractType(String pContractType) {
        this.contractType = pContractType;
    }

    /**
     *
     * @return creationDate
     */
    public Date getCreationDate() {
        return this.creationDate;
    }

    /**
     *
     * @param pCreationDate creationDate value
     */
    public void setCreationDate(Date pCreationDate) {
        this.creationDate = pCreationDate;
    }

    /**
     *
     * @return creationSignature
     */
    public String getCreationSignature() {
        return this.creationSignature;
    }

    /**
     *
     * @param pCreationSignature creationSignature value
     */
    public void setCreationSignature(String pCreationSignature) {
        this.creationSignature = pCreationSignature;
    }

    /**
     *
     * @return creationSite
     */
    public String getCreationSite() {
        return this.creationSite;
    }

    /**
     *
     * @param pCreationSite creationSite value
     */
    public void setCreationSite(String pCreationSite) {
        this.creationSite = pCreationSite;
    }

    /**
     *
     * @return modificationDate
     */
    public Date getModificationDate() {
        return this.modificationDate;
    }

    /**
     *
     * @param pModificationDate modificationDate value
     */
    public void setModificationDate(Date pModificationDate) {
        this.modificationDate = pModificationDate;
    }

    /**
     *
     * @return modificationSignature
     */
    public String getModificationSignature() {
        return this.modificationSignature;
    }

    /**
     *
     * @param pModificationSignature modificationSignature value
     */
    public void setModificationSignature(String pModificationSignature) {
        this.modificationSignature = pModificationSignature;
    }

    /**
     *
     * @return modificationSite
     */
    public String getModificationSite() {
        return this.modificationSite;
    }

    /**
     *
     * @param pModificationSite modificationSite value
     */
    public void setModificationSite(String pModificationSite) {
        this.modificationSite = pModificationSite;
    }

    /**
     *
     * @return prefilledNumbersData
     */
    public Set<PrefilledNumbersData> getPrefilledNumbersData() {
        return this.prefilledNumbersData;
    }

    /**
     *
     * @param pPrefilledNumbersData prefilledNumbersData value
     */
    public void setPrefilledNumbersData(Set<PrefilledNumbersData> pPrefilledNumbersData) {
        this.prefilledNumbersData = pPrefilledNumbersData;
    }

    /**
     *
     * @return prefilledNumbersId
     */
    public Integer getPrefilledNumbersId() {
        return this.prefilledNumbersId;
    }

    /**
     *
     * @param pPrefilledNumbersId prefilledNumbersId value
     */
    public void setPrefilledNumbersId(Integer pPrefilledNumbersId) {
        this.prefilledNumbersId = pPrefilledNumbersId;
    }

    /**
     *
     * @return sgin
     */
    public String getSgin() {
        return this.sgin;
    }

    /**
     *
     * @param pSgin sgin value
     */
    public void setSgin(String pSgin) {
        this.sgin = pSgin;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_hagZcPKoEeKCpfUhWpPN4g) ENABLED START*/
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
        buffer.append("prefilledNumbersId=").append(getPrefilledNumbersId());
        buffer.append(",");
        buffer.append("contractNumber=").append(getContractNumber());
        buffer.append(",");
        buffer.append("contractType=").append(getContractType());
        buffer.append(",");
        buffer.append("sgin=").append(getSgin());
        buffer.append(",");
        buffer.append("creationDate=").append(getCreationDate());
        buffer.append(",");
        buffer.append("creationSignature=").append(getCreationSignature());
        buffer.append(",");
        buffer.append("creationSite=").append(getCreationSite());
        buffer.append(",");
        buffer.append("modificationDate=").append(getModificationDate());
        buffer.append(",");
        buffer.append("modificationSignature=").append(getModificationSignature());
        buffer.append(",");
        buffer.append("modificationSite=").append(getModificationSite());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _hagZcPKoEeKCpfUhWpPN4g) ENABLED START*/
    
    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

    	HashCodeBuilder builder = new HashCodeBuilder(17, 31);
    	builder = builder.append(sgin);
    	builder = builder.append(contractType);
    	
    	return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Two prefilled numbers are equals when :
     * <ul>
     *  <li>they have the same gin</li>
     *  <li>they have the same contract type</li>
     * </ul>
     * </p>
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

    	if(obj==this) {
    		return true;
    	}
    	
    	if(!(obj instanceof PrefilledNumbers)) {
    		return false;
    	}
    	
    	PrefilledNumbers other = (PrefilledNumbers)obj;
    	
    	EqualsBuilder builder = new EqualsBuilder();
    	builder.append(sgin,other.sgin);
    	builder.append(contractType,other.contractType);
    	
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

    /*PROTECTED REGION ID(_hagZcPKoEeKCpfUhWpPN4g u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
