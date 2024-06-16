package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

import java.util.List;

/**
 * IdentifyCustomerCrossReferential ResponseDTO
 * @author t950700
 *
 */
public class ResponseDTO {

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	
	private List<CustomerDTO> customers;
	private ContinuityDTO continuity;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public ResponseDTO() {
		super();
	}
	
	public ResponseDTO(List<CustomerDTO> customers, ContinuityDTO continuity) {
		super();
		this.customers = customers;
		this.continuity = continuity;
	}
	
	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	
	public List<CustomerDTO> getCustomers() {
		return customers;
	}
	public void setCustomers(List<CustomerDTO> customers) {
		this.customers = customers;
	}
	public ContinuityDTO getContinuity() {
		return continuity;
	}
	public void setContinuity(ContinuityDTO continuity) {
		this.continuity = continuity;
	}
	
	

}
