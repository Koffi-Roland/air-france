package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

/**
 * IndetifyCustomerCrossrefrential - CustomerDTO
 * @author t950700
 *
 */
public class CustomerDTO {

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private IndividualDTO individual;
	private CorporateDTO corporate;

	private AgencyDTO agency;

	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public CustomerDTO() {
		super();
	}
	

	
	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	
	public IndividualDTO getIndividual() {
		return individual;
	}
	
	
	public void setIndividual(IndividualDTO individual) {
		this.individual = individual;
	}
	
	
	public CorporateDTO getCorporate() {
		return corporate;
	}
	
	
	public void setCorporate(CorporateDTO corporate) {
		this.corporate = corporate;
	}

	public AgencyDTO getAgency() {
		return agency;
	}


	public void setAgency(AgencyDTO agency) {
		this.agency = agency;
	}


}
