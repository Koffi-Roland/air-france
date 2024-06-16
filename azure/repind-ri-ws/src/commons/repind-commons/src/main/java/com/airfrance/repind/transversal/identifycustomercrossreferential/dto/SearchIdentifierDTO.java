package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

/**
 * IdentifyCustomerCrossReferential -  Request's searchIdentifier DTO
 * @author t950700
 *
 */
public class SearchIdentifierDTO {
	
	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private TelecomDTO telecom;
	private IndividualIdentityDTO individualIdentity;
	private EmailDTO email;
	private PostalAddressDTO postalAddress;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public SearchIdentifierDTO() {
		super();
	}
	
	public SearchIdentifierDTO(TelecomDTO telecom,
			IndividualIdentityDTO individualIdentity, EmailDTO email,PostalAddressDTO postalAddress) {
		super();
		this.telecom = telecom;
		this.individualIdentity = individualIdentity;
		this.email = email;
		this.postalAddress = postalAddress;
	}
	
	/*==========================================*/
	/*                                          */
	/*               ACCESSORS                  */
	/*                                          */
	/*==========================================*/
	
	public TelecomDTO getTelecom() {
		return telecom;
	}
	
	public void setTelecom(TelecomDTO telecom) {
		this.telecom = telecom;
	}
	
	public IndividualIdentityDTO getIndividualIdentity() {
		return individualIdentity;
	}
	
	public void setIndividualIdentity(IndividualIdentityDTO individualIdentity) {
		this.individualIdentity = individualIdentity;
	}
	
	public EmailDTO getEmail() {
		return email;
	}
	
	public void setEmail(EmailDTO email) {
		this.email = email;
	}
	
	public PostalAddressDTO getPostalAddress() {
		return postalAddress;
	}
	
	public void setPostalAddress(PostalAddressDTO postalAddress) {
		this.postalAddress = postalAddress;
	}
	
	
	
}
