package com.airfrance.repind.dto.ws.createupdaterolecontract.v1;


import com.airfrance.repind.dto.individu.WarningResponseDTO;
import com.airfrance.repind.dto.ws.CommunicationPreferencesResponseDTO;

import java.io.Serializable;

/**
 * <p>Title : CreateUpdateRoleContractResponseDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2018</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class CreateUpdateRoleContractResponseDTO implements Serializable {
        
    /**
     * success
     */
    private Boolean success;
        
        
    /**
     * contractNumber
     */
    private String contractNumber;
        
        
    /**
     * communicationPreferenceResponse
     */
    private CommunicationPreferencesResponseDTO communicationPreferenceResponse;
        
        
    /**
     * warningResponse
     */
    private WarningResponseDTO warningResponse;
    
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
    
    /** 
     * default constructor 
     */
    public CreateUpdateRoleContractResponseDTO() {
    //empty constructor
    }
	    
    /**
	 * @return the success
	 */
	public Boolean getSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	/**
	 * @return the contractNumber
	 */
	public String getContractNumber() {
		return contractNumber;
	}

	/**
	 * @param contractNumber the contractNumber to set
	 */
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	/**
	 * @return the communicationPreferenceResponse
	 */
	public CommunicationPreferencesResponseDTO getCommunicationPreferenceResponse() {
		return communicationPreferenceResponse;
	}

	/**
	 * @param communicationPreferenceResponse the communicationPreferenceResponse to set
	 */
	public void setCommunicationPreferenceResponse(CommunicationPreferencesResponseDTO communicationPreferenceResponse) {
		this.communicationPreferenceResponse = communicationPreferenceResponse;
	}

	/**
	 * @return the warningResponse
	 */
	public WarningResponseDTO getWarningResponse() {
		return warningResponse;
	}

	/**
	 * @param warningResponse the warningResponse to set
	 */
	public void setWarningResponse(WarningResponseDTO warningResponse) {
		this.warningResponse = warningResponse;
	}

	/**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        result = toStringImpl();
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
        buffer.append("success=").append(getSuccess());
        buffer.append(",");
        buffer.append("contractNumber=").append(getContractNumber());
        buffer.append(",");
        buffer.append("communicationPreferenceResponse=").append(getCommunicationPreferenceResponse());
        buffer.append(",");
        buffer.append("warningResponse=").append(getWarningResponse());
        buffer.append("]");
        return buffer.toString();
    }

}
