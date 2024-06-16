package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto;

public class SalesZonesDTO {

	private String zv0;
	private String zv1;
	private String zv2;
	private String zv3;
	private String zvAlpha;
	private String libZvAlpha;
	private String currencyCode;
	
	public SalesZonesDTO() {
		super();
	}

	public SalesZonesDTO(String zv0, String zv1, String zv2, String zv3, String zvAlpha, String libZvAlpha,
			String currencyCode) {
		super();
		this.zv0 = zv0;
		this.zv1 = zv1;
		this.zv2 = zv2;
		this.zv3 = zv3;
		this.zvAlpha = zvAlpha;
		this.libZvAlpha = libZvAlpha;
		this.currencyCode = currencyCode;
	}

	public String getZv0() {
		return zv0;
	}

	public void setZv0(String zv0) {
		this.zv0 = zv0;
	}

	public String getZv1() {
		return zv1;
	}

	public void setZv1(String zv1) {
		this.zv1 = zv1;
	}

	public String getZv2() {
		return zv2;
	}

	public void setZv2(String zv2) {
		this.zv2 = zv2;
	}

	public String getZv3() {
		return zv3;
	}

	public void setZv3(String zv3) {
		this.zv3 = zv3;
	}

	public String getZvAlpha() {
		return zvAlpha;
	}

	public void setZvAlpha(String zvAlpha) {
		this.zvAlpha = zvAlpha;
	}

	public String getLibZvAlpha() {
		return libZvAlpha;
	}

	public void setLibZvAlpha(String libZvAlpha) {
		this.libZvAlpha = libZvAlpha;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
}
