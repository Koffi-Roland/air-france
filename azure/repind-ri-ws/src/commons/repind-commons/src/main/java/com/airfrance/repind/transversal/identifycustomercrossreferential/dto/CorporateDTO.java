package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

import java.util.List;

/**
 * IndetifyCustomerCrossReferential - CorporateDTO
 * @author t950700
 *
 */
public class CorporateDTO implements Comparable<CorporateDTO>
{

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private int relevance;
	private CorporateInformationsDTO corporateInformations;
	private CommercialZonesCorporateDTO commercialZonesCorporate;
	private PostalAddressCorporateDTO postalAddressCorporate;
	private List<EmailCorporateDTO> emailCorporate;
	private List<TelecomCorporateDTO> telecomCorporate;
	private List<MemberCorporateDTO> memberCorporate;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public CorporateDTO() {
		super();
	}
	
	public CorporateDTO(int relevance,
			CorporateInformationsDTO corporateInformations,
			PostalAddressCorporateDTO postalAddressCorporate,
			List<EmailCorporateDTO> emailCorporate,
			List<TelecomCorporateDTO> telecomCorporate,
			List<MemberCorporateDTO> memberCorporate) {
		super();
		this.relevance = relevance;
		this.corporateInformations = corporateInformations;
		this.postalAddressCorporate = postalAddressCorporate;
		this.emailCorporate = emailCorporate;
		this.telecomCorporate = telecomCorporate;
		this.memberCorporate = memberCorporate;
	}
	
	/*==========================================*/
	/*                                          */
	/*           COMPARABLE INTERFACE           */
	/*                 METHOD                   */
	/*==========================================*/
	
	/**
	 * Comparable method
	 */
	public int compareTo(CorporateDTO other) {
		int result = 0;
		
		if(other != null)
		{
			if((this.getCorporateInformations() != null)
					&&	(other.getCorporateInformations() != null)
					&&	(this.getCorporateInformations().getCorporateKey() != null)
					&&	(other.getCorporateInformations().getCorporateKey() != null))
			{
				result = this.getCorporateInformations().getCorporateKey().compareTo(other.getCorporateInformations().getCorporateKey());
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
	public CorporateInformationsDTO getCorporateInformations() {
		return corporateInformations;
	}
	public void setCorporateInformations(
			CorporateInformationsDTO corporateInformations) {
		this.corporateInformations = corporateInformations;
	}
	public CommercialZonesCorporateDTO getCommercialZonesCorporate() {
		return commercialZonesCorporate;
	}
	public void setCommercialZonesCorporate(
			CommercialZonesCorporateDTO commercialZonesCorporate) {
		this.commercialZonesCorporate = commercialZonesCorporate;
	}
	public PostalAddressCorporateDTO getPostalAddressCorporate() {
		return postalAddressCorporate;
	}
	public void setPostalAddressCorporate(
			PostalAddressCorporateDTO postalAddressCorporate) {
		this.postalAddressCorporate = postalAddressCorporate;
	}
	public List<EmailCorporateDTO> getEmailCorporate() {
		return emailCorporate;
	}
	public void setEmailCorporate(List<EmailCorporateDTO> emailCorporate) {
		this.emailCorporate = emailCorporate;
	}
	public List<TelecomCorporateDTO> getTelecomCorporate() {
		return telecomCorporate;
	}
	public void setTelecomCorporate(List<TelecomCorporateDTO> telecomCorporate) {
		this.telecomCorporate = telecomCorporate;
	}
	public List<MemberCorporateDTO> getMemberCorporate() {
		return memberCorporate;
	}
	public void setMemberCorporate(List<MemberCorporateDTO> memberCorporate) {
		this.memberCorporate = memberCorporate;
	}

	

	
	
	
}
