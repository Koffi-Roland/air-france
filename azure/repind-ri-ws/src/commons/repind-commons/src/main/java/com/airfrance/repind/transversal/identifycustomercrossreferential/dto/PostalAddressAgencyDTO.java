package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

/**
 * IdentifyCustomerCrossReferential - PostalAddressAgencyDTO
 * @author t950700
 *
 */
public class PostalAddressAgencyDTO {

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private String mediumCode;
	private String mediumStatus;
	private String corporateName;
	private String city;
	private String additionalInformations;
	private String numberAndStreet;
	private String district;
	private String zipCode;
	private String countryCode;
	private String stateCode;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public PostalAddressAgencyDTO() {
		super();
	}
	
	
	public PostalAddressAgencyDTO(String mediumCode, String mediumStatus,
			String corporateName, String city, String additionalInformations,
			String numberAndStreet, String district, String zipCode,
			String countryCode, String stateCode) {
		super();
		this.mediumCode = mediumCode;
		this.mediumStatus = mediumStatus;
		this.corporateName = corporateName;
		this.city = city;
		this.additionalInformations = additionalInformations;
		this.numberAndStreet = numberAndStreet;
		this.district = district;
		this.zipCode = zipCode;
		this.countryCode = countryCode;
		this.stateCode = stateCode;
	}
	
	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	public String getMediumCode() {
		return mediumCode;
	}
	public void setMediumCode(String mediumCode) {
		this.mediumCode = mediumCode;
	}
	public String getMediumStatus() {
		return mediumStatus;
	}
	public void setMediumStatus(String mediumStatus) {
		this.mediumStatus = mediumStatus;
	}
	public String getCorporateName() {
		return corporateName;
	}
	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAdditionalInformations() {
		return additionalInformations;
	}
	public void setAdditionalInformations(String additionalInformations) {
		this.additionalInformations = additionalInformations;
	}
	public String getNumberAndStreet() {
		return numberAndStreet;
	}
	public void setNumberAndStreet(String numberAndStreet) {
		this.numberAndStreet = numberAndStreet;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	
	
	  
}
