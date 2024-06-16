package com.airfrance.repind.dto.ws.createupdaterolecontract.v1;


import com.airfrance.repind.dto.individu.ContractDataDTO;
import com.airfrance.repind.dto.individu.ContractV2DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Title : ContractRequestDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2018</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class ContractRequestDTO implements Serializable {

    /**
     * contractData
     */
    private List<ContractDataDTO> contractData;
        
        
    /**
     * contract
     */
    private ContractV2DTO contract;
        
    
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
    public ContractRequestDTO() {
    	this.contractData = new ArrayList<ContractDataDTO>();
    }
     
	/**
	 * @return the contractData
	 */
	public List<ContractDataDTO> getContractData() {
		return contractData;
	}

	/**
	 * @param contractData the contractData to set
	 */
	public void setContractData(List<ContractDataDTO> contractData) {
		this.contractData = contractData;
	}

	/**
	 * @return the contract
	 */
	public ContractV2DTO getContract() {
		return contract;
	}

	/**
	 * @param contract the contract to set
	 */
	public void setContract(ContractV2DTO contract) {
		this.contract = contract;
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
        buffer.append("contractData=").append(getContractData());
        buffer.append(",");
        buffer.append("contract=").append(getContract());
        buffer.append("]");
        return buffer.toString();
    }

}
