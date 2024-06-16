package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

import java.util.Date;

/**
 * IdentifyCustomerCrossReferntial - IndividualInformationsDTO
 * @author t950700
 *
 */
public class IndividualInformationsDTO {

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private String individualKey;
	private String civility;
	private String lastNameInternal;
	private String lastNameExternal;
	private String firstNameInternal;
	private String firstNameExternal;
	private Date birthDate;
	private String customerStatus;
	private String spokenLanguage;
	private String ginFusion;
	private String optinIndividual;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public IndividualInformationsDTO() {
		super();
		// TODO Module de remplacement de constructeur auto-généré
	}
	
	
	public IndividualInformationsDTO(String individualKey, String civility,
			String lastNameInternal, String lastNameExternal,
			String firstNameInternal, String firstNameExternal, Date birthDate,
			String customerStatus, String spokenLanguage, String optinIndividual) {
		super();
		this.individualKey = individualKey;
		this.civility = civility;
		this.lastNameInternal = lastNameInternal;
		this.lastNameExternal = lastNameExternal;
		this.firstNameInternal = firstNameInternal;
		this.firstNameExternal = firstNameExternal;
		this.birthDate = birthDate;
		this.customerStatus = customerStatus;
		this.spokenLanguage = spokenLanguage;
		this.optinIndividual = optinIndividual;
	}
	
	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	
	public String getIndividualKey() {
		return individualKey;
	}
	public void setIndividualKey(String individualKey) {
		this.individualKey = individualKey;
	}
	public String getCivility() {
		return civility;
	}
	public void setCivility(String civility) {
		this.civility = civility;
	}
	public String getLastNameInternal() {
		return lastNameInternal;
	}
	public void setLastNameInternal(String lastNameInternal) {
		this.lastNameInternal = lastNameInternal;
	}
	public String getLastNameExternal() {
		return lastNameExternal;
	}
	public void setLastNameExternal(String lastNameExternal) {
		this.lastNameExternal = lastNameExternal;
	}
	public String getFirstNameInternal() {
		return firstNameInternal;
	}
	public void setFirstNameInternal(String firstNameInternal) {
		this.firstNameInternal = firstNameInternal;
	}
	public String getFirstNameExternal() {
		return firstNameExternal;
	}
	public void setFirstNameExternal(String firstNameExternal) {
		this.firstNameExternal = firstNameExternal;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getCustomerStatus() {
		return customerStatus;
	}
	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}
	public String getSpokenLanguage() {
		return spokenLanguage;
	}
	public void setSpokenLanguage(String spokenLanguage) {
		this.spokenLanguage = spokenLanguage;
	}
	public String getGinFusion() {
		return ginFusion;
	}
	public void setGinFusion(String ginFusion) {
		this.ginFusion = ginFusion;
	}
	public String getOptinIndividual() {
		return optinIndividual;
	}
	public void setOptinIndividual(String optinIndividual) {
		this.optinIndividual = optinIndividual;
	}
}
