package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

import java.util.Date;

/**
 * IdentifyCustomerCrossReferential -  Request's IndividualIdentity DTO
 * @author t950700
 *
 */
public class IndividualIdentityDTO {
	
	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private String title;
	private String lastName;
	private String lastNameSearchType;
	private String firstName;
	private String firstNameSearchType;
	private Date birthDate;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public IndividualIdentityDTO() {
		super();
	}
	
	public IndividualIdentityDTO(String title, String lastName,
			String firstName, Date birthDate) {
		super();
		this.title = title;
		this.lastName = lastName;
		this.firstName = firstName;
		this.birthDate = birthDate;
	}
	
	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getLastNameSearchType() {
		return lastNameSearchType;
	}

	public void setLastNameSearchType(String lastNameSearchType) {
		this.lastNameSearchType = lastNameSearchType;
	}

	public String getFirstNameSearchType() {
		return firstNameSearchType;
	}

	public void setFirstNameSearchType(String firstNameSearchType) {
		this.firstNameSearchType = firstNameSearchType;
	}
	
	

}
