package com.afklm.rigui.dto.external;

/*PROTECTED REGION ID(_Yvnh8E4kEeS-eLH--0fARw i) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.dto.individu.IndividuDTO;
import com.afklm.rigui.dto.individu.SignatureDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>Title : ExternalIdentifierDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ExternalIdentifierDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 6433465177157897816L;


	/**
     * GIN
     */
    private String gin;

    /**
     * identifierId
     */
    private Long identifierId;
        
        
    /**
     * identifier
     */
    private String identifier;
        
        
    /**
     * type
     */
    private String type;
        
        
    /**
     * lastSeenDate
     */
    private Date lastSeenDate;
        
        
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
     * externalIdentifierDataList
     */
    private List<ExternalIdentifierDataDTO> externalIdentifierDataList;
        
        
    /**
     * individu
     */
    private IndividuDTO individu;
        

    /*PROTECTED REGION ID(_Yvnh8E4kEeS-eLH--0fARw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ExternalIdentifierDTO() {
    
    }
    
    /** 
     * full constructor
     * @param pIdentifierId identifierId
     * @param pIdentifier identifier
     * @param pGin gin
     * @param pType type
     * @param pLastSeenDate lastSeenDate
     * @param pCreationDate creationDate
     * @param pCreationSignature creationSignature
     * @param pCreationSite creationSite
     * @param pModificationDate modificationDate
     * @param pModificationSignature modificationSignature
     * @param pModificationSite modificationSite
     */
    public ExternalIdentifierDTO(Long pIdentifierId, String pGin, String pIdentifier, String pType, Date pLastSeenDate, Date pCreationDate, String pCreationSignature, String pCreationSite, Date pModificationDate, String pModificationSignature, String pModificationSite) {
        this.identifierId = pIdentifierId;
        this.gin = pGin;
        this.identifier = pIdentifier;
        this.type = pType;
        this.lastSeenDate = pLastSeenDate;
        this.creationDate = pCreationDate;
        this.creationSignature = pCreationSignature;
        this.creationSite = pCreationSite;
        this.modificationDate = pModificationDate;
        this.modificationSignature = pModificationSignature;
        this.modificationSite = pModificationSite;
    }
    
    /** 
     * full constructor
     * @param pIdentifierId identifierId
     * @param pIdentifier identifier
     * @param pType type
     * @param pLastSeenDate lastSeenDate
     * @param pCreationDate creationDate
     * @param pCreationSignature creationSignature
     * @param pCreationSite creationSite
     * @param pModificationDate modificationDate
     * @param pModificationSignature modificationSignature
     * @param pModificationSite modificationSite
     */
    public ExternalIdentifierDTO(Long pIdentifierId, String pIdentifier, String pType, Date pLastSeenDate, Date pCreationDate, String pCreationSignature, String pCreationSite, Date pModificationDate, String pModificationSignature, String pModificationSite) {
        this.identifierId = pIdentifierId;
        this.identifier = pIdentifier;
        this.type = pType;
        this.lastSeenDate = pLastSeenDate;
        this.creationDate = pCreationDate;
        this.creationSignature = pCreationSignature;
        this.creationSite = pCreationSite;
        this.modificationDate = pModificationDate;
        this.modificationSignature = pModificationSignature;
        this.modificationSite = pModificationSite;
    }

    /**
    *
    * @return GIN
    */
   public String getGin() {
       return this.gin;
   }

   /**
    *
    * @param pGin GIN value
    */
   public void setGin(String pGin) {
       this.gin = pGin;
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
     * @return externalIdentifierDataList
     */
    public List<ExternalIdentifierDataDTO> getExternalIdentifierDataList() {
        return this.externalIdentifierDataList;
    }

    /**
     *
     * @param pExternalIdentifierDataList externalIdentifierDataList value
     */
    public void setExternalIdentifierDataList(List<ExternalIdentifierDataDTO> pExternalIdentifierDataList) {
        this.externalIdentifierDataList = pExternalIdentifierDataList;
    }

    /**
     *
     * @return identifier
     */
    public String getIdentifier() {
        return this.identifier;
    }

    /**
     *
     * @param pIdentifier identifier value
     */
    public void setIdentifier(String pIdentifier) {
        this.identifier = pIdentifier;
    }

    /**
     *
     * @return identifierId
     */
    public Long getIdentifierId() {
        return this.identifierId;
    }

    /**
     *
     * @param pIdentifierId identifierId value
     */
    public void setIdentifierId(Long pIdentifierId) {
        this.identifierId = pIdentifierId;
    }

    /**
     *
     * @return individu
     */
    public IndividuDTO getIndividu() {
        return this.individu;
    }

    /**
     *
     * @param pIndividu individu value
     */
    public void setIndividu(IndividuDTO pIndividu) {
        this.individu = pIndividu;
    }

    /**
     *
     * @return lastSeenDate
     */
    public Date getLastSeenDate() {
        return this.lastSeenDate;
    }

    /**
     *
     * @param pLastSeenDate lastSeenDate value
     */
    public void setLastSeenDate(Date pLastSeenDate) {
        this.lastSeenDate = pLastSeenDate;
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
        /*PROTECTED REGION ID(toString_Yvnh8E4kEeS-eLH--0fARw) ENABLED START*/
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
        buffer.append("identifierId=").append(getIdentifierId());
        buffer.append(",");
        buffer.append("gin=").append(getGin());
        buffer.append(",");
        buffer.append("identifier=").append(getIdentifier());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("lastSeenDate=").append(getLastSeenDate());
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

    /*PROTECTED REGION ID(_Yvnh8E4kEeS-eLH--0fARw u m) ENABLED START*/

    public void prepareForCreation(SignatureDTO signatureDTO) {

		Date today = new Date();
    	
    	setCreationDate(today);
    	setCreationSignature(signatureDTO.getSignature());
    	setCreationSite(signatureDTO.getSite());
    	
		setModificationDate(today);
		setModificationSignature(signatureDTO.getSignature());
		setModificationSite(signatureDTO.getSite());
		
		setLastSeenDate(today);
	}
	
	public void prepareForUpdate(SignatureDTO signatureDTO) {
		
		Date today = new Date();
		
		setModificationDate(today);
		setModificationSignature(signatureDTO.getSignature());
		setModificationSite(signatureDTO.getSite());
		
		setLastSeenDate(today);
	}
    
    /*PROTECTED REGION END*/

}
