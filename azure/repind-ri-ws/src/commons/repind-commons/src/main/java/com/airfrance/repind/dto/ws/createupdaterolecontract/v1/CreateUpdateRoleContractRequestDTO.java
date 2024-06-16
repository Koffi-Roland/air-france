package com.airfrance.repind.dto.ws.createupdaterolecontract.v1;


import com.airfrance.repind.dto.ws.RequestorDTO;

import java.io.Serializable;

/**
 * <p>Title : CreateUpdateRoleContractRequestDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2018</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class CreateUpdateRoleContractRequestDTO implements Serializable {

    /**
     * gin
     */
    private String gin;
        
        
    /**
     * actionCode
     */
    private String actionCode;
        
        
    /**
     * contractRequest
     */
    private ContractRequestDTO contractRequest;
        
        
    /**
     * requestor
     */
    private RequestorDTO requestor;
    
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
    public CreateUpdateRoleContractRequestDTO() {
    //empty constructor
    }
        
    /**
	 * @return the gin
	 */
	public String getGin() {
		return gin;
	}

	/**
	 * @param gin the gin to set
	 */
	public void setGin(String gin) {
		this.gin = gin;
	}

	/**
	 * @return the actionCode
	 */
	public String getActionCode() {
		return actionCode;
	}

	/**
	 * @param actionCode the actionCode to set
	 */
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	/**
	 * @return the contractRequest
	 */
	public ContractRequestDTO getContractRequest() {
		return contractRequest;
	}

	/**
	 * @param contractRequest the contractRequest to set
	 */
	public void setContractRequest(ContractRequestDTO contractRequest) {
		this.contractRequest = contractRequest;
	}

	/**
	 * @return the requestor
	 */
	public RequestorDTO getRequestor() {
		return requestor;
	}

	/**
	 * @param requestor the requestor to set
	 */
	public void setRequestor(RequestorDTO requestor) {
		this.requestor = requestor;
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
        buffer.append("gin=").append(getGin());
        buffer.append(",");
        buffer.append("actionCode=").append(getActionCode());
        buffer.append(",");
        buffer.append("contractRequest=").append(getContractRequest());
        buffer.append(",");
        buffer.append("requestor=").append(getRequestor());
        buffer.append("]");
        return buffer.toString();
    }

}
