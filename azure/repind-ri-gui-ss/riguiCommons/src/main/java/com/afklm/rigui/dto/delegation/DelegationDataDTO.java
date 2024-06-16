package com.afklm.rigui.dto.delegation;

/*PROTECTED REGION ID(_KcW4EJSNEeOwS89XbJiNOw i) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.dto.individu.IndividuDTO;
import com.afklm.rigui.dto.individu.SignatureDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : DelegationDataDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class DelegationDataDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -7762006393071664605L;


	/**
     * delegationId
     */
    private Integer delegationId;
        
        
    /**
     * status
     */
    private String status;
        
        
    /**
     * type
     */
    private String type;
        
        
    /**
     * creationSite
     */
    private String creationSite;
        
        
    /**
     * creationDate
     */
    private Date creationDate;
        
        
    /**
     * creationSignature
     */
    private String creationSignature;
        
        
    /**
     * modificationSite
     */
    private String modificationSite;
        
        
    /**
     * modificationSignature
     */
    private String modificationSignature;
        
        
    /**
     * modificationDate
     */
    private Date modificationDate;
        
        
    /**
     * sender
     */
    private String sender;
        
        
    /**
     * delegateDTO
     */
    private IndividuDTO delegateDTO;
        
        
    /**
     * delegatorDTO
     */
    private IndividuDTO delegatorDTO;
    
    /**
     * delegationDataInfoDTO
     */
    private Set<DelegationDataInfoDTO> delegationDataInfoDTO;

	

    /*PROTECTED REGION ID(_KcW4EJSNEeOwS89XbJiNOw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

	
	/**
	*
	* @return delegationDataInfoDTO
	*/
	public Set<DelegationDataInfoDTO> getDelegationDataInfoDTO() {
	   return this.delegationDataInfoDTO;
	}
	
	   /**
	*
	* @param pDelegationDataInfoDTO delegationDataInfoDTO value
	*/
	public void setDelegationDataInfoDTO(Set<DelegationDataInfoDTO> pDelegationDataInfoDTO) {
	    this.delegationDataInfoDTO = pDelegationDataInfoDTO;
	}
    
    
	
	/**
	 * default constructor
	 */
    public DelegationDataDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pDelegationId delegationId
     * @param pStatus status
     * @param pType type
     * @param pCreationSite creationSite
     * @param pCreationDate creationDate
     * @param pCreationSignature creationSignature
     * @param pModificationSite modificationSite
     * @param pModificationSignature modificationSignature
     * @param pModificationDate modificationDate
     * @param pSender sender
     */
    public DelegationDataDTO(Integer pDelegationId, String pStatus, String pType, String pCreationSite, Date pCreationDate, String pCreationSignature, String pModificationSite, String pModificationSignature, Date pModificationDate, String pSender) {
        this.delegationId = pDelegationId;
        this.status = pStatus;
        this.type = pType;
        this.creationSite = pCreationSite;
        this.creationDate = pCreationDate;
        this.creationSignature = pCreationSignature;
        this.modificationSite = pModificationSite;
        this.modificationSignature = pModificationSignature;
        this.modificationDate = pModificationDate;
        this.sender = pSender;
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
     * @return delegateDTO
     */
    public IndividuDTO getDelegateDTO() {
        return this.delegateDTO;
    }

    /**
     *
     * @param pDelegateDTO delegateDTO value
     */
    public void setDelegateDTO(IndividuDTO pDelegateDTO) {
        this.delegateDTO = pDelegateDTO;
    }

    /**
     *
     * @return delegationId
     */
    public Integer getDelegationId() {
        return this.delegationId;
    }

    /**
     *
     * @param pDelegationId delegationId value
     */
    public void setDelegationId(Integer pDelegationId) {
        this.delegationId = pDelegationId;
    }

    /**
     *
     * @return delegatorDTO
     */
    public IndividuDTO getDelegatorDTO() {
        return this.delegatorDTO;
    }

    /**
     *
     * @param pDelegatorDTO delegatorDTO value
     */
    public void setDelegatorDTO(IndividuDTO pDelegatorDTO) {
        this.delegatorDTO = pDelegatorDTO;
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
     * @return sender
     */
    public String getSender() {
        return this.sender;
    }

    /**
     *
     * @param pSender sender value
     */
    public void setSender(String pSender) {
        this.sender = pSender;
    }

    /**
     *
     * @return status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     *
     * @param pStatus status value
     */
    public void setStatus(String pStatus) {
        this.status = pStatus;
    }

    /**
     *
     * @return type
     */
    public String getType() {
        return this.type;
    }

    /**
     *
     * @param pType type value
     */
    public void setType(String pType) {
        this.type = pType;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_KcW4EJSNEeOwS89XbJiNOw) ENABLED START*/
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
        buffer.append("delegationId=").append(getDelegationId());
        buffer.append(",");
        buffer.append("status=").append(getStatus());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("creationSite=").append(getCreationSite());
        buffer.append(",");
        buffer.append("creationDate=").append(getCreationDate());
        buffer.append(",");
        buffer.append("creationSignature=").append(getCreationSignature());
        buffer.append(",");
        buffer.append("modificationSite=").append(getModificationSite());
        buffer.append(",");
        buffer.append("modificationSignature=").append(getModificationSignature());
        buffer.append(",");
        buffer.append("modificationDate=").append(getModificationDate());
        buffer.append(",");
        buffer.append("sender=").append(getSender());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_KcW4EJSNEeOwS89XbJiNOw u m) ENABLED START*/

    public void prepareForCreation(SignatureDTO signatureDTO) {

		Date today = new Date();
		//Can have the same Date for Delegation and Postal Address and Telecom
		signatureDTO.setDate(today);

		setCreationDate(today);
		setCreationSignature(signatureDTO.getSignature());
		setCreationSite(signatureDTO.getSite());
    	
		setModificationDate(today);
		setModificationSignature(signatureDTO.getSignature());
		setModificationSite(signatureDTO.getSite());
		
	}
	
	public void prepareForUpdate(SignatureDTO signatureDTO, DelegationDataDTO delegationDataDTO) {
		
		Date today = new Date();
		//Can have the same Date for Delegation and Postal Address and Telecom
		delegationDataDTO.setModificationDate(today);
		
		setModificationDate(today);
		setModificationSignature(signatureDTO.getSignature());
		setModificationSite(signatureDTO.getSite());
	}
	
    /*PROTECTED REGION END*/

}
