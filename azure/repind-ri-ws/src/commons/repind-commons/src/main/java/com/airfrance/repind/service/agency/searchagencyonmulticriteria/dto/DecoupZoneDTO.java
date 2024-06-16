package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto;

import java.util.Date;

public class DecoupZoneDTO {

	private String gin;
	private Date creationDate;
	private String status;
	private String type;
	private String subType;
	private String nature;
	
	private CommercialZonesDTO commercialZones;
	private FinancialZoneDTO financialZone;
	private SalesZonesDTO salesZones;
	
	public DecoupZoneDTO() {
		super();
	}

	public DecoupZoneDTO(String gin, Date creationDate, String status, String type, String subType, String nature,
			CommercialZonesDTO commercialZones, FinancialZoneDTO financialZone, SalesZonesDTO salesZones) {
		super();
		this.gin = gin;
		this.creationDate = creationDate;
		this.status = status;
		this.type = type;
		this.subType = subType;
		this.nature = nature;
		this.commercialZones = commercialZones;
		this.financialZone = financialZone;
		this.salesZones = salesZones;
	}

	public String getGin() {
		return gin;
	}

	public void setGin(String gin) {
		this.gin = gin;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public CommercialZonesDTO getCommercialZones() {
		return commercialZones;
	}

	public void setCommercialZones(CommercialZonesDTO commercialZones) {
		this.commercialZones = commercialZones;
	}

	public FinancialZoneDTO getFinancialZone() {
		return financialZone;
	}

	public void setFinancialZone(FinancialZoneDTO financialZone) {
		this.financialZone = financialZone;
	}

	public SalesZonesDTO getSalesZones() {
		return salesZones;
	}

	public void setSalesZones(SalesZonesDTO salesZones) {
		this.salesZones = salesZones;
	}
}
