package com.airfrance.repind.dto.external;

/*PROTECTED REGION ID(_YvniAU4kEeS-eLH--0fARw i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.util.SicStringUtils;

import java.io.Serializable;
import java.util.Date;


/*PROTECTED REGION END*/

/**
 * <p>Title : ExternalIdentifierDataDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ExternalIdentifierDataDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -4140651821222757083L;


	/**
     * identifierDataId
     */
    private Long identifierDataId;
        
        
    /**
     * key
     */
    private String key;
        
        
    /**
     * value
     */
    private String value;
        
        
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
     * externalIdentifier
     */
    private ExternalIdentifierDTO externalIdentifier;
        

    /*PROTECTED REGION ID(_YvniAU4kEeS-eLH--0fARw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ExternalIdentifierDataDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pIdentifierDataId identifierDataId
     * @param pKey key
     * @param pValue value
     * @param pCreationDate creationDate
     * @param pCreationSignature creationSignature
     * @param pCreationSite creationSite
     * @param pModificationDate modificationDate
     * @param pModificationSignature modificationSignature
     * @param pModificationSite modificationSite
     */
    public ExternalIdentifierDataDTO(Long pIdentifierDataId, String pKey, String pValue, Date pCreationDate, String pCreationSignature, String pCreationSite, Date pModificationDate, String pModificationSignature, String pModificationSite) {
        this.identifierDataId = pIdentifierDataId;
        this.key = pKey;
        //this.value = pValue;
        this.setValue(pValue); //execute the function to verify is exist bad char - IM02214017
        this.creationDate = pCreationDate;
        this.creationSignature = pCreationSignature;
        this.creationSite = pCreationSite;
        this.modificationDate = pModificationDate;
        this.modificationSignature = pModificationSignature;
        this.modificationSite = pModificationSite;
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
     * @return externalIdentifier
     */
    public ExternalIdentifierDTO getExternalIdentifier() {
        return this.externalIdentifier;
    }

    /**
     *
     * @param pExternalIdentifier externalIdentifier value
     */
    public void setExternalIdentifier(ExternalIdentifierDTO pExternalIdentifier) {
        this.externalIdentifier = pExternalIdentifier;
    }

    /**
     *
     * @return identifierDataId
     */
    public Long getIdentifierDataId() {
        return this.identifierDataId;
    }

    /**
     *
     * @param pIdentifierDataId identifierDataId value
     */
    public void setIdentifierDataId(Long pIdentifierDataId) {
        this.identifierDataId = pIdentifierDataId;
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
     * @return value
     */
    public String getValue() {
        return SicStringUtils.cleanBadChar(this.value); //check if every char is alphanum - IM02214017
    	//return this.value
    }

    /**
     *
     * @param pValue value value
     */
    public void setValue(String pValue) {
        this.value = SicStringUtils.cleanBadChar(pValue); //check if every char is alphanum - IM02214017
    	//this.value = pValue;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_YvniAU4kEeS-eLH--0fARw) ENABLED START*/
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
        buffer.append("identifierDataId=").append(getIdentifierDataId());
        buffer.append(",");
        buffer.append("key=").append(getKey());
        buffer.append(",");
        buffer.append("value=").append(getValue());
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

    /*PROTECTED REGION ID(_YvniAU4kEeS-eLH--0fARw u m) ENABLED START*/

    public void prepareForCreation(SignatureDTO signatureDTO) {

		Date today = new Date();
    	
    	setCreationDate(today);
    	setCreationSignature(signatureDTO.getSignature());
    	setCreationSite(signatureDTO.getSite());
    	
		setModificationDate(today);
		setModificationSignature(signatureDTO.getSignature());
		setModificationSite(signatureDTO.getSite());
		
	}
	
	
    
    /*PROTECTED REGION END*/

}
