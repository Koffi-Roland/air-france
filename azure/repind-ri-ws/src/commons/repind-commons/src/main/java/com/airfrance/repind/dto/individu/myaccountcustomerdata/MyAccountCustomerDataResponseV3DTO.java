package com.airfrance.repind.dto.individu.myaccountcustomerdata;

/*PROTECTED REGION ID(_1GKCEAQqEeOQUdRdCaaNpA i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.individu.PrefilledNumbersDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : MyAccountCustomerDataResponseV3DTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class MyAccountCustomerDataResponseV3DTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -2787655682804649958L;


	/**
     * accountDelegationDataDTO
     */
    private Set<DelegationDataDTO> accountDelegationDataDTO;
        
        
    /**
     * myAccountCustomerDataResponseV2DTO
     */
    private MyAccountCustomerDataResponseV2DTO myAccountCustomerDataResponseV2DTO;
        
        
    /**
     * prefilledNumbersDTO
     */
    private List<PrefilledNumbersDTO> prefilledNumbersDTO;
        

    /*PROTECTED REGION ID(_1GKCEAQqEeOQUdRdCaaNpA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    
    /** 
     * full constructor
     */
    public MyAccountCustomerDataResponseV3DTO() {
    }

    /**
     *
     * @return accountDelegationDataDTO
     */
    public Set<DelegationDataDTO> getAccountDelegationDataDTO() {
        return this.accountDelegationDataDTO;
    }

    /**
     *
     * @param pAccountDelegationDataDTO accountDelegationDataDTO value
     */
    public void setAccountDelegationDataDTO(Set<DelegationDataDTO> pAccountDelegationDataDTO) {
        this.accountDelegationDataDTO = pAccountDelegationDataDTO;
    }

    /**
     *
     * @return myAccountCustomerDataResponseV2DTO
     */
    public MyAccountCustomerDataResponseV2DTO getMyAccountCustomerDataResponseV2DTO() {
        return this.myAccountCustomerDataResponseV2DTO;
    }

    /**
     *
     * @param pMyAccountCustomerDataResponseV2DTO myAccountCustomerDataResponseV2DTO value
     */
    public void setMyAccountCustomerDataResponseV2DTO(MyAccountCustomerDataResponseV2DTO pMyAccountCustomerDataResponseV2DTO) {
        this.myAccountCustomerDataResponseV2DTO = pMyAccountCustomerDataResponseV2DTO;
    }

    /**
     *
     * @return prefilledNumbersDTO
     */
    public List<PrefilledNumbersDTO> getPrefilledNumbersDTO() {
        return this.prefilledNumbersDTO;
    }

    /**
     *
     * @param pPrefilledNumbersDTO prefilledNumbersDTO value
     */
    public void setPrefilledNumbersDTO(List<PrefilledNumbersDTO> pPrefilledNumbersDTO) {
        this.prefilledNumbersDTO = pPrefilledNumbersDTO;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_1GKCEAQqEeOQUdRdCaaNpA) ENABLED START*/
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
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_1GKCEAQqEeOQUdRdCaaNpA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
