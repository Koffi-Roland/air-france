package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

import java.util.List;

/**
 * IdentifyCustomerCrossReferential - IndividualDTO
 * @author t950700
 *
 */
public class IndividualDTO {

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	
	private int relevance;
	private IndividualInformationsDTO individualInformations;
	private List<PostalAddressIndividualDTO> postalAddressIndividual;
	private List<ContractIndividualDTO> contractIndividual;
	private List<EmailIndividualDTO> emailIndividual;
	private List<TelecomIndividualDTO> telecomIndividual;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public IndividualDTO() {
		super();
		// TODO Module de remplacement de constructeur auto-généré
	}
	
	
	public IndividualDTO(int relevance,
			IndividualInformationsDTO individualInformations,
			List<PostalAddressIndividualDTO> postalAddressIndividual,
			List<ContractIndividualDTO> contractIndividual,
			List<EmailIndividualDTO> emailIndividual,
			List<TelecomIndividualDTO> telecomIndividual) {
		super();
		this.relevance = relevance;
		this.individualInformations = individualInformations;
		this.postalAddressIndividual = postalAddressIndividual;
		this.contractIndividual = contractIndividual;
		this.emailIndividual = emailIndividual;
		this.telecomIndividual = telecomIndividual;
	}
	
	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	
	public int getRelevance() {
		return relevance;
	}
	public void setRelevance(int relevance) {
		this.relevance = relevance;
	}
	public IndividualInformationsDTO getIndividualInformations() {
		return individualInformations;
	}
	public void setIndividualInformations(
			IndividualInformationsDTO individualInformations) {
		this.individualInformations = individualInformations;
	}
	public List<PostalAddressIndividualDTO> getPostalAddressIndividual() {
		return postalAddressIndividual;
	}
	public void setPostalAddressIndividual(
			List<PostalAddressIndividualDTO> postalAddressIndividual) {
		this.postalAddressIndividual = postalAddressIndividual;
	}
	public List<ContractIndividualDTO> getContractIndividual() {
		return contractIndividual;
	}
	public void setContractIndividual(List<ContractIndividualDTO> contractIndividual) {
		this.contractIndividual = contractIndividual;
	}
	public List<EmailIndividualDTO> getEmailIndividual() {
		return emailIndividual;
	}
	public void setEmailIndividual(List<EmailIndividualDTO> emailIndividual) {
		this.emailIndividual = emailIndividual;
	}
	public List<TelecomIndividualDTO> getTelecomIndividual() {
		return telecomIndividual;
	}
	public void setTelecomIndividual(List<TelecomIndividualDTO> telecomIndividual) {
		this.telecomIndividual = telecomIndividual;
	}
	
	
	
}
