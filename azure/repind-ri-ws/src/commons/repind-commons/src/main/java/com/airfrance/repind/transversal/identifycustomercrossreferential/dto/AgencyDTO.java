package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

import java.util.List;

/**
 * IdentifyCustomerCrossReferential - Agency DTO
 * @author t950700
 *
 */
public class AgencyDTO implements Comparable<AgencyDTO>
{

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private int relevance;
	private AgencyInformationsDTO agencyInformations;
	private List<TelecomAgencyDTO> telecomAgency;
	private PostalAddressAgencyDTO postalAddressAgency;
	private List<EmailAgencyDTO> emailAgency;
	private List<MemberAgencyDTO> memberAgency;
	private List<CommercialZonesAgencyDTO> commercialZonesAgency;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public AgencyDTO() {
		super();
	}
	
	
	public AgencyDTO(int relevance, AgencyInformationsDTO agencyInformations,
                     List<TelecomAgencyDTO> telecomAgency,
                     PostalAddressAgencyDTO postalAddressAgency,
                     List<EmailAgencyDTO> emailAgency, List<MemberAgencyDTO> memberAgency,
                     List<CommercialZonesAgencyDTO> commercialZonesAgency) {
		super();
		this.relevance = relevance;
		this.agencyInformations = agencyInformations;
		this.telecomAgency = telecomAgency;
		this.postalAddressAgency = postalAddressAgency;
		this.emailAgency = emailAgency;
		this.memberAgency = memberAgency;
		this.setCommercialZonesAgency(commercialZonesAgency);
	}
	
	
	/*==========================================*/
	/*                                          */
	/*           COMPARABLE INTERFACE           */
	/*                 METHOD                   */
	/*==========================================*/
	
	
	public int compareTo(AgencyDTO other) 
	{
		int result = 0;
		
		if(other != null)
		{
			if((this.getAgencyInformations() != null)
					&&	(other.getAgencyInformations() != null)
					&&	(this.getAgencyInformations().getAgencyKey() != null)
					&&	(other.getAgencyInformations().getAgencyKey() != null))
			{
				result = this.getAgencyInformations().getAgencyKey().compareTo(other.getAgencyInformations().getAgencyKey());
			}
		}
		return result;
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
	
	
	public AgencyInformationsDTO getAgencyInformations() {
		return agencyInformations;
	}
	
	
	public void setAgencyInformations(AgencyInformationsDTO agencyInformations) {
		this.agencyInformations = agencyInformations;
	}
	
	
	public List<TelecomAgencyDTO> getTelecomAgency() {
		return telecomAgency;
	}
	
	
	public void setTelecomAgency(List<TelecomAgencyDTO> telecomAgency) {
		this.telecomAgency = telecomAgency;
	}
	
	
	public PostalAddressAgencyDTO getPostalAddressAgency() {
		return postalAddressAgency;
	}
	
	
	public void setPostalAddressAgency(PostalAddressAgencyDTO postalAddressAgency) {
		this.postalAddressAgency = postalAddressAgency;
	}
	
	
	public List<EmailAgencyDTO> getEmailAgency() {
		return emailAgency;
	}
	
	
	public void setEmailAgency(List<EmailAgencyDTO> emailAgency) {
		this.emailAgency = emailAgency;
	}
	
	
	public List<MemberAgencyDTO> getMemberAgency() {
		return memberAgency;
	}
	
	
	public void setMemberAgency(List<MemberAgencyDTO> memberAgency) {
		this.memberAgency = memberAgency;
	}


	public List<CommercialZonesAgencyDTO> getCommercialZonesAgency() {
		return commercialZonesAgency;
	}


	public void setCommercialZonesAgency(List<CommercialZonesAgencyDTO> commercialZonesAgency) {
		this.commercialZonesAgency = commercialZonesAgency;
	}
}
