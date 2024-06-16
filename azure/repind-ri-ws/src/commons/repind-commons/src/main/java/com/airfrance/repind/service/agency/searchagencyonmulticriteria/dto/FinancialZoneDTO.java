package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto;

public class FinancialZoneDTO {

	private String zoneGeo;
	private String cityCode;
	private String ufCode;
	private String countryCode;
	
	public FinancialZoneDTO() {
		super();
	}
	
	public FinancialZoneDTO(String zoneGeo, String cityCode, String ufCode, String countryCode) {
		super();
		this.zoneGeo = zoneGeo;
		this.cityCode = cityCode;
		this.ufCode = ufCode;
		this.countryCode = countryCode;
	}

	public String getZoneGeo() {
		return zoneGeo;
	}

	public void setZoneGeo(String zoneGeo) {
		this.zoneGeo = zoneGeo;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getUfCode() {
		return ufCode;
	}

	public void setUfCode(String ufCode) {
		this.ufCode = ufCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
