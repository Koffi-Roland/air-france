package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

import java.util.List;


/**
 * Ide,tifyCustomerCrossReferential - MemberAgencyDTO
 * @author t950700
 *
 */
public class MemberAgencyDTO {

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private String jobTitle;
	private List<EmailAgencyMemberDTO> emailAgencyMember;
	private List<TelecomAgencyMemberDTO> telecomAgencyMember;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public MemberAgencyDTO() {
		super();
	}
	
	
	public MemberAgencyDTO(String jobTitle,
			List<EmailAgencyMemberDTO> emailAgencyMember,
			List<TelecomAgencyMemberDTO> telecomAgencyMember) {
		super();
		this.jobTitle = jobTitle;
		this.emailAgencyMember = emailAgencyMember;
		this.telecomAgencyMember = telecomAgencyMember;
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
	public List<EmailAgencyMemberDTO> getEmailAgencyMember() {
		return emailAgencyMember;
	}
	public void setEmailAgencyMember(List<EmailAgencyMemberDTO> emailAgencyMember) {
		this.emailAgencyMember = emailAgencyMember;
	}
	public List<TelecomAgencyMemberDTO> getTelecomAgencyMember() {
		return telecomAgencyMember;
	}
	public void setTelecomAgencyMember(
			List<TelecomAgencyMemberDTO> telecomAgencyMember) {
		this.telecomAgencyMember = telecomAgencyMember;
	}
	
	
	
	
}
