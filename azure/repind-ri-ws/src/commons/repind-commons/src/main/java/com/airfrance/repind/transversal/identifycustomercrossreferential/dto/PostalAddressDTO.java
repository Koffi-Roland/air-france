package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

/**
 * Input postal address DTO
 * @author t950700
 *
 */
public class PostalAddressDTO {

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private String numberAndStreet;
	private String city;
	private String zipCode;
	private String countryCode;
	private String citySearchType;
	private String zipCodeSearchType;
	private String additionalInformations;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public PostalAddressDTO() {
		super();

	}

	public PostalAddressDTO(String numberAndStreet, String city,
			String zipCode, String countryCode, String citySearchType,
			String zipCodeSearchType, String additionalInformations) {
		super();
		this.numberAndStreet = numberAndStreet;
		this.city = city;
		this.zipCode = zipCode;
		this.countryCode = countryCode;
		this.citySearchType = citySearchType;
		this.zipCodeSearchType = zipCodeSearchType;
		this.additionalInformations = additionalInformations;
	}

	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	
	public String getNumberAndStreet() {
		return numberAndStreet;
	}

	public void setNumberAndStreet(String numberAndStreet) {
		this.numberAndStreet = numberAndStreet;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getCitySearchType() {
		return citySearchType;
	}

	public void setCitySearchType(String citySearchType) {
		this.citySearchType = citySearchType;
	}

	public String getZipCodeSearchType() {
		return zipCodeSearchType;
	}

	public void setZipCodeSearchType(String zipCodeSearchType) {
		this.zipCodeSearchType = zipCodeSearchType;
	}

	public String getAdditionalInformations() {
		return additionalInformations;
	}

	public void setAdditionalInformations(String additionalInformations) {
		this.additionalInformations = additionalInformations;
	}
	
	
	
	
}
