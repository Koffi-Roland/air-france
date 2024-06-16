package com.airfrance.repind.dto.individu;

/*PROTECTED REGION ID(_G06wMPLPEeKCpfUhWpPN4g i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : PrefilledNumbersDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class PrefilledNumbersDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -8331119625986553164L;


	/**
     * prefilledNumbersId
     */
    private Integer prefilledNumbersId;
        
        
    /**
     * contractNumber
     */
    private String contractNumber;
        
        
    /**
     * contractType
     */
    private String contractType;
        
        
    /**
     * sgin
     */
    private String sgin;
        
        
    /**
     * creationDate
     */
    private Date creationDate;
        
        
    /**
     * creationSignature
     */
    private String creationSignature;
        
        
    /**
     * creationSite
     */
    private String creationSite;
        
        
    /**
     * modificationDate
     */
    private Date modificationDate;
        
        
    /**
     * modificationSignature
     */
    private String modificationSignature;
        
        
    /**
     * modificationSite
     */
    private String modificationSite;
        
        
    /**
     * prefilledNumbersDataDTO
     */
    private Set<PrefilledNumbersDataDTO> prefilledNumbersDataDTO;
        

    /*PROTECTED REGION ID(_G06wMPLPEeKCpfUhWpPN4g u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public PrefilledNumbersDTO() {
    
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
    public PrefilledNumbersDTO(Integer pPrefilledNumbersId, String pContractNumber, String pContractType, String pSgin, Date pCreationDate, String pCreationSignature, String pCreationSite, Date pModificationDate, String pModificationSignature, String pModificationSite) {
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
     * @return prefilledNumbersDataDTO
     */
    public Set<PrefilledNumbersDataDTO> getPrefilledNumbersDataDTO() {
        return this.prefilledNumbersDataDTO;
    }

    /**
     *
     * @param pPrefilledNumbersDataDTO prefilledNumbersDataDTO value
     */
    public void setPrefilledNumbersDataDTO(Set<PrefilledNumbersDataDTO> pPrefilledNumbersDataDTO) {
        this.prefilledNumbersDataDTO = pPrefilledNumbersDataDTO;
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
        /*PROTECTED REGION ID(toString_G06wMPLPEeKCpfUhWpPN4g) ENABLED START*/
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

    /*PROTECTED REGION ID(_G06wMPLPEeKCpfUhWpPN4g u m) ENABLED START*/
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
     * Two prefilled numbers dto are equals when :
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
    	
    	if(!(obj instanceof PrefilledNumbersDTO)) {
    		return false;
    	}
    	
    	PrefilledNumbersDTO other = (PrefilledNumbersDTO)obj;
    	
    	EqualsBuilder builder = new EqualsBuilder();
    	builder.append(sgin,other.sgin);
    	builder.append(contractType,other.contractType);
    	
    	return builder.isEquals();
    
    }
    /*PROTECTED REGION END*/

}
