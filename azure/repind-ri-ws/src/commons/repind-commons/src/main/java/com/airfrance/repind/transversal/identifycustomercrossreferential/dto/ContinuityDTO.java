package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

/**
 * IdentifyCustomerCrossReferential response's Continuity DTO
 * @author t950700
 *
 */
public class ContinuityDTO {

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private Long totalNumber;
	private int firstIndex;
	private int lastIndex;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public ContinuityDTO() {
		super();
	}
	public ContinuityDTO(Long totalNumber, int firstIndex, int lastIndex) {
		super();
		this.totalNumber = totalNumber;
		this.firstIndex = firstIndex;
		this.lastIndex = lastIndex;
	}
	
	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	public Long getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(Long totalNumber) {
		this.totalNumber = totalNumber;
	}
	public int getFirstIndex() {
		return firstIndex;
	}
	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}
	public int getLastIndex() {
		return lastIndex;
	}
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}
	
	
}
