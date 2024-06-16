package com.airfrance.repind.dto.individu.myaccountcustomerdata;

/*PROTECTED REGION ID(_FaPg8GoiEeKKIYA_vvLbUA i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.dto.individu.WarningDTO;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : MyAccountCustomerDataResponseV2DTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class MyAccountCustomerDataResponseV2DTO  {
        
    /**
     * socialNetworkId
     */
    private String socialNetworkId;
        
        
    /**
     * myAccountCustomerDataResponseDTO
     */
    private MyAccountCustomerDataResponseDTO myAccountCustomerDataResponseDTO;
        
        
    /**
     * warningDTO
     */
    private Set<WarningDTO> warningDTO;
        

    /*PROTECTED REGION ID(_FaPg8GoiEeKKIYA_vvLbUA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public MyAccountCustomerDataResponseV2DTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pSocialNetworkId socialNetworkId
     */
    public MyAccountCustomerDataResponseV2DTO(String pSocialNetworkId) {
        this.socialNetworkId = pSocialNetworkId;
    }

    /**
     *
     * @return myAccountCustomerDataResponseDTO
     */
    public MyAccountCustomerDataResponseDTO getMyAccountCustomerDataResponseDTO() {
        return this.myAccountCustomerDataResponseDTO;
    }

    /**
     *
     * @param pMyAccountCustomerDataResponseDTO myAccountCustomerDataResponseDTO value
     */
    public void setMyAccountCustomerDataResponseDTO(MyAccountCustomerDataResponseDTO pMyAccountCustomerDataResponseDTO) {
        this.myAccountCustomerDataResponseDTO = pMyAccountCustomerDataResponseDTO;
    }

    /**
     *
     * @return socialNetworkId
     */
    public String getSocialNetworkId() {
        return this.socialNetworkId;
    }

    /**
     *
     * @param pSocialNetworkId socialNetworkId value
     */
    public void setSocialNetworkId(String pSocialNetworkId) {
        this.socialNetworkId = pSocialNetworkId;
    }

    /**
     *
     * @return warningDTO
     */
    public Set<WarningDTO> getWarningDTO() {
        return this.warningDTO;
    }

    /**
     *
     * @param pWarningDTO warningDTO value
     */
    public void setWarningDTO(Set<WarningDTO> pWarningDTO) {
        this.warningDTO = pWarningDTO;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_FaPg8GoiEeKKIYA_vvLbUA) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }

    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        return new ToStringBuilder(this)
            .append("socialNetworkId", getSocialNetworkId())
            .toString();
    }

    /*PROTECTED REGION ID(_FaPg8GoiEeKKIYA_vvLbUA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
