package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

import java.util.List;

/**
 * IdentifyCustomerCrossReferential - MemberCorporateDTO
 * @author t950700
 *
 */
public class MemberCorporateDTO {

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private String jobTitle;
	private List<TelecomCorporateMemberDTO> telecomCorporateMember;
	private List<EmailCorporateMemberDTO> emailCorporateMember;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public MemberCorporateDTO() {
		super();
	}
	
	public MemberCorporateDTO(String jobTitle,
			List<TelecomCorporateMemberDTO> telecomCorporateMember,
			List<EmailCorporateMemberDTO> emailCorporateMember) {
		super();
		this.jobTitle = jobTitle;
		this.telecomCorporateMember = telecomCorporateMember;
		this.emailCorporateMember = emailCorporateMember;
	}
	
	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public List<TelecomCorporateMemberDTO> getTelecomCorporateMember() {
		return telecomCorporateMember;
	}
	public void setTelecomCorporateMember(
			List<TelecomCorporateMemberDTO> telecomCorporateMember) {
		this.telecomCorporateMember = telecomCorporateMember;
	}
	public List<EmailCorporateMemberDTO> getEmailCorporateMember() {
		return emailCorporateMember;
	}
	public void setEmailCorporateMember(
			List<EmailCorporateMemberDTO> emailCorporateMember) {
		this.emailCorporateMember = emailCorporateMember;
	}
	
	
}
