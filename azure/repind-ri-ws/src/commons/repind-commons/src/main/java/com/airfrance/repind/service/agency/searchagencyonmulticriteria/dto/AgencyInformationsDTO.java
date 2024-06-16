package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * AgencyInformations DTO
 * 
 * @author t950700
 *
 */
public class AgencyInformationsDTO {

	/* =============================================== */
	/* INSTANCE VARIABLES */
	/* =============================================== */

	private String agencyKey;
	private String agencyStatus;
	private String typeAgreement;
	private String agreementNumber;
	private String phoneNumber;
	private String email;
	private String agencyType;
	private String agencyName;
	private String commercialZone;
	private PostalAddressBlocDTO postalAddressBloc;
	private String agencyUsualName;
	private String agencyCommercialName;
	private List<DecoupZoneDTO> decoupZones;
	private List<IdentificationDTO> identityBloc;

	/* =============================================== */
	/* CONSTRUCTORS */
	/* =============================================== */
	
	public AgencyInformationsDTO() {
		super();
	}

	public AgencyInformationsDTO(String agencyKey, String agencyStatus, String phoneNumber, String email,
			String agencyType, String agencyName, String commercialZone,
			PostalAddressBlocDTO postalAddressBloc, String agencyUsualName,
			String agencyCommercialName, List<DecoupZoneDTO> decoupZones, List<IdentificationDTO> identityBloc) {
		super();
		this.agencyKey = agencyKey;
		this.agencyStatus = agencyStatus;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.agencyType = agencyType;
		this.agencyName = agencyName;
		this.commercialZone = commercialZone;
		this.postalAddressBloc = postalAddressBloc;
		this.agencyUsualName = agencyUsualName;
		this.agencyCommercialName = agencyCommercialName;
		this.decoupZones = decoupZones;
		this.identityBloc = identityBloc;
	}

	/* =============================================== */
	/* ACCESSORS */
	/* =============================================== */

	public String getAgencyKey() {
		return agencyKey;
	}

	public void setAgencyKey(String agencyKey) {
		this.agencyKey = agencyKey;
	}

	public String getAgencyStatus() {
		return agencyStatus;
	}

	public void setAgencyStatus(String agencyStatus) {
		this.agencyStatus = agencyStatus;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAgencyType() {
		return agencyType;
	}

	public void setAgencyType(String agencyType) {
		this.agencyType = agencyType;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getCommercialZone() {
		return commercialZone;
	}

	public void setCommercialZone(String commercialZone) {
		this.commercialZone = commercialZone;
	}

	public PostalAddressBlocDTO getPostalAddressBloc() {
		return postalAddressBloc;
	}

	public void setPostalAddressBloc(PostalAddressBlocDTO postalAddressBloc) {
		this.postalAddressBloc = postalAddressBloc;
	}

	public String getAgreementNumber() {
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}

	public String getTypeAgreement() {
		return typeAgreement;
	}

	public void setTypeAgreement(String typeAgreement) {
		this.typeAgreement = typeAgreement;
	}

	public String getAgencyUsualName() {
		return agencyUsualName;
	}

	public void setAgencyUsualName(String agencyUsualName) {
		this.agencyUsualName = agencyUsualName;
	}
	
	public String getAgencyCommercialName() {
		return agencyCommercialName;
	}

	public void setAgencyCommercialName(String agencyCommercialName) {
		this.agencyCommercialName = agencyCommercialName;
	}
	
	public List<DecoupZoneDTO> getDecoupZones() {
		if (decoupZones == null)
			this.decoupZones = new ArrayList<DecoupZoneDTO>();
		
		return decoupZones;
	}

	public void setDecoupZones(List<DecoupZoneDTO> decoupZones) {
		this.decoupZones = decoupZones;
	}
	
	public void addDecoupZone(DecoupZoneDTO decoupZone) {
		this.getDecoupZones().add(decoupZone);
	}
	
	public List<IdentificationDTO> getIdentityBloc() {
		if (identityBloc == null)
			this.identityBloc = new ArrayList<IdentificationDTO>();
		
		return identityBloc;
	}

	public void setIdentityBloc(List<IdentificationDTO> identBloc) {
		this.identityBloc = identBloc;
	}

	
}
