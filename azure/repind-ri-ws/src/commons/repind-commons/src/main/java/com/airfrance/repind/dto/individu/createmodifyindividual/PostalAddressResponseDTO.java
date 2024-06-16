package com.airfrance.repind.dto.individu.createmodifyindividual;

/*PROTECTED REGION ID(_nV_M5jRkEeCc7ZsKsK1lbQ i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>Title : PostalAddressDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2017</p>
 * <p>Company : AIRFRANCE</p>
 */
public class PostalAddressResponseDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -3803489275418717427L;

	private List<UsageAddressDTO> usageAddress;
	private SoftComputingResponseDTO softComputingResponse;
	private List<SignatureDTO> signature;
	private PostalAddressPropertiesDTO postalAddressProperties;
	private PostalAddressContentDTO postalAddressContent;
	
	

    /*PROTECTED REGION ID(_nV_M5jRkEeCc7ZsKsK1lbQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public PostalAddressResponseDTO() {
    
    }
    

    
    

    public List<UsageAddressDTO> getUsageAddress() {
		return usageAddress;
	}





	public void setUsageAddress(List<UsageAddressDTO> usageAddress) {
		this.usageAddress = usageAddress;
	}





	public SoftComputingResponseDTO getSoftComputingResponse() {
		return softComputingResponse;
	}





	public void setSoftComputingResponse(
			SoftComputingResponseDTO softComputingResponse) {
		this.softComputingResponse = softComputingResponse;
	}





	public List<SignatureDTO> getSignature() {
		return signature;
	}





	public void setSignature(List<SignatureDTO> signature) {
		this.signature = signature;
	}





	public PostalAddressPropertiesDTO getPostalAddressProperties() {
		return postalAddressProperties;
	}





	public void setPostalAddressProperties(
			PostalAddressPropertiesDTO postalAddressProperties) {
		this.postalAddressProperties = postalAddressProperties;
	}





	public PostalAddressContentDTO getPostalAddressContent() {
		return postalAddressContent;
	}





	public void setPostalAddressContent(PostalAddressContentDTO postalAddressContent) {
		this.postalAddressContent = postalAddressContent;
	}





	public static long getSerialversionuid() {
		return serialVersionUID;
	}





	@Override
	public String toString() {
		return "PostalAddressResponseDTO [usageAddress=" + usageAddress
				+ ", softComputingResponse=" + softComputingResponse
				+ ", signature=" + signature + ", postalAddressProperties="
				+ postalAddressProperties + ", postalAddressContent="
				+ postalAddressContent + "]";
	}


    /*PROTECTED REGION ID(_nV_M5jRkEeCc7ZsKsK1lbQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
