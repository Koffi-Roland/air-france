package com.afklm.rigui.model.individual;

import java.util.Set;

import com.afklm.rigui.model.resources.AbstractResourceModel;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author t528182
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelAddress extends AbstractResourceModel {

	private String identifiant;
	private String version;
	private String type;
	private String status;
	private String corporateName;
	private String addressComplement;
	private String numberAndStreet;
	private String locality;
	private String zipCode;
	private String city;
	private String state;
	private String country;
	private String forced;
	private ModelSignature signature;
	private Set<ModelUsageMedium> usages;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getIdentifiant() {
		return identifiant;
	}
	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
	public String getCorporateName() {
		return corporateName;
	}
	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}
	public String getAddressComplement() {
		return addressComplement;
	}
	public void setAddressComplement(String addressComplement) {
		this.addressComplement = addressComplement;
	}
	public String getNumberAndStreet() {
		return numberAndStreet;
	}
	public void setNumberAndStreet(String numberAndStreet) {
		this.numberAndStreet = numberAndStreet;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getForced() {
		return forced;
	}
	public void setForced(String forced) {
		this.forced = forced;
	}
	public Set<ModelUsageMedium> getUsages() {
		return usages;
	}
	public void setUsages(Set<ModelUsageMedium> usages) {
		this.usages = usages;
	}
	public ModelSignature getSignature() {
		return signature;
	}
	public void setSignature(ModelSignature signature) {
		this.signature = signature;
	}
	

}
